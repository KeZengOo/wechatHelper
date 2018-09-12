package com.nuoxin.virtual.rep.api.entity.v2_5;

import lombok.Data;

@Data
public class VirtualDoctorCallInfoParams{
	private long callId;
	private String sinToken;
	private long virtualDrugUserId;
	private Long virtualDoctorId;
	private Integer doctorQuestionnaireId;
	private Integer productId;
	
	private String mobile;
	private int type;
	private String visitResult="";
	private int attitude=0;
	private String nextVisitTime;
	private String remark;
	private int status;
	private String statusName;
}
