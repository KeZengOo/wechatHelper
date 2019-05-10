package com.nuoxin.virtual.rep.api.mybatis;

import com.nuoxin.virtual.rep.api.web.controller.request.v3_0.DailyReportRequest;

/**
 * 日报相关
 * @author tiancun
 * @date 2019-05-09
 */
public interface DailyReportMapper {


    /**
     * 招募医生数
     * @param reportRequest
     * @return
     */
    Integer recruitDoctorNum(DailyReportRequest reportRequest);



    /**
     * 活跃覆盖医生(收益)
     * 满足其中之一：
     * 1、单次通话时长大于等于 2.5min
     * 2、医生微信回复条数大于等于三次
     * 3、 全部会议的参会累计时长大于等于 10min
     * @param reportRequest
     * @return
     */
    Integer activeCoverDoctorNum(DailyReportRequest reportRequest);

}
