package com.nuoxin.virtual.rep.api.web.controller.request.v2_5.doctor;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 保存单个医生
 * @author xiekaiyu
 */
@ApiModel("保存单个医生信息对象")
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
    @ApiModelProperty(value = "医生次要电话")
    private String secondaryMobile;
    @ApiModelProperty(value = "医生三要电话")
    private String thirdaryMobile;
	
	@ApiModelProperty(value = "微信")
	private String wechat;
	@ApiModelProperty(value = "座机")
	private String fixedPhone;
	@ApiModelProperty(value = "邮箱")
	private String email;
	@ApiModelProperty(value = "地址")
	private String address;
	@ApiModelProperty(value = "科室")
	private String depart;
	@ApiModelProperty(value = "技术头衔")
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
	
	@NotNull(message="hciLevel is null")
	@ApiModelProperty(value = "医院等级")
	private Integer hciLevel;
	
	@NotNull(message="productLineId is null")
	@ApiModelProperty(value = "产品ID")
	private Integer productLineId;
	
	@ApiModelProperty(value = "是否有药")
	private Integer isHasDrug;
	@ApiModelProperty(value = "是否招募")
	private Integer isRecruit;
	
	// TODO 客户等级,医生潜力 @田存
	
}
