package com.nuoxin.virtual.rep.api.mybatis;

import com.nuoxin.virtual.rep.api.web.controller.request.WorkStationRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.work.TodayStatisticsResponseBean;

import java.util.Map;

/**
 * Create by tiancun on 2017/10/12
 */
public interface MeetingDetailMapper {


    //会议统计
    TodayStatisticsResponseBean getMeetingStatistic(WorkStationRequestBean bean);

}
