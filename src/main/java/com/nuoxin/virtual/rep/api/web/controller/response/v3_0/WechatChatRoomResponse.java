package com.nuoxin.virtual.rep.api.web.controller.response.v3_0;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 微信群
 * @author tiancun
 * @date 2019-05-08
 */
@Data
@ApiModel(value = "微信群")
public class WechatChatRoomResponse implements Serializable {
    private static final long serialVersionUID = -6685861307302577426L;

    @ApiModelProperty(value = "群ID")
    private String chatRoomId;

    @ApiModelProperty(value = "群名字")
    private String chatRoomName;


}
