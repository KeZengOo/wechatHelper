package com.nuoxin.virtual.rep.api.mybatis;

import com.nuoxin.virtual.rep.api.web.controller.request.v3_0.DailyReportRequest;
import com.nuoxin.virtual.rep.api.web.controller.response.v3_0.*;
import com.nuoxin.virtual.rep.api.web.controller.response.v3_0.daily.CallVisitStatisticsResponse;
import com.nuoxin.virtual.rep.api.web.controller.response.v3_0.daily.VisitChannelDoctorNumResponse;
import com.nuoxin.virtual.rep.api.web.controller.response.v3_0.daily.VisitResultDoctorNumStatisticsResponse;
import com.nuoxin.virtual.rep.api.web.controller.response.v3_0.daily.VisitResultHospitalNumStatisticsResponse;
import org.apache.ibatis.annotations.Param;

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
     * 电话拜访统计，次数，时长
     * @param reportRequest
     * @return
     */
    CallVisitStatisticsResponse getCallVisitStatistics(DailyReportRequest reportRequest);



    /**
     * 电话接通次数
     * @param reportRequest
     * @return
     */
    Integer callConnectCount(DailyReportRequest reportRequest);


    /**
     * 电话接通未次数
     * @param reportRequest
     * @return
     */
    Integer callUnConnectCount(DailyReportRequest reportRequest);


    /**
     * 不同渠道拜访医生人数
     * @param reportRequest
     * @return
     */
    List<VisitChannelDoctorNumResponse> getVisitChannelDoctorNumList(DailyReportRequest reportRequest);


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


    /**
     * 拜访医生数：
     * 所有打电话的医生+拜访登记的医生+参会医生+微信聊过天的医生（无论医生是否回复）+ 短信分享文章 + 小程序阅读
     * @param reportRequest
     * @return
     */
    List<VisitDoctorStatisticsResponse> visitDateDoctorNum(DailyReportRequest reportRequest);



    /**
     * 拜访医院数：
     * 所有打电话的医生+拜访登记的医生+参会医生+微信聊过天的医生（无论医生是否回复）+ 短信分享文章 + 小程序阅读
     * @param reportRequest
     * @return
     */
    List<VisitHospitalStatisticsResponse> visitDateHospitalNum(DailyReportRequest reportRequest);

    /**
     * 不同的拜访结果的医生数统计
     * @param reportRequest
     * @param visitType
     * @return
     */
    Integer visitResultDoctorNum(DailyReportRequest reportRequest,@Param(value = "visitType") Integer visitType);


    /**
     * 不同的拜访结果的医生数统计
     * @param reportRequest
     * @param visitType
     * @return
     */
    List<VisitDoctorStatisticsResponse> visitDateVisitResultDoctorNum(DailyReportRequest reportRequest,@Param(value = "visitType") Integer visitType);


    /**
     * 不同的拜访结果的医院数统计
     * @param reportRequest
     * @param visitType
     * @return
     */
    Integer visitResultHospitalNum(DailyReportRequest reportRequest,@Param(value = "visitType") Integer visitType);


    /**
     * 不同的拜访结果的医院数统计
     * @param reportRequest
     * @param visitType
     * @return
     */
    List<VisitHospitalStatisticsResponse> visitDateVisitResultHospitalNum(DailyReportRequest reportRequest,@Param(value = "visitType") Integer visitType);




    /**
     * 拜访次数
     * 算法：拜访登记+参会+内容(APP+小程序)
     *
     * @param reportRequest
     * @return
     */
    Integer visitCount(DailyReportRequest reportRequest);



    /**
     * 拜访次数, 分日期
     * 算法：拜访登记+参会+内容(APP+小程序)
     *
     * @param reportRequest
     * @return
     */
    List<VisitCountStatisticsResponse> visitDateVisitCount(DailyReportRequest reportRequest);



    /**
     * 不同的拜访渠道的医生人数统计结果
     * @param reportRequest
     * @param visitChannel
     * @return
     */
    Integer callDoctorNum(DailyReportRequest reportRequest,@Param(value = "visitChannel") Integer visitChannel);


    /**
     * 不同的拜访渠道的医生人数统计结果,分日期
     * @param reportRequest
     * @param visitChannel
     * @return
     */
    List<VisitCountStatisticsResponse> visitDateCallDoctorNum(DailyReportRequest reportRequest,@Param(value = "visitChannel") Integer visitChannel);






    /**
     * 电话接通数,分日期
     * @param reportRequest
     * @return
     */
    List<VisitCountStatisticsResponse> visitDateCallConnectCount(DailyReportRequest reportRequest);

    /**
     * 参会医生数
     * @param reportRequest
     * @return
     */
    Integer attendMeetingDoctorNum(DailyReportRequest reportRequest);


    /**
     * 参会医生数,分日期
     * @param reportRequest
     * @return
     */
    List<VisitCountStatisticsResponse> visitDateAttendMeetingDoctorNum(DailyReportRequest reportRequest);


    /**
     * 阅读医生数
     * @param reportRequest
     * @return
     */
    Integer readDoctorNum(DailyReportRequest reportRequest);



    /**
     * 阅读医生数，分日期
     * @param reportRequest
     * @return
     */
    List<VisitCountStatisticsResponse> visitDateReadDoctorNum(DailyReportRequest reportRequest);

}
