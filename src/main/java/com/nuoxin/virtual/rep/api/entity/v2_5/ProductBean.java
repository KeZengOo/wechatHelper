package com.nuoxin.virtual.rep.api.entity.v2_5;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value="获取最近一次作答")
@Data
public class ProductBean {
	@ApiModelProperty(value = "产品 ID")
	private Integer productId;
	@ApiModelProperty(value = "产品名")
	private String productName;
}
