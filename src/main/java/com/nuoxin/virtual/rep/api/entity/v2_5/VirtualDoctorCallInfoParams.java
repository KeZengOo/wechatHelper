package com.nuoxin.virtual.rep.api.entity.v2_5;

import lombok.Data;

@Data
public class VirtualDoctorCallInfoParams{
	private Long callId;
	private String sinToken;
	private Long virtualDrugUserId;
	private Long virtualDoctorId;
	private Long doctorQuestionnaireId;
	private Integer productId;
	
	private String mobile;
	private Integer type;
	private String visitResult;
	private Integer attitude;
	private String nextVisitTime;
	private String remark;
}
