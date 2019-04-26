package com.nuoxin.virtual.rep.api.web.controller.request.v2_5.wechat;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * 安卓手机微信群聊联系人请求参数
 * @author tiancun
 * @date 2018-12-03
 */
@Data
@ApiModel(value = "安卓手机微信群聊联系人请求参数")
public class WechatAndroidChatroomContactRequestBean {

    /**
     * 微信群ID
     */
    private String chatroomId;

    /**
     * 微信群名称
     */
    private String chatroomName;

    /**
     * 群成员ID
     */
    private String memberId;

    /**
     * 群成员名称，有备注显示备注
     */
    private String memberName;

    /**
     * 群主ID
     */
    private String roomOwnerId;

    /**
     * 群里代表备注的姓名，没有备注就为空
     */
    private String roomDrugUserName;

    /**
     * 上传的代表ID
     */
    private Long drugUserId;

    /**
     * 上传的代表姓名
     */
    private String drugUserName;


    /**
     * 能匹配上的医生ID
     */
    private Long doctorId;

    /**
     * 能匹配上的医生姓名
     */
    private String doctorName;

    /**
     * 提取备注中的手机号
     */
    private String telephone;




}
