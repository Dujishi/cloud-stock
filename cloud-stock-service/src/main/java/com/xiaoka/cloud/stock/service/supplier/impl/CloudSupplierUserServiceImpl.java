package com.xiaoka.cloud.stock.service.supplier.impl;

import com.google.common.collect.Lists;
import com.xiaoka.cloud.stock.core.epc.repo.UserBrandRepo;
import com.xiaoka.cloud.stock.core.epc.repo.entity.UserBrandEntity;
import com.xiaoka.cloud.stock.core.supplier.constant.SupplierRoleEnum;
import com.xiaoka.cloud.stock.core.supplier.repo.CloudSupplierCompanyRepo;
import com.xiaoka.cloud.stock.core.supplier.repo.CloudSupplierUserRepo;
import com.xiaoka.cloud.stock.core.supplier.repo.entity.CloudSupplierCompanyEntity;
import com.xiaoka.cloud.stock.core.supplier.repo.entity.CloudSupplierUserEntity;
import com.xiaoka.cloud.stock.service.core.util.AssertUtil;
import com.xiaoka.cloud.stock.service.core.util.BeanUtils;
import com.xiaoka.cloud.stock.service.supplier.CloudSupplierUserService;
import com.xiaoka.cloud.stock.service.supplier.dto.CloudSupplierUserDto;
import com.xiaoka.cloud.stock.service.supplier.dto.SimpleSupplierUserDto;
import com.xiaoka.cloud.stock.service.supplier.param.SupplierUserParam;
import com.xiaoka.cloud.stock.service.wrapper.soa.ShopSoaWrapperService;
import com.xiaoka.shop.care.soa.api.shop.result.ShopResult;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * Do something
 *
 * @author gancao 2017/11/14 11:16
 * @see [相关类/方法]
 * @since [版本号]
 */
@Service
public class CloudSupplierUserServiceImpl implements CloudSupplierUserService {

	private static final Logger logger = LoggerFactory.getLogger(CloudSupplierUserServiceImpl.class);

	@Resource
	private ShopSoaWrapperService shopSoaWrapperService;
	@Resource
	private CloudSupplierUserRepo cloudSupplierUserRepo;
	@Resource
	private CloudSupplierCompanyRepo cloudSupplierCompanyRepo;
	@Resource
	private UserBrandRepo userBrandRepo;

	@Override
	public boolean isSuperAdmin(Integer id) {
		CloudSupplierUserEntity entity = cloudSupplierUserRepo.selectById(id);
		if (entity!= null && SupplierRoleEnum.超级管理员.getRoleId().equals(entity.getRoleId())) {
			return true;
		}
		return false;
	}

	@Override
	public CloudSupplierUserDto getCloudSupplierUserDtoByPhone(String phone) {
		CloudSupplierUserEntity userEntity = cloudSupplierUserRepo.selectByPhone(phone);
		AssertUtil.assertNotNullWithApiException(userEntity, "该手机号无登录权限");
		ShopResult shopResult = shopSoaWrapperService.searchShopById(userEntity.getSupplierId());
		AssertUtil.assertNotNullWithApiException(shopResult, "该手机号对应的供应商不存在");
		CloudSupplierUserDto cloudSupplierUserDto = getCloudSupplierUserDto(shopResult, userEntity);
		setSupplierCompanyInfo(cloudSupplierUserDto);
		return cloudSupplierUserDto;
	}

	@Override
	public boolean checkPhone(String phone) {
		AssertUtil.assertNotNullWithApiException(phone, "手机号不能为空");
		int count = cloudSupplierUserRepo.countByPhone(phone);
		return count > 0;
	}

	@Override
	public List<SimpleSupplierUserDto> getCloudSupplierUserList(Integer supplierId, CloudSupplierUserDto userDto) {
		List<CloudSupplierUserEntity> userEntityList = cloudSupplierUserRepo.selectBySupplierId(supplierId);
		if (CollectionUtils.isNotEmpty(userEntityList)) {
			List<SimpleSupplierUserDto> userDtoList = BeanUtils.transformList(SimpleSupplierUserDto.class, userEntityList);
			userDtoList.forEach(entity -> entity.setRoleName(SupplierRoleEnum.getRoleNameById(entity.getRoleId())));
			handlerUser(userDtoList, userDto);
			return userDtoList;
		}
		return null;
	}

	@Override
	public void saveOrUpdateCloudSupplierUser(SupplierUserParam param, CloudSupplierUserDto userDto) {
		CloudSupplierUserEntity entity = BeanUtils.transform(CloudSupplierUserEntity.class, param);
		checkPermission(entity, userDto);
		if (Objects.nonNull(entity.getId())) {//更新
			cloudSupplierUserRepo.update(entity);
			//更新session
		} else {//新增
			//判断新增手机号是否已关联供应商
			AssertUtil.assertTrueWithApiException(!checkPhone(entity.getPhone()), "该手机号已存在关联的供应商");
			cloudSupplierUserRepo.insert(entity);
		}
	}

