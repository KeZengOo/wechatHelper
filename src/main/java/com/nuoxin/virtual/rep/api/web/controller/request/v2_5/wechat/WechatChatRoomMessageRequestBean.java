package com.nuoxin.virtual.rep.api.web.controller.request.v2_5.wechat;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * 微信群消息请求
 * @author tiancun
 * @date 2018-09-15
 */
@Data
@ApiModel(value = "微信群消息请求")
public class WechatChatRoomMessageRequestBean implements Serializable {

    private static final long serialVersionUID = -1219082270295759929L;

    /**
     * 微信群ID
     */
    private String chatRoomId;

    /**
     * 能匹配上的医生ID
     * 如果备注中有医生的手机号并且能匹配上库里的医生才有值
     */
    private Long doctorId;

    /**
     * 当前代表ID
     */
    private Long drugUserId;

    /**
     * 群成员ID
     */
    private String memberId;

    /**
     * 群成员名称，有备注显示备注
     */
    private String memberName;

    /**
     * 提取出的备注中的手机号
     */
    private String telephone;

    /**
     * 发送或者接收
     */
    private String wechatMessageStatus;

    /**
     * 消息类型
     */
    private String wechatMessageType;

    /**
     * 消息
     */
    private String wechatMessage;

    /**
     * 视频或者图片消息地址
     */
    private String filePath;

    /**
     * 聊天消息时间
     */
    private String messageTime;

}
