package com.nuoxin.virtual.rep.api.entity.v2_5;

import lombok.Data;

/**
 * 代表转移记录日志表
 * @author tiancun
 * @date 2019-07-04
 */
@Data
public class DrugUserDoctorLogParams {

	/**
	 * 原来的代表ID
	 */
	private Long oldDrugUserId;

	/**
	 * 原来的代表姓名
	 */
	private String oldDrugUserName;


	/**
	 * 新的代表ID
	 */
	private Long newDrugUserId;

	/**
	 * 新的代表姓名
	 */
	private String newDrugUserName;

	/**
	 * 操作者代表ID
	 */
	private Long operateDrugUserId;

	/**
	 * 操作者
	 */
	private String operateDrugUserName;

	/**
	 * 医生ID
	 */
	private Long doctorId;


	/**
	 * 产品ID
	 */
	private Long productId;

	/**
	 * 操作方式，1是单个，2是批量
	 */
	private Integer operateWay;
	
}
