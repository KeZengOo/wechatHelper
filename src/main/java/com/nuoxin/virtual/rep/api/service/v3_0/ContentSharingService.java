package com.nuoxin.virtual.rep.api.service.v3_0;

import com.nuoxin.virtual.rep.api.common.bean.PageResponseBean;
import com.nuoxin.virtual.rep.api.entity.v3_0.params.ContentReadLogsParams;
import com.nuoxin.virtual.rep.api.entity.v3_0.params.ContentSharingParams;
import com.nuoxin.virtual.rep.api.entity.v3_0.params.FilePathParams;
import com.nuoxin.virtual.rep.api.entity.v3_0.request.ContentReadLogsRequest;
import com.nuoxin.virtual.rep.api.entity.v3_0.request.ContentSharingRequest;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
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

    /**
     * 内容分享列表
     * @param contentReadLogsRequest
     * @return list
     */
    PageResponseBean<List<ContentReadLogsParams>> getContentReadLogsListPage(ContentReadLogsRequest contentReadLogsRequest);


    /**
     * 导出内容分享列表
     * @param contentSharingRequest
     * @param response
     */
    FilePathParams exportFile(ContentSharingRequest contentSharingRequest, HttpServletResponse response);

    /**
     * 导出内容分享列表下载
     * @param outPutPath
     * @param fileName
     * @param response
     */
    void download(String outPutPath, String fileName, HttpServletResponse response);

    /**
     * 导出内容分享列表下载
     * @param productId
     * @param drugUserId
     * @param startTimeAfter
     * @param startTimeBefore
     * @param shareType
     * @param response
     */
    void contentSharingExportFile(Integer productId, Long[] drugUserId, String startTimeAfter, String startTimeBefore,Integer shareType, HttpServletResponse response);
}
