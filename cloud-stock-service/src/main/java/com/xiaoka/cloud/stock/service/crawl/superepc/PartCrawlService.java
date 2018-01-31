package com.xiaoka.cloud.stock.service.crawl.superepc;

import com.google.common.collect.Lists;
import com.xiaoka.cloud.stock.core.epc.repo.CarModelPartRepo;
import com.xiaoka.cloud.stock.core.epc.repo.CarModelPicRepo;
import com.xiaoka.cloud.stock.core.epc.repo.CarModelRepo;
import com.xiaoka.cloud.stock.core.epc.repo.PartCodeRepo;
import com.xiaoka.cloud.stock.core.epc.repo.entity.CarModelEntity;
import com.xiaoka.cloud.stock.core.epc.repo.entity.CarModelPartEntity;
import com.xiaoka.cloud.stock.core.epc.repo.entity.CarModelPicEntity;
import com.xiaoka.cloud.stock.core.epc.repo.entity.PartCodeEntity;
import com.xiaoka.cloud.stock.service.core.util.RetryOperateUtil;
import com.xiaoka.cloud.stock.service.epc.CarModelPartService;
import com.xiaoka.cloud.stock.service.wrapper.superepc.SuperEPCAdaptationService;
import com.xiaoka.cloud.stock.service.wrapper.superepc.SuperEPCconstant;
import com.xiaoka.cloud.stock.service.wrapper.superepc.resp.GetPartsInfoResp;
import com.xiaoka.freework.utils.json.Jackson;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 该服务用户爬取车款下的所有原厂配件。
 * <p>
 * <pre>
 *     第一步：实现单个车款的配件数据爬取，参见方法 {@link #crawlModelPartByModelId(Integer)}, 该服务依赖车款对应信息（组成、子组成、分组成图片）已初始化完成。
 *     1). 根据model id从数据库 car_model_pic 中获取其对应的所有子组成配件图，参见方法 {@link CarModelPicRepo#selectGroupPicByModelId(Integer)}
 *     2). 通过接口3.3 {@link SuperEPCAdaptationService#getPartsInfo(Integer, List, String, List)} 获取车款下的配件信息
 *     3). 将车款下的配件信息存储到数据库 car_model_part 中。
 *
 *
 *     第二步：循环所有的car_model，全量爬取每个车款下的配件。车款较多，为合理利用资源，需要考虑多线程。 TODO
 *
 *     <B>注意：</B> 由于一个车款下的子组成图片大概有200～500张左右，每张图上的配件大约有10～20个左右，每个车款下的配件大约有2000～6000个左右，正时当前共有22467个车款，
 *     车款下的配件大约上亿，为了保证扩展性，车款下的配件会根据车款进行分表存储。分表存储的实现方案 TODO
 *
 *      <B>注意：</B> 如何避免已经爬过的车型数据重复爬取，优化的过程中考虑。TODO
 *
 * </pre>
 * <p>
 * Created by sabo on 21/11/2017.
 */
@Service
public class PartCrawlService {

	private static final Logger logger = LoggerFactory.getLogger(PartCrawlService.class);

	@Resource
	SuperEPCAdaptationService superEPCAdaptationService;

	@Resource
	CarModelRepo carModelRepo;

	@Resource
	CarModelPartRepo carModelPartRepo;

	@Resource
	CarModelPicRepo carModelPicRepo;

	@Resource
	PartCodeRepo partCodeRepo;

	@Resource
	CarModelPartService carModelPartService;


	public void crawlModelPartTask(Integer pageNumber, Integer pageSize) {
		int                  count              = carModelRepo.countDistinctModelId();
		List<CarModelEntity> carModelEntityList = carModelRepo.selectGroupByModelId(pageNumber, pageSize, null);
		if (carModelEntityList == null) {
			logger.info("No result found with pageNumber={}, pageSize={}", pageNumber, pageSize);
			return;
		}
		for (CarModelEntity carModelEntity : carModelEntityList) {//TODO 整个过程可以多线程
			crawlModelPartByModelId(carModelEntity.getModelId());
		}
	}

	public void crawlModelPartByModelId(Integer carModelId) {
		logger.info("Prepare craw car model part for carModelId=={} ", carModelId);
		List<CarModelPicEntity> carModelPicEntityList = carModelPicRepo.selectGroupPicByModelId(carModelId);
		List<String>            picNumList            = new ArrayList<>();
		for (CarModelPicEntity carModelPicEntity : carModelPicEntityList) {
			picNumList.add(carModelPicEntity.getPicNum());
		}
		if (CollectionUtils.isEmpty(picNumList)) {
			logger.info("No pic nums found with model_id={}", carModelId);
			return;
		}
		this.crawModelPartByModelIdAndPicNum(carModelId, picNumList);
	}

