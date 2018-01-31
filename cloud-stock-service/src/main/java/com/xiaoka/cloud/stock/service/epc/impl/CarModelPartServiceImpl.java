package com.xiaoka.cloud.stock.service.epc.impl;

import com.google.common.collect.Lists;
import com.xiaoka.cloud.stock.core.crawl.repo.entity.ZeroSubGroupPartsEntity;
import com.xiaoka.cloud.stock.core.crawl.service.ZeroSubGroupPartsService;
import com.xiaoka.cloud.stock.core.epc.repo.StandardPartRepo;
import com.xiaoka.cloud.stock.core.epc.repo.entity.CarModelPartEntity;
import com.xiaoka.cloud.stock.core.epc.repo.entity.PartReplaceEntity;
import com.xiaoka.cloud.stock.core.epc.repo.entity.StandardPartEntity;
import com.xiaoka.cloud.stock.service.core.util.BeanUtils;
import com.xiaoka.cloud.stock.service.epc.CarModelPartService;
import com.xiaoka.cloud.stock.service.epc.SuperEpcService;
import com.xiaoka.cloud.stock.service.epc.dto.PartInfoDto;
import com.xiaoka.cloud.stock.service.epc.util.ImageUtil;
import com.xiaoka.cloud.stock.service.wrapper.superepc.resp.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.xiaoka.cloud.stock.service.wrapper.superepc.SuperEPCconstant.EPC_IMG_HOST;

/**
 * Do something
 *
 * @author gancao 2017/11/18 18:52
 * @see [相关类/方法]
 * @since [版本号]
 */
@Service
public class CarModelPartServiceImpl implements CarModelPartService {
	private Logger logger = LoggerFactory.getLogger(CarModelPartServiceImpl.class);

	@Resource
	private SuperEpcService superEpcService;
	@Resource
	private StandardPartRepo standardPartRepo;
	@Resource
	private ZeroSubGroupPartsService zeroSubGroupPartsService;


	@Override
	public List<PartInfoDto> getPartInfoDtoByNameList(Integer carModelId, List<String> partNameList) {
		List<PartInfoDto> partInfoDtoList = Lists.newArrayList();
		//根据名字获取标准配件id
		List<StandardPartEntity> entityList = standardPartRepo.getPartIdByNames(partNameList);
		if (CollectionUtils.isEmpty(entityList)) {
			return partInfoDtoList;
		}
		List<Integer> partIdList = entityList.stream().map(StandardPartEntity::getStandardPartId).distinct().collect(Collectors.toList());
		List<GetPartsInfoResp> partsInfoRespList = superEpcService.getPartsInfo(carModelId, partIdList, null, null);
		if (CollectionUtils.isNotEmpty(partsInfoRespList)) {
			partInfoDtoList.addAll(partsInfoRespList.stream().map(this :: transformGetPartsInfoResp).collect(Collectors.toList()));
		}
		return partInfoDtoList;
	}

	@Override
	public List<PartInfoDto> getPartInfoDtoList(Integer carModelId, String picNum, String picName, String partCode) {
		List<PartInfoDto> partInfoDtoList = Lists.newArrayList();
		//本地库中获取车型下该图号的配件列表
		/*List<CarModelPartEntity> partEntityList = carModelQueryService.getCarModelPartEntityList(carModelId, picNum, picName, partCode);
		if (CollectionUtils.isNotEmpty(partEntityList)) {//数据转换
			partInfoDtoList.addAll(partEntityList.stream().map(p -> transformCarModelPartEntity(p, null)).collect(Collectors.toList()));
		}*/
		if (CollectionUtils.isEmpty(partInfoDtoList)) {//本地车型配件库未找到数据走正时EPC接口获取
			logger.info("车型id:{},图号:{},零件号:{},本地未获取配件信息,走EPC接口查询", carModelId, picNum, partCode);
			partInfoDtoList = this.getPartInfoDtoListBySuperEPC(carModelId, picNum, picName, partCode);
		}
		this.handlerPartSort(partInfoDtoList);
		return partInfoDtoList;
	}

