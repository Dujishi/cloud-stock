package com.xiaoka.cloud.stock.service.epc.impl;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.xiaoka.cloud.stock.core.epc.CarModelQueryService;
import com.xiaoka.cloud.stock.core.epc.repo.StandardAssemblyRepo;
import com.xiaoka.cloud.stock.core.epc.repo.StandardSubAssemblyRepo;
import com.xiaoka.cloud.stock.core.epc.repo.entity.CarModelCategoryEntity;
import com.xiaoka.cloud.stock.core.epc.repo.entity.CarModelPicEntity;
import com.xiaoka.cloud.stock.core.epc.repo.entity.StandardAssemblyEntity;
import com.xiaoka.cloud.stock.service.epc.CarModelCategoryService;
import com.xiaoka.cloud.stock.service.epc.SuperEpcService;
import com.xiaoka.cloud.stock.service.epc.ZeroEpcService;
import com.xiaoka.cloud.stock.service.epc.constant.CategoryTypeEnum;
import com.xiaoka.cloud.stock.service.epc.dto.PartAssemblyDto;
import com.xiaoka.cloud.stock.service.epc.dto.PartCategoryDto;
import com.xiaoka.cloud.stock.service.epc.dto.PartSubAssemblyDto;
import com.xiaoka.cloud.stock.service.epc.util.ImageUtil;
import com.xiaoka.cloud.stock.service.supplier.dto.CloudSupplierUserDto;
import com.xiaoka.cloud.stock.service.wrapper.superepc.resp.GetAssemblyCatalogueResp;
import com.xiaoka.cloud.stock.service.wrapper.superepc.resp.GetPicInfoForAssemblyResp;
import com.xiaoka.cloud.stock.service.wrapper.superepc.resp.GetPicInfoResp;
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
 * @author gancao 2017/11/18 15:22
 * @see [相关类/方法]
 * @since [版本号]
 */
@Service
public class CarModelCategoryServiceImpl implements CarModelCategoryService {

	private static final Logger logger = LoggerFactory.getLogger(CarModelCategoryServiceImpl.class);
	private static final Joiner JOINER = Joiner.on(",").skipNulls();
	private static final Splitter SPLITTER = Splitter.on(",").trimResults();

	@Resource
	private StandardSubAssemblyRepo standardSubAssemblyRepo;
	@Resource
	private SuperEpcService superEpcService;
	@Resource
	private StandardAssemblyRepo standardAssemblyRepo;
	@Resource
	private CarModelQueryService carModelQueryService;
	@Resource
	private ZeroEpcService zeroEpcService;

	@Override
	public List<PartSubAssemblyDto> getCarSubAssemblyDtoList(Integer carModelId, String assemblyId, String assemblyName,String extraParam,  Integer type, CloudSupplierUserDto cloudSupplierUserDto) {
		List<PartSubAssemblyDto> partSubAssemblyDtoList = Lists.newArrayList();
		if (StringUtils.isNotBlank(extraParam) && Objects.equals(CategoryTypeEnum.零零汽分类.getType(), type)){
			return zeroEpcService.getPartSubAssemblyDtoByZeroGroup(extraParam, cloudSupplierUserDto);
		}
		List<String> assemblyIdList = SPLITTER.splitToList(assemblyId);
		assemblyIdList.forEach(id -> {
			if (Objects.equals(CategoryTypeEnum.正时分类.getType(), type)) {
				List<GetPicInfoForAssemblyResp> assemblyRespList = superEpcService.getPicInfoForAssembly(carModelId, type, null, Integer.valueOf(id));
				List<PartSubAssemblyDto> subAssemblyDtoList = this.getPartSubAssemblyDtoList(assemblyRespList);
				if (CollectionUtils.isNotEmpty(subAssemblyDtoList)) {
					partSubAssemblyDtoList.addAll(subAssemblyDtoList);
				}
			} else if (Objects.equals(CategoryTypeEnum.原厂分类.getType(), type)) {
				List<PartSubAssemblyDto> subAssemblyDtoList = null;
				if ("0".equals(id)){
					//查询原厂分类总成下面所有分总成
					List<GetPicInfoForAssemblyResp> assemblyRespList = superEpcService.getPicInfoForAssembly(carModelId, type, assemblyName, null);
					subAssemblyDtoList = this.getPartSubAssemblyDtoList(assemblyRespList);
				}else {
					List<GetPicInfoResp> getPicInfoRespList = superEpcService.getPicInfo(carModelId, type, assemblyName, id, null);
					subAssemblyDtoList = this.getPartSubAssemblyDtoListByOriginal(getPicInfoRespList, id);
				}
				if (CollectionUtils.isNotEmpty(subAssemblyDtoList)){
					partSubAssemblyDtoList.addAll(subAssemblyDtoList);
				}
			}
		});
		return partSubAssemblyDtoList;
	}

