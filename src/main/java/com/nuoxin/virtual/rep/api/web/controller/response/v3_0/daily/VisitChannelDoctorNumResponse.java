package com.nuoxin.virtual.rep.api.web.controller.response.v3_0.daily;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 不同的拜访渠道医生人数统计
 * @author tiancun
 * @date 2019-05-19
 */
@Data
@ApiModel(value = "不同的拜访渠道医生人数统计")
public class VisitChannelDoctorNumResponse implements Serializable {
    private static final long serialVersionUID = -4948196248755344498L;

    @ApiModelProperty(value = "渠道对应值")
    private Integer visitChannel;

    @ApiModelProperty(value = "渠道对应的名称")
    private String visitChannelStr;

    @ApiModelProperty(value = "医生数量")
    private Integer doctorNum;

}
