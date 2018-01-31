package com.xiaoka.cloud.stock.service.epc.dto;

import com.xiaoka.cloud.stock.service.epc.output.ExtraParamDto;

import java.io.Serializable;

/**
 * 配件类目实体对象
 *
 * @author gancao 2017/11/13 10:05
 * @see [相关类/方法]
 * @since [版本号]
 */
public class PartCategoryDto extends ExtraParamDto implements Serializable{

	private static final long serialVersionUID = 6859959651191189729L;

	//类目id
	private Integer categoryId;
	//类目名称
	private String categoryName;
	//类目图片
	private String categoryPicUrl;
	//分类类型 1:正时 0：原厂
	private Integer type;

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getCategoryPicUrl() {
		return categoryPicUrl;
	}

	public void setCategoryPicUrl(String categoryPicUrl) {
		this.categoryPicUrl = categoryPicUrl;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
}
