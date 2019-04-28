package com.nuoxin.virtual.rep.api.web.controller.request.v3_0;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 微信消息请求参数
 * @author tiancun
 * @date 2019-04-28
 */
@Data
@ApiModel(value = "微信消息请求参数")
public class WechatMessageRequest implements Serializable {
    private static final long serialVersionUID = -783498611511449406L;

    @ApiModelProperty(value = "消息类型")
    private Integer type;

    @ApiModelProperty(value = "搜索的关键词")
    private String searchKeyword;


}
