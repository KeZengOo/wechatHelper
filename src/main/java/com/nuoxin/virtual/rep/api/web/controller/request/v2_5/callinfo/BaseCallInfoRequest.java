package com.nuoxin.virtual.rep.api.web.controller.request.v2_5.callinfo;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "保存电话拜访信息")
@Data
public class BaseCallInfoRequest {

	@NotNull(message = "sinToken is null")
	@ApiModelProperty(value = "语音供应商传来的唯一标识")
	private String sinToken;

	///////////////////////////////////////////////////////
	
	@NotNull(message = "virtualDrugUserId is null")
	@ApiModelProperty(value = "虚拟代表 id ")
	private Long virtualDrugUserId;
	@NotNull(message = "virtualDoctorId is null")
	@ApiModelProperty(value = "医生ID")
	private Long virtualDoctorId;
	@NotNull(message = "productId is null")
	@ApiModelProperty(value = "产品ID")
	private Integer productId;
	
	///////////////////////////////////////////////////////
	
	@NotNull(message = "mobile is null")
	@ApiModelProperty(value = "拨打的医生手机号")
	private String mobile;
	
	@Min(value=1,message = "min is 1")
	@Max(value=2, message = "max is 2")
	@NotNull(message = "type is null")
	@ApiModelProperty(value = "呼叫类型 1.呼出,2.呼入")
	private Integer type;
	
	@ApiModelProperty(value = "通话状态:1.接通,0未接通")
	private int status;
	@ApiModelProperty(value = "未接通原因,由前端传过来,英文单词形式,如:noanswer,emptynumber等")
	private String statuaName;
	@ApiModelProperty(value = "备注")
	private String remark;
	
	///////////////////////////////////////////////////////
	
	@ApiModelProperty(value = "下次拜访时间,以字符串形式传入 如:2018-09-11 11:23:25")
	private String nextVisitTime;

}
