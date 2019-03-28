package com.nuoxin.virtual.rep.api.web.controller.request.v2_5.callinfo;

import javax.validation.constraints.NotNull;

import com.nuoxin.virtual.rep.api.common.bean.PageRequestBean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@ApiModel(value = "获取电话拜访列表信息")
@EqualsAndHashCode(callSuper=false)
@Data
public class CallInfoListRequest extends PageRequestBean{
	
	private static final long serialVersionUID = 8813160133107726881L;
	
	@NotNull(message="virtualDoctorId is null")
	@ApiModelProperty(value = "客户医生 ID")
	private Long virtualDoctorId;

	@ApiModelProperty(value = "leaderPath")
	private String leaderPath;


	@ApiModelProperty(value = "时间筛选，0或者null为全部，1是近一个月，2是近三个")
	private Integer timeType;

	@ApiModelProperty(value = "选择的互动代表ID")
	private List<Long> drugUserIdList;

}
