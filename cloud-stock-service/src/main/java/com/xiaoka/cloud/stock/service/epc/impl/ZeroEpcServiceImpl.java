package com.xiaoka.cloud.stock.service.epc.impl;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xiaoka.cloud.stock.service.epc.ZeroEpcService;
import com.xiaoka.cloud.stock.service.epc.constant.CategoryTypeEnum;
import com.xiaoka.cloud.stock.service.epc.dto.*;
import com.xiaoka.cloud.stock.service.epc.output.EPCSearchResultDto;
import com.xiaoka.cloud.stock.service.epc.output.PartDetailDto;
import com.xiaoka.cloud.stock.service.supplier.dto.CloudSupplierUserDto;
import com.xiaoka.cloud.stock.service.wrapper.zero.ZeroEPCWrapperService;
import com.xiaoka.cloud.stock.service.wrapper.zero.resp.*;
import com.xiaoka.freework.utils.encrypt.Base64;
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

/**
 * @author zhouze
 * @date 2017/12/21
 * @see [相关类/方法]
 * @since [版本号]
 */
@Service
public class ZeroEpcServiceImpl implements ZeroEpcService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ZeroEpcServiceImpl.class);
	private static final Splitter SPLITTER = Splitter.on(":").trimResults();

	@Resource
	private ZeroEPCWrapperService zeroEPCWrapperService;

	@Override
	public EPCSearchResultDto searchZeroVinDataByVin(String vin, CloudSupplierUserDto userDto) {
		if (StringUtils.isBlank(vin)) {
			return null;
		}
		EPCSearchResultDto epcSearchResultDto = new EPCSearchResultDto();
		VinSearchZeroResp vinSearchZeroResp = zeroEPCWrapperService.getVinSearchZeroResp(vin, userDto);
		if (Objects.isNull(vinSearchZeroResp)) {
			return null;
		}
		List<ZeroGroupResp> zeroGroupRespList = zeroEPCWrapperService.getZeroGroupResp(vin, vinSearchZeroResp.getBrand(), userDto);
		if (CollectionUtils.isNotEmpty(zeroGroupRespList)) {
			CarModelDto carModelDto = new CarModelDto();
			carModelDto.setCarModelId(0);
			carModelDto.setCarModelName(vinSearchZeroResp.getVins());
			carModelDto.setCarBrandName(vinSearchZeroResp.getBrand());
			carModelDto.setCarModelConfig(getConfig(vinSearchZeroResp.getMains()));
			epcSearchResultDto.setCarModelList(Collections.singletonList(carModelDto));
			epcSearchResultDto.setPartCategoryList(getPartCategoryDtoList(zeroGroupRespList));
		}
		return epcSearchResultDto;
	}

	@Override
	public List<PartSubAssemblyDto> getPartSubAssemblyDtoByZeroGroup(String extraParam, CloudSupplierUserDto userDto) {
		List<PartSubAssemblyDto> partSubAssemblyDtoList = Lists.newArrayList();
		List<ZeroSubGroupResp> zeroSubGroupRespList = zeroEPCWrapperService.getZeroSubGroupResp(extraParam, userDto);
		if (CollectionUtils.isNotEmpty(zeroSubGroupRespList)) {
			int i = 0;
			for (ZeroSubGroupResp resp : zeroSubGroupRespList) {
				PartSubAssemblyDto dto = new PartSubAssemblyDto();
				dto.setPicName(i + "");
				dto.setPicNum(i + "");
				dto.setPicUrl(resp.getUrl());
				dto.setSubAssemblyName(resp.getSubGroupName());
				dto.setExtraParam(this.getExtraParam(resp.getUriParam()));
				partSubAssemblyDtoList.add(dto);
				i++;
			}
		}
		return partSubAssemblyDtoList;
	}

	@Override
	public List<PartInfoDto> getPartInfoDtoListByZeroSubGroup(String extraParam, CloudSupplierUserDto userDto) {
		List<PartInfoDto> partInfoDtoList = Lists.newArrayList();
		ZeroSubGroupPartResp zeroSubGroupPartResp = zeroEPCWrapperService.getZeroSubGroupPartResp(extraParam, userDto);
		if (Objects.nonNull(zeroSubGroupPartResp) && CollectionUtils.isNotEmpty(zeroSubGroupPartResp.getData())) {
			zeroSubGroupPartResp.getData().forEach(resp ->
					resp.forEach(partResp -> {
						PartInfoDto partInfoDto = new PartInfoDto();
						partInfoDto.setPicSequence(partResp.getNum());
						partInfoDto.setPartCode(partResp.getRealPid());
						partInfoDto.setPartName(partResp.getLabel());
						partInfoDto.setRemark(partResp.getDetail());
						partInfoDtoList.add(partInfoDto);
					})
			);
		}
		return partInfoDtoList;
	}

	@Override
	public CarModelPicMarkDto getCarModelPicMarkDto(String extraParam, CloudSupplierUserDto userDto) {
		ZeroSubGroupImageResp zeroSubGroupImageResp = zeroEPCWrapperService.getZeroSubGroupImageResp(extraParam, userDto);
		if (Objects.nonNull(zeroSubGroupImageResp)) {
			CarModelPicMarkDto carModelPicMarkDto = new CarModelPicMarkDto();
			//007大图的图片地址
			carModelPicMarkDto.setPicLocalPath(zeroSubGroupImageResp.getImgUrl());
			return carModelPicMarkDto;
		}
		return null;
	}

	@Override
	public PartDetailDto getPartDetailDtoByZero(String partCode, String carBrandName, CloudSupplierUserDto userDto) {
		PartDetailDto partDetailDto = new PartDetailDto();
		//查询零零汽数据
		ZeroPartSearchResp zeroPartSearchResp = zeroEPCWrapperService.getZeroPartSearchResp(carBrandName, partCode, userDto);
		if (Objects.nonNull(zeroPartSearchResp) && CollectionUtils.isNotEmpty(zeroPartSearchResp.getData())) {
			List<PartInfoDto> partInfoDtos = Lists.newArrayList();
			zeroPartSearchResp.getData().forEach(p -> {
				PartInfoDto partInfo = new PartInfoDto();
				partInfo.setPartCode(partCode);
				partInfo.setPartName(p.getLabel());
				partInfo.setOemBrand(carBrandName);
				partInfo.setRemark(p.getRemark());
				partInfo.setPrice(p.getPrices());
				partInfoDtos.add(partInfo);
			});
			partDetailDto.setPartInfoList(partInfoDtos);
		}else {
			LOGGER.info("partCode:{},brand:{}调007接口未查询到配件信息...", partCode, carBrandName);
		}
		if (CollectionUtils.isNotEmpty(partDetailDto.getPartInfoList())){
			LOGGER.info("调零零汽接口查询partCode:{}的适用车型的相关数据...");
			//查询零零汽数据
			List<List<ZeroPartCarResp>> zeroPartCarResp = zeroEPCWrapperService.getZeroPartCarResp(carBrandName, partCode, userDto);
			if (CollectionUtils.isNotEmpty(zeroPartCarResp)) {
				List<PartSuitCarDto> partSuitCarDtoList = Lists.newArrayList();
				List<ZeroPartCarResp> zeroPartCarRespList = Lists.newArrayList();
				zeroPartCarResp.forEach(resp -> zeroPartCarRespList.add(resp.get(0)));
				Map<String, List<ZeroPartCarResp>> map = zeroPartCarRespList.stream().collect(Collectors.groupingBy(ZeroPartCarResp :: getCarsModel));
				map.forEach((k, v) -> {
					PartSuitCarDto suitCarDto = new PartSuitCarDto();
					ZeroPartCarResp resp = v.get(0);
					suitCarDto.setCarModelId(0);
					suitCarDto.setCarBrandName(carBrandName);
					suitCarDto.setCarModelName(resp.getCarsModel());
					suitCarDto.setPicName("0");
					suitCarDto.setPicNum("0");
					suitCarDto.setSubAssemblyName(resp.getGroupName());
					suitCarDto.setExtraParam(this.getExtraParam(resp.getUriParam()));
					partSuitCarDtoList.add(suitCarDto);
				});
				partDetailDto.setSuitCarList(partSuitCarDtoList);
			}
		}
		return partDetailDto;
	}

	private Map<String, String> getConfig(List<String> mains) {
		if (CollectionUtils.isEmpty(mains)) {
			return null;
		}
		Map<String, String> result = Maps.newHashMap();
		mains.forEach(value -> {
			List<String> content = SPLITTER.splitToList(value);
			if (content.size() == 2) {
				result.put(StringUtils.deleteWhitespace(content.get(0)), StringUtils.deleteWhitespace(content.get(1)));
			}
		});
		return result;
	}

	private List<PartCategoryDto> getPartCategoryDtoList(List<ZeroGroupResp> zeroGroupRespList) {
		List<PartCategoryDto> partCategoryDtoList = Lists.newArrayList();
		zeroGroupRespList.forEach(resp -> {
			PartCategoryDto partCategoryDto = new PartCategoryDto();
			partCategoryDto.setType(CategoryTypeEnum.零零汽分类.getType());
			partCategoryDto.setCategoryName(resp.getGroupname());
			partCategoryDto.setExtraParam(this.getExtraParam(resp.getUriParam()));
			partCategoryDtoList.add(partCategoryDto);
		});
		return partCategoryDtoList;
	}

	private String getExtraParam(UriParam uriParam) {
		String uri = String.format("vin=%s&auth=%s&code=%s", StringUtils.isNoneBlank(uriParam.getVin()) ? uriParam.getVin().toUpperCase() : "",
				uriParam.getAuth(), uriParam.getCode());
		//将&和=进行替换
		try {
			uri = Base64.encode(uri.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return uri;
	}

}