	@Override
	public List<PartAssemblyDto> getCarModelCategory(Integer carModelId, Integer categoryId, String categoryName, String extraParam, Integer type) {
		List<PartAssemblyDto> partAssemblyDtoList = Lists.newArrayList();
		if (Objects.equals(CategoryTypeEnum.零零汽分类.getType(), type)){
			partAssemblyDtoList.add(this.getPartAssemblyDto(extraParam, type));
			return partAssemblyDtoList;
		}
		//通过车型id和分类id本地获取该车型对应分类下的总成和分总成
		List<CarModelCategoryEntity> categoryEntityList = carModelQueryService
				.getCarModelCategoryEntityList(carModelId, categoryId, categoryName, type);
		if (CollectionUtils.isNotEmpty(categoryEntityList)) {//获取到本地车型总成和分总
			partAssemblyDtoList.addAll(getPartAssemblyDtoList(categoryEntityList));
		}
		if (CollectionUtils.isEmpty(partAssemblyDtoList)) {//本地未获取到数据走EPC接口获取
			logger.info("车型id:{},分类:{}查正时分类,本地未获取到车型分类数据,走EPC接口查询", carModelId, categoryId);
			partAssemblyDtoList.addAll(getPartAssemblyDtoListBySuperEPC(carModelId, categoryId, categoryName, type));
		}
		if (CollectionUtils.isNotEmpty(partAssemblyDtoList)) {
			//添加全部
			PartAssemblyDto partAssemblyDto = new PartAssemblyDto();
			partAssemblyDto.setType(type);
			partAssemblyDto.setAssemblyName("全部");
			partAssemblyDto.setAssemblyId(Objects.equals(1, type) ? JOINER.join(partAssemblyDtoList.stream().map(PartAssemblyDto::getAssemblyId).
					collect(Collectors.toList())) : "0");
			partAssemblyDtoList.add(0, partAssemblyDto);
		}
		return partAssemblyDtoList;
	}

	@Override
	public List<PartCategoryDto> getCarModelCategory(Integer carModelId) {
		List<PartCategoryDto> partCategoryDtoList = Lists.newArrayList();
		List<CarModelCategoryEntity> categoryEntityList = this.getRealCategoryEntityList(carModelId, null, null, null);//先查正时分类
		if (CollectionUtils.isNotEmpty(categoryEntityList)) {
			partCategoryDtoList = this.getPartCategoryDtoList(categoryEntityList, categoryEntityList.get(0).getType());
		}
		if (CollectionUtils.isEmpty(partCategoryDtoList)) {//本地查询为空走接口查询
			logger.info("车型id:{},本地未获取到车型分类数据,走EPC接口查询", carModelId);
			partCategoryDtoList = this.getPartCategoryDtoListBySuperEPC(carModelId, null);
		}
		this.handlerSort(partCategoryDtoList);
		return partCategoryDtoList;
	}

	private List<CarModelCategoryEntity> getRealCategoryEntityList(Integer carModelId, Integer categoryId, String categoryName, Integer type) {
		type = Objects.nonNull(type) ? type : 1;//默认正时分类
		List<CarModelCategoryEntity> categoryEntityList = carModelQueryService
				.getCarModelCategoryEntityList(carModelId, categoryId, categoryName, type);//先查某个分类
		if (CollectionUtils.isEmpty(categoryEntityList) && Objects.equals(1, type)) {//正时分类不存在则查原厂分类
			type = 0;
			categoryEntityList = carModelQueryService.getCarModelCategoryEntityList(carModelId, categoryId, categoryName, type);//再查原厂分类
		}
		return categoryEntityList;
	}

	private List<CarModelPicEntity> getCarModelPicEntityList(List<CarModelCategoryEntity> categoryEntityList, Integer carModelId, Integer type) {
		List<String> subAssemblyIdList = categoryEntityList.stream().map(CarModelCategoryEntity::getSubAssemblyName).collect(Collectors.toList());
		return carModelQueryService.getCarModelPicListByModelIdAndSubAssemblyName(carModelId, type, subAssemblyIdList);
	}

