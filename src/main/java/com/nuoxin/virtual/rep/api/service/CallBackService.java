package com.nuoxin.virtual.rep.api.service;

import java.util.Map;

/**
 * 回调接口类
 * @author xiekaiyu
 */
public interface CallBackService {
	
	/**
	 * 回调业务方法
	 * @param paramsMap 参数键值对
	 */
	void callBack(Map<String, String> paramsMap);
}
