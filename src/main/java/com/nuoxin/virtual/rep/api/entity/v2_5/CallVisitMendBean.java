package com.nuoxin.virtual.rep.api.entity.v2_5;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("电话拜访扩展 Bean")
@Data
public class CallVisitMendBean {
	
	@ApiModelProperty(value = "callId")
	private Long callId;
	@ApiModelProperty(value = "医生态度")
	private Integer attitude;

}
