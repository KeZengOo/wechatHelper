package com.nuoxin.virtual.rep.api.common.constant;

/**
 * 定时器Cron常量
 * @author xiekaiyu
 */
public interface TimeCronConstant {
	/**
	 * 产品定时任务timer
	 */
	String PRODUCT_CRON = "0 0 23 * * ?";

	/**
	 * role定时任务timer
	 */
	String ROLE_CRON = "0 0 23 * * ?";

	/**
	 * 销售与医生关系指标表同步定时任务timer
	 */
	String DRUG_USER_DOCTOR_QUATE_CRON = "0 0 23 * * ?";

	/**
	 * 电话拜访扩展表同步定时任务timer
	 */
	String VIRTUAL_DOCTOR_CALL_INFO_MEND_CRON = "0 9 20 * * ?";

}