	/**
	 * a.正时分类
	 * 1.先通过车型id获取标准分类下的所有分总成
	 * 2.通过EPC3.1接口获取该车型的所有总成和分总集合
	 * 3.将正时的分总成和标准分类中的分总成一起取交集得到车型的该大类下的分总成
	 * 4.通过EPC3.2接口获取分总成下的图号信息并选择一个timer_id大于0的
	 * 5.通过EPC3.3接口获取timer_id配件的原厂分总成名称
	 * b.原厂分类
	 * 1.通过EPC3.1接口获取该车型的所有原厂总成和分总集合
	 * 2.将原厂分类的总成和参数传递进来的做筛选，得到当前选择的总成下面的所有分总成
	 * 3.通过EPC3.2接口获取分总成下的图号信息，因为是原厂分类，所以可以直接拿到分总成名称不需要再通过timer_id去取一次配件详情
	 *
	 * @param carModelId
	 * @param categoryId
	 * @return
	 */
	private List<PartAssemblyDto> getPartAssemblyDtoListBySuperEPC(Integer carModelId, Integer categoryId, String categoryName, Integer type) {
		List<PartAssemblyDto> partAssemblyDtoList = Lists.newArrayList();
		List<GetAssemblyCatalogueResp> resp = superEpcService.getAssemblyCatalogue(carModelId, type);//正时该车型分类
		if (CollectionUtils.isEmpty(resp)) {
			logger.info("车型id:{}调正时接口未获取到分类", carModelId);
			return partAssemblyDtoList;
		}
		if (Objects.equals(1, type)) {//正时分类,筛选当前大类
			List<Integer> subAssemblyIdList = standardSubAssemblyRepo.selectSusAssemblyIdByCategoryId(categoryId);//标准的子总成
			resp = resp.stream().filter(p -> subAssemblyIdList.contains(Integer.valueOf(p.getTimerSubAssemblyId()))).collect(Collectors.toList());
		} else if (Objects.equals(0, type)) {//原厂分类,筛选当前总成
			resp = resp.stream().filter(p -> categoryName.equals(p.getAssembly())).collect(Collectors.toList());
		}
		return getPartAssemblyDtoList(resp, type);
	}

	private List<PartAssemblyDto> getPartAssemblyDtoList(List<CarModelCategoryEntity> categoryEntityList) {
		List<PartAssemblyDto> partAssemblyDtoList = Lists.newArrayList();
		Map<Integer, List<CarModelCategoryEntity>> categoryMap = categoryEntityList.stream().collect(
				Collectors.groupingBy(CarModelCategoryEntity::getAssemblyId));//以总成纬度展示
		categoryMap.forEach((k, v) -> {
			CarModelCategoryEntity entity = v.get(0);
			PartAssemblyDto partAssemblyDto = new PartAssemblyDto();
			if (Objects.equals(1, entity.getType())) {
				partAssemblyDto.setAssemblyName(entity.getAssemblyName());
				partAssemblyDto.setAssemblyId(entity.getAssemblyId().toString());
			} else if (Objects.equals(0, entity.getType())) {
				partAssemblyDto.setAssemblyName(entity.getSubAssemblyName());
				partAssemblyDto.setAssemblyId(entity.getSubAssemblyName());
			}
			partAssemblyDtoList.add(partAssemblyDto);
		});
		return partAssemblyDtoList;
	}

	private List<PartAssemblyDto> getPartAssemblyDtoList(List<GetAssemblyCatalogueResp> resp, Integer type) {
		List<PartAssemblyDto> partAssemblyDtoList = Lists.newArrayList();
		//总成下的分总成
		if (Objects.equals(1, type)) {
			Map<String, List<GetAssemblyCatalogueResp>> map = resp.stream().collect(Collectors.groupingBy(GetAssemblyCatalogueResp::getAssembly));
			map.forEach((k, v) -> {
				GetAssemblyCatalogueResp catalogueResp = v.get(0);
				PartAssemblyDto partAssemblyDto = new PartAssemblyDto();
				partAssemblyDto.setAssemblyName(catalogueResp.getAssembly());//总成名
				partAssemblyDto.setType(type);
				if (StringUtils.isNotBlank(catalogueResp.getTimerAssemblyId())) {
					partAssemblyDto.setAssemblyId(catalogueResp.getTimerAssemblyId());
				}
				partAssemblyDtoList.add(partAssemblyDto);
			});
		} else if (Objects.equals(0, type)) {
			Map<String, List<GetAssemblyCatalogueResp>> map = resp.stream().collect(Collectors.groupingBy(GetAssemblyCatalogueResp::getSubAssembly));
			map.forEach((k, v) -> {
				GetAssemblyCatalogueResp catalogueResp = v.get(0);
				PartAssemblyDto partAssemblyDto = new PartAssemblyDto();
				partAssemblyDto.setAssemblyName(catalogueResp.getSubAssembly());//子总成名
				partAssemblyDto.setType(type);
				partAssemblyDto.setAssemblyId(catalogueResp.getSubAssembly());
				partAssemblyDtoList.add(partAssemblyDto);
			});
		}

		return partAssemblyDtoList;
	}

