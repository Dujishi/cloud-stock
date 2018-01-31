package com.xiaoka.cloud.stock.service.epc.impl;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xiaoka.cloud.stock.core.epc.repo.*;
import com.xiaoka.cloud.stock.core.epc.repo.entity.CarBrandEntity;
import com.xiaoka.cloud.stock.core.epc.repo.entity.CarModelEntity;
import com.xiaoka.cloud.stock.core.epc.repo.entity.UserBrandEntity;
import com.xiaoka.cloud.stock.core.supplier.constant.SearchTypeEnum;
import com.xiaoka.cloud.stock.service.core.exception.CloudStockErrorCode;
import com.xiaoka.cloud.stock.service.core.util.AssertUtil;
import com.xiaoka.cloud.stock.service.core.util.BeanUtils;
import com.xiaoka.cloud.stock.service.epc.*;
import com.xiaoka.cloud.stock.service.epc.constant.CategoryTypeEnum;
import com.xiaoka.cloud.stock.service.epc.dto.*;
import com.xiaoka.cloud.stock.service.epc.output.EPCSearchResultDto;
import com.xiaoka.cloud.stock.service.epc.output.PartDetailDto;
import com.xiaoka.cloud.stock.service.epc.param.EPCQueryParam;
import com.xiaoka.cloud.stock.service.supplier.dto.CloudSupplierUserDto;
import com.xiaoka.cloud.stock.service.wrapper.zero.ZeroEPCWrapperService;
import com.xiaoka.cloud.stock.service.wrapper.zero.resp.BaseZeroResp;
import com.xiaoka.cloud.stock.service.wrapper.zero.resp.PartSearchResp;
import com.xiaoka.cloud.stock.service.wrapper.zero.resp.ZeroPartSearchResp;
import com.xiaoka.freework.help.api.ApiException;
import com.xiaoka.freework.utils.json.Jackson;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Do something
 *
 * @author gancao 2017/11/17 13:58
 * @see [相关类/方法]
 * @since [版本号]
 */
@Service
public class EpcQueryServiceImpl implements EpcQueryService {

	private static final Logger logger = LoggerFactory.getLogger(EpcQueryServiceImpl.class);
	private static final Joiner JOINER = Joiner.on(",").skipNulls();

	@Resource
	private CarModelService carModelService;
	@Resource
	private CarBrandRepo carBrandRepo;
	@Resource
	private CarSeriesRepo carSeriesRepo;
	@Resource
	private CarModelRepo carModelRepo;
	@Resource
	private UserBrandRepo userBrandRepo;
	@Resource
	private CarBrandIconRepo carBrandIconRepo;
	@Resource
	private CarModelCategoryService carModelCategoryService;
	@Resource
	private CarModelPartService carModelPartService;
	@Resource
	private EpcSearchHistoryService epcSearchHistoryService;
	@Resource
	private ZeroEPCWrapperService zeroEPCWrapperService;
	@Resource
	private ZeroEpcService zeroEpcService;

	@Override
	public EPCSearchResultDto getCarPartCategoryDto(String vin, Integer carModelId, CloudSupplierUserDto cloudSupplierUserDto) {
		AssertUtil.assertTrueWithApiException(StringUtils.isNotBlank(vin) || Objects.nonNull(carModelId), "vin码或者车型参数缺失");
		EPCSearchResultDto epcSearchResultDto = new EPCSearchResultDto();
		Integer brandId = null;
		//1.获取车型
		if (StringUtils.isNotBlank(vin)) {
			List<CarModelDto> carModelDtoList = carModelService.getCarModelListByVin(vin);
			//插入搜索日志
			epcSearchHistoryService.insertSearchHistory(SearchTypeEnum.VIN_CODE.getType(), vin, cloudSupplierUserDto);
			if (CollectionUtils.isNotEmpty(carModelDtoList)) {
				epcSearchResultDto.setCarModelList(carModelDtoList);
				carModelId = carModelDtoList.get(0).getCarModelId();
				brandId = carModelDtoList.get(0).getBrandId();
			}
		}

		if (Objects.nonNull(carModelId)) {
			epcSearchResultDto.setPartCategoryList(carModelCategoryService.getCarModelCategory(carModelId));
		}
		if (StringUtils.isNotBlank(vin) && CollectionUtils.isEmpty(epcSearchResultDto.getCarModelList())) {
			logger.info("vin:{}正时未查询到vin码相关信息，调零零汽接口查询...", vin);
			//车型不存在查007数据
			epcSearchResultDto = zeroEpcService.searchZeroVinDataByVin(vin, cloudSupplierUserDto);
		}
		AssertUtil.assertTrueWithApiException(Objects.nonNull(epcSearchResultDto) && CollectionUtils.isNotEmpty(epcSearchResultDto.getCarModelList()),
				"该VIN码没有查询到结果");
		//校验品牌权限
		checkBrandAuth(brandId, carModelId, epcSearchResultDto.getCarModelList().get(0).getCarBrandName(), cloudSupplierUserDto);
		return epcSearchResultDto;
	}

