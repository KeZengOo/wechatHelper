package com.nuoxin.virtual.rep.api.entity.v2_5;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel
@Data
public class HospitalProvinceBean {
	@ApiModelProperty(value = "医院")
	private int id;
	@ApiModelProperty(value = "医院名")
	private String name;
	@ApiModelProperty(value = "省份")
	private String province;
	@ApiModelProperty(value = "城市")
	private String city;
	@ApiModelProperty(value = "等级")
	private Integer level;
}
