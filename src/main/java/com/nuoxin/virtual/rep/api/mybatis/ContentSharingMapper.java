package com.nuoxin.virtual.rep.api.mybatis;

import com.nuoxin.virtual.rep.api.entity.v3_0.params.ContentReadLogsParams;
import com.nuoxin.virtual.rep.api.entity.v3_0.params.ContentReadLogsTimeParams;
import com.nuoxin.virtual.rep.api.entity.v3_0.params.ContentSharingParams;
import com.nuoxin.virtual.rep.api.entity.v3_0.request.ContentReadLogsRequest;
import com.nuoxin.virtual.rep.api.entity.v3_0.request.ContentSharingRequest;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 内容分享
 * @author wujiang
 * @date 20190505
 */
public interface ContentSharingMapper {

    /**
     * 内容分享列表
     * @param contentSharingRequest
     * @return list
     */
    List<ContentSharingParams> getContentSharingListPage(@Param("contentSharingRequest") ContentSharingRequest contentSharingRequest);

    /**
     * 内容分享列表Count
     * @param contentSharingRequest
     * @return int
     */
    Integer getContentSharingListCount(@Param("contentSharingRequest") ContentSharingRequest contentSharingRequest);

    /**
     * 内容阅读记录列表
     * @param contentReadLogsRequest
     * @return list
     */
    List<ContentReadLogsParams> getContentReadLogsListPage(@Param("contentReadLogsRequest") ContentReadLogsRequest contentReadLogsRequest);

    /**
     * 内容阅读记录列表Count
     * @param contentReadLogsRequest
     * @return int
     */
    Integer getContentReadLogsListCount(@Param("contentReadLogsRequest") ContentReadLogsRequest contentReadLogsRequest);

    /**
     * 根据内容ID和医生ID查询阅读时间数组和阅读时长数组
     * @param dataId
     * @param doctorId
     * @return list
     */
    List<ContentReadLogsTimeParams> getReadTimeAndReadDurationByDataIdAndDoctorId(@Param("dataId")Long dataId, @Param("doctorId")Long doctorId);

}
