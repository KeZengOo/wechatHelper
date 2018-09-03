package com.nuoxin.virtual.rep.api.mybatis;

import com.nuoxin.virtual.rep.api.web.controller.request.meeting.MeetingRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.meeting.MeetingResponseBean;

import java.util.List;

/**
 * Create by tiancun on 2017/10/11
 */
public interface MeetingMapper {



    List<MeetingResponseBean> getList(MeetingRequestBean bean);

    Integer getListCount(MeetingRequestBean bean);

}
