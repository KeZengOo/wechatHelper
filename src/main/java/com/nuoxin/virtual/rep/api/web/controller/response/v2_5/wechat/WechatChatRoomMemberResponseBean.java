package com.nuoxin.virtual.rep.api.web.controller.response.v2_5.wechat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 微信群成员
 * @author tiancun
 * @date 2019-04-26
 */
@ApiModel(value = "微信群成员")
@Data
public class WechatChatRoomMemberResponseBean implements Serializable {
    private static final long serialVersionUID = 3539487358061617708L;

    @ApiModelProperty(value = "成员微信ID")
    private String memberId;

    @ApiModelProperty(value = "群成员名称")
    private String memberName;


}
