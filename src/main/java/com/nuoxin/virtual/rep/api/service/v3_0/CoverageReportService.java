package com.nuoxin.virtual.rep.api.service.v3_0;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @ClassName CoverageReportService
 * @Description 拜访数据汇总 覆盖月报Service
 * @Author dangjunhui
 * @Date 2019/6/14 16:08
 * @Version 1.0
 */
public interface CoverageReportService {

    /**
     * 根据产品id和时间查询覆盖月报总览
     * @param proId 产品id
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return map
     */
    Map<String, Object> findOverviewListByProductIdAndTime(Long proId, String startTime, String endTime);

    /**
     * 根据产品id导出覆盖月报总览
     * @param response 输出流
     * @param proId 产品id
     * @param startTime 开始时间
     * @param endTime 结束时间
     */
    void exportOverview(HttpServletResponse response, Long proId, String startTime, String endTime);

    /**
     * 根据产品id和时间查询电话覆盖数据
     * @param productId 产品id
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return map
     */
    Map<String,Object> findCallListByProductIdAndTime(Long productId, String startTime, String endTime);

    /**
     * 根据产品id导出电话覆盖数据
     * @param response 输出流
     * @param proId 产品id
     * @param startTime 开始时间
     * @param endTime 结束时间
     */
    void exportCall(HttpServletResponse response, Long proId, String startTime, String endTime);

    /**
     * 根据产品id和时间查询微信覆盖数据
     * @param productId 产品id
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return map
     */
    Map<String,Object> findWeChatListByProductIdAndTime(Long productId, String startTime, String endTime);

    /**
     * 根据产品id导出微信覆盖数据
     * @param response 输出流
     * @param proId 产品id
     * @param startTime 开始时间
     * @param endTime 结束时间
     */
    void exportWeChat(HttpServletResponse response, long proId, String startTime, String endTime);

    /**
     * 根据产品id和时间查询会议覆盖数据
     * @param productId 产品id
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return map
     */
    Map<String,Object> findMeetingListByProductIdAndTime(Long productId, String startTime, String endTime);

    /**
     * 根据产品id导出会议覆盖数据
     * @param response 输出流
     * @param proId 产品id
     * @param startTime 开始时间
     * @param endTime 结束时间
     */
    void exportMeeting(HttpServletResponse response, long proId, String startTime, String endTime);

    /**
     * 根据产品id和时间查询内容覆盖数据
     * @param productId 产品id
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return map
     */
    Map<String,Object> findContentListByProductIdAndTime(Long productId, String startTime, String endTime);

    /**
     * 根据产品id导出内容覆盖数据
     * @param response 输出流
     * @param proId 产品id
     * @param startTime 开始时间
     * @param endTime 结束时间
     */
    void exportContent(HttpServletResponse response, long proId, String startTime, String endTime);

    /**
     * 根据产品id和时间查询患者量覆盖数据
     * @param productId 产品id
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return map
     */
    Map<String,Object> findPatientVolumeListByProductIdAndTime(Long productId, String startTime, String endTime);

    /**
     * 根据产品id导出患者量覆盖数据
     * @param response 输出流
     * @param proId 产品id
     * @param startTime 开始时间
     * @param endTime 结束时间
     */
    void exportPatientVolume(HttpServletResponse response, long proId, String startTime, String endTime);

}