	@Override
	public String getPartPrice(String partCode, String brandName, String markName) {
		/*List<Part4sPriceEntity> part4sPriceEntityList = partQueryService.getPart4sPriceEntity(partCode, brandName, markName);//本地获取4S报价
		if (CollectionUtils.isEmpty(part4sPriceEntityList) && partCode.indexOf(" ") > 0){//4S店价格为空，且零件号带空格，去空格再取一次
			part4sPriceEntityList = partQueryService.getPart4sPriceEntity(partCode.replaceAll(" ", ""), brandName, markName);//本地获取4S报价
		}
		if (CollectionUtils.isNotEmpty(part4sPriceEntityList) && Objects.nonNull(part4sPriceEntityList.get(0).getPrice())) {
			return part4sPriceEntityList.get(0).getPrice().toString();
		}*/
		logger.info("零件号:{},品牌名:{},厂商:{},本地未获取到4S店价格信息,走EPC接口查询", partCode, brandName, markName);
		//走正时EPC3.7接口获取
		List<GetOePriceResp> oePriceRespList = superEpcService.getOEPrice(Collections.singletonList(partCode), brandName, markName);
		if (CollectionUtils.isNotEmpty(oePriceRespList)) {
			//消息通知更新零件的4S店价格
			//epcMessageNotifyService.notifyPart4SPriceUpdate(partCode);
			return oePriceRespList.get(0).getPrice();
		}else {
			logger.info("零件号:{}获取4S店价格调正时接口未获取到数据", partCode);
		}
		return null;
	}

	@Override
	public List<PartInfoDto> getPartReplaceList(String partCode, String brandName) {
		List<PartInfoDto> partInfoDtoList = Lists.newArrayList();
		/*List<PartReplaceEntity> partReplaceEntityList = partQueryService.getPartReplaceEntity(partCode, brandName);
		if (CollectionUtils.isNotEmpty(partReplaceEntityList)) {//本地替换件不为空
			partInfoDtoList.addAll(partReplaceEntityList.stream().map(this::transformPartReplaceEntity).collect(Collectors.toList()));
		}*/
		if (CollectionUtils.isEmpty(partInfoDtoList)) {//本地数据没有获取到替换件，走正时接口获取
			logger.info("零件号:{},品牌名:{},本地未获取零件替换件信息,走EPC接口查询", partCode, brandName);
			GetReplacePartsResp partsResp = superEpcService.getReplaceParts(null, partCode, null);//正时EPC3.9接口
			if (Objects.nonNull(partsResp) && CollectionUtils.isNotEmpty(partsResp.getList())) {
				//消息通知更新零件替换件
				//epcMessageNotifyService.notifyPartReplaceUpdate(partCode);
				partInfoDtoList = this.getPartInfoDtoListBySuperEPC(partsResp.getList());
			}else {
				logger.info("零件号:{}更新替换件调正时接口未获取到数据", partCode);
			}
		}
		return partInfoDtoList;
	}

	private List<PartInfoDto> getPartInfoDtoListBySuperEPC(Integer carModelId, String picNum, String picName, String partCode) {
		List<PartInfoDto> partInfoDtoList = Lists.newArrayList();
		//调EPC3.3接口查配件信息
		List<GetPartsInfoResp> respList = superEpcService.getPartsInfo(carModelId, null, partCode, Collections.singletonList(picNum));
		if (CollectionUtils.isNotEmpty(respList)) {
			if (StringUtils.isNotBlank(picName) && StringUtils.isNotBlank(picNum)){
				//图号名筛选,跟正时的展示逻辑不一样，这里我们以picName作为其中的筛选逻辑
				respList = respList.stream().filter(p -> p.getPicName().equals(picName)).collect(Collectors.toList());
			}
			Map<String, List<GetPartsInfoResp>> map = respList.stream().collect(Collectors.groupingBy(GetPartsInfoResp :: getKpsCode));
			map.forEach((k ,v) -> partInfoDtoList.add(this.transformGetPartsInfoResp(v.get(0))));
			//消息通知更新车型配件
			//epcMessageNotifyService.notifyCarModelPartUpdate(carModelId, picNum, partCode);
		}else {
			logger.info("车型id:{},图号:{},零件号:{}获取车型配件调正时接口未获取到数据", carModelId, picNum, partCode);
		}
		return partInfoDtoList;
	}

	private List<PartInfoDto> getPartInfoDtoListBySuperEPC(List<GetReplacePartsRespList> respList) {
		List<PartInfoDto> partInfoDtoList = Lists.newArrayList();
		respList.forEach(resp -> {
			if (CollectionUtils.isNotEmpty(resp.getResult())) {
				partInfoDtoList.addAll(resp.getResult().stream().map(p -> this.transformGetReplacePartsResp(p, resp.getType()))
				                           .collect(Collectors.toList()));//数据转换分原厂和品牌件
			}
		});
		return partInfoDtoList;
	}