	public void crawModelPartByModeIdAndPartCode(Integer carModelId, String partCode) {
		List<CarModelPartEntity> partEntityList = this.getCarModelPartEntityList(carModelId, partCode);
		if (CollectionUtils.isNotEmpty(partEntityList)) {
			logger.info("车款id:{},零件号:{}本地存在数据不需要在调接口处理", carModelId, partCode);
			return;
		}
		List<GetPartsInfoResp> getPartsInfoRespList = superEPCAdaptationService.getPartsInfo(carModelId, null, partCode, null);
		if (CollectionUtils.isEmpty(getPartsInfoRespList)) {
			logger.info("No result found with model_id={} and partCode={} of URL_3_3", carModelId, partCode);
			return;
		}
		List<CarModelPartEntity> carModelPartEntityList = getPartsInfoRespList.stream().map(this::convertGetPartsInfoResp).collect(Collectors.toList());
		carModelPartRepo.batchInsert(carModelPartEntityList);
		logger.info("车款id:{},零件号:{}插入配件信息成功 ", partCode, carModelId);
		//保存配件编码信息
		insertPartCodes(carModelPartEntityList);
	}

	public List<CarModelPartEntity> crawModelPartByModelIdAndPicNum(Integer carModelId, List<String> picNumList) {
		List<GetPartsInfoResp> getPartsInfoRespList = batchGetpartsInfoByPicEntityList(carModelId, picNumList);
		if (CollectionUtils.isEmpty(getPartsInfoRespList)) {
			logger.info("No result found with model_id={} and picNumList={} of URL_3_3", carModelId, Jackson.mobile().writeValueAsString(picNumList));
			return null;
		}

		List<CarModelPartEntity> carModelPartEntityList = new ArrayList<>();
		for (GetPartsInfoResp getPartsInfoResp : getPartsInfoRespList) {
			carModelPartEntityList.add(convertGetPartsInfoResp(getPartsInfoResp));
		}
		//model_id, part_code, pic_num, pic_sequence
		List<CarModelPartEntity> insertList     = Lists.newArrayList();
		List<CarModelPartEntity> hasExistedList = carModelPartRepo.selectListByCodesAndModelId(carModelPartEntityList);
		if (CollectionUtils.isNotEmpty(hasExistedList)) {
			insertList.addAll(carModelPartEntityList.stream().filter(
					p -> hasExistedList.stream().noneMatch(
							x -> Objects.equals(x.getModelId(), p.getModelId())
									&& Objects.equals(x.getPartCode(), p.getPartCode())
									&& Objects.equals(x.getPicNum(), p.getPicNum())
									&& Objects.equals(x.getPicSequence(), p.getPicSequence())
									&& Objects.equals(x.getRemarkDetail(), p.getRemarkDetail())
									&& Objects.equals(x.getRemarkBrief(), p.getRemarkBrief())
					)
			).collect(Collectors.toList()));
		} else {
			insertList.addAll(carModelPartEntityList);
		}

		if (CollectionUtils.isNotEmpty(insertList)) {
			int result = carModelPartRepo.batchInsert(insertList);
			logger.info("Successfully batch insert car model part {} rows for carModelId=={}!!! ", result, carModelId);
		}

		//保存配件编码信息
		insertPartCodes(carModelPartEntityList);
		return carModelPartEntityList;
	}

	private List<GetPartsInfoResp> batchGetpartsInfoByPicEntityList(Integer carModelId, List<String> picNumList) {
		List<List<String>> partitionPicNumLists = Lists.partition(picNumList, SuperEPCconstant.PIC_NUM_SIZE_FOR_GET_PART);

		List<GetPartsInfoResp> getPartsInfoRespList = new ArrayList<>();
		for (List<String> partitionPicNumList : partitionPicNumLists) {
			getPartsInfoRespList.addAll(superEPCAdaptationService.getPartsInfo(carModelId, null, null, partitionPicNumList));
		}
		return getPartsInfoRespList;
	}

