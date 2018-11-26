package com.nuoxin.virtual.rep.api.entity.v2_5;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 *销售拜访业务数量
 */
@ApiModel("销售拜访业务数量")
@Data
public class StatisticsDrugNumResponse {

	@ApiModelProperty(value = "销售代表id")
	private Long drugUserId;

	@ApiModelProperty(value = "业务数量")
	private Integer total;

}
