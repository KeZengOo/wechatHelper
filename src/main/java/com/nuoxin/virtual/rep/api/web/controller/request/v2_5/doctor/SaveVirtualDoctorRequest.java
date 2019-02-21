package com.nuoxin.virtual.rep.api.web.controller.request.v2_5.doctor;

import java.util.ArrayList;
import java.util.List;

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
public class SaveVirtualDoctorRequest{

	@ApiModelProperty(value = "医生ID")
	private Long doctorId;

	@NotNull(message = "name is null")
	@ApiModelProperty(value = "医生姓名")
	private String name;
	@ApiModelProperty(value = "性别")
	@NotNull(message = "gender is null")
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

	@NotNull(message = "hospital is null")
	@ApiModelProperty(value = "医院")
	private String hospital;
	@NotNull(message = "province is null")
	@ApiModelProperty(value = "省份")
	private String province;
	@NotNull(message = "city is null")
	@ApiModelProperty(value = "城市")
	private String city;
	@NotNull(message = "hciLevel is null")
	@ApiModelProperty(value = "医院等级")
	private Integer hciLevel;
	
	@ApiModelProperty(value = "第二页扩展信息")
	List<SaveVirtualDoctorMendRequest> mends = new ArrayList<>();

	@ApiModelProperty(value = "医生基本信息动态字段")
	private DoctorBasicDynamicFieldValueListRequestBean doctorBasicDynamicField;
}