	private CarModelPartEntity convertGetPartsInfoResp(GetPartsInfoResp getPartsInfoResp) {
		CarModelPartEntity carModelPartEntity = new CarModelPartEntity();
		carModelPartEntity.setModelId(Integer.valueOf(getPartsInfoResp.getTid()));
		carModelPartEntity.setPartCode(StringUtils.deleteWhitespace(getPartsInfoResp.getKpsCode()));
		carModelPartEntity.setPartName(getPartsInfoResp.getKpsName());
		carModelPartEntity.setPerUseNum(getPartsInfoResp.getPerUseNum());
		carModelPartEntity.setPicName(getPartsInfoResp.getPicName());
		carModelPartEntity.setPicNum(getPartsInfoResp.getPicNum());
		carModelPartEntity.setPicPath(getPartsInfoResp.getPicPath());
		carModelPartEntity.setOriginalAssemblyName(getPartsInfoResp.getAssembly());
		carModelPartEntity.setOriginalSubAssemblyName(getPartsInfoResp.getSubAssembly());
		carModelPartEntity.setPicSequence(getPartsInfoResp.getPicSequence());
		carModelPartEntity.setRemarkBrief(getPartsInfoResp.getRemarkBrief());
		carModelPartEntity.setRemarkDetail(getPartsInfoResp.getRemarkDetail());
		carModelPartEntity.setStandardPartId(SuperEPCUtil.integerValueOf(getPartsInfoResp.getTimerId(), "getPartsInfoResp.getTimerId()", logger));
		carModelPartEntity.setAssemblyId(SuperEPCUtil.integerValueOf(getPartsInfoResp.getTimerAssemblyId(), "getPartsInfoResp.getTimerAssemblyId()", logger));
		carModelPartEntity.setAssemblyName(getPartsInfoResp.getTimerAssembly());
		carModelPartEntity.setSubAssemblyId(SuperEPCUtil.integerValueOf(getPartsInfoResp.getTimerSubAssemblyId(), "getPartsInfoResp.getSubAssembly()", logger));
		carModelPartEntity.setSubAssemblyName(getPartsInfoResp.getTimerSubAssembly());
		carModelPartEntity.setEpcNo(SuperEPCUtil.integerValueOf(getPartsInfoResp.getEpcNo(), "getPartsInfoResp.getEpcNo()", logger));
		carModelPartEntity.setIsValid(1);
		carModelPartEntity.setCreateBy(SuperEPCconstant.CREATE_BY);
		return carModelPartEntity;
	}

	public void insertPartCodes(List<CarModelPartEntity> carModelPartEntityList) {
		if (CollectionUtils.isEmpty(carModelPartEntityList)) {
			return;
		}

		List<String> allCodes = carModelPartEntityList.stream()
				.map(x -> StringUtils.deleteWhitespace(x.getPartCode()))
				.distinct()
				.collect(Collectors.toList());

		//build objects to insert
		List<PartCodeEntity> insertList = assembleInsertPartCodes(carModelPartEntityList, allCodes);

		//remove duplicate
		handleRemoveDuplicateCodes(insertList);

		if (CollectionUtils.isNotEmpty(insertList)) {
			try {
				partCodeRepo.insertList(insertList);
			} catch (DuplicateKeyException e) {
				RetryOperateUtil.doRetry(() -> retryInsertPartCodes(insertList), 3);
			}
		}
	}

	/**
	 * 重试执行插入数据
	 *
	 * @param allList
	 */
	public void retryInsertPartCodes(List<PartCodeEntity> allList) {
		handleRemoveDuplicateCodes(allList);
		if (CollectionUtils.isNotEmpty(allList)) {
			List<PartCodeEntity> insertPartCodes = Lists.newArrayList(allList);
			partCodeRepo.insertList(insertPartCodes);
		}
	}

	/**
	 * 组装待插入的数据
	 *
	 * @param carModelPartEntityList
	 * @param codes
	 * @return
	 */
	private List<PartCodeEntity> assembleInsertPartCodes(List<CarModelPartEntity> carModelPartEntityList, List<String> codes) {
		List<PartCodeEntity> insertList = Lists.newArrayList();
		codes.forEach(p -> {
			PartCodeEntity partCodeEntity = new PartCodeEntity();
			partCodeEntity.setPartCode(StringUtils.deleteWhitespace(p));
			CarModelPartEntity carModelPart = carModelPartEntityList.stream().filter(part -> StringUtils.deleteWhitespace(part.getPartCode()).equals(p))
					.findAny().orElse(null);
			if (null != carModelPart) {
				partCodeEntity.setPartName(carModelPart.getPartName());
			}
			insertList.add(partCodeEntity);
		});
		return insertList;
	}

	/**
	 * 去重或去除已存在的重复零件码
	 *
	 * @param list
	 * @return
	 */
	private void handleRemoveDuplicateCodes(List<PartCodeEntity> list) {
		if (CollectionUtils.isEmpty(list)) {
			return;
		}
		List<String>         codes     = list.stream().map(PartCodeEntity::getPartCode).distinct().collect(Collectors.toList());
		List<PartCodeEntity> existList = partCodeRepo.selectListByCodes(codes);

		list.removeIf(partCodeEntity -> CollectionUtils.isNotEmpty(existList) &&
				existList.stream().anyMatch(exist -> exist.getPartCode().equals(partCodeEntity.getPartCode())));
	}

	private List<CarModelPartEntity> getCarModelPartEntityList(Integer carModelId, String partCode) {
		CarModelPartEntity param = new CarModelPartEntity();
		param.setModelId(carModelId);

		if (StringUtils.isNotBlank(partCode)) {
			param.setPartCode(partCode);
		}
		param.setIsValid(1);
		return carModelPartRepo.select(param);
	}

}
