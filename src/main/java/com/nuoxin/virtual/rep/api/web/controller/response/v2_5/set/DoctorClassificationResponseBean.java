package com.nuoxin.virtual.rep.api.web.controller.response.v2_5.set;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 得到医生输入的分型字段
 * @author tiancun
 * @date 2019-01-30
 */
@Data
public class DoctorClassificationResponseBean implements Serializable {
    private static final long serialVersionUID = 5200233166820167325L;

    @ApiModelProperty(value = "医生ID")
    private Long doctorId;

    @ApiModelProperty(value = "医生输入的分型")
    private String classification;
}
