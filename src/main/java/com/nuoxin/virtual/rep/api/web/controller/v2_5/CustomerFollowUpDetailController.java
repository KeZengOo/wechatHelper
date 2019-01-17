package com.nuoxin.virtual.rep.api.web.controller.v2_5;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nuoxin.virtual.rep.api.common.bean.DefaultResponseBean;
import com.nuoxin.virtual.rep.api.common.bean.PageResponseBean;
import com.nuoxin.virtual.rep.api.entity.DrugUser;
import com.nuoxin.virtual.rep.api.entity.v2_5.CallVisitBean;
import com.nuoxin.virtual.rep.api.entity.v2_5.CallVisitStatisticsBean;
import com.nuoxin.virtual.rep.api.entity.v2_5.MeetingBean;
import com.nuoxin.virtual.rep.api.entity.v2_5.VirtualDoctorBasicResponse;
import com.nuoxin.virtual.rep.api.entity.v2_5.VirtualDoctorMiniResponse;
import com.nuoxin.virtual.rep.api.service.v2_5.VirtualDoctorCallInfoService;
import com.nuoxin.virtual.rep.api.service.v2_5.VirtualDoctorMeetingService;
import com.nuoxin.virtual.rep.api.service.v2_5.VirtualDoctorPushService;
import com.nuoxin.virtual.rep.api.service.v2_5.VirtualDoctorService;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.callinfo.CallInfoListRequest;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.meeting.MeetingListRequestBean;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 客户跟进-首页 Controller 类
 * @author xiekaiyu
 */
@Api(value = "V2.5客户跟进-详情相关接口")
@RequestMapping(value = "/customer/followup/details")
@RestController
public class CustomerFollowUpDetailController extends NewBaseController {
	
	@Resource(name="virtualDoctorMeetingServiceImpl")
	private VirtualDoctorMeetingService meetingService;
	@Resource
	private VirtualDoctorCallInfoService callInfoService;
	@Resource
	private VirtualDoctorService virtualDoctorService;
	@Resource
	private VirtualDoctorPushService pushService;
	
	@SuppressWarnings("unchecked")
	@ApiOperation(value = "获取电话拜访列表信息(统计接通数与未接通数) TODO @田存")
	@RequestMapping(value = { "/call/info/list/statistics/get" }, method = { RequestMethod.POST })
	public DefaultResponseBean<CallVisitStatisticsBean> getCallVisitListStatistics(HttpServletRequest request,
			@ApiParam("医生ID") @RequestParam(value = "doctor_id") Long virtualDoctorId) {
		DrugUser user = this.getDrugUser(request);
		if(user == null) {
			return super.getLoginErrorResponse();
		} 
		
		DefaultResponseBean<CallVisitStatisticsBean> responseBean = new DefaultResponseBean<>();
		CallVisitStatisticsBean result;
		if(virtualDoctorId == null || virtualDoctorId.equals(0L)) {
			result = new CallVisitStatisticsBean();
			result.setConnectedTotalNums(0);
			result.setUnConnectedTotalNums(0);
		} else {
			String leaderPath = user.getLeaderPath();
			result = callInfoService.getCallVisitListStatistics(virtualDoctorId, leaderPath);
		}
		
		responseBean.setData(result);
		return responseBean;
	}
	
	@SuppressWarnings("unchecked")
	@ApiOperation(value = "获取电话拜访列表信息(电话拜访记录)")
	@RequestMapping(value = { "/call/info/list/get" }, method = { RequestMethod.POST })
	public DefaultResponseBean<PageResponseBean<List<CallVisitBean>>> getCallVisitList(HttpServletRequest request,
			@RequestBody CallInfoListRequest listRequest) {
		DrugUser user = this.getDrugUser(request);
		if(user == null) {
			return super.getLoginErrorResponse();
		} 
		
		String leaderPath = user.getLeaderPath();
		PageResponseBean<List<CallVisitBean>> result = callInfoService.getCallVisitList(listRequest, leaderPath);
		DefaultResponseBean<PageResponseBean<List<CallVisitBean>>> responseBean = new DefaultResponseBean<PageResponseBean<List<CallVisitBean>>>();
		responseBean.setData(result);
		
		return responseBean;
	}
	
	@SuppressWarnings("unchecked")
	@ApiOperation(value = "获取参会列表信息(参会记录)")
	@RequestMapping(value = "/meeting/info/list/get", method = { RequestMethod.POST })
	public DefaultResponseBean<PageResponseBean<List<MeetingBean>>> getMeetingList(HttpServletRequest request,
			@RequestBody MeetingListRequestBean listRequest) {
		DrugUser user = this.getDrugUser(request);
		if(user == null) {
			return super.getLoginErrorResponse();
		} 
		
		if(listRequest.getVirtualDoctorId() == null) {
			return super.getParamsErrorResponse("virtualDoctorId is null");
		}
		listRequest.setLeaderPath(user.getLeaderPath());
		PageResponseBean<List<MeetingBean>> result = meetingService.getMeetingList(listRequest);
		DefaultResponseBean<PageResponseBean<List<MeetingBean>>> responseBean = new DefaultResponseBean<PageResponseBean<List<MeetingBean>>>();
		responseBean.setData(result);
		
		return responseBean;
	}
	
	@SuppressWarnings("unchecked")
	@ApiOperation(value = "获取单个客户医生简要信息")
	@RequestMapping(value = "/doctor/single/mini/get", method = { RequestMethod.GET })
	public DefaultResponseBean<VirtualDoctorMiniResponse> getSingleMini(
			@ApiParam("医生ID") @RequestParam(value = "doctor_id") Long virtualDoctorId, HttpServletRequest request) {
		DrugUser user = this.getDrugUser(request);
		if (user == null) {
			return super.getLoginErrorResponse();
		}

		if (virtualDoctorId == null) {
			virtualDoctorId = 0L;
		}

		VirtualDoctorMiniResponse virtualDoctorBasic = virtualDoctorService.getVirtualDoctorMini(virtualDoctorId);
		DefaultResponseBean<VirtualDoctorMiniResponse> responseBean = new DefaultResponseBean<VirtualDoctorMiniResponse>();
		responseBean.setData(virtualDoctorBasic);
		return responseBean;
	}
	
	@SuppressWarnings("unchecked")
	@ApiOperation(value = "获取单个客户医生信息(基本信息)")
	@RequestMapping(value = "/doctor/single/get", method = { RequestMethod.GET })
	public DefaultResponseBean<VirtualDoctorBasicResponse> getSingleBasic(
			@ApiParam("医生ID") @RequestParam(value = "doctor_id") Long virtualDoctorId, HttpServletRequest request) {
		DrugUser user = this.getDrugUser(request);
		if (user == null) {
			return super.getLoginErrorResponse();
		}

		if (virtualDoctorId == null) {
			virtualDoctorId = 0L;
		}

		VirtualDoctorBasicResponse virtualDoctorBasic = virtualDoctorService.getVirtualDoctorBasic(virtualDoctorId, user.getLeaderPath());
		DefaultResponseBean<VirtualDoctorBasicResponse> responseBean = new DefaultResponseBean<VirtualDoctorBasicResponse>();
		responseBean.setData(virtualDoctorBasic);
		return responseBean;
	}

}
