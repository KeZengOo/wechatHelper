package com.nuoxin.virtual.rep.api.web.controller.response.v2_5.wechat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author tiancun
 * @date 2018-12-04
 */
@Data
@ApiModel(value = "返回文件上次的上传时间")
public class WechatAndroidUploadTimeResponseBean implements Serializable {

    private static final long serialVersionUID = 5405445799355497763L;


    @ApiModelProperty(value = "联系人上次上传时间")
    private String contactUploadTime = "";

    @ApiModelProperty(value = "微信消息上次上传时间")
    private String messageUploadTime = "";


    @JsonIgnore
    private Long contactUploadTimeValue;

    @JsonIgnore
    private Long messageUploadTimeValue;



}
