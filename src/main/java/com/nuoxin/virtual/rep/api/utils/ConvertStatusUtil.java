package com.nuoxin.virtual.rep.api.utils;

/**
 * 通话状态工具类
 * @author xiekaiyu
 */
public class ConvertStatusUtil {
	
	/**
	 * 7moor 呼出状态-> 转成老的状态
	 * @param statusName 7moor 状态名
	 * @return 返回转换后的状态名
	 */
	public static String convertStatusName(String callType, String statusName) {
		if ("dialout".equalsIgnoreCase(callType)) { // 7moor 呼出状态-> 转成老的状态
			if ("dealing".equalsIgnoreCase(statusName)) {
				statusName = "answer";
			} else {
				statusName = notDeal(statusName);
			}
		} else if ("normal".equalsIgnoreCase(callType)) { // 7moor 呼入状态-> 转成老的状态
			if ("dealing".equalsIgnoreCase(statusName)) {
				statusName = "incall";
			} else {
				statusName = notDeal(statusName);
			}
		}

		return statusName;
	}
	
	/**
	 * 根据 statusName 计算 statue 值
	 * @param statusName
	 * @return 返回 status 1接通,0未接通
	 */
	public static Integer getStatus(String statusName) {
		if ("cancelmakecall".equalsIgnoreCase(statusName)) { // 未接通
			return 0;
		} else { // 已接通
			return 1;
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * 未接转换-> cancelmakecall
	 * @param statusName
	 * @return
	 */
	private static String notDeal(String statusName) {
		if ("notDeal".equalsIgnoreCase(statusName) || "leak".equalsIgnoreCase(statusName)
				|| "queueLeak".equalsIgnoreCase(statusName) || "blackList".equalsIgnoreCase(statusName)
				|| "voicemail".equalsIgnoreCase(statusName)) {
			statusName = "cancelmakecall";
		}

		return statusName;
	}
}
