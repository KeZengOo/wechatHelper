package com.nuoxin.virtual.rep.api.web.controller.response.v2_5;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value="获取最近一次作答")
@Data
public class VirtualQuestionnaireRecordResponse {

	@ApiModelProperty(value = "问题 ID")
	private Integer id;
	@ApiModelProperty(value = "题干")
	private String title;
	@ApiModelProperty(value = "选项")
	private Object options;
	@ApiModelProperty(value = "题型")
	private Byte type;
	@ApiModelProperty(value = "用户作答")
	private String answer;
	
}
