package com.nuoxin.virtual.rep.api.web.controller.v2_5;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nuoxin.virtual.rep.api.common.bean.DefaultResponseBean;
import com.nuoxin.virtual.rep.api.common.bean.PageResponseBean;
import com.nuoxin.virtual.rep.api.entity.DrugUser;
import com.nuoxin.virtual.rep.api.entity.v2_5.MeetingBean;
import com.nuoxin.virtual.rep.api.service.v2_5.VirtualDoctorMeetingService;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.meeting.MeetingListRequest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 会议 Controller 类
 * @author xiekaiyu
 */
@Api(value = "V2.5会议接口")
@RequestMapping(value = "/meeting/info")
@RestController
public class VirtualDoctorMeetingController extends BaseController{
	
	@Resource(name="virtualDoctorMeetingServiceImpl")
	private VirtualDoctorMeetingService meetingService;
	
	@SuppressWarnings("unchecked")
	@ApiOperation(value = "获取参会列表信息", notes = "获取参会列表信息")
	@RequestMapping(value = "/list/get", method = { RequestMethod.POST })
	public DefaultResponseBean<PageResponseBean<List<MeetingBean>>> getCallVisitList(HttpServletRequest request,
			@RequestBody MeetingListRequest listRequest) {
		DrugUser user = this.getDrugUser(request);
		if(user == null) {
			return super.getLoginErrorResponse();
		} 
		
		if(listRequest.getVirtualDoctorId() == null) {
			return super.getParamsErrorResponse("virtualDoctorId is null");
		}
		
		DefaultResponseBean<PageResponseBean<List<MeetingBean>>> responseBean = new DefaultResponseBean<PageResponseBean<List<MeetingBean>>>();
		PageResponseBean<List<MeetingBean>> result = meetingService.getMeetingList(listRequest);
		responseBean.setData(result);
		
		return responseBean;
	}
	
}
