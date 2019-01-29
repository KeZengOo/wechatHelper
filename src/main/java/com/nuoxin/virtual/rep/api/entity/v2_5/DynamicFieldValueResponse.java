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
public class DynamicFieldValueResponse {

	@ApiModelProperty(value = "汉语解释")
	private String lable;

	@ApiModelProperty(value = "英文字段")
	private String prop;

	@ApiModelProperty(value = "值")
	private String value;

	@ApiModelProperty(value = "医生id")
	private Long doctorId;

	@ApiModelProperty(value = "拜访id")
	private Long callId;
}