	@Override
	public void deleteCloudSupplierUser(Integer id, CloudSupplierUserDto userDto) {
		CloudSupplierUserEntity entity = cloudSupplierUserRepo.selectById(id);
		checkPermission(entity, userDto);
		cloudSupplierUserRepo.deleteSupplierUser(id);
	}

	@Override
	public void setBrandAuth(Integer userId, List<Integer> brandIds, String operator) {
		AssertUtil.assertTrueWithApiException(userId != null, "用户不能为空");
		AssertUtil.assertTrueWithApiException(CollectionUtils.isNotEmpty(brandIds), "品牌不能为空");

		UserBrandEntity param = new UserBrandEntity();
		param.setCsUserId(userId);
		//先删除原先权限
		userBrandRepo.delete(param);
		//插入新的权限
		List<UserBrandEntity> data = Lists.newArrayList();
		brandIds.forEach(x -> {
			UserBrandEntity ub = new UserBrandEntity();
			ub.setCsUserId(userId);
			ub.setBrandId(x);
			ub.setCreateBy(operator);
			data.add(ub);
		});
		userBrandRepo.batchInsert(data);
	}

	/**
	 * 组装供应商及用户基本信息
	 *
	 * @param shopResult
	 * @return
	 */
	private CloudSupplierUserDto getCloudSupplierUserDto(ShopResult shopResult, CloudSupplierUserEntity userEntity) {
		CloudSupplierUserDto cloudSupplierUserDto = new CloudSupplierUserDto();
		//供应商基础信息
		cloudSupplierUserDto.setId(userEntity.getId());
		cloudSupplierUserDto.setSupplierName(shopResult.getCareShopName());
		cloudSupplierUserDto.setSupplierId(shopResult.getCareShopId());
		cloudSupplierUserDto.setSupplierCity(shopResult.getCity());
		cloudSupplierUserDto.setSupplierProv(shopResult.getProv());
		cloudSupplierUserDto.setAddress(shopResult.getAddress());
		cloudSupplierUserDto.setCompanyType(1);
		//用户相关信息
		cloudSupplierUserDto.setName(userEntity.getName());
		cloudSupplierUserDto.setPhone(userEntity.getPhone());
		cloudSupplierUserDto.setRoleId(userEntity.getRoleId());
		cloudSupplierUserDto.setRoleName(SupplierRoleEnum.getRoleNameById(userEntity.getRoleId()));
		return cloudSupplierUserDto;
	}

	/**
	 * 供应商总公司设置
	 *
	 * @param dto
	 */
	private void setSupplierCompanyInfo(CloudSupplierUserDto dto) {
		CloudSupplierCompanyEntity companyEntity = cloudSupplierCompanyRepo.selectBySupplierId(dto.getSupplierId());//子供应商上级总公司
		if (Objects.nonNull(companyEntity)) {
			dto.setCompanyId(companyEntity.getId());
			dto.setCompanyName(companyEntity.getCompanyName());
		}
	}

	/**
	 * 判断当前帐号对操作帐号是否有权限（roleId越小权限越大）
	 *
	 * @param entity
	 */
	private void checkPermission(CloudSupplierUserEntity entity, CloudSupplierUserDto userDto) {
		AssertUtil.assertNotNullWithApiException(entity, "该用户不存在或已被删除");
		if (Objects.nonNull(userDto)) {
			entity.setSupplierId(userDto.getSupplierId());
			//修改非本人帐号只能上级帐号有权限，修改本人的帐号只能改用户名
			if (Objects.equals(entity.getId(), userDto.getId())) {
				if (StringUtils.isNotBlank(entity.getPhone())) {
					AssertUtil.assertTrueWithApiException(entity.getPhone().equals(userDto.getPhone()), "不能修改手机号");
				}
				if (Objects.nonNull(entity.getRoleId())) {
					AssertUtil.assertTrueWithApiException(Objects.equals(entity.getRoleId(), userDto.getRoleId()), "不能修改角色");
				}
			} else {
				AssertUtil.assertTrueWithApiException(userDto.getRoleId() < entity.getRoleId(), "您对该帐号无操作权限");
			}
		}
	}

	private void handlerUser(List<SimpleSupplierUserDto> userDtoList,CloudSupplierUserDto userDto) {
		if (Objects.nonNull(userDto)) {
			userDtoList.forEach(dto -> {
				//修改只操作本人和下级的权限， 删除只能操作下级权限
				if (Objects.equals(dto.getId(), userDto.getId())){
					dto.setIsloginUser(1);
					dto.setCanEdit(1);
				}else {
					//登录帐号的权限大于该帐号，拥有编辑和删除的权限
					if (dto.getRoleId() > userDto.getRoleId()){
						dto.setCanEdit(1);
						dto.setCanDelete(1);
					}
				}
			});
		}
	}
}
