package com.nuoxin.virtual.rep.api.web.controller.request.v2_5.questionnaire;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 提交问卷作答结果
 * @author xiekaiyu
 */
@ApiModel
@Data
public class VirtualQuestionRequestBean {
	
	@ApiModelProperty(value = "问题 ID")
	private Long virtualQuestionId;
	@ApiModelProperty(value = "作答")
	private String answer;
	
}
