package com.nuoxin.virtual.rep.api.entity.v2_5;

import lombok.Data;

@Data
public class DrugUserDoctorQuateParams {
	private long virtualDrugUserId;
	private long doctorId;
	// 默认给0，null会插入失败
	private int productLineId=0;
	private int isHasDrug;
	private int isHasAe;
	private int isTarget;
	private int isRecruit;
	private int isBreakOff;
	private int hcpPotential;
}
