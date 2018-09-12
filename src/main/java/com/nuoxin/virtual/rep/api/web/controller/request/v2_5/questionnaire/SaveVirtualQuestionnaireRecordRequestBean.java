package com.nuoxin.virtual.rep.api.web.controller.request.v2_5.questionnaire;

import java.util.ArrayList;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel
@Data
public class SaveVirtualQuestionnaireRecordRequestBean {

	@ApiModelProperty(value = "医生 ID")
	private Long virtualDoctorId;
	@ApiModelProperty(value = "虚拟代表 ID")
	private Long virtualDrugUserId;
	@ApiModelProperty(value = "问卷ID")
	private Integer virtualQuestionaireId;
	@ApiModelProperty(value = "电话 ID")
	private Long callId;
	
	@ApiModelProperty(value = "作答结果")
	List<VirtualQuestionRequestBean> questions = new ArrayList<>();
	
}
