package com.nuoxin.virtual.rep.api.service.v2_5;

import java.util.List;

import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.push.PushListRequestBean;

/**
 * 推送业务接口
 * @author xiekaiyu
 */
public interface VirtualDoctorPushService {
	
	/**
	 * @李成新 TODO
	 * @param leaderPath
	 * @param pushRequest
	 * @return
	 */
	List<Object> getPushList(String leaderPath, PushListRequestBean pushRequest);
}