	private List<PartCategoryDto> getPartCategoryDtoList(List<CarModelCategoryEntity> categoryEntityList, Integer type) {
		List<PartCategoryDto> partCategoryDtoList = Lists.newArrayList();//以大类名称为聚合
		if (Objects.equals(1, type)) {//正时聚合分类以正时定义的分类为标准
			Map<String, List<CarModelCategoryEntity>> map = categoryEntityList.stream()
			                                                                  .filter(p -> StringUtils.isNotBlank(p.getCategoryName()))
			                                                                  .collect(
					                                                                  Collectors.groupingBy(CarModelCategoryEntity::getCategoryName));
			map.forEach((k, v) -> {
				PartCategoryDto partCategoryDto = new PartCategoryDto();
				partCategoryDto.setType(type);
				partCategoryDto.setCategoryName(v.get(0).getCategoryName());
				partCategoryDto.setCategoryId(v.get(0).getCategoryId());
				partCategoryDtoList.add(partCategoryDto);
			});
		} else if (Objects.equals(0, type)) {//原厂没有大分类，最上层的分类就是总成
			Map<String, List<CarModelCategoryEntity>> map = categoryEntityList.stream().collect(
					Collectors.groupingBy(CarModelCategoryEntity::getAssemblyName));
			map.forEach((k, v) -> {
				PartCategoryDto partCategoryDto = new PartCategoryDto();
				partCategoryDto.setType(type);
				partCategoryDto.setCategoryName(v.get(0).getAssemblyName());
				partCategoryDtoList.add(partCategoryDto);
			});
		}
		return partCategoryDtoList;
	}

	private List<PartCategoryDto> getPartCategoryDtoListBySuperEPC(Integer carModelId, Integer type) {
		type = Objects.nonNull(type) ? type : 1;//默认取正时分类
		List<PartCategoryDto> partCategoryDtoList = Lists.newArrayList();//以大类名称为聚合
		List<GetAssemblyCatalogueResp> resp = superEpcService.getAssemblyCatalogue(carModelId, type);//该车型正时或者原厂分类
		if (CollectionUtils.isEmpty(resp) && Objects.equals(1, type)) {//正时的分类为空，取原厂分类
			type = 0;
			resp = superEpcService.getAssemblyCatalogue(carModelId, type);//该车型原厂分类
		}
		if (CollectionUtils.isNotEmpty(resp)) {
			if (Objects.equals(1, type)) {//正时分类,先取总成然后聚合大类
				List<Integer> assemblyIdList = resp.stream().map(GetAssemblyCatalogueResp::getTimerAssemblyId).map(Integer::valueOf).distinct()
				                                   .collect(Collectors.toList());
				List<StandardAssemblyEntity> assemblyEntityList = standardAssemblyRepo.selectByIds(assemblyIdList);
				if (CollectionUtils.isNotEmpty(assemblyEntityList)) {
					Map<Integer, List<StandardAssemblyEntity>> map = assemblyEntityList.stream().collect(
							Collectors.groupingBy(StandardAssemblyEntity::getCategoryId));
					map.forEach((k, v) -> {
						PartCategoryDto partCategoryDto = new PartCategoryDto();
						partCategoryDto.setCategoryName(v.get(0).getCategoryName());
						partCategoryDto.setCategoryId(k);
						partCategoryDtoList.add(partCategoryDto);
					});
				}
			} else if (Objects.equals(0, type)) {//原厂分类，没有大类总成即大类
				Map<String, List<GetAssemblyCatalogueResp>> map = resp.stream().collect(Collectors.groupingBy(GetAssemblyCatalogueResp::getAssembly));
				map.forEach((k, v) -> {
					PartCategoryDto partCategoryDto = new PartCategoryDto();
					partCategoryDto.setCategoryName(v.get(0).getAssembly());
					partCategoryDtoList.add(partCategoryDto);
				});
			}
			for (PartCategoryDto partCategoryDto : partCategoryDtoList) {//设置类目类型
				partCategoryDto.setType(type);
			}
		} else {
			logger.info("车型id:{}调正时接口也未获取到分类数据", carModelId);
		}
	/*	if (CollectionUtils.isNotEmpty(partCategoryDtoList)) {
			epcMessageNotifyService.notifyCarModelAssemblyUpdate(carModelId);
		}*/
		return partCategoryDtoList;
	}

