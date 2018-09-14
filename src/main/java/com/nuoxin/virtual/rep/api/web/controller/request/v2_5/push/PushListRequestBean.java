package com.nuoxin.virtual.rep.api.web.controller.request.v2_5.push;

import javax.validation.constraints.NotNull;

import com.nuoxin.virtual.rep.api.common.bean.PageRequestBean;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper=false)
@Data
public class PushListRequestBean extends PageRequestBean {

	private static final long serialVersionUID = 6122980461264600121L;
	
	@NotNull(message="virtualDoctorId is null")
	@ApiModelProperty(value = "客户医生 ID")
	private Long virtualDoctorId;
}
