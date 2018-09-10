package com.nuoxin.virtual.rep.api.entity.v2_5;

import lombok.Data;

@Data
public class VirtualDoctorMendParams {
	private long virtualDoctorId;
	private String wechat;
	private String address;
	private String fixedPhone;
}