	@Override
	public EPCSearchResultDto getCarPartInfoDto(EPCQueryParam param, CloudSupplierUserDto cloudSupplierUserDto) {
		AssertUtil.assertTrueWithApiException(CollectionUtils.isNotEmpty(param.getPartNameList()), "零件名参数缺失");
		AssertUtil.assertTrueWithApiException(Objects.nonNull(param.getCarModelId()) || StringUtils.isNotBlank(param.getVin()), "参数异常");
		EPCSearchResultDto epcSearchResultDto = new EPCSearchResultDto();
		Integer carModelId = param.getCarModelId();
		Integer brandId = null;
		//根据vin码获取车型
		if (StringUtils.isNotBlank(param.getVin())) {
			List<CarModelDto> carModelDtoList = carModelService.getCarModelListByVin(param.getVin());
			AssertUtil.assertNotNullWithApiException(CollectionUtils.isNotEmpty(carModelDtoList), "该VIN码没有查询到结果");
			epcSearchResultDto.setCarModelList(carModelDtoList);
			carModelId = carModelDtoList.get(0).getCarModelId();//取第一个作为展示的车型
			brandId = carModelDtoList.get(0).getBrandId();
		}
		//校验品牌权限
		checkBrandAuth(brandId, carModelId, null, cloudSupplierUserDto);

		epcSearchResultDto.setPartList(carModelPartService.getPartInfoDtoByNameList(carModelId, param.getPartNameList()));//获取搜索的车型配件
		return epcSearchResultDto;
	}

	@Override
	public List<PartAssemblyDto> getCarAssemblyDtoList(Integer carModelId, Integer categoryId, String categoryName, String extraParam, Integer type) {
		AssertUtil.assertNotNullWithApiException(type, "分类类型参数缺失");
		if (Objects.equals(CategoryTypeEnum.正时分类.getType(), type)) {
			AssertUtil.assertNotNullWithApiException(categoryId, "分类id参数缺失");
		} else if (Objects.equals(CategoryTypeEnum.原厂分类.getType(), type)) {
			AssertUtil.assertTrueWithApiException(StringUtils.isNotBlank(categoryName), "分类名称参数缺失");
		} else if (Objects.equals(CategoryTypeEnum.零零汽分类.getType(), type)) {
			AssertUtil.assertTrueWithApiException(StringUtils.isNotBlank(extraParam), "扩展参数参数缺失");
		} else {
			AssertUtil.assertNotNullWithApiException(null, "不能识别的分类类型");
		}
		return carModelCategoryService.getCarModelCategory(carModelId, categoryId, categoryName, extraParam, type);
	}

	@Override
	public List<PartSubAssemblyDto> getCarSubAssemblyDtoList(Integer carModelId, String categoryName, String assemblyId, String assemblyName,
			String extraParam, Integer type, CloudSupplierUserDto cloudSupplierUserDto) {
		AssertUtil.assertNotNullWithApiException(type, "分类类型参数缺失");
		if (Objects.equals(CategoryTypeEnum.原厂分类.getType(), type)) {
			AssertUtil.assertTrueWithApiException(StringUtils.isNotBlank(categoryName), "总成名称参数缺失");
			//原厂分类及总成
			assemblyName = categoryName;
		} else if (Objects.equals(CategoryTypeEnum.零零汽分类.getType(), type)) {
			AssertUtil.assertTrueWithApiException(StringUtils.isNotBlank(extraParam), "扩展参数参数缺失");
		}
		return carModelCategoryService.getCarSubAssemblyDtoList(carModelId, assemblyId, assemblyName, extraParam, type, cloudSupplierUserDto);
	}

