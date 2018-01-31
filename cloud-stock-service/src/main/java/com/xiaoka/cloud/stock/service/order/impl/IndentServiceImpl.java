package com.xiaoka.cloud.stock.service.order.impl;

import com.xiaoka.cloud.stock.core.order.CsCustomerHintRepo;
import com.xiaoka.cloud.stock.core.order.CsIndentPartRepo;
import com.xiaoka.cloud.stock.core.order.CsIndentRepo;
import com.xiaoka.cloud.stock.core.order.entity.CsCustomerHintEntity;
import com.xiaoka.cloud.stock.core.order.entity.CsIndentEntity;
import com.xiaoka.cloud.stock.core.order.entity.CsIndentPartEntity;
import com.xiaoka.cloud.stock.service.core.exception.CloudStockErrorCode;
import com.xiaoka.cloud.stock.service.core.util.BeanUtils;
import com.xiaoka.cloud.stock.service.order.BigDecimalUtil;
import com.xiaoka.cloud.stock.service.order.IndentService;
import com.xiaoka.cloud.stock.service.order.IndentStatus;
import com.xiaoka.cloud.stock.service.order.dto.IndentDto;
import com.xiaoka.cloud.stock.service.order.dto.IndentPartDto;
import com.xiaoka.cloud.stock.service.supplier.dto.CloudSupplierUserDto;
import com.xiaoka.freework.help.api.ApiException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class IndentServiceImpl implements IndentService{

	private Logger logger = LoggerFactory.getLogger(IndentServiceImpl.class);

	private static final String DEFAULT_INDENT_NAME = "新建配货单";

	@Resource
	CsIndentPartRepo csIndentPartRepo;

	@Resource
	CsIndentRepo csIndentRepo;

	@Resource
	CsCustomerHintRepo csCustomerHintRepo;

	@Resource
	IndentHelper indentHelper;

	@Override public List<IndentDto> getIndentList(CloudSupplierUserDto cloudSupplierUserDto) {
		CsIndentEntity csIndentEntity = new CsIndentEntity();
		csIndentEntity.setSellerCode(indentHelper.getSellerCode(cloudSupplierUserDto.getSupplierId()));
		csIndentEntity.setSalesmanId(cloudSupplierUserDto.getId());
		csIndentEntity.setIndentStatus(IndentStatus.Created.getCode());
		List<CsIndentEntity> csIndentEntityList = csIndentRepo.select(csIndentEntity);
		return BeanUtils.transformList(IndentDto.class, csIndentEntityList);
	}

	@Override public IndentDto getIndentDetail(CloudSupplierUserDto cloudSupplierUserDto, String indentNo) {
		List<CsIndentEntity> csIndentEntityList = getIndentEntity(cloudSupplierUserDto, indentNo, IndentStatus.Created);
		if (CollectionUtils.isEmpty(csIndentEntityList)) {
			logger.warn("Invalid indentNo={} with sellerCode={} and salesmanId={}", indentNo, cloudSupplierUserDto.getSupplierId(),
					cloudSupplierUserDto.getId());
			throw new ApiException(CloudStockErrorCode.INDENT_NO_INVALID, "无效的配货单号");
		}
		IndentDto indentDto = BeanUtils.transform(IndentDto.class, csIndentEntityList.get(0));
		CsIndentPartEntity csIndentPartEntityParam = new CsIndentPartEntity();
		csIndentPartEntityParam.setIndentNo(indentDto.getIndentNo());
		csIndentPartEntityParam.setIsValid(true);
		List<CsIndentPartEntity> csIndentPartEntityList = csIndentPartRepo.select(csIndentPartEntityParam);
		indentDto.setIndentPartDtoList(BeanUtils.transformList(IndentPartDto.class, csIndentPartEntityList));
		return indentDto;
	}

	@Transactional
	@Override
	public IndentDto saveIndentDetail(CloudSupplierUserDto cloudSupplierUserDto, IndentDto indentDto) {
		List<CsIndentEntity> csIndentEntityList = getIndentEntity(cloudSupplierUserDto, indentDto.getIndentNo(), IndentStatus.Created);
		if (CollectionUtils.isEmpty(csIndentEntityList)) {
			logger.warn("Invalid indentNo={} with sellerCode={} and salesmanId={}", indentDto.getIndentNo(), cloudSupplierUserDto.getSupplierId(),
					cloudSupplierUserDto.getId());
			throw new ApiException(CloudStockErrorCode.INDENT_NO_INVALID, "无效的配货单号");
		}

		CsIndentEntity csIndentEntity = csIndentEntityList.get(0);
		CsIndentEntity csIndentEntityParam = BeanUtils.transform(CsIndentEntity.class, indentDto);
		csIndentEntityParam.setBuyerCode(indentHelper.getBuyerCodeByContactPhone(indentDto.getContactPhone()));
		csIndentEntityParam.setId(csIndentEntity.getId());
		csIndentRepo.updateBySelective(csIndentEntityParam);

		CsIndentPartEntity csIndentPartEntityParam = new CsIndentPartEntity();
		csIndentPartEntityParam.setIndentNo(csIndentEntity.getIndentNo());
		csIndentPartEntityParam.setIsValid(true);
		List<CsIndentPartEntity> csIndentPartEntityListInDb = csIndentPartRepo.select(csIndentPartEntityParam);

		List<IndentPartDto> indentPartDtoList = indentDto.getIndentPartDtoList();
		if (CollectionUtils.isNotEmpty(indentPartDtoList)) {
			for (IndentPartDto indentPartDto : indentPartDtoList) {
				CsIndentPartEntity oldCsIndentPartEntity = findCsIndentPartEntity(csIndentPartEntityListInDb, indentPartDto);
				if (oldCsIndentPartEntity != null) {
					csIndentPartEntityParam = BeanUtils.transform(CsIndentPartEntity.class, indentPartDto);
					csIndentPartEntityParam.setUpdateBy(cloudSupplierUserDto.getName());
					csIndentPartEntityParam.setBuyerCode(indentHelper.getBuyerCodeByContactPhone(indentDto.getContactPhone()));
					if (indentPartDto.getAmount() != null && indentPartDto.getUnitPrice() != null) {
						csIndentPartEntityParam.setSubtotal(BigDecimalUtil.scaleOf(indentPartDto.getAmount().multiply(indentPartDto.getUnitPrice())));
					}
					csIndentPartRepo.updateBySelective(csIndentPartEntityParam);
					csIndentPartEntityListInDb.remove(oldCsIndentPartEntity);
				} else {
					throw new ApiException(CloudStockErrorCode.INDENT_PART_NO_INVALID, "无效的配货单配件");
				}
			}
		}
		if (CollectionUtils.isNotEmpty(csIndentPartEntityListInDb)) {
			for (CsIndentPartEntity csIndentPartEntity : csIndentPartEntityListInDb) {
				csIndentPartRepo.delete(csIndentPartEntity);
			}
		}

		saveCustomerHint(cloudSupplierUserDto, indentDto);
		return indentDto;
	}

	private void saveCustomerHint(CloudSupplierUserDto cloudSupplierUserDto, IndentDto indentDto) {
		CsCustomerHintEntity csCustomerHintEntity = BeanUtils.transform(CsCustomerHintEntity.class, indentDto);
		if(StringUtils.isNotBlank(csCustomerHintEntity.getCustomerName())){
			CsCustomerHintEntity CsCustomerHintEntityParam = new CsCustomerHintEntity();
			CsCustomerHintEntityParam.setCustomerName(csCustomerHintEntity.getCustomerName());
			CsCustomerHintEntityParam.setSellerCode(csCustomerHintEntity.getSellerCode());
			if (CollectionUtils.isNotEmpty(csCustomerHintRepo.select(CsCustomerHintEntityParam))) {
				csCustomerHintEntity.setUpdateBy(cloudSupplierUserDto.getName());
				csCustomerHintRepo.updateBySelective(csCustomerHintEntity);
			} else {
				csCustomerHintEntity.setCreateBy(cloudSupplierUserDto.getName());
				csCustomerHintEntity.setSellerCode(indentHelper.getSellerCode(cloudSupplierUserDto.getSupplierId()));
				csCustomerHintEntity.setIsValid(true);
				csCustomerHintRepo.insert(csCustomerHintEntity);
			}
		}
	}

	private CsIndentPartEntity findCsIndentPartEntity(List<CsIndentPartEntity> csIndentPartEntityListInDb, IndentPartDto indentPartDto) {
		if (CollectionUtils.isEmpty(csIndentPartEntityListInDb)) {
			return null;
		}
		for (CsIndentPartEntity csIndentPartEntity : csIndentPartEntityListInDb) {
			if (csIndentPartEntity.getId().equals(indentPartDto.getId())) {
				return csIndentPartEntity;
			}
		}
		return null;

	}

	@Override public IndentPartDto addIndentPart(CloudSupplierUserDto cloudSupplierUserDto, IndentPartDto indentPartDto) {
		CsIndentPartEntity csIndentPartEntity = BeanUtils.transform(CsIndentPartEntity.class, indentPartDto);
		List<CsIndentEntity> csIndentEntityList = getIndentEntity(cloudSupplierUserDto, indentPartDto.getIndentNo(), IndentStatus.Created);
		if (CollectionUtils.isEmpty(csIndentEntityList)) {
			logger.warn("Invalid indentNo={} with sellerCode={} and salesmanId={}", indentPartDto.getIndentNo(), cloudSupplierUserDto.getSupplierId(),
					cloudSupplierUserDto.getId());
			throw new ApiException(CloudStockErrorCode.INDENT_NO_INVALID, "无效的配货单号");
		}
		CsIndentEntity csIndentEntity = csIndentEntityList.get(0);
		csIndentPartEntity.setCreateBy(cloudSupplierUserDto.getName());
		csIndentPartEntity.setSellerCode(csIndentEntity.getSellerCode());
		csIndentPartEntity.setBuyerCode(csIndentEntity.getBuyerCode());
		if (csIndentPartEntity.getAmount() != null && csIndentPartEntity.getUnitPrice() != null) {
			csIndentPartEntity.setSubtotal(BigDecimalUtil.scaleOf(csIndentPartEntity.getAmount().multiply(csIndentPartEntity.getUnitPrice())));
		}
		csIndentPartRepo.insert(csIndentPartEntity);
		indentPartDto.setId(csIndentPartEntity.getId());
		return indentPartDto;
	}

	@Override public IndentDto addIndent(CloudSupplierUserDto cloudSupplierUserDto, String indentName) {
		List<CsIndentEntity> csIndentEntityList = getIndentEntity(cloudSupplierUserDto, null, IndentStatus.Created);

		String indentNameParam = getIndentName(csIndentEntityList, indentName);

		CsIndentEntity csIndentEntity = new CsIndentEntity();
		csIndentEntity.setSellerCode(indentHelper.getSellerCode(cloudSupplierUserDto.getSupplierId()));
		csIndentEntity.setSalesmanId(cloudSupplierUserDto.getId());
		csIndentEntity.setSalesmanName(cloudSupplierUserDto.getName());
		csIndentEntity.setCreateBy(cloudSupplierUserDto.getName());
		csIndentEntity.setName(indentNameParam);
		csIndentEntity.setIsValid(true);
		csIndentEntity.setIndentStatus(IndentStatus.Created.getCode());
		csIndentRepo.insert(csIndentEntity);

		CsIndentEntity csIndentEntityParam = new CsIndentEntity();
		csIndentEntityParam.setId(csIndentEntity.getId());
		csIndentEntityParam.setIndentNo(indentHelper.getIndentNo(csIndentEntity.getId()));
		csIndentRepo.updateBySelective(csIndentEntityParam);

		csIndentEntity.setIndentNo(csIndentEntityParam.getIndentNo());
		return BeanUtils.transform(IndentDto.class, csIndentEntity);
	}

	@Override public void deleteIndent(CloudSupplierUserDto cloudSupplierUserDto, String indentNo) {
		CsIndentEntity csIndentEntity = new CsIndentEntity();
		csIndentEntity.setSellerCode(indentHelper.getSellerCode(cloudSupplierUserDto.getSupplierId()));
		csIndentEntity.setIndentNo(indentNo);
		csIndentEntity.setSalesmanId(cloudSupplierUserDto.getId());
		csIndentRepo.delete(csIndentEntity);
	}

	@Override
	public void updateIndentName(CloudSupplierUserDto cloudSupplierUserDto, String indentNo, String indentName) {
		CsIndentEntity csIndentEntity = new CsIndentEntity();
		csIndentEntity.setIndentNo(indentNo);
		csIndentEntity.setSalesmanId(cloudSupplierUserDto.getId());
		csIndentEntity.setSellerCode(indentHelper.getSellerCode(cloudSupplierUserDto.getSupplierId()));
		csIndentEntity.setName(indentName);
		csIndentRepo.updateIndentName(csIndentEntity);
	}



	@Override public void updateStatusByIndentNo(String indentNo, Integer indentStatus) {
		CsIndentEntity csIndentEntity = new CsIndentEntity();
		csIndentEntity.setIndentNo(indentNo);
		csIndentEntity.setIndentStatus(indentStatus);
		csIndentRepo.updateBySelective(csIndentEntity);
	}

	private String getIndentName(List<CsIndentEntity> csIndentEntityList, String indentName) {
		String indentParam = StringUtils.isBlank(indentName) ? DEFAULT_INDENT_NAME : indentName;
		if (CollectionUtils.isEmpty(csIndentEntityList)) {
			return indentParam;
		}
		if (DEFAULT_INDENT_NAME.equals(indentParam)) {
			Set<String> defaultIndentName = new HashSet<>();
			for (CsIndentEntity csIndentEntity : csIndentEntityList) {
				if (csIndentEntity.getName().contains(DEFAULT_INDENT_NAME)) {
					defaultIndentName.add(csIndentEntity.getName());
				}
			}
			if (defaultIndentName.isEmpty()) {
				return indentParam;
			}
			int i = 1;
			while (defaultIndentName.contains(indentParam + i)) {
				i++;
			}
			return indentParam + i;
		} else {
			return indentParam;
		}

	}



	/**
	 * 查询业务员对应状态的配货单信息
	 * @param cloudSupplierUserDto
	 * @param indentNo
	 * @param indentStatus
	 * @return
	 */
	private List<CsIndentEntity> getIndentEntity(CloudSupplierUserDto cloudSupplierUserDto, String indentNo, IndentStatus indentStatus) {
		CsIndentEntity csIndentEntity = new CsIndentEntity();
		csIndentEntity.setSellerCode(indentHelper.getSellerCode(cloudSupplierUserDto.getSupplierId()));
		csIndentEntity.setSalesmanId(cloudSupplierUserDto.getId());
		csIndentEntity.setIndentNo(indentNo);
		if (indentStatus != null) {
			csIndentEntity.setIndentStatus(indentStatus.getCode());
		}
		return csIndentRepo.select(csIndentEntity);
	}
}
