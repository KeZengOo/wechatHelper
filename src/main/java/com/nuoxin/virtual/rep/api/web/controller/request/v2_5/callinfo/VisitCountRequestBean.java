package com.nuoxin.virtual.rep.api.web.controller.request.v2_5.callinfo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 显示指定日期拜访人数请求参数
 * @author tiancun
 * @date 2018-12-29
 */
@Data
public class VisitCountRequestBean implements Serializable {
    private static final long serialVersionUID = -9003963549580698986L;

    @ApiModelProperty(value = "销售代表ID")
    private Long drugUserId;


    @ApiModelProperty(value = "leaderPath")
    @JsonIgnore
    private String leaderPath;

    @ApiModelProperty(value = "日期，YYYY-MM年月格式")
    private String date;

}
