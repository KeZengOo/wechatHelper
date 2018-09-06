package com.nuoxin.virtual.rep.api.service.v2_5;

import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.callinfo.SaveCallInfoRequest;

/**
 * 电话拜访业务接口类
 * @author xiekaiyu
 */
public interface VirtualDoctorlCallInfoService {
	
	/**
	 * 保存电话拜访结果,有RERUIRED事务
	 * @param saveRequest
	 * @return 成功返回 true ,否则返回 false
	 */
	boolean saveCallInfo(SaveCallInfoRequest saveRequest);
}
