package com.xiaoka.cloud.stock.service.order;

import com.xiaoka.cloud.stock.service.order.dto.IndentDto;
import com.xiaoka.cloud.stock.service.order.dto.IndentPartDto;
import com.xiaoka.cloud.stock.service.order.dto.OrderDto;
import com.xiaoka.cloud.stock.service.supplier.dto.CloudSupplierUserDto;

import java.util.List;

public interface IndentService {

	/**
	 * 获取配货单列表，不包含配货单配件信息
	 * @param cloudSupplierUserDto
	 * @return
	 */
	List<IndentDto> getIndentList(CloudSupplierUserDto cloudSupplierUserDto);

	/**
	 * 获取配货单详情，包含配货单配件信息
	 * @param cloudSupplierUserDto
	 * @param indentNo
	 * @return
	 */
	IndentDto getIndentDetail(CloudSupplierUserDto cloudSupplierUserDto, String indentNo);

	/**
	 * 保存配货单
	 * @param cloudSupplierUserDto
	 * @param indentDto
	 * @return
	 */
	IndentDto saveIndentDetail(CloudSupplierUserDto cloudSupplierUserDto, IndentDto indentDto);

	/**
	 * 添加配件到配货单
	 * @param cloudSupplierUserDto
	 * @param indentPartDto
	 * @return
	 */
	IndentPartDto addIndentPart(CloudSupplierUserDto cloudSupplierUserDto, IndentPartDto indentPartDto);

	/**
	 * 根据配货单名字添加配货单，配货单名字如果有重名的自动跟后缀序号
	 * @param cloudSupplierUserDto
	 * @param indentName
	 * @return
	 */
	IndentDto addIndent(CloudSupplierUserDto cloudSupplierUserDto, String indentName);

	/**
	 * 根据配货单编号与用户身份删除指定的配货单
	 * @param cloudSupplierUserDto
	 * @param indentNo
	 */
	void deleteIndent(CloudSupplierUserDto cloudSupplierUserDto, String indentNo);

	/**
	 * 修改指定配货单号的状态，调用之前验证权限
	 * @param indentNo
	 * @param indentStatus
	 */
	void updateStatusByIndentNo(String indentNo, Integer indentStatus);

	void updateIndentName(CloudSupplierUserDto cloudSupplierUserDto, String indentNo, String indentName);
}
