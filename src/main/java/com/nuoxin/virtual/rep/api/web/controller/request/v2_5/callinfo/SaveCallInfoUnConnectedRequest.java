package com.nuoxin.virtual.rep.api.web.controller.request.v2_5.callinfo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * 保存电话未接通拜访信息
 * @author xiekaiyu
 */
@ApiModel(value = "保存电话未接通拜访信息")
@EqualsAndHashCode(callSuper=false)
@Data
public class SaveCallInfoUnConnectedRequest extends BaseCallInfoRequest {
	
	@ApiModelProperty(value = "产品ID")
	private Integer productId;
	
}
