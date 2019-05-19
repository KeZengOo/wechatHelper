package com.nuoxin.virtual.rep.api.web.controller.v3_0;

import com.nuoxin.virtual.rep.api.common.bean.DefaultResponseBean;
import com.nuoxin.virtual.rep.api.common.bean.PageResponseBean;
import com.nuoxin.virtual.rep.api.common.enums.ErrorEnum;
import com.nuoxin.virtual.rep.api.common.exception.BusinessException;
import com.nuoxin.virtual.rep.api.service.v2_5.WechatService;
import com.nuoxin.virtual.rep.api.service.v3_0.WechatMessageService;
import com.nuoxin.virtual.rep.api.utils.StringUtil;
import com.nuoxin.virtual.rep.api.web.controller.request.v3_0.WechatChatRoomMessageRequest;
import com.nuoxin.virtual.rep.api.web.controller.request.v3_0.WechatMessageRequest;
import com.nuoxin.virtual.rep.api.web.controller.response.v3_0.WechatChatRoomMessageResponse;
import com.nuoxin.virtual.rep.api.web.controller.response.v3_0.WechatChatRoomResponse;
import com.nuoxin.virtual.rep.api.web.controller.response.v3_0.WechatMessageSummaryResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 我的客户相关接口
 * @author tiancun
 * @date 2019-05-14
 */
@RestController
@Api(value = "V3.0.1微信聊天消息查询相关")
@RequestMapping(value = "/wechat")
public class WechatMessageController {

    @Resource
    private WechatService wechatService;

    @Resource
    private WechatMessageService wechatMessageService;


    @ApiOperation(value = "概要列表", notes = "概要列表")
    @PostMapping(value = "/message/summary/list")
    public DefaultResponseBean<PageResponseBean<WechatMessageSummaryResponse>> wechatMessageSummaryPage(@RequestBody WechatMessageRequest request) {

        PageResponseBean<WechatMessageSummaryResponse> wechatMessageSummaryPage = wechatMessageService.getWechatMessageSummaryPage(request);
        DefaultResponseBean<PageResponseBean<WechatMessageSummaryResponse>> responseBean = new DefaultResponseBean<>();
        responseBean.setData(wechatMessageSummaryPage);
        return responseBean;
    }



    @ApiOperation(value = "概要列表导出", notes = "概要列表导出")
    @GetMapping(value = "/message/summary/list/export")
    public void wechatMessageSummaryPage(HttpServletResponse response, HttpServletRequest request) {

        WechatMessageRequest bean = this.getExportParams(request);
        wechatMessageService.exportWechatMessageSummaryList(response, bean);
    }


    @ApiOperation(value = "医生详情群列表", notes = "医生详情群列表")
    @GetMapping(value = "/doctor/chat/room/list/{drugUserId}/{doctorId}")
    public DefaultResponseBean<List<WechatChatRoomResponse>> getDoctorPage(@PathVariable(value = "drugUserId") Long drugUserId, @PathVariable(value = "doctorId") Long doctorId) {

        List<WechatChatRoomResponse> wechatChatRoomList = wechatService.getWechatChatRoomList(drugUserId, doctorId);
        DefaultResponseBean<List<WechatChatRoomResponse>> responseBean = new DefaultResponseBean<>();
        responseBean.setData(wechatChatRoomList);
        return responseBean;
    }


    @ApiOperation(value = "微信群消息", notes = "微信群消息")
    @PostMapping(value = "/chat/room/message")
    public DefaultResponseBean<PageResponseBean<WechatChatRoomMessageResponse>> getWechatChatRoomMessagePage(@RequestBody WechatChatRoomMessageRequest request) {

        PageResponseBean<WechatChatRoomMessageResponse> wechatChatRoomMessagePage = wechatService.getWechatChatRoomMessagePage(request);
        DefaultResponseBean<PageResponseBean<WechatChatRoomMessageResponse>> responseBean = new DefaultResponseBean<>();
        responseBean.setData(wechatChatRoomMessagePage);
        return responseBean;
    }


    /**
     * 得到导出的请求参数
     * @param request
     * @return
     */
    private WechatMessageRequest getExportParams(HttpServletRequest request) {

        String searchKeyword = request.getParameter("searchKeyword");
        String productIdStr = request.getParameter("productIdStr");
        String drugUserIdStr = request.getParameter("drugUserIdStr");



        List<Long> productIdList;
        List<Long> drugUserIdList;

        try {
            productIdList = StringUtil.getIdList(productIdStr);
        }catch (Exception e){
            throw new BusinessException(ErrorEnum.ERROR, "产品ID输入不合法，多个以逗号分开");
        }
        try {
            drugUserIdList = StringUtil.getIdList(drugUserIdStr);
        }catch (Exception e){
            throw new BusinessException(ErrorEnum.ERROR, "代表ID输入不合法，多个以逗号分开");
        }

        WechatMessageRequest wechatMessageRequest = new WechatMessageRequest();
        wechatMessageRequest.setSearchKeyword(searchKeyword);
        wechatMessageRequest.setDrugUserIdList(drugUserIdList);
        wechatMessageRequest.setProductIdList(productIdList);
        return wechatMessageRequest;

    }


}
