package com.nuoxin.virtual.rep.api.entity.v2_5;

import com.alibaba.fastjson.JSONArray;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 电话拜访
 * @author xiekaiyu
 */
@ApiModel("电话拜访 Bean")
@Data
public class CallVisitBean {
	
	@ApiModelProperty(value = "虚拟代表姓名")
	private String drugUserName;
	@ApiModelProperty(value = "callId")
	private Long callId;
	@ApiModelProperty(value = "通话时长")
	private Integer callTime;
	@ApiModelProperty(value = "通话状态")
	private String statueName;
	@ApiModelProperty(value = "录音URL")
	private String callUrl;
	@ApiModelProperty(value = "医生态度")
	private Integer attitude;
	@ApiModelProperty(value = "拜访结果")
	private JSONArray visitResult;
	@ApiModelProperty(value = "备注")
	private String remark;
	@ApiModelProperty(value = "电话拜访时间")
	private String createTime;
	@ApiModelProperty(value = "电话拜访类型1呼出,2呼入")
	private Integer type;
}
