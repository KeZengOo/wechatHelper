package com.nuoxin.virtual.rep.api.entity.v2_5;

import lombok.Data;

@Data
public class DrugUserDoctorOneToOneParams {
	private Long drugUserId;
	private Long doctorId;
	private Integer isAddWechat;
}
