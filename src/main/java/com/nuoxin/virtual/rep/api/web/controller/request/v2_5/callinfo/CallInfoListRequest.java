package com.nuoxin.virtual.rep.api.web.controller.request.v2_5.callinfo;

import javax.validation.constraints.NotNull;

import com.nuoxin.virtual.rep.api.common.bean.PageRequestBean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@ApiModel(value = "获取电话拜访列表信息")
@EqualsAndHashCode(callSuper=false)
@Data
public class CallInfoListRequest extends PageRequestBean{
	
	private static final long serialVersionUID = 8813160133107726881L;
	
	@NotNull(message="virtualDrugUserId is null")
	@ApiModelProperty(value = "虚拟代表 ID")
	private Long virtualDrugUserId;
	
	@NotNull(message="virtualDoctorId is null")
	@ApiModelProperty(value = "客户医生 ID")
	private Long virtualDoctorId;
	
}
