package com.nuoxin.virtual.rep.api.mybatis;

import com.nuoxin.virtual.rep.api.entity.v3_0.params.ContentReadLogsParams;
import com.nuoxin.virtual.rep.api.entity.v3_0.params.ContentReadLogsTimeParams;
import com.nuoxin.virtual.rep.api.entity.v3_0.params.ContentSharingParams;
import com.nuoxin.virtual.rep.api.entity.v3_0.request.ContentReadLogsRequest;
import com.nuoxin.virtual.rep.api.entity.v3_0.request.ContentSharingRequest;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 内容分享
 * @author wujiang
 * @date 20190505
 */
public interface ContentSharingMapper {

    /**
     * 内容分享列表分页
     * @param contentSharingRequest
     * @param drugUserIds
     * @return list
     */
    List<ContentSharingParams> getContentSharingListPage(@Param("contentSharingRequest") ContentSharingRequest contentSharingRequest, @Param("drugUserIds") List<Long> drugUserIds);

    /**
     * 内容分享列表Count
     * @param contentSharingRequest
     * @param drugUserIds
     * @return int
     */
    List<ContentSharingParams> getContentSharingListCount(@Param("contentSharingRequest") ContentSharingRequest contentSharingRequest, @Param("drugUserIds") List<Long> drugUserIds);

    /**
     * 内容阅读记录列表
     * @param contentReadLogsRequest
     * @param drugUserIds
     * @return list
     */
    List<ContentReadLogsParams> getContentReadLogsListPage(@Param("contentReadLogsRequest") ContentReadLogsRequest contentReadLogsRequest, @Param("drugUserIds") List<Long> drugUserIds);

    /**
     * 内容阅读记录列表Count
     * @param contentReadLogsRequest
     * @param drugUserIds
     * @return int
     */
    Integer getContentReadLogsListCount(@Param("contentReadLogsRequest") ContentReadLogsRequest contentReadLogsRequest, @Param("drugUserIds") List<Long> drugUserIds);

    /**
     * 根据内容ID和医生ID查询阅读时间数组和阅读时长数组
     * @param dataId
     * @param doctorId
     * @return list
     */
    List<ContentReadLogsTimeParams> getReadTimeAndReadDurationByDataIdAndDoctorId(@Param("dataId")Long dataId, @Param("doctorId")Long doctorId);

    /**
     * 内容分享列表
     * @param contentSharingRequest
     * @param drugUserIds
     * @return list
     */
    List<Map<String,Object>> getContentSharingList(@Param("contentSharingRequest") ContentSharingRequest contentSharingRequest, @Param("drugUserIds") List<Long> drugUserIds);

    /**
     * 内容分享列表
     * @param productId
     * @param drugUserIds
     * @param startTimeAfter
     * @param startTimeBefore
     * @param title
     * @return list
     */
    List<ContentSharingParams> getContentSharingCSVList(@Param("productId")Integer productId, @Param("drugUserIds") List<Long> drugUserIds, @Param("startTimeAfter")String startTimeAfter, @Param("startTimeBefore")String startTimeBefore, @Param("shareType") Integer shareType, @Param("title") String title);

    /**
     * 查询文章列表的代表类型
     * @param drugUserId
     * @return
     */
    List<ContentSharingParams> getContentSharingRoleNameByDrugUserId(@Param("drugUserId") Long drugUserId);
}
