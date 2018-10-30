package com.nuoxin.virtual.rep.api.entity.v2_5;

import lombok.Data;
import lombok.ToString;

@Data
public class DrugUserDoctorParams {
	private Long drugUserDoctorId;
	private Long doctorId;
	private String doctorName;
	private String doctorEmail;
	
	private Long drugUserId;
	private String drugUserName;
	private String drugUserEmail;
	
	private Integer prodId;
	
}