	@Override
	public List<PartInfoDto> getPartInfoDtoList(Integer carModelId, String picNum, String picName, String extraParam, CloudSupplierUserDto cloudSupplierUserDto) {
		if (StringUtils.isNotBlank(extraParam)) {
			return zeroEpcService.getPartInfoDtoListByZeroSubGroup(extraParam, cloudSupplierUserDto);
		} else {
			AssertUtil.assertTrueWithApiException(Objects.nonNull(carModelId), "车型参数缺失");
			AssertUtil.assertTrueWithApiException(StringUtils.isNoneBlank(picNum), "配件图号参数缺失");
			AssertUtil.assertTrueWithApiException(StringUtils.isNoneBlank(picName), "配件图号名参数缺失");
			return carModelPartService.getPartInfoDtoList(carModelId, picNum, picName, null);
		}
	}

	@Override
	public PartDetailDto getPartDetailDto(Integer carModelId, String partCode, String carBrandName, CloudSupplierUserDto cloudSupplierUserDto) {
		AssertUtil.assertTrueWithApiException(StringUtils.isNoneBlank(carBrandName), "车辆品牌参数缺失");
		AssertUtil.assertTrueWithApiException(StringUtils.isNoneBlank(partCode), "零件号参数缺失");
		partCode = StringUtils.deleteWhitespace(partCode);
		PartDetailDto partDetailDto = new PartDetailDto();
		List<PartSuitCarDto> carDtoList = carModelService.getSuitCarModelListByPartCode(partCode);//先通过零件号获取到零件的适用车型
		if (CollectionUtils.isNotEmpty(carDtoList)) {
			carDtoList = carDtoList.stream().filter(p -> carBrandName.equals(p.getCarBrandName())).collect(Collectors.toList());//品牌过滤
		} else {
			logger.info("零件号:{}未找到适应车型", partCode);
		}
		partDetailDto.setPartInfoList(this.getPartInfoDtoList(carDtoList, carModelId, partCode));//配件信息，按品牌厂商维度展示
		partDetailDto.setSuitCarList(this.getPartSuitCarDtoList(carDtoList, partCode));//适用车型，按车系维度展示
		if (CollectionUtils.isNotEmpty(partDetailDto.getPartInfoList())) {//设置零件的替换件,原厂件有4S店价格，品牌件没有
			partDetailDto.setReplacePartList(this.getReplacePartInfoDtoList(partCode, partDetailDto));
		}
		if (CollectionUtils.isEmpty(partDetailDto.getPartInfoList())) {
			logger.info("正时没有partCode:{}的相关信息，调零零汽接口查询...", partCode);
			partDetailDto = zeroEpcService.getPartDetailDtoByZero(partCode, carBrandName, cloudSupplierUserDto);
		}
		return partDetailDto;
	}

