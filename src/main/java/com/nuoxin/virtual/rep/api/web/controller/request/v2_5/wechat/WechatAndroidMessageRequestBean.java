package com.nuoxin.virtual.rep.api.web.controller.request.v2_5.wechat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 安卓微信消息导入请求参数
 * @author tiancun
 * @date 2018-12-03
 */
@Data
@ApiModel(value = "安卓微信消息导入请求参数")
public class WechatAndroidMessageRequestBean implements Serializable {

    @ApiModelProperty(value = "导入文件的时间")
    private String uploadFileTime;


}
