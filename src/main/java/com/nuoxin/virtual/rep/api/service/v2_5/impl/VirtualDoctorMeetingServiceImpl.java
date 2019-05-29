package com.nuoxin.virtual.rep.api.service.v2_5.impl;

import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.nuoxin.virtual.rep.api.common.bean.PageResponseBean;
import com.nuoxin.virtual.rep.api.entity.v2_5.MeetingBean;
import com.nuoxin.virtual.rep.api.mybatis.MeetingAttendMapper;
import com.nuoxin.virtual.rep.api.service.v2_5.VirtualDoctorMeetingService;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.meeting.MeetingListRequestBean;

/**
 * 会议业务接口类
 * @author xiekaiyu
 */
@Service("virtualDoctorMeetingServiceImpl")
public class VirtualDoctorMeetingServiceImpl implements VirtualDoctorMeetingService{
	
	@Resource
	private MeetingAttendMapper meetingAttendMapper;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public PageResponseBean<List<MeetingBean>> getMeetingList(MeetingListRequestBean request) {
		PageResponseBean pageResponse = null;
		Long virtualDoctorId = request.getVirtualDoctorId();
		//String leaderPath = request.getLeaderPath();
		List<Long> productIdList = request.getProductIdList();
		int count = meetingAttendMapper.getProductMeetingAttendCount(virtualDoctorId, productIdList);
		if(count > 0) {
			List<MeetingBean> list = meetingAttendMapper.getProductMeetingAttendList(virtualDoctorId, productIdList, request.getCurrentSize(),
					request.getPageSize());
			pageResponse = new PageResponseBean(request, count, list);
		}
		
		if (pageResponse == null) {
			pageResponse = new PageResponseBean(request, 0, Collections.emptyList());
		}
		
		return pageResponse;
	}
	
}
