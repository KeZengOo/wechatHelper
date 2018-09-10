package com.nuoxin.virtual.rep.api.entity.v2_5;

import lombok.Data;

@Data
public class VirtualDoctorDO {
	private String name;
	private char gender;
	private String mobile;
	private String title;
	private String depart;
	private String email;
	
	private String address;
	private String wechat;
	private String fixedPhone;
	
	private String hospitalName;
}
