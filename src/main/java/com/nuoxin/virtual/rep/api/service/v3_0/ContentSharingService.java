package com.nuoxin.virtual.rep.api.service.v3_0;

import com.nuoxin.virtual.rep.api.common.bean.PageResponseBean;
import com.nuoxin.virtual.rep.api.entity.v3_0.params.ContentSharingParams;
import com.nuoxin.virtual.rep.api.entity.v3_0.request.ContentSharingRequest;

import java.util.List;

/**
 * 内容分享Service
 * @author wujiang
 * @date 20190505
 */
public interface ContentSharingService {

    /**
     * 内容分享列表
     * @param contentSharingRequest
     * @return list
     */
    PageResponseBean<List<ContentSharingParams>> getContentSharingListPage(ContentSharingRequest contentSharingRequest);
}