	@Override
	public List<CarBrandDto> getCarBrandDtoList(List<String> partCodeList, CloudSupplierUserDto cloudSupplierUserDto) {
		AssertUtil.assertTrueWithApiException(CollectionUtils.isNotEmpty(partCodeList), "零件号参数缺失");
		partCodeList = partCodeList.stream().map(StringUtils::deleteWhitespace).filter(StringUtils::isNotBlank).distinct()
		                           .collect(Collectors.toList());//零件号去除空格并去重
		List<CarBrandDto> carBrandDtoList = Lists.newArrayList();
		Map<String, List<PartSuitCarDto>> map = carModelService.getSuitCarModelListByPartCodeList(partCodeList);
		Map<String, String> brandIconMap = Maps.newHashMap();//007的不走本地的品牌图片
		//正时查不到的去查零零汽
		map.forEach((k, v) -> {
			if (CollectionUtils.isEmpty(v)) {
				BaseZeroResp<List<List<PartSearchResp>>> baseZeroResp= zeroEPCWrapperService.getBrandName(k, cloudSupplierUserDto);
				if (Objects.nonNull(baseZeroResp) && StringUtils.isNotBlank(baseZeroResp.getBrand())){
					PartSuitCarDto partSuitCarDto = new PartSuitCarDto();
					partSuitCarDto.setCarBrandName(baseZeroResp.getBrand());
					v.add(partSuitCarDto);
					brandIconMap.put(baseZeroResp.getBrand(), baseZeroResp.getImg());
				}
			}
		});
		//插入搜索日志
		epcSearchHistoryService.insertSearchHistory(SearchTypeEnum.OE_CODE.getType(), JOINER.join(partCodeList), cloudSupplierUserDto);
		List<PartSuitCarDto> partSuitCarDtoList = Lists.newArrayList();
		map.forEach((k, v) -> partSuitCarDtoList.addAll(v));
		List<String> brandNameList = partSuitCarDtoList.stream().map(PartSuitCarDto::getCarBrandName).distinct().collect(Collectors.toList());
		brandNameList.forEach(name -> {
			CarBrandDto dto = new CarBrandDto();
			dto.setBrandName(name);
			String image = brandIconMap.get(name);
			if (StringUtils.isNotBlank(image)){
				dto.setBrandIconUrl(image);
			}else {
				dto.setBrandIconUrl(carBrandIconRepo.selectIconByName(name));
			}
			carBrandDtoList.add(dto);
		});
		AssertUtil.assertTrueWithApiException(CollectionUtils.isNotEmpty(carBrandDtoList), "该零件码没有查询到结果");
		return carBrandDtoList;
	}

	@Override
	public List<PartInfoDto> getPartInfoDtoList(List<String> partCodeList, String brandName, CloudSupplierUserDto cloudSupplierUserDto) {
		AssertUtil.assertTrueWithApiException(CollectionUtils.isNotEmpty(partCodeList), "零件号参数缺失");
		AssertUtil.assertTrueWithApiException(StringUtils.isNotBlank(brandName), "品牌名参数缺失");
		partCodeList = partCodeList.stream().map(StringUtils::deleteWhitespace).filter(StringUtils::isNotBlank).distinct()
		                           .collect(Collectors.toList());

		//校验品牌权限
		List<CarBrandEntity> brandEntities = carBrandRepo.selectByNames(Arrays.asList(brandName));
		AssertUtil.assertTrueWithApiException(CollectionUtils.isNotEmpty(brandEntities), "品牌不存在");
		checkBrandAuth(brandEntities.get(0).getBrandId(), null, brandName, cloudSupplierUserDto);

		//先查询配件适用的车型
		List<PartInfoDto> partInfoDtoList = Lists.newArrayList();
		Map<String, List<PartSuitCarDto>> map = carModelService.getSuitCarModelListByPartCodeList(partCodeList);
		partCodeList.forEach(partCode -> {
			PartInfoDto partInfoDto = new PartInfoDto();
			partInfoDto.setPartCode(partCode);
			List<PartSuitCarDto> partSuitCarDtoList = map.get(partCode);
			if (CollectionUtils.isNotEmpty(partSuitCarDtoList)) {//品牌过滤后随意选择一个车型去获取配件信息
				partSuitCarDtoList = partSuitCarDtoList.stream().filter(p -> brandName.equals(p.getCarBrandName())).collect(Collectors.toList());
				if (CollectionUtils.isNotEmpty(partSuitCarDtoList)) {
					List<PartInfoDto> dtoList = carModelPartService
							.getPartInfoDtoList(partSuitCarDtoList.get(0).getCarModelId(), null, null, partCode);
					if (CollectionUtils.isNotEmpty(dtoList)) {//设置配件名称
						partInfoDto.setPartName(dtoList.get(0).getPartName());
					}
				}
			}
			partInfoDtoList.add(partInfoDto);
		});
		if (CollectionUtils.isEmpty(partInfoDtoList)
				|| partCodeList.stream().anyMatch(p -> partInfoDtoList.stream().noneMatch(x -> Objects.equals(x.getPartCode(), p)))) {
			List<String> remainCodes = partCodeList.stream().filter(p -> partInfoDtoList.stream().noneMatch(x -> Objects.equals(x.getPartCode(), p)))
			                                       .collect(Collectors.toList());
			remainCodes.stream().forEach(p -> {
				ZeroPartSearchResp zeroPartSearchResp = zeroEPCWrapperService.getZeroPartSearchResp(brandName, p, cloudSupplierUserDto);
				if (Objects.nonNull(zeroPartSearchResp) && CollectionUtils.isNotEmpty(zeroPartSearchResp.getData())) {
					PartInfoDto partInfoDto = new PartInfoDto();
					partInfoDto.setPartCode(p);
					partInfoDto.setPartName(zeroPartSearchResp.getData().get(0).getLabel());
					partInfoDtoList.add(partInfoDto);
				}
			});
		}
		return partInfoDtoList;
	}

