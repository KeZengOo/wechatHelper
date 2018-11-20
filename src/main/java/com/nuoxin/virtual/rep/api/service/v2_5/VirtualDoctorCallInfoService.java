package com.nuoxin.virtual.rep.api.service.v2_5;

import java.util.List;

import com.nuoxin.virtual.rep.api.common.bean.PageResponseBean;
import com.nuoxin.virtual.rep.api.entity.v2_5.CallVisitBean;
import com.nuoxin.virtual.rep.api.entity.v2_5.CallVisitStatisticsBean;
import com.nuoxin.virtual.rep.api.web.controller.request.call.CallRequestBean;
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
	
	CallVisitStatisticsBean getCallVisitListStatistics(Long virtualDoctorId, String leaderPath);
	
	/**
	 * 保存电话接通拜访结果,有RERUIRED事务
	 * @param saveRequest
	 * @return 成功返回 true ,否则返回 false
	 */
	boolean saveConnectedCallInfo(SaveCallInfoRequest saveRequest);
	
	/**
	 * 保存电话未接通拜访结果,有RERUIRED事务
	 * @param saveRequest
	 * @return
	 */
	boolean saveUnconnectedCallInfo(SaveCallInfoUnConnectedRequest saveRequest);


	/**
	 * 保存电话记录
	 * @param bean
	 */
	void saveCallInfo(CallRequestBean bean);


	/**
	 * 根据产品ID得到医生招募状态
	 * @param productId
	 * @param doctorId
	 * @return
	 */
	Integer getProductRecruit(Long productId, Long doctorId);

}
