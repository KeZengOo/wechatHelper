package com.nuoxin.virtual.rep.api.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 回调Service类
 * @author xiekaiyu
 */
@Service("sevenMoor")
public class SevenMoorCallBackImpl extends BaseCallBackImpl implements CallBackService{

	private static final Logger logger = LoggerFactory.getLogger(CallBackService.class);

	/**
	 * 参考链接 https://developer.7moor.com/event/
	 * @param map
	 */
	public void callBack(Map<String, String> paramsMap) {
		this.pause();
		
		// 与数据库对应的字段 sin_token(callId)
		String sinToken = paramsMap.get("CallSheetID");
		// 与数据库对应的字段 status_name
		String statusName = paramsMap.get("State");
		// 电话录音下载地址
		String monitorFilenameUrl = paramsMap.get("MonitorFilename");
		
		// 7moor 状态-> 转成老的状态
		if ("dealing".equalsIgnoreCase(statusName)) {
			statusName = "answer";
		} else if ("notDeal".equalsIgnoreCase(statusName)) {
			statusName = "incall";
		}

		super.processCallBack(sinToken, statusName, monitorFilenameUrl);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * 线程暂停70秒
	 */
	private void pause () {
		try {
			Thread.sleep(70000);
		} catch (InterruptedException e) {
			logger.error("InterruptedException", e);
		}
	}
}