	private List<PartInfoDto> getPartInfoDtoList(List<PartSuitCarDto> carDtoList, Integer carModelId, String partCode) {
		List<PartInfoDto> partInfoDtoList = Lists.newArrayList();
		if (CollectionUtils.isEmpty(carDtoList)) {
			return partInfoDtoList;
		}
		Map<String, List<PartSuitCarDto>> oemMap = Maps.newHashMap();//厂商维度
		if (Objects.nonNull(carModelId)) {//车型不为空，厂商维度只展示该车型的厂商
			PartSuitCarDto partSuitCarDto = carDtoList.stream().filter(p -> Objects.equals(p.getCarModelId(), carModelId)).findFirst().orElse(null);
			if (Objects.nonNull(partSuitCarDto)) {
				oemMap.put(partSuitCarDto.getMakeName(), Collections.singletonList(partSuitCarDto));
			} else {
				logger.info("零件号:{},车型id:{},没有在零件的适用车型中找到该车型", partCode, carModelId);
			}
		}
		if (oemMap.isEmpty()) {
			oemMap = carDtoList.stream().collect(Collectors.groupingBy(PartSuitCarDto::getMakeName));//用于展示配件信息
		}
		logger.info("零件号:{},车型id:{},按厂商维度聚合车型:{}", partCode, carDtoList, Jackson.mobile().writeValueAsString(oemMap));
		//获取车型配件数据
		oemMap.forEach((k, v) -> {
			Integer modelId = v.get(0).getCarModelId();//取厂商下面第一个车型用来取配件基础信息(零件号、零件名等)
			List<PartInfoDto> itemList = carModelPartService.getPartInfoDtoList(modelId, null, null, partCode);
			if (CollectionUtils.isNotEmpty(itemList)) {//一个厂商下面展示一个配件
				PartInfoDto partInfoDto = itemList.get(0);
				partInfoDto.setSource(v.get(0).getType());//类型
				partInfoDto.setOemBrand(v.get(0).getCarBrandName());
				partInfoDto.setOemName(v.get(0).getMakeName());
				if ("原厂".equals(partInfoDto.getSource())) {
					partInfoDto.setPrice(carModelPartService.getPartPrice(partCode, v.get(0).getCarBrandName(), v.get(0).getMakeName()));//获取4S店价格
				}
				partInfoDtoList.add(partInfoDto);
			}
		});
		return partInfoDtoList;
	}

	private List<PartSuitCarDto> getPartSuitCarDtoList(List<PartSuitCarDto> carDtoList, String partCode) {
		List<PartSuitCarDto> partSuitCarDtoList = Lists.newArrayList();
		if (CollectionUtils.isEmpty(carDtoList)) {
			return partSuitCarDtoList;
		}
		Map<String, List<PartSuitCarDto>> carMap = carDtoList.stream().collect(Collectors.groupingBy(PartSuitCarDto::getCarSeriesName));//车系维度

		carMap.forEach((k, v) -> {
			PartSuitCarDto partSuitCarDto = v.get(0);//车系下只有取一个车型进行返回即可
			Integer modelId = partSuitCarDto.getCarModelId();//获取零件组和图号，取一个车型获取即可
			List<PartInfoDto> itemList = carModelPartService.getPartInfoDtoList(modelId, null, null, partCode);
			if (CollectionUtils.isNotEmpty(itemList)) {
				PartInfoDto partInfoDto = itemList.get(0);
				partSuitCarDto.setSubAssemblyName(partInfoDto.getSubAssemblyName());//设置原厂分总成
				partSuitCarDto.setPicNum(partInfoDto.getPicNum());//设置零件图号
				partSuitCarDto.setPicName(partInfoDto.getPicName());
			} else {
				logger.info("车型id:{},零件号:{}未获取到配件信息", modelId, partCode);
			}
			partSuitCarDtoList.add(partSuitCarDto);
		});

		return partSuitCarDtoList;
	}

