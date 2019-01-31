package com.nuoxin.virtual.rep.api.web.controller.request.v2_5.followup;

import com.nuoxin.virtual.rep.api.common.bean.PageRequestBean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 首页查询请求类 
 * @author xiekaiyu
 */
@ApiModel
@EqualsAndHashCode(callSuper=false)
@Data
public class SearchRequestBean extends ListRequestBean {

	private static final long serialVersionUID = 8545014227620171255L;
	
	/**
	 * 搜索值
	 */
	@ApiModelProperty(value="搜索值")
	private String search;


	// 基本信息固定字段高级搜索
	@ApiModelProperty(value = "性别")
	private Integer sex;

	@ApiModelProperty(value = "医生的微信")
	private String wechat;

	@ApiModelProperty(value = "医生是否添加微信")
	private Integer isAddWechat;

	@ApiModelProperty(value = "医生邮箱")
	private String email;

	@ApiModelProperty(value = "医生地址")
	private String address;

	@ApiModelProperty(value = "医生职称")
	private String title;

	@ApiModelProperty(value = "筛选的开始医生创建时间")
	private String startDoctorCreateTime;


	@ApiModelProperty(value = "筛选的结束医生创建时间")
	private String endDoctorCreateTime;


	// 医院固定字段高级搜索
	@ApiModelProperty(value = "医院ID")
	private Long hospitalId;

	@ApiModelProperty(value = "医院的省份")
	private String hospitalProvince;

	@ApiModelProperty(value = "医院的城市")
	private String hospitalCity;

	@ApiModelProperty(value = "医院等级")
	private Integer hospitalGrade;


	// 处方信息固定字段高级搜索
	@ApiModelProperty(value = "是否有药")
	private Integer hasDrug;

	@ApiModelProperty(value = "是否是目标客户")
	private Integer target;

	@ApiModelProperty(value = "是否招募")
	private Integer recruit;

	@ApiModelProperty(value = "是否脱落")
	private Integer breakOff;



	// 拜访记录
	@ApiModelProperty(value = "筛选开始上次拜访时间")
	private String startLastVisitTime;

	@ApiModelProperty(value = "筛选结束上次拜访时间")
	private String endLastVisitTime;

	@ApiModelProperty(value = "拜访结果的ID数组")
	private List<Long> interviewResult;

	@ApiModelProperty(value = "筛选开始下次拜访时间")
	private String startNextVisitTime;

	@ApiModelProperty(value = "筛选结束下次拜访时间")
	private String endNextVisitTime;


	// 所有动态字段输入的值
	@ApiModelProperty(value = "动态字段输入的所有值")
	private List<SearchDynamicFieldRequestBean> valueList;

}
