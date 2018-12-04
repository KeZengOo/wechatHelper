package com.nuoxin.virtual.rep.api.web.controller.request.v2_5.wechat;

import lombok.Data;

import java.io.Serializable;

/**
 * 微信消息请求
 * @author tiancun
 * @date 2018-09-15
 */
@Data
public class WechatMessageRequestBean implements Serializable {
    private static final long serialVersionUID = -1680699765572748883L;

    private Long userId;


    private Integer userType;

    private String nickname;


    private Long drugUserId;

    private String uploadTime;

    private Long doctorId;

    private String wechatNumber;

    private String telephone;


    private String wechatMessageStatus;


    private String message;

    private String imgPath;

    private String wechatMessageType;

    private String messageTime;

}
