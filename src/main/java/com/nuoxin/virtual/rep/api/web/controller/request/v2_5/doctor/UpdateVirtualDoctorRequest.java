package com.nuoxin.virtual.rep.api.web.controller.request.v2_5.doctor;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * 保存单个医生
 * @author xiekaiyu
 */
@ApiModel("更新单个医生信息对象")
@Data
public class UpdateVirtualDoctorRequest {

	@ApiModelProperty(value = "医生ID")
	private Long id;

	@ApiModelProperty(value = "医生姓名")
	private String name;

	@ApiModelProperty(value = "性别")
	private Integer gender;

	@ApiModelProperty(value = "医生的联系方式，可以有多个")
	private List<String> telephones;

	@ApiModelProperty(value = "是否添加微信:1是,0否")
	private Integer isAddWechat;
	@ApiModelProperty(value = "微信")
	private String wechat;
	@ApiModelProperty(value = "邮箱")
	private String email;
	@ApiModelProperty(value = "地址")
	private String address;
	@ApiModelProperty(value = "科室")
	private String depart;
	@ApiModelProperty(value = "职称")
	private String title;

	@ApiModelProperty(value = "医院")
	private String hospital;
	@ApiModelProperty(value = "省份")
	private String province;
	@ApiModelProperty(value = "城市")
	private String city;
	@ApiModelProperty(value = "医院等级")
	private Integer hciLevel;

	@ApiModelProperty(value = "每次拜访的ID，如果是拜访修改的则需要传，否则不需要")
	private Long callId;

	@JsonIgnore
	private String telephoneStr;

}
