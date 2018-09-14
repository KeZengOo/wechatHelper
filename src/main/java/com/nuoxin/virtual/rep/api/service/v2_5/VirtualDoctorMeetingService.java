package com.nuoxin.virtual.rep.api.service.v2_5;

import java.util.List;

import com.nuoxin.virtual.rep.api.common.bean.PageResponseBean;
import com.nuoxin.virtual.rep.api.entity.v2_5.MeetingBean;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.meeting.MeetingListRequestBean;

/**
 * 会议业务接口类
 * @author xiekaiyu
 */
public interface VirtualDoctorMeetingService {
	
	/**
	 * 获取参会列表
	 * @param request
	 * @return
	 */
	PageResponseBean<List<MeetingBean>> getMeetingList(MeetingListRequestBean request);
}
