package com.nuoxin.virtual.rep.api.web.controller.request.call;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 识别录音文件
 * @author tiancun
 * @date 2018-11-26
 */
@Data
@ApiModel(value = "识别录音文件")
public class IdentifyCallUrlRequestBean implements Serializable {
    private static final long serialVersionUID = 4731722573256774667L;

    @ApiModelProperty(value = "限制处理的条数")
    private Integer limitNum;




}
