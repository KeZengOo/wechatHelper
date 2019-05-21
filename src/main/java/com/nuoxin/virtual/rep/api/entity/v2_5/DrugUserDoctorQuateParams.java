package com.nuoxin.virtual.rep.api.entity.v2_5;

import lombok.Data;

@Data
public class DrugUserDoctorQuateParams {

	private Long id;

	private Long quateId;

	private Long virtualDrugUserId;
	private Long doctorId;
	// 默认给0，null会插入失败
	private Integer productLineId=0;
	private Integer isHasDrug;
	private Integer isHasAe;
	private Integer isTarget;
	private Integer isRecruit;
	private Integer isCover;
	private Integer isBreakOff;
	private Integer hcpPotential;
	private Integer attitude;
}
