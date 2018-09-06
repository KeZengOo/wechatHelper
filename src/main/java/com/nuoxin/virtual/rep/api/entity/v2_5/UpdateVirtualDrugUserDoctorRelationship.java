package com.nuoxin.virtual.rep.api.entity.v2_5;

import lombok.Data;

@Data
public class UpdateVirtualDrugUserDoctorRelationship {
	private Long virtualDrugUserId;
	private Long doctorId;
	private Integer productLineId;
	private Integer isHasDrug;
	private Integer isHasAe;
	private Integer isTarget;
}