	private List<PartInfoDto> getReplacePartInfoDtoList(String partCode, PartDetailDto partDetailDto) {
		String partName = null;
		if (CollectionUtils.isNotEmpty(partDetailDto.getPartInfoList())) {
			partName = partDetailDto.getPartInfoList().get(0).getPartName();
		}
		List<PartInfoDto> partInfoDtoList = carModelPartService.getPartReplaceList(partCode, null);//获取替换件
		if (CollectionUtils.isNotEmpty(partInfoDtoList)) {
			for (PartInfoDto dto : partInfoDtoList) {
				dto.setPartName(partName);
				if ("原厂".equals(dto.getSource())) {//原厂件取4S店价格
					dto.setPrice(carModelPartService.getPartPrice(dto.getPartCode(), dto.getOemBrand(), dto.getOemName()));//获取4S店价格
				}
			}
		}
		return partInfoDtoList;
	}

	//**********************************************
	//********** 品牌权限控制及车型查询相关 ***********
	//**********************************************

	@Override
	public List<CarBrandDto> getBrandFirstLetter(CloudSupplierUserDto cloudSupplierUserDto) {
		List<Integer> brandIdList = this.getUserBrand(cloudSupplierUserDto.getId());
		return BeanUtils.transformList(CarBrandDto.class, carBrandRepo.selectBrandFirstLetterByBrandIds(brandIdList));
	}

	@Override
	public List<CarBrandDto> getAllBrandFirstLetter() {
		return BeanUtils.transformList(CarBrandDto.class, carBrandRepo.selectBrandFirstLetter());
	}

	public static void main(String[] args) {
		BeanUtils.transformList(CarBrandDto.class, null);
	}

	@Override
	public List<CarBrandDto> getBrandByFirstLetter(String firstLetter) {
		AssertUtil.assertTrueWithApiException(StringUtils.isNotBlank(firstLetter), "品牌首字母不能为空");
		List<CarBrandDto> carBrandDtos = BeanUtils
				.transformList(CarBrandDto.class, carBrandRepo.selectBrandByFirstLetter(firstLetter));
		if (CollectionUtils.isEmpty(carBrandDtos)) {
			return null;
		}
		initIcon(carBrandDtos);
//		carBrandDtos.forEach(dto -> dto.setBrandIconUrl(carBrandIconRepo.selectIconByName(dto.getBrandName())));
		return carBrandDtos;
	}

	@Override
	public List<CarSeriesDto> getSeriesByBrand(Integer brandId, CloudSupplierUserDto cloudSupplierUserDto) {
		AssertUtil.assertTrueWithApiException(brandId != null, "品牌不能为空");
		checkBrandAuth(brandId, null, null, cloudSupplierUserDto);
		return BeanUtils.transformList(CarSeriesDto.class, carSeriesRepo.selectDistinctSeriesByBrand(brandId));
	}

	@Override
	public List<CarModelWebDto> getModelYearBySeries(Integer brandId, String series) {
		AssertUtil.assertTrueWithApiException(StringUtils.isNotBlank(series), "车系不能为空");
		return BeanUtils.transformList(CarModelWebDto.class, carModelRepo.selectModelYearBySeries(brandId, series));
	}

	@Override
	public List<CarModelWebDto> getModelBySeries(Integer brandId, String series, String modelYear) {
		AssertUtil.assertTrueWithApiException(StringUtils.isNotBlank(series), "车系不能为空");
		CarModelEntity param = new CarModelEntity();
		param.setBrandId(brandId);
		param.setSeries(series);
		param.setModelYear(modelYear);
		List<CarModelEntity> carModelEntityList = carModelRepo.select(param);
		if (CollectionUtils.isEmpty(carModelEntityList)){
			return Collections.emptyList();
		}
		List<CarModelEntity> result = Lists.newArrayList();
		Map<Integer, List<CarModelEntity>> map = carModelEntityList.stream().collect(Collectors.groupingBy(CarModelEntity::getModelId));
		map.forEach((k ,v) -> result.add(v.get(0)));
		return BeanUtils.transformList(CarModelWebDto.class, result);
	}

