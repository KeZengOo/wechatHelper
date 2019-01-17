package com.nuoxin.virtual.rep.api.web.controller.response.v2_5;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value="获取最近一次作答")
@Data
public class VirtualQuestionnaireResponse {
	@ApiModelProperty(value = "问卷 ID")
	private Integer id;
	@ApiModelProperty(value = "问卷 TITLE")
	private String title;
}
