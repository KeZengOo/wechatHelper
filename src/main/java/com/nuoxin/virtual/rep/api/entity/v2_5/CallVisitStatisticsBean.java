package com.nuoxin.virtual.rep.api.entity.v2_5;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("电话拜访 Bean")
@Data
public class CallVisitStatisticsBean {
	@ApiModelProperty(value = "接通总数")
	private Integer connectedTotalNums;
	@ApiModelProperty(value = "未接通总数")
	private Integer unConnectedTotalNums;
}
