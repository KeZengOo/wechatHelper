package com.nuoxin.virtual.rep.api.web.controller.request.v2_5.callinfo;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.questionnaire.VirtualQuestionRequestBean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "保存电话拜访信息")
@Data
public class SaveCallInfoRequest {
	
	@NotNull(message="sinToken is null")
	@ApiModelProperty(value = "语音供应商传来的唯一标识")
	private String sinToken;
	
	@NotNull(message="virtualDrugUserId is null")
	@ApiModelProperty(value = "虚拟代表 id ")
	private Long virtualDrugUserId;
	@NotNull(message="virtualDoctorId is null")
	@ApiModelProperty(value = "医生ID")
	private Long virtualDoctorId;
	@ApiModelProperty(value = "医生手机号")
	private String mobile;
	@ApiModelProperty(value = "呼叫类型 1.呼出,2.呼入")
	private Integer type;
	@ApiModelProperty(value = "备注")
	private String remark;
	@NotNull(message="productId is null")
	@ApiModelProperty(value = "产品线ID")
	private Integer productId;
	
	///////////////////////////////////////////////////////
	
	@NotNull(message="isHasDrug is null 1.有,0.无")
	@ApiModelProperty(value = "是否有药")
	private Integer isHasDrug;
	@NotNull(message="isTarget is null")
	@ApiModelProperty(value = "是否是目标客户 1.是,0.非")
	private Integer isTarget;
	@NotNull(message="isHasAe is null")
	@ApiModelProperty(value = "是否有 AE 1.是,0.非")
	private Integer isHasAe;
	
	///////////////////////////////////////////////////////
	
	@ApiModelProperty(value = "拜访结果,以字符串数组形式传入如[\"a\",\"b\"]")
	private List<String> visitResult;
	@ApiModelProperty(value = "医生态度 0-5")
	private Integer attitude;
	@ApiModelProperty(value = "下次拜访时间,以字符串形式传入 如:2018-09-11 11:23:25")
	private String nextVisitTime;
	
	///////////////////////////////////////////////////////
	
	@NotNull(message="virtualQuestionaireId is null")
	@ApiModelProperty(value = "问卷ID")
	private Long virtualQuestionaireId;
	@ApiModelProperty(value = "作答结果")
	List<VirtualQuestionRequestBean> questions = new ArrayList<>();
}
