package com.nuoxin.virtual.rep.api.web.controller.request.v3_0;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 内容请求参数
 * @author tiancun
 * @date 2019-04-28
 */
@ApiModel(value = "内容请求参数")
@Data
public class ContentShareRequest extends CommonRequest implements Serializable {
    private static final long serialVersionUID = -3491283969415738407L;

    @ApiModelProperty(value = "分享方式：1 微信，2短信，3邮件")
    private Integer shareType;

    @ApiModelProperty(value = "分享开始时间")
    private String shareStartTime;

    @ApiModelProperty(value = "分享结束时间")
    private String shareEndTime;

    @ApiModelProperty(value = "搜索的关键词")
    private String searchKeyword;

}
