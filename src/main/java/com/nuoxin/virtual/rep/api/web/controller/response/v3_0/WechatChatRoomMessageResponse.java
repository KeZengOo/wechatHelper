package com.nuoxin.virtual.rep.api.web.controller.response.v3_0;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 微信群消息
 * @author tiancun
 * @date 2019-05-08
 */
@ApiModel(value = "微信群消息")
@Data
public class WechatChatRoomMessageResponse implements Serializable {
    private static final long serialVersionUID = -1345015097752281650L;

    @ApiModelProperty(value = "群ID")
    private String chatRoomId;

    @ApiModelProperty(value = "群成员ID")
    private String memberId;

    @ApiModelProperty(value = "群成员名称")
    private String memberName;

    @ApiModelProperty(value = "发送或者接收")
    private String wechatMessageStatus;

    @ApiModelProperty(value = "消息类型")
    private String wechatMessageType;

    @ApiModelProperty(value = "微信消息")
    private String wechatMessage;

    @ApiModelProperty(value = "文件路径")
    private String filePath;

    @ApiModelProperty(value = "聊天消息时间")
    private String messageTime;

}
