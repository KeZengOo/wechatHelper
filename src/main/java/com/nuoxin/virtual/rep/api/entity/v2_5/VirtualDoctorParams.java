package com.nuoxin.virtual.rep.api.entity.v2_5;

import lombok.Data;

@Data
public class VirtualDoctorParams {
	private long id;
	private String name;
	private Integer gender;
	private String mobile;
	private String email;
	private String depart;
	/**
	 * 字段废弃，使用新的
	 */
	@Deprecated
	private String title;

	/**
	 * 医生职称
	 */
	private String doctorTitle;


	/**
	 * 医生职务
	 */
	private String doctorPosition;


	private String hospital;
	private Integer hospitalId;
	private String province;
	private String city;
}