	@Override
	public List<CarBrandDto> getBrandByModelName(String modelCondition) {
		AssertUtil.assertTrueWithApiException(StringUtils.isNotBlank(modelCondition), "搜索条件不能为空");
		List<CarBrandDto> carBrandDtos = BeanUtils
				.transformList(CarBrandDto.class, carModelRepo.selectBrandByModelName(modelCondition));
		if (CollectionUtils.isEmpty(carBrandDtos)) {
			return null;
		}
		initIcon(carBrandDtos);
//		carBrandDtos.forEach(dto -> dto.setBrandIconUrl(carBrandIconRepo.selectIconByName(dto.getBrandName())));
		return carBrandDtos;
	}

	@Override
	public List<CarModelWebDto> getModelYearByModelName(String modelCondition, Integer brandId, CloudSupplierUserDto cloudSupplierUserDto) {
		AssertUtil.assertTrueWithApiException(StringUtils.isNotBlank(modelCondition), "搜索条件不能为空");
		AssertUtil.assertTrueWithApiException(brandId != null, "品牌不能为空");
		checkBrandAuth(brandId, null, null, cloudSupplierUserDto);
		return BeanUtils.transformList(CarModelWebDto.class, carModelRepo.selectModelYearByModelName(modelCondition,brandId));
	}

	@Override
	public List<CarModelWebDto> getModelBySearch(String modelCondition, Integer brandId, String modelYear) {
		AssertUtil.assertTrueWithApiException(StringUtils.isNotBlank(modelCondition), "搜索条件不能为空");
		AssertUtil.assertTrueWithApiException(brandId != null, "品牌不能为空");
		AssertUtil.assertTrueWithApiException(StringUtils.isNotBlank(modelYear), "车款年份不能为空");
		return BeanUtils.transformList(CarModelWebDto.class, carModelRepo.selectModelBySearch(modelCondition,brandId, modelYear));
	}

	@Override
	public List<CarBrandDto> getAuthorisedByFirstLetter(String firstLetter, Integer userId) {
		AssertUtil.assertTrueWithApiException(userId != null, "用户不能为空");
		UserBrandEntity param = new UserBrandEntity();
		param.setCsUserId(userId);
		List<UserBrandEntity> userBrandEntities = userBrandRepo.select(param);
		if (CollectionUtils.isEmpty(userBrandEntities)) {
			return null;
		}
		List<Integer> brandIds = userBrandEntities.stream().map(UserBrandEntity::getBrandId)
		                                          .collect(Collectors.toList());

		List<CarBrandDto> carBrandDtos = BeanUtils.transformList(CarBrandDto.class, carBrandRepo.selectBrandByIdsAndFirstLetter(firstLetter, brandIds));

		if (CollectionUtils.isEmpty(carBrandDtos)) {
			return null;
		}
		initIcon(carBrandDtos);
//		carBrandDtos.forEach(dto -> dto.setBrandIconUrl(carBrandIconRepo.selectIconByName(dto.getBrandName())));
		return carBrandDtos;
	}

	@Override
	public List<CarBrandDto> getAllBrand() {
		List<CarBrandDto> carBrandDtos = BeanUtils
				.transformList(CarBrandDto.class,
						BeanUtils.transformList(CarBrandDto.class, carBrandRepo.selectAllBrandsBySort()));
		if (CollectionUtils.isEmpty(carBrandDtos)) {
			return null;
		}
		initIcon(carBrandDtos);
//		carBrandDtos.forEach(dto -> dto.setBrandIconUrl(carBrandIconRepo.selectIconByName(dto.getBrandName())));
		return carBrandDtos;
	}

	private void initIcon(List<CarBrandDto> carBrandDtos) {
		if (CollectionUtils.isEmpty(carBrandDtos)) {
			return;
		}
		List<String> iconList = carBrandDtos.stream().map(CarBrandDto::getBrandName).collect(Collectors.toList());
		Map<String, String> iconNameMap = carBrandIconRepo.selectIconByNames(iconList);
		if (iconNameMap != null) {
			carBrandDtos.forEach(dto -> dto.setBrandIconUrl(iconNameMap.get(dto.getBrandName())));
		}

	}

