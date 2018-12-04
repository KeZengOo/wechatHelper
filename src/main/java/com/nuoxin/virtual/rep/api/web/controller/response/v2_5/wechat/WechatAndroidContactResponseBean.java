package com.nuoxin.virtual.rep.api.web.controller.response.v2_5.wechat;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * 安卓手机微信消息联系人
 * @author tiancun
 * @date 2018-12-04
 */
@Data
@ApiModel(value = "安卓手机微信消息联系人")
public class WechatAndroidContactResponseBean implements Serializable {
    private static final long serialVersionUID = -4112587325262451937L;


    private Long drugUserId;

    /**
     * 联系人微信号
     */
    private String userName;


    private String nickname;


    private String alias;

    private String conRemark;


    /**
     * 文件上传时间
     */
    private String uploadTime;


}
