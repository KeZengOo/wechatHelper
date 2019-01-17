package com.nuoxin.virtual.rep.api.entity.v2_5;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 医生基本信息
 * @author xiekaiyu
 */
@ApiModel("医生基本信息")
@Data
public class VirtualDoctorBasicResponse {
	
	@ApiModelProperty(value = "医生信息")
	private VirtualDoctorDO virtualDoctor;
	
	@ApiModelProperty(value = "医院信息")
	private HospitalProvinceBean hospital;
	
}
