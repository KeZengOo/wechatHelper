package com.nuoxin.virtual.rep.api.web.controller.request.v3_0;

import com.nuoxin.virtual.rep.api.common.bean.PageRequestBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 群消息请求参数
 * @author tiancun
 * @date 2019-05-09
 */
@Data
@ApiModel(value = "群消息请求参数")
public class WechatChatRoomMessageRequest extends PageRequestBean implements Serializable {
    private static final long serialVersionUID = -1967427012507107792L;

    @ApiModelProperty(value = "群ID")
    private String chatRoomId;



}
