package com.nuoxin.virtual.rep.api.service.v2_5;

import java.util.List;

import com.nuoxin.virtual.rep.api.common.bean.PageResponseBean;
import com.nuoxin.virtual.rep.api.entity.v2_5.CallVisitBean;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.callinfo.CallInfoListRequest;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.callinfo.SaveCallInfoRequest;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.callinfo.SaveCallInfoUnConnectedRequest;

/**
 * 电话拜访业务接口类
 * @author xiekaiyu
 */
public interface VirtualDoctorCallInfoService {
	
	/**
	 * 根据 leaderPath 获取电话拜访列表
	 * @param request
	 * @param leaderPath
	 * @return
	 */
	PageResponseBean<List<CallVisitBean>> getCallVisitList(CallInfoListRequest request, String leaderPath);
	
	/**
	 * 保存电话接通拜访结果,有RERUIRED事务
	 * @param saveRequest
	 * @return 成功返回 true ,否则返回 false
	 */
	boolean connectedSaveCallInfo(SaveCallInfoRequest saveRequest);
	
	/**
	 * 保存电话未接通拜访结果,有RERUIRED事务
	 * @param saveRequest
	 * @return
	 */
	boolean unconnectedSaveCallInfo(SaveCallInfoUnConnectedRequest saveRequest);
	
}
