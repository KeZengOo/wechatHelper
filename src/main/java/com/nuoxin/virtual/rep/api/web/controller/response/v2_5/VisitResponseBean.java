package com.nuoxin.virtual.rep.api.web.controller.response.v2_5;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 拜访记录返回数据
 * @author tiancun
 * @date 2018-10-31
 */
@ApiModel(value = "拜访记录返回数据")
@Data
public class VisitResponseBean implements Serializable {
    private static final long serialVersionUID = -7037155524193607542L;

    @ApiModelProperty(value = "拜访结果")
    private String visitResult = "";

}
