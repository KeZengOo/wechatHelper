package com.nuoxin.virtual.rep.api.web.controller.request.v2_5.doctor;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SaveVirtualDoctorMendRequest {
	@NotNull(message="productLineId is null")
	@ApiModelProperty(value = "产品ID")
	private Integer productLineId;
	@ApiModelProperty(value = "是否有药")
	private Integer isHasDrug;
	@ApiModelProperty(value = "是否招募")
	private Integer isRecruit;
	@ApiModelProperty(value = "客户等级")
	private String hcpLevel;
	@ApiModelProperty(value = "医生潜力")
	private String hcpPotential;
}
