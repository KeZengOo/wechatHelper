package com.nuoxin.virtual.rep.api.mybatis;

import com.nuoxin.virtual.rep.api.entity.v3_0.params.ContentSharingParams;
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
}
