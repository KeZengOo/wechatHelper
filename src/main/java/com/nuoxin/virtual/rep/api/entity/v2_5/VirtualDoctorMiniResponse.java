package com.nuoxin.virtual.rep.api.entity.v2_5;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 医生简要信息
 * @author xiekaiyu
 */
@ApiModel("医生简要信息")
@Data
public class VirtualDoctorMiniResponse {
	
	@ApiModelProperty(value = "姓名")
	private String name;
	@ApiModelProperty(value = "性别: 0男,1女,2未知")
	private char gender;
	@ApiModelProperty(value = "医生等级")
	private String hcpLevel;
	@ApiModelProperty(value = "职称")
	private String title;
	@ApiModelProperty(value = "科室")
	private String depart;
	@ApiModelProperty(value = "主手机号")
	private String mobile;
	@ApiModelProperty(value = "医院名")
	private String hospitalName;

	@ApiModelProperty(value = "医院等级")
	private Integer hospitalGrade;
	
	@ApiModelProperty(value = "下次拜访时间文字")
	private String nextVisitTimeContent;
	@ApiModelProperty(value = "下次拜访时间")
	private String nextVisitTime;
	@JsonIgnore
	private Date nextVisit;
	
}
