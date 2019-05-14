package com.nuoxin.virtual.rep.api.mybatis;

import com.nuoxin.virtual.rep.api.web.controller.request.v3_0.WechatMessageRequest;
import com.nuoxin.virtual.rep.api.web.controller.response.v3_0.WechatMessageSummaryResponse;

import java.util.List;

/**
 * 微信消息相关
 * @author tiancun
 * @date 2019-05-14
 */
public interface VirtualWechatMessageMapper {


    /**
     * 微信概况列表
     * @param request
     * @return
     */
    List<WechatMessageSummaryResponse> getWechatMessageSummaryList(WechatMessageRequest request);


    /**
     * 微信概况列表总数
     * @param request
     * @return
     */
    Integer getWechatMessageSummaryListCount(WechatMessageRequest request);


}
