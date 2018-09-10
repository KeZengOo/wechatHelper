package com.nuoxin.virtual.rep.api.entity.v2_5;

import lombok.Data;

@Data
public class VirtualDoctorParams {
	private String name;
	private Integer gender;
	private String mobile;
	private String fixedPhone; // 
	private String wechat; //
	private String email;
	private String depart;
	private String title;
	private String hospital;
	private Integer hospitalId;
	private String province;
	private String city;
}
