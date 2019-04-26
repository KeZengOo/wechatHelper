package com.nuoxin.virtual.rep.api.web.controller.request.v2_5.wechat;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 微信消息请求
 * @author tiancun
 * @date 2018-09-15
 */
@Data
public class WechatMessageRequestBean implements Serializable {
    private static final long serialVersionUID = -1680699765572748883L;

    /**
     * 当前聊天的用户ID
     */
    private Long userId;

    /**
     * 1是代表2是医生
     */
    private Integer userType;

    /**
     * 聊天的昵称
     */
    private String nickname;

    /**
     * 代表ID
     */
    private Long drugUserId;

    /**
     * 上传的时间
     */
    private Long uploadTime;

    /**
     * 医生ID
     */
    private Long doctorId;

    /**
     * 微信号
     */
    private String wechatNumber;

    /**
     * 能匹配上的医生手机号
     */
    private String telephone;

    /**
     * 发送或者接收
     */
    private String wechatMessageStatus;

    /**
     * 聊天消息
     */
    private String message;

    /**
     * 文件地址
     */
    private String imgPath;

    /**
     * 消息类型，文本消息、视频消息、表情消息等
     */
    private String wechatMessageType;

    /**
     * 聊天的时间
     */
    private String messageTime;

    /**
     * 聊天时间对应的时间戳
     */
    private Long messageTimestamp;

}
