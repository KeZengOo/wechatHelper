package com.nuoxin.virtual.rep.api.mybatis;

import com.nuoxin.virtual.rep.api.web.controller.request.v3_0.DailyReportRequest;
import com.nuoxin.virtual.rep.api.web.controller.response.v3_0.VisitResultDoctorNumStatisticsResponse;
import com.nuoxin.virtual.rep.api.web.controller.response.v3_0.VisitResultHospitalNumStatisticsResponse;

import java.util.List;

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
     * 招募医院数
     * @param reportRequest
     * @return
     */
    Integer recruitHospitalNum(DailyReportRequest reportRequest);


    /**
     * 覆盖医生数
     * @param reportRequest
     * @return
     */
    Integer coverDoctorNum(DailyReportRequest reportRequest);

    /**
     * 覆盖医院数
     * @param reportRequest
     * @return
     */
    Integer coverHospitalNum(DailyReportRequest reportRequest);



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


    /**
     * 多渠道覆盖医生数，满足任意三项
     * 1、单次通话时长大于等于 1分15秒
     * 2、医生有微信回复
     * 3、存在参会记录
     * 4、文章阅读大于等于 50s（包含 app 和小程序）
     * 5、医生写过问卷 (暂未提供)
     * @param reportRequest
     * @return
     */
    Integer mulChannelDoctorNum(DailyReportRequest reportRequest);


    /**
     * 统计产品设置的各个拜访结果下的医生数量
     * @param reportRequest
     * @return
     */
    List<VisitResultDoctorNumStatisticsResponse> getVisitResultDoctorNumList(DailyReportRequest reportRequest);

    /**
     * 统计产品设置的各个拜访结果下的医院数量
     * @param reportRequest
     * @return
     */
    List<VisitResultHospitalNumStatisticsResponse> getVisitResultHospitalNumList(DailyReportRequest reportRequest);


    /**
     * 微信回复医生人数
     * @param reportRequest
     * @return
     */
    Integer getWechatReplyDoctorNum(DailyReportRequest reportRequest);


    /**
     * 微信回复医生人次
     * @param reportRequest
     * @return
     */
    Integer getWechatReplyDoctorCount(DailyReportRequest reportRequest);


    /**
     * 添加微信的人数，根据微信聊天消息判断
     * @param reportRequest
     * @return
     */
    Integer addWechatDoctorNum(DailyReportRequest reportRequest);

    /**
     * 有微信的医生人数
     * @param reportRequest
     * @return
     */
    Integer hasWechatDoctorNum(DailyReportRequest reportRequest);

    /**
     * 有需求的医生数
     * @param reportRequest
     * @return
     */
    Integer hasDemandDoctorNum(DailyReportRequest reportRequest);

    /**
     * 有AE医生人数
     * @param reportRequest
     * @return
     */
    Integer hasAeDoctorNum(DailyReportRequest reportRequest);


    /**
     * 退出项目医生数
     * @param reportRequest
     * @return
     */
    Integer quitDoctorNum(DailyReportRequest reportRequest);

}