	private List<PartSubAssemblyDto> getPartSubAssemblyDtoList(List<GetPicInfoForAssemblyResp> assemblyRespList) {
		if (CollectionUtils.isEmpty(assemblyRespList)){
			return Collections.emptyList();
		}
		List<PartSubAssemblyDto> partSubAssemblyDtoList = Lists.newArrayList();
		//先以分总成维度处理
		Map<String, List<GetPicInfoForAssemblyResp>> subAssemblyMap = assemblyRespList.stream().collect(
				Collectors.groupingBy(GetPicInfoForAssemblyResp::getSubAssemblyName));
		subAssemblyMap.forEach((k, v) -> {
			//然后以picNum维度处理
			Map<String, List<GetPicInfoForAssemblyResp>> picNumMap = v.stream().collect(Collectors.groupingBy(GetPicInfoForAssemblyResp::getPicNum));
			picNumMap.forEach((picNum, picNumList) -> {
				//再以picName维度展示
				Map<String, List<GetPicInfoForAssemblyResp>> picNameMap = picNumList.stream()
				                                                           .collect(Collectors.groupingBy(GetPicInfoForAssemblyResp::getPicName));
				picNameMap.forEach((picName, picNameList) -> {
					GetPicInfoForAssemblyResp resp = picNameList.get(0);
					boolean hasMore = picNameMap.size() > 0;
					PartSubAssemblyDto partSubAssemblyDto = this
							.getPartSubAssemblyDto(picName, picNum, resp.getPicPath(), resp.getSubAssemblyName(), hasMore);
					partSubAssemblyDtoList.add(partSubAssemblyDto);
				});
			});
		});
		return partSubAssemblyDtoList;
	}

	private List<PartSubAssemblyDto> getPartSubAssemblyDtoListByOriginal(List<GetPicInfoResp> getPicInfoRespList, String subAssemblyName) {
		if (CollectionUtils.isEmpty(getPicInfoRespList)){
			return Collections.emptyList();
		}
		List<PartSubAssemblyDto> partSubAssemblyDtoList = Lists.newArrayList();
		Map<String, List<GetPicInfoResp>> picNumMap = getPicInfoRespList.stream().collect(Collectors.groupingBy(GetPicInfoResp::getPicNum));
		picNumMap.forEach((k, v) -> {
			Map<String, List<GetPicInfoResp>> picNameMap = getPicInfoRespList.stream().collect(Collectors.groupingBy(GetPicInfoResp::getPicName));
			picNameMap.forEach((picName, picNameList) -> {
				GetPicInfoResp getPicInfoResp = picNameList.get(0);
				boolean hasMore = picNameMap.size() > 0;
				PartSubAssemblyDto partSubAssemblyDto = this.getPartSubAssemblyDto(picName, k, getPicInfoResp.getPicPath(), subAssemblyName, hasMore);
				partSubAssemblyDtoList.add(partSubAssemblyDto);
			});
		});
		return partSubAssemblyDtoList;
	}

	private void handlerSort(List<PartCategoryDto> partCategoryDtoList) {
		if (CollectionUtils.isNotEmpty(partCategoryDtoList) && Objects.equals(1, partCategoryDtoList.get(0).getType())) {
			//正时分类其他放在最后
			int x = 0, index = -1;
			for (PartCategoryDto partCategoryDto : partCategoryDtoList) {
				if ("其它".equals(partCategoryDto.getCategoryName())) {
					index = x;
					break;
				}
				x++;
			}
			if (index >= 0) {
				PartCategoryDto partCategoryDto = partCategoryDtoList.get(index);
				partCategoryDtoList.remove(index);
				partCategoryDtoList.add(partCategoryDto);
			}
		}
	}

	private PartSubAssemblyDto getPartSubAssemblyDto(String picName, String picNum, String path, String name, boolean hasMore) {
		PartSubAssemblyDto partSubAssemblyDto = new PartSubAssemblyDto();
		if (hasMore) {
			partSubAssemblyDto.setSubAssemblyName(name.concat("-" + picName));
		} else {
			partSubAssemblyDto.setSubAssemblyName(name);
		}
		partSubAssemblyDto.setPicName(picName);
		partSubAssemblyDto.setPicNum(picNum);
		partSubAssemblyDto.setPicUrl(ImageUtil.enCodeUrl(EPC_IMG_HOST + path));
		return partSubAssemblyDto;
	}

	private PartAssemblyDto getPartAssemblyDto(String extraParam, Integer type){
		PartAssemblyDto partAssemblyDto = new PartAssemblyDto();
		partAssemblyDto.setAssemblyName("全部");
		partAssemblyDto.setExtraParam(extraParam);
		partAssemblyDto.setType(type);
		return partAssemblyDto;
	}


}