	@Override
	public List<CarBrandDto> getModelByModelName(String modelCondition, CloudSupplierUserDto cloudSupplierUserDto) {
		List<CarBrandDto> carBrandDtoList = Lists.newArrayList();
		List<Integer> brandIdList = this.getUserBrand(cloudSupplierUserDto.getId());
		List<CarModelEntity> carModelEntityList = carModelRepo.selectByModelName(modelCondition, brandIdList);
		if (CollectionUtils.isNotEmpty(carModelEntityList)){
			Map<Integer, List<CarModelEntity>> brandMap = carModelEntityList.stream().filter(p -> Objects.nonNull(p.getBrandId())).
					collect(Collectors.groupingBy(CarModelEntity::getBrandId));
			brandMap.forEach((k ,v) -> {
				CarBrandDto carBrandDto = new CarBrandDto();
				carBrandDto.setBrandId(k);
				carBrandDto.setBrandName(v.get(0).getBrandName());

				List<CarYearDto> yearDtoList = Lists.newArrayList();
				//按年份聚合
				Map<String, List<CarModelEntity>> yearMap = v.stream().filter(p -> StringUtils.isNotBlank(p.getModelYear())).
						collect(Collectors.groupingBy(CarModelEntity::getModelYear));
				yearMap.forEach((year, modelList) -> {
					CarYearDto carYearDto = new CarYearDto();
					carYearDto.setYear(year);
					carYearDto.setCarModelList(BeanUtils.transformList(CarModelWebDto.class, modelList));
					yearDtoList.add(carYearDto);
				});
				carBrandDto.setYearList(yearDtoList);
				carBrandDtoList.add(carBrandDto);
			});
		}
		return carBrandDtoList;
	}

	private void checkBrandAuth(Integer brandId, Integer modelId, String carModelName, CloudSupplierUserDto cloudSupplierUserDto) {
		AssertUtil.assertTrueWithApiException(Objects.nonNull(cloudSupplierUserDto), "请先登录");
		if (Objects.nonNull(modelId) &&  modelId != 0){
			if (brandId == null) {
				List<CarModelEntity> carModelEntities = carModelRepo.selectByIds(Collections.singletonList(modelId));
				brandId = CollectionUtils.isEmpty(carModelEntities) ? null : carModelEntities.get(0).getBrandId();
			}
			AssertUtil.assertTrueWithApiException(brandId != null, "品牌不能为空");
		}else {
			//零零汽车型
			if (StringUtils.isNotBlank(carModelName) && carModelName.indexOf("－") > 0){
				carModelName = carModelName.split("－")[0];
			}
			brandId = this.getBrandIdByBrandName(carModelName);
		}

		UserBrandEntity param = new UserBrandEntity();
		param.setCsUserId(cloudSupplierUserDto.getId());
		param.setBrandId(brandId);
		List<UserBrandEntity> userBrandEntities = userBrandRepo.select(param);
		AssertUtil.assertBrandAuthWithApiException(CollectionUtils.isNotEmpty(userBrandEntities), "无此品牌操作权限！");
	}

	private List<Integer> getUserBrand(Integer userId){
		UserBrandEntity param = new UserBrandEntity();
		param.setCsUserId(userId);
		List<UserBrandEntity> userBrandEntityList = userBrandRepo.select(param);
		if (CollectionUtils.isNotEmpty(userBrandEntityList)){
			return userBrandEntityList.stream().map(UserBrandEntity :: getBrandId).distinct().collect(Collectors.toList());
		}
		throw new ApiException(CloudStockErrorCode.CLOUD_STOCK_BRAND_FORBID, "当前无任何品牌的查询权限");
	}

	private Integer getBrandIdByBrandName(String brandName){
		CarBrandEntity carBrandEntity = new CarBrandEntity();
		carBrandEntity.setBrandName(brandName);
		carBrandEntity.setIsValid(1);
		List<CarBrandEntity> brandEntityList = carBrandRepo.select(carBrandEntity);
		if (CollectionUtils.isNotEmpty(brandEntityList)){
			return brandEntityList.get(0).getBrandId();
		}else {
			carBrandEntity.setBrandName(null);
			carBrandEntity.setBrand(brandName);
			brandEntityList = carBrandRepo.select(carBrandEntity);
			if (CollectionUtils.isNotEmpty(brandEntityList)){
				return brandEntityList.get(0).getBrandId();
			}
		}
		return null;
	}
}
