package com.nuoxin.virtual.rep.api.web.controller.response.v2_5;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel
@Data
public class TableHeader {
	@ApiModelProperty(value = "变量名")
	private String name;
	@ApiModelProperty(value = "标签")
	private String label;
}
