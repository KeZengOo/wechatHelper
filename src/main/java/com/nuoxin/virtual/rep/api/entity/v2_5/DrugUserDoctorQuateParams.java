package com.nuoxin.virtual.rep.api.entity.v2_5;

import lombok.Data;

@Data
public class DrugUserDoctorQuateParams {
	private long virtualDrugUserId;
	private long doctorId;
	private int productLineId;
	private int isHasDrug;
	private int isHasAe;
	private int isTarget;
	private int isRecruit;
	private int isBreakOff;
	private int hcpPotential;
}
