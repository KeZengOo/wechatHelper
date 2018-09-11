package com.nuoxin.virtual.rep.api.entity.v2_5;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 参会记录
 * @author xiekaiyu
 */
@ApiModel("参会记录 Bean")
@Data
public class MeetingBean {

	@ApiModelProperty(value = "会议标题")
	private String title;
	@ApiModelProperty(value = "演讲人")
	private String speaker;
	@ApiModelProperty(value = "演讲人所属医院")
	private String speakerHospital;
	@ApiModelProperty(value = "会议开始时间")
	private String startTime;
	@ApiModelProperty(value = "会议结束时间")
	private String endTime;

	@ApiModelProperty(value = "参会开始时间")
	private String attendStartTime;
	@ApiModelProperty(value = "参会结束时间")
	private String attendEndTime;
	@ApiModelProperty(value = "参会类型")
	private Integer attendType;
	@ApiModelProperty(value = "类型:1参会,2回看")
	private Integer type;
	@ApiModelProperty(value = "下载")
	private Integer download;

}
