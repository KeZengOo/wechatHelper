package com.nuoxin.virtual.rep.api.web.controller.request.v2_5.doctor;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel
@Data
public class SaveVirtualDoctorRequest {
	
	@NotNull(message="name is null")
	@ApiModelProperty(value = "医生姓名")
	private String name;
	
	@ApiModelProperty(value = "性别")
	@NotNull(message="gender is null")
	private Integer gender;
	
	@ApiModelProperty(value = "手机号")
	@NotNull(message="mobile is null")
	private String mobile;
	
	private String wechat;
	private String fixedPhone;
	private String email;
	private String address;
	private String depart;
	private String title;
	
	@NotNull(message="hospital is null")
	@ApiModelProperty(value = "医院")
	private String hospital;
	
	@NotNull(message="province is null")
	@ApiModelProperty(value = "省份")
	private String province;
	
	@NotNull(message="city is null")
	@ApiModelProperty(value = "城市")
	private String city;
	
	@ApiModelProperty(value = "医院等级")
	private Integer hciLevel;
	
}
