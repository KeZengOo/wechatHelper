package com.nuoxin.virtual.rep.api.entity.v2_5;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 拜访统计返回
 */
@ApiModel("拜访统计返回")
@Data
public class StatisticsResponse {

	@ApiModelProperty(value = "销售代表id")
	private Long drugUserId;

	@ApiModelProperty(value = "销售代表名称")
	private String drugUserName;


	@ApiModelProperty(value = "拜访医生数")
	private Integer visitDoctorNum=0;

	@ApiModelProperty(value = "接触医生数")
	private Integer contactDoctorNum=0;

	@ApiModelProperty(value = "成功医生数")
	private Integer successDoctorNum=0;

	@ApiModelProperty(value = "招募医生数")
	private Integer recruitDoctorNum=0;

	@ApiModelProperty(value = "覆盖医生数")
	private Integer coverDoctorNum=0;

	@ApiModelProperty(value = "高潜力医生数")
	private Integer potentialDoctorHighNum=0;

	@ApiModelProperty(value = "中潜力医生数")
	private Integer potentialDoctorMiddleNum=0;

	@ApiModelProperty(value = "低潜力医生数")
	private Integer potentialDoctorLowNum=0;

	@ApiModelProperty(value = "微信发送消息人数")
	private Integer wxSendNum=0;

	@ApiModelProperty(value = "微信回复消息人数")
	private Integer wxReplyNum=0;

	@ApiModelProperty(value = "内容发送人数")
	private Integer contentSendNum=0;

	@ApiModelProperty(value = "内容阅读人数")
	private Integer contentReadNum=0;

	@ApiModelProperty(value = "内容阅读率")
	private String contentReadRate="0.00";

	@ApiModelProperty(value = "内容阅读时长")
	private Integer contentReadTime=0;
}
