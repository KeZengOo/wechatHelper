package com.nuoxin.virtual.rep.api.web.controller.v3_0;


import com.nuoxin.virtual.rep.api.common.bean.DefaultResponseBean;
import com.nuoxin.virtual.rep.api.common.bean.PageResponseBean;
import com.nuoxin.virtual.rep.api.entity.DrugUser;
import com.nuoxin.virtual.rep.api.service.v2_5.WechatService;
import com.nuoxin.virtual.rep.api.web.controller.request.v3_0.CommonPoolRequest;
import com.nuoxin.virtual.rep.api.web.controller.response.v3_0.CommonPoolDoctorResponse;
import com.nuoxin.virtual.rep.api.web.controller.response.v3_0.WechatChatRoomResponse;
import com.nuoxin.virtual.rep.api.web.controller.v2_5.NewBaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 微信相关接口
 * @author tiancun
 * @date 2019-05-06
 */
@RestController
@Api(value = "V3.0.1微信相关接口")
@RequestMapping(value = "/wechat")
public class WechatChatRoomController extends NewBaseController {

    @Resource
    private WechatService wechatService;

    @ApiOperation(value = "医生详情群列表", notes = "医生详情群列表")
    @GetMapping(value = "/doctor/chat/room/list/{drugUserId}/{doctorId}")
    public DefaultResponseBean<List<WechatChatRoomResponse>> getDoctorPage(@PathVariable(value = "drugUserId") Long drugUserId,@PathVariable(value = "doctorId") Long doctorId) {

        List<WechatChatRoomResponse> wechatChatRoomList = wechatService.getWechatChatRoomList(drugUserId, doctorId);
        DefaultResponseBean<List<WechatChatRoomResponse>> responseBean = new DefaultResponseBean<>();
        responseBean.setData(wechatChatRoomList);
        return responseBean;
    }

}
