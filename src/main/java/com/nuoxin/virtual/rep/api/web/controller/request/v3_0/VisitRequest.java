package com.nuoxin.virtual.rep.api.web.controller.request.v3_0;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 拜访记录查询请求参数
 * @author tiancun
 * @date 2019-04-28
 */
@ApiModel(value = "拜访记录查询请求参数")
@Data
public class VisitRequest extends DoctorBaseRequest implements Serializable {
    private static final long serialVersionUID = -6202944363266115586L;

    @ApiModelProperty(value = "拜访开始时间")
    private String visitStartTime;

    @ApiModelProperty(value = "拜访结束时间")
    private String visitEndTime;


}
