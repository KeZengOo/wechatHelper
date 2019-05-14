package com.nuoxin.virtual.rep.api.service.v3_0;

import com.nuoxin.virtual.rep.api.common.bean.PageResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.request.v3_0.WechatMessageRequest;
import com.nuoxin.virtual.rep.api.web.controller.response.v3_0.WechatMessageSummaryResponse;

import javax.servlet.http.HttpServletResponse;

/**
 * 微信消息相关
 * @author tiancun
 * @date 2019-05-14
 */
public interface WechatMessageService {


    /**
     * 代表和医生聊天或者群消息概要统计
     * @param request
     * @return
     */
    PageResponseBean<WechatMessageSummaryResponse> getWechatMessageSummaryPage(WechatMessageRequest request);


    /**
     * 代表和医生聊天或者群消息概要统计导出
     * @param response
     * @param request
     */
    void exportWechatMessageSummaryList(HttpServletResponse response, WechatMessageRequest request);


}
