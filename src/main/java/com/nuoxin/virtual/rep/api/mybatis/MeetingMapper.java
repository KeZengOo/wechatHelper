package com.nuoxin.virtual.rep.api.mybatis;

import com.nuoxin.virtual.rep.api.entity.v2_5.MeetingNoRemindParams;
import com.nuoxin.virtual.rep.api.web.controller.request.meeting.MeetingRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.meeting.MeetingResponseBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Create by tiancun on 2017/10/11
 */
public interface MeetingMapper {


    List<MeetingResponseBean> getList(MeetingRequestBean bean);

    Integer getListCount(MeetingRequestBean bean);


    /**
     * 会议不需要提醒的
     * @param list
     */
    void addMeetingNoRemind(List<MeetingNoRemindParams> list);


    /**
     * 得到需要提醒医生的会议
     * @param productId
     * @param overMinute
     * @return
     */
    List<Long> getOverMinuteMeetingList(@Param(value = "productId") Long productId,@Param(value = "overMinute") Integer overMinute);
}
