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
	String VIRTUAL_DOCTOR_CALL_INFO_MEND_CRON = "0 0 23 * * ?";

	/**
	 * 销售代表给医生打电话表同步定时任务timer
	 */
	String VIRTUAL_DOCTOR_CALL_INFO_CRON = "0 0 23 * * ?";

	/**
	 * 医院表同步定时任务timer
	 */
	String ENTERPRISE_HCI_CRON = "0 0 23 * * ?";

	/**
	 * 会议表同步定时任务timer
	 */
	String T_MEETING_DATA_CRON = "0 0 23 * * ?";

	/**
	 * 医生参加诺和内部会议详情表同步定时任务timer
	 */
	String T_MEETING_ATTEND_DETAILS_CRON = "0 44 19 * * ?";
}
