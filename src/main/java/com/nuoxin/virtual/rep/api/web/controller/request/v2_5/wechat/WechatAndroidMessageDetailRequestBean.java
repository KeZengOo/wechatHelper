package com.nuoxin.virtual.rep.api.web.controller.request.v2_5.wechat;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * 安卓手机聊天消息详情
 * @author tiancun
 * @date 2018-12-04
 */
@Data
@ApiModel(value = "安卓手机聊天消息详情")
public class WechatAndroidMessageDetailRequestBean implements Serializable {
    private static final long serialVersionUID = 471462851482044419L;

    /**
     * 当前聊天者的微信号
     */
    private String talker;

    private String content;


    private String createTime;

    /**
     * 如果是视频，语音消息，文件路径
     */
    private String imgPath;

    /**
     * 是发送还是接收
     */
    private String isSend;

    /**
     * 消息类型
     */
    private String type;



}
