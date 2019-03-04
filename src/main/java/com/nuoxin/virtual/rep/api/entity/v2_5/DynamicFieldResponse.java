package com.nuoxin.virtual.rep.api.entity.v2_5;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 动态字段返回
 */
@ApiModel("动态字段")
@Data
public class DynamicFieldResponse {

	@ApiModelProperty(value = "汉语解释")
	private String lable;

	@ApiModelProperty(value = "英文字段")
	private String prop;

	@ApiModelProperty(value = "分类")
	private Integer classification;

	/**
	 * 子集
	 */
	private List<DynamicFieldResponse> children;
}
