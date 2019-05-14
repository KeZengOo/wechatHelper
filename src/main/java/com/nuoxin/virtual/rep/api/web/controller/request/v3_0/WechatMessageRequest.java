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
public class WechatMessageRequest extends CommonRequest implements Serializable {
    private static final long serialVersionUID = -783498611511449406L;

    @ApiModelProperty(value = "消息类型：默认全部，1是医生，2是群聊")
    private Integer messageType;

    @ApiModelProperty(value = "搜索的关键词")
    private String searchKeyword;

    @ApiModelProperty(value = "是否是可分页的,0和null 可以分页的，其他是可以分页的")
    private Integer paginable;


}