	private PartInfoDto transformCarModelPartEntity(CarModelPartEntity entity, Map<Integer, List<StandardPartEntity>> map) {
		PartInfoDto partInfoDto = BeanUtils.transform(PartInfoDto.class, entity);
		partInfoDto.setRemark(entity.getRemarkDetail());
		partInfoDto.setPicUrl(ImageUtil.enCodeUrl(ImageUtil.enCodeUrl(EPC_IMG_HOST + entity.getPicPath())));
		if (Objects.nonNull(map) && !map.isEmpty()){
			//设置标准配件名称
			List<StandardPartEntity> standardPartEntityList = map.get(entity.getStandardPartId());
			if (CollectionUtils.isNotEmpty(standardPartEntityList)){
				partInfoDto.setPartName(standardPartEntityList.get(0).getStandardPartName());
			}
		}
		return partInfoDto;
	}

	private PartInfoDto transformGetPartsInfoResp(GetPartsInfoResp partsInfoResp) {
		PartInfoDto partInfoDto = new PartInfoDto();
		partInfoDto.setAssemblyName(partsInfoResp.getAssembly());//原厂总成
		partInfoDto.setSubAssemblyName(partsInfoResp.getSubAssembly());//原厂分总成
		partInfoDto.setPicNum(partsInfoResp.getPicNum());
		partInfoDto.setPicName(partsInfoResp.getPicName());
		partInfoDto.setPartName(partsInfoResp.getKpsName());//原厂配件名称
		partInfoDto.setPicUrl(partsInfoResp.getPicPath());
		partInfoDto.setPartCode(partsInfoResp.getKpsCode());//原厂零件号
		partInfoDto.setPicSequence(partsInfoResp.getPicSequence());
		partInfoDto.setPicUrl(ImageUtil.enCodeUrl(EPC_IMG_HOST + partsInfoResp.getPicPath()));
		return partInfoDto;
	}

	private PartInfoDto transformPartReplaceEntity(PartReplaceEntity entity) {
		PartInfoDto partInfoDto = new PartInfoDto();
		partInfoDto.setOemBrand(entity.getBrandName());
		partInfoDto.setPartCode(entity.getReplacePartCode());
		partInfoDto.setOemName(entity.getMakeName());
		if ("2".equals(entity.getType())){
			partInfoDto.setSource("原厂");
		}else if ("3".equals(entity.getType())){
			partInfoDto.setSource("品牌");
		}

		return partInfoDto;
	}

	private PartInfoDto transformGetReplacePartsResp(GetReplacePartsRespListResult result, String type) {
		PartInfoDto partInfoDto = new PartInfoDto();
		partInfoDto.setPartCode(result.getKpsCode());
		if ("2".equals(type)) {
			partInfoDto.setOemBrand(result.getCOemBrand());
			partInfoDto.setOemName(result.getCOemAbbrebiation());
			partInfoDto.setSource("原厂");
		} else if ("3".equals(type)) {
			partInfoDto.setOemBrand(result.getPartBrand());
			partInfoDto.setSource("品牌");
		}
		return partInfoDto;
	}

	private void handlerPartSort(List<PartInfoDto> partInfoDtoList){
		partInfoDtoList.forEach(dto ->{
			String sequence = dto.getPicSequence();
			if (StringUtils.isNotBlank(sequence)){
				try {
					if (sequence.startsWith("0") && StringUtils.isNumeric(sequence.substring(1))){
						dto.setSequence(Integer.valueOf(sequence.substring(1)));
					}else if (StringUtils.isNumeric(sequence)){
						dto.setSequence(Integer.valueOf(sequence));
					}
				}catch (Exception e){
					logger.error("sequence:{}类型转换错误", sequence);
				}
			}
		});
		Collections.sort(partInfoDtoList, (a, b) -> a.getSequence() - b.getSequence());
	}

	private List<PartInfoDto> getPartInfoDtoListByZero(String subGroupId){
		List<PartInfoDto> partInfoDtoList = Lists.newArrayList();
		ZeroSubGroupPartsEntity param = new ZeroSubGroupPartsEntity();
		param.setSubGroupId(Integer.valueOf(subGroupId));
		param.setIsValid(1);
		List<ZeroSubGroupPartsEntity> partsEntityList = zeroSubGroupPartsService.getListByAnd(param);
		if (CollectionUtils.isNotEmpty(partsEntityList)){
			partsEntityList.forEach(entity -> {
				PartInfoDto partInfoDto = new PartInfoDto();
				partInfoDto.setPicSequence(entity.getNum());
				partInfoDto.setPartCode(entity.getRealPid());
				partInfoDto.setPartName(entity.getLabelName());
				partInfoDtoList.add(partInfoDto);
			});
		}
		return partInfoDtoList;
	}
}
