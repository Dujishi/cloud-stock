package com.xiaoka.cloud.stock.service.crawl.linglingqi.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.xiaoka.cloud.stock.core.crawl.repo.entity.*;
import com.xiaoka.cloud.stock.core.crawl.service.*;
import com.xiaoka.cloud.stock.service.crawl.linglingqi.dto.*;
import com.xiaoka.cloud.stock.service.crawl.linglingqi.service.ZeroDataCollectService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author zhouze
 * @date 2017/12/14
 * @see [相关类/方法]
 * @since [版本号]
 */
@Service
public class ZeroDataCollectServiceImpl implements ZeroDataCollectService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ZeroDataCollectServiceImpl.class);

	@Resource
	private ZeroBrandService           zeroBrandService;
	@Resource
	private ZeroCarModelService        zeroCarModelService;
	@Resource
	private ZeroGroupService           zeroGroupService;
	@Resource
	private ZeroSubGroupService        zeroSubGroupService;
	@Resource
	private ZeroSubGroupImgService     zeroSubGroupImgService;
	@Resource
	private ZeroSubGroupPartsService   zeroSubGroupPartsService;
	@Resource
	private ZeroPartInfoService        zeroPartInfoService;
	@Resource
	private ZeroPartPriceService       zeroPartPriceService;
	@Resource
	private ZeroAdapterCarModelService zeroAdapterCarModelService;


	@Override
	public void saveBrands(List<ZeroBrandDto> list) {
		if (CollectionUtils.isEmpty(list)) {
			return;
		}

		List<String>          brands           = list.stream().map(ZeroBrandDto::getBrand).collect(Collectors.toList());
		List<ZeroBrandEntity> hasExistedBrands = zeroBrandService.selectByBrands(brands);
		List<ZeroBrandEntity> insertEntities   = Lists.newArrayList();
		if (CollectionUtils.isNotEmpty(hasExistedBrands)) {
			list.stream().filter(p -> hasExistedBrands.stream().noneMatch(x -> Objects.equals(x.getBrand(), p.getBrand())))
					.forEach(p -> {
						ZeroBrandEntity zeroBrandEntity = buildZeroBrandEntity(p);
						insertEntities.add(zeroBrandEntity);
					});
		} else {
			list.stream().forEach(p -> {
				ZeroBrandEntity zeroBrandEntity = buildZeroBrandEntity(p);
				insertEntities.add(zeroBrandEntity);
			});
		}
		if (CollectionUtils.isNotEmpty(insertEntities)) {
			zeroBrandService.insertList(insertEntities);
		}
	}

	private ZeroBrandEntity buildZeroBrandEntity(ZeroBrandDto p) {
		ZeroBrandEntity zeroBrandEntity = new ZeroBrandEntity();
		zeroBrandEntity.setBrand(p.getBrand());
		zeroBrandEntity.setName(p.getName());
		zeroBrandEntity.setUri(p.getUri());
		return zeroBrandEntity;
	}

	@Override
	public void saveCarModels(List<ZeroCarModelDto> list) {
		if (CollectionUtils.isEmpty(list)) {
			return;
		}

		List<ZeroCarModelEntity> carModelParams = Lists.newArrayList();
		list.forEach(p -> {
			ZeroCarModelEntity entity = new ZeroCarModelEntity();
			entity.setBrand(p.getBrand());
			entity.setCarModel(p.getCarModel());
			entity.setMarket(p.getMarket());
			carModelParams.add(entity);
		});
		List<ZeroCarModelEntity> hasExistedCarModels = zeroCarModelService.selectByCarModels(carModelParams);
		if (CollectionUtils.isNotEmpty(hasExistedCarModels)) {
			List<ZeroCarModelEntity> insertEntities = carModelParams.stream().filter(
					p -> hasExistedCarModels.stream().noneMatch(
							x -> Objects.equals(p.getBrand(), x.getBrand()) && Objects.equals(p.getCarModel(), x.getCarModel())
					)
			).collect(Collectors.toList());
			if (CollectionUtils.isNotEmpty(insertEntities)) {
				zeroCarModelService.insertList(insertEntities);
			}
		} else {
			List<ZeroCarModelEntity> insertEntities = Lists.newArrayList();
			insertEntities.addAll(carModelParams);
			if (CollectionUtils.isNotEmpty(insertEntities)) {
				zeroCarModelService.insertList(insertEntities);
			}
		}
	}

	@Override
	public void saveGroups(List<ZeroGroupDto> list) {
		if (CollectionUtils.isEmpty(list)) {
			return;
		}
		LOGGER.info("保存主组信息及零件组信息:{}", JSON.toJSONString(list));

		list.forEach(p -> {
			//保存主组信息
			ZeroGroupEntity       zeroGroupEntity  = buildZeroGroupEntity(p);
			List<ZeroGroupEntity> hasExistedGroups = zeroGroupService.selectByGroupName(p.getGroupName(), p.getcId());
			Integer               groupId;
			if (CollectionUtils.isNotEmpty(hasExistedGroups)) {
				List<ZeroGroupEntity> actualList = hasExistedGroups.stream()
						.filter(judgeZeroGroupPredicate(p)).collect(Collectors.toList());
				if (CollectionUtils.isNotEmpty(actualList)) {
					groupId = actualList.get(0).getId();
				} else {
					groupId = zeroGroupService.insert(zeroGroupEntity);
				}
			} else {
				groupId = zeroGroupService.insert(zeroGroupEntity);
			}
			p.setGroupId(groupId);

			//保存零件组信息
			List<ZeroSubGroupDto> zeroSubGroups = p.getZeroSubGroups();
			if (CollectionUtils.isEmpty(zeroSubGroups)) {
				return;
			}
			handleSaveZeroSubGroups(groupId, zeroSubGroups);

		});
	}

	private ZeroSubGroupEntity buildZeroSubGroupEntity(Integer groupId, ZeroSubGroupDto zsg) {
		ZeroSubGroupEntity zeroSubGroupEntity = new ZeroSubGroupEntity();
		zeroSubGroupEntity.setcId(zsg.getcId());
		zeroSubGroupEntity.setGroupId(groupId);
		zeroSubGroupEntity.setBrand(zsg.getBrand());
		zeroSubGroupEntity.setCarModel(zsg.getCarModel());
		zeroSubGroupEntity.setGroupImg(zsg.getGroupImg());
		zeroSubGroupEntity.setMarket(zsg.getMarket());
		zeroSubGroupEntity.setGroupName(zsg.getGroupName());
		zeroSubGroupEntity.setSubGroup(zsg.getSubGroup());
		zeroSubGroupEntity.setSubGroupName(zsg.getSubGroupName());
		zeroSubGroupEntity.setSubGroupUrl(zsg.getSubGroupUrl());
		zeroSubGroupEntity.setYear(zsg.getYear());
		zeroSubGroupEntity.setSubDesc(zsg.getSubDesc());
		zeroSubGroupEntity.setSubMid(zsg.getSubMid());
		zeroSubGroupEntity.setSubModel(zsg.getSubModel());
		return zeroSubGroupEntity;
	}

	private Predicate<ZeroGroupEntity> judgeZeroGroupPredicate(ZeroGroupDto p) {
		return x -> Objects.equals(x.getBrand(), p.getBrand())
				&& Objects.equals(x.getCarModel(), p.getCarModel())
				&& Objects.equals(x.getEngine(), p.getEngine())
				&& Objects.equals(x.getGearBox(), p.getGearBox())
				&& Objects.equals(x.getMarket(), p.getMarket())
				&& Objects.equals(x.getYear(), p.getYear());
	}

	private ZeroGroupEntity buildZeroGroupEntity(ZeroGroupDto p) {
		ZeroGroupEntity zeroGroupEntity = new ZeroGroupEntity();
		zeroGroupEntity.setcId(p.getcId());
		zeroGroupEntity.setBrand(p.getBrand());
		zeroGroupEntity.setCarModel(p.getCarModel());
		zeroGroupEntity.setEngine(p.getEngine());
		zeroGroupEntity.setYear(p.getYear());
		zeroGroupEntity.setGearBox(p.getGearBox());
		zeroGroupEntity.setGroupName(p.getGroupName());
		zeroGroupEntity.setGroupImg(p.getGroupImg());
		zeroGroupEntity.setGroupNum(p.getGroupNum());
		zeroGroupEntity.setMarket(p.getMarket());
		zeroGroupEntity.setGroupNum(p.getGroupNum());
		return zeroGroupEntity;
	}

	@Override
	public void saveSubGroups(List<ZeroSubGroupDto> subGroups) {
		if (CollectionUtils.isEmpty(subGroups)) {
			return;
		}
		subGroups.stream().collect(Collectors.groupingBy(ZeroSubGroupDto::getGroupId))
				.forEach((groupId, zeroSubGroups) -> {
					handleSaveZeroSubGroups(groupId, zeroSubGroups);
				});
	}

	private void handleSaveZeroSubGroups(Integer groupId, List<ZeroSubGroupDto> zeroSubGroups) {
		List<ZeroSubGroupEntity> subGroupEntities = Lists.newArrayList();
		zeroSubGroups.forEach(zsg -> {
			ZeroSubGroupEntity zeroSubGroupEntity = buildZeroSubGroupEntity(groupId, zsg);
			subGroupEntities.add(zeroSubGroupEntity);
		});

		List<ZeroSubGroupEntity> hasExistedSubGroups = zeroSubGroupService.selectHasExistedSubGroup(subGroupEntities);
		if (CollectionUtils.isNotEmpty(hasExistedSubGroups)) {
			List<ZeroSubGroupEntity> insertSubGroups = subGroupEntities.stream().filter(sg ->
					hasExistedSubGroups.stream().noneMatch(existSubGroup ->
							Objects.equals(sg.getGroupId(), existSubGroup.getGroupId())
									&& Objects.equals(sg.getSubGroupName(), existSubGroup.getSubGroupName())
					)
			).collect(Collectors.toList());
			if (CollectionUtils.isNotEmpty(insertSubGroups)) {
				zeroSubGroupService.insertList(insertSubGroups);
			}
		} else {
			zeroSubGroupService.insertList(subGroupEntities);
		}

		//查询并返回主键
		List<ZeroSubGroupEntity> alreadySaveList = zeroSubGroupService.selectHasExistedSubGroup(subGroupEntities);
		if (CollectionUtils.isNotEmpty(alreadySaveList)) {
			zeroSubGroups.stream().forEach(p -> {
				p.setGroupId(groupId);
				ZeroSubGroupEntity alreadySubGroup = alreadySaveList.stream().filter(x ->
						Objects.equals(x.getGroupId(), p.getGroupId())
								&& Objects.equals(x.getSubGroupName(), p.getSubGroupName())
				).findFirst().orElse(null);
				if (null != alreadySubGroup) {
					p.setSubGroupId(alreadySubGroup.getId());
				}
			});
		}
	}

	@Override
	public void saveSubGroupImgs(List<ZeroSubGroupImgDto> list) {
		if (CollectionUtils.isEmpty(list)) {
			return;
		}

		List<ZeroSubGroupImgEntity> subGroupImgs = Lists.newArrayList();
		list.forEach(x -> {
			ZeroSubGroupImgEntity zeroSubGroupImgEntity = buildSubGroupImg(x);
			subGroupImgs.add(zeroSubGroupImgEntity);
		});

		List<ZeroSubGroupImgEntity> hasExistedImgs = zeroSubGroupImgService.selectByConditions(subGroupImgs);
		List<ZeroSubGroupImgEntity> insertEntities = Lists.newArrayList();
		if (CollectionUtils.isNotEmpty(hasExistedImgs)) {
			subGroupImgs.stream().filter(p -> hasExistedImgs.stream().noneMatch(
					x -> Objects.equals(x.getGroupId(), p.getGroupId())
							&& Objects.equals(x.getSubGroupId(), p.getSubGroupId())
							&& Objects.equals(x.getImgUrl(), p.getImgUrl())
							&& Objects.equals(x.getItId(), p.getItId())
							&& Objects.equals(x.getX1(), p.getX1())
							&& Objects.equals(x.getY1(), p.getY1())
			)).forEach(insertEntities::add);
		} else {
			insertEntities.addAll(subGroupImgs);
		}
		if (CollectionUtils.isNotEmpty(insertEntities)) {
			zeroSubGroupImgService.insertList(insertEntities);
		}
	}

	private ZeroSubGroupImgEntity buildSubGroupImg(ZeroSubGroupImgDto x) {
		ZeroSubGroupImgEntity zeroSubGroupImgEntity = new ZeroSubGroupImgEntity();
		zeroSubGroupImgEntity.setGroupId(x.getGroupId());
		zeroSubGroupImgEntity.setGroupName(x.getGroupName());
		zeroSubGroupImgEntity.setSubGroup(x.getSubgroup());
		zeroSubGroupImgEntity.setSubGroupId(x.getSubGroupId());
		zeroSubGroupImgEntity.setSubGroupName(x.getSubGroupName());
		zeroSubGroupImgEntity.setImgUrl(x.getImgUrl());
		zeroSubGroupImgEntity.setWidth(x.getWidth());
		zeroSubGroupImgEntity.setHeight(x.getHeight());
		zeroSubGroupImgEntity.setItId(x.getItId());
		zeroSubGroupImgEntity.setX1(x.getX1());
		zeroSubGroupImgEntity.setX2(x.getX2());
		zeroSubGroupImgEntity.setY1(x.getY1());
		zeroSubGroupImgEntity.setY2(x.getY2());
		return zeroSubGroupImgEntity;
	}

	@Override
	public void saveSubGroupParts(List<ZeroSubGroupPartsDto> list) {
		if (CollectionUtils.isEmpty(list)) {
			return;
		}

		List<ZeroSubGroupPartsEntity> subGroupParts = Lists.newArrayList();
		list.forEach(x -> {
			ZeroSubGroupPartsEntity zeroSubGroupImgEntity = buildSubGroupPart(x);
			subGroupParts.add(zeroSubGroupImgEntity);
		});

		List<ZeroSubGroupPartsEntity> hasExistedParts = zeroSubGroupPartsService.selectByConditions(subGroupParts);
		List<ZeroSubGroupPartsEntity> insertEntities  = Lists.newArrayList();
		if (CollectionUtils.isNotEmpty(hasExistedParts)) {
			subGroupParts.stream().filter(p -> hasExistedParts.stream().noneMatch(
					x -> Objects.equals(x.getGroupId(), p.getGroupId())
							&& Objects.equals(x.getSubGroupId(), p.getSubGroupId())
							&& Objects.equals(x.getPid(), p.getPid())
			)).forEach(insertEntities::add);
		} else {
			insertEntities.addAll(subGroupParts);
		}
		if (CollectionUtils.isNotEmpty(insertEntities)) {
			zeroSubGroupPartsService.insertList(insertEntities);
		}
	}

	private ZeroSubGroupPartsEntity buildSubGroupPart(ZeroSubGroupPartsDto x) {
		ZeroSubGroupPartsEntity partsEntity = new ZeroSubGroupPartsEntity();
		partsEntity.setcId(x.getcId());
		partsEntity.setPid(x.getPid());
		partsEntity.setRealPid(x.getRealPid());
		partsEntity.setBrand(x.getBrand());
		partsEntity.setCarModel(x.getCarModel());
		partsEntity.setGroupId(x.getGroupId());
		partsEntity.setGroupImg(x.getGroupImg());
		partsEntity.setGroupName(x.getGroupName());
		partsEntity.setSubGroup(x.getSubGroup());
		partsEntity.setSubGroupId(x.getSubGroupId());
		partsEntity.setSubGroupName(x.getSubGroupName());
		partsEntity.setImgUrl(x.getImgUrl());
		partsEntity.setYear(x.getYear());
		partsEntity.setMarket(x.getMarket());
		partsEntity.setItId(x.getItId());
		partsEntity.setNum(x.getNum());
		partsEntity.setCount(x.getCount());
		partsEntity.setLabelName(x.getLabelName());
		return partsEntity;
	}

	@Override
	public void savePartInfos(List<ZeroPartInfoDto> list) {
		if (CollectionUtils.isEmpty(list)) {
			return;
		}

		List<ZeroPartInfoEntity> subPartInfos = Lists.newArrayList();
		list.forEach(x -> {
			ZeroPartInfoEntity zeroPartInfoEntity = buildPartInfo(x);
			subPartInfos.add(zeroPartInfoEntity);
		});

		List<ZeroPartInfoEntity> hasExistedParts = zeroPartInfoService.selectByConditions(subPartInfos);
		List<ZeroPartInfoEntity> insertEntities  = Lists.newArrayList();
		List<ZeroPartInfoEntity> updateEntities  = Lists.newArrayList();
		if (CollectionUtils.isNotEmpty(hasExistedParts)) {
			subPartInfos.stream().filter(p -> hasExistedParts.stream().noneMatch(
					x -> Objects.equals(x.getBrand(), p.getBrand())
							&& Objects.equals(x.getPid(), p.getPid())
			)).forEach(insertEntities::add);
			subPartInfos.stream().filter(p -> hasExistedParts.stream().anyMatch(
					x -> Objects.equals(x.getBrand(), p.getBrand())
							&& Objects.equals(x.getPid(), p.getPid())
			)).forEach(updateEntities::add);
		} else {
			insertEntities.addAll(subPartInfos);
		}
		if (CollectionUtils.isNotEmpty(insertEntities)) {
			zeroPartInfoService.insertList(insertEntities);
		}
		if (CollectionUtils.isNotEmpty(updateEntities)) {
			zeroPartInfoService.updateList(updateEntities);
		}
	}

	private ZeroPartInfoEntity buildPartInfo(ZeroPartInfoDto x) {
		ZeroPartInfoEntity zeroPartInfoEntity = new ZeroPartInfoEntity();
		zeroPartInfoEntity.setBrand(x.getBrand());
		zeroPartInfoEntity.setPid(x.getPid());
		zeroPartInfoEntity.setPidLabel(x.getPidLabel());
		zeroPartInfoEntity.setPidModel(x.getPidModel());
		zeroPartInfoEntity.setRealPid(x.getRealPid());
		zeroPartInfoEntity.setPidRemark(x.getPidRemark());
		return zeroPartInfoEntity;
	}

	@Override
	public void savePartPrices(List<ZeroPartPriceDto> list) {
		if (CollectionUtils.isEmpty(list)) {
			return;
		}

		List<ZeroPartPriceEntity> subPartPrices = Lists.newArrayList();
		list.forEach(x -> {
			ZeroPartPriceEntity zeroPartInfoEntity = buildPartPrice(x);
			subPartPrices.add(zeroPartInfoEntity);
		});

		List<ZeroPartPriceEntity> hasExistedParts = zeroPartPriceService.selectByConditions(subPartPrices);
		List<ZeroPartPriceEntity> insertEntities  = Lists.newArrayList();
		List<ZeroPartPriceEntity> updateEntities  = Lists.newArrayList();
		if (CollectionUtils.isNotEmpty(hasExistedParts)) {
			subPartPrices.stream().filter(p -> hasExistedParts.stream().noneMatch(
					x -> Objects.equals(x.getBrand(), p.getBrand())
							&& Objects.equals(x.getPid(), p.getPid())
			)).forEach(insertEntities::add);
			subPartPrices.stream().filter(p -> hasExistedParts.stream().anyMatch(
					x -> Objects.equals(x.getBrand(), p.getBrand())
							&& Objects.equals(x.getPid(), p.getPid())
			)).forEach(updateEntities::add);
		} else {
			insertEntities.addAll(subPartPrices);
		}
		if (CollectionUtils.isNotEmpty(updateEntities)) {
			zeroPartPriceService.updateList(updateEntities);
		}

		if (CollectionUtils.isNotEmpty(insertEntities)) {
			zeroPartPriceService.insertList(insertEntities);
		}
	}

	private ZeroPartPriceEntity buildPartPrice(ZeroPartPriceDto x) {
		ZeroPartPriceEntity partPriceEntity = new ZeroPartPriceEntity();
		partPriceEntity.setAmount(x.getAmount());
		partPriceEntity.setBrand(x.getBrand());
		partPriceEntity.setPid(x.getPid());
		partPriceEntity.setChannel(x.getChannel());
		partPriceEntity.setCostPrice(x.getCostPrice());
		partPriceEntity.setEotPrice(x.getEotPrice());
		partPriceEntity.setPrice(x.getPrice());
		partPriceEntity.setFactoryType(x.getFactoryType());
		partPriceEntity.setLocation(x.getLocation());
		partPriceEntity.setMill(x.getMill());
		partPriceEntity.setOrigin(x.getOrigin());
		partPriceEntity.setPartType(x.getPartType());
		partPriceEntity.setRemark(x.getRemark());
		partPriceEntity.setSupplier(x.getSupplier());
		return partPriceEntity;
	}

	@Override
	public void saveAdapterCarModels(List<ZeroAdapterCarModelDto> list) {
		if (CollectionUtils.isEmpty(list)) {
			return;
		}

		List<ZeroAdapterCarModelEntity> subPartPrices = Lists.newArrayList();
		list.forEach(x -> {
			ZeroAdapterCarModelEntity zeroAdapterCarModelEntity = buildAdapterCarModel(x);
			subPartPrices.add(zeroAdapterCarModelEntity);
		});

		List<ZeroAdapterCarModelEntity> hasExistedParts = zeroAdapterCarModelService.selectByConditions(subPartPrices);
		List<ZeroAdapterCarModelEntity> insertEntities  = Lists.newArrayList();
		List<ZeroAdapterCarModelEntity> updateEntities  = Lists.newArrayList();
		if (CollectionUtils.isNotEmpty(hasExistedParts)) {
			subPartPrices.stream().filter(p -> hasExistedParts.stream().noneMatch(
					x -> Objects.equals(x.getBrand(), p.getBrand())
							&& Objects.equals(x.getPid(), p.getPid())
							&& Objects.equals(x.getCarModel(), p.getCarModel())
			)).forEach(insertEntities::add);
			subPartPrices.stream().filter(p -> hasExistedParts.stream().anyMatch(
					x -> Objects.equals(x.getBrand(), p.getBrand())
							&& Objects.equals(x.getPid(), p.getPid())
							&& Objects.equals(x.getCarModel(), p.getCarModel())
			)).forEach(updateEntities::add);
		} else {
			insertEntities.addAll(subPartPrices);
		}
		if (CollectionUtils.isNotEmpty(insertEntities)) {
			zeroAdapterCarModelService.insertList(insertEntities);
		}
		if (CollectionUtils.isNotEmpty(updateEntities)) {
			zeroAdapterCarModelService.updateList(updateEntities);
		}
	}

	private ZeroAdapterCarModelEntity buildAdapterCarModel(ZeroAdapterCarModelDto x) {
		ZeroAdapterCarModelEntity adapterCarModel = new ZeroAdapterCarModelEntity();
		adapterCarModel.setBrand(x.getBrand());
		adapterCarModel.setPid(x.getPid());
		adapterCarModel.setCarModel(x.getCarModel());
		adapterCarModel.setGroupName(x.getGroupName());
		adapterCarModel.setMainGroupName(x.getMainGroupName());
		adapterCarModel.setMarket(x.getMarket());
		adapterCarModel.setYear(x.getYear());
		return adapterCarModel;
	}
}
