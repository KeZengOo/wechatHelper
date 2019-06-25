package com.nuoxin.virtual.rep.api.entity.v2_5;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@ApiModel("医生基本信息")
@Data
public class VirtualDoctorDO {
	
	@ApiModelProperty(value = "姓名")
	private String name;
	@ApiModelProperty(value = "性别: 0男,1女,2未知")
	private Integer gender;
	@ApiModelProperty(value = "主手机号")
	private List<String> mobiles = new ArrayList<>();

	@Deprecated
	@ApiModelProperty(value = "头衔,字段废弃，使用新的")
	private String title;

	@ApiModelProperty(value = "医生职称")
	private String doctorTitle;

	@ApiModelProperty(value = "医生职务")
	private String doctorPosition;

	@ApiModelProperty(value = "科室")
	private String depart;
	@ApiModelProperty(value = "电子邮件")
	private String email;
	@ApiModelProperty(value = "地址")
	private String address;
	@ApiModelProperty(value = "微信")
	private String wechat;
	@ApiModelProperty(value = "座机")
	private String fixedPhone;
	@ApiModelProperty(value = "是否添加微信 1,添加,0未添加")
	private Integer isAddWechat;
	@JsonIgnore
	private String hospitalName;
	
}
