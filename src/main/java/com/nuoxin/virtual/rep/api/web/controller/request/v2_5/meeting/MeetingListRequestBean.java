package com.nuoxin.virtual.rep.api.web.controller.request.v2_5.meeting;

import javax.validation.constraints.NotNull;

import com.nuoxin.virtual.rep.api.common.bean.PageRequestBean;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper=false)
@Data
public class MeetingListRequestBean extends PageRequestBean {

	private static final long serialVersionUID = -6532794852068276506L;
	
	@NotNull(message="virtualDoctorId is null")
	@ApiModelProperty(value = "客户医生 ID")
	private Long virtualDoctorId;

	@ApiModelProperty(value = "前端不用传")
	private String leaderPath;


	@ApiModelProperty(value = "产品ID")
	private List<Long> productIdList;
}
