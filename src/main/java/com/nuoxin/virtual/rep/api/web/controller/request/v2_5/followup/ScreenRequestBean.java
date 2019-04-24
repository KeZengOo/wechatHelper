package com.nuoxin.virtual.rep.api.web.controller.request.v2_5.followup;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nuoxin.virtual.rep.api.common.bean.PageRequestBean;

import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.plan.VisitDoctorResponseBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 首页查询请求类 
 * @author xiekaiyu
 */
@ApiModel
@EqualsAndHashCode(callSuper=false)
@Data
public class ScreenRequestBean extends PageRequestBean {

	private static final long serialVersionUID = 8545014227620171255L;

	@ApiModelProperty(value = "销售代表ID")
	private Long drugUserId;

	/**
	 * 虚拟代表 ids
	 */
	@ApiModelProperty(value="虚拟代表ids")
	private List<Long> virtualDrugUserIds;


	/**
	 * 产品线 id
	 */
	@ApiModelProperty(value="产品线ids")
	private List<Long> productLineIds;

	@ApiModelProperty(value = "排序规则，默认是下次拜访时间由近及远排序，1下次拜访时间由远及近排序，" +
			"2上次拜访时间由近及远，3是上次拜访时间由远及近，4是创建时间由近及远，5是创建时间由远及近")
	private Integer order;

	@ApiModelProperty(value = "用于值的比较")
	private Integer minValue = Integer.MIN_VALUE;


	@ApiModelProperty(value = "目前有6种：1.下次拜访时间为今天的；2.根据医生潜力、分型应今日拜访的；3.离上次拜访时间已经到XX天的" +
			"4.转入后应在XX天内拜访的；5.今日需要发送参会提醒的；6.今日需要会议回访的, 其他默认全部的")
	private Integer searchType = 0;

	@JsonIgnore
	private List<VisitDoctorResponseBean> VisitDoctorList;

	/**
	 * 1是工作日，2是小时
	 */
	@JsonIgnore
	private Integer meetingTimeType = 0;

}
