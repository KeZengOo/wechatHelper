package com.nuoxin.virtual.rep.api.web.controller.response.v2_5;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author tiancun
 * @date 2018-11-26
 */
@Data
public class VisitResultResponseBean implements Serializable {

    private static final long serialVersionUID = -7342135767343193401L;



    @ApiModelProperty(value = "医生ID")
    private Long doctorId;


    @ApiModelProperty(value = "拜访结果")
    private String visitResult;


}
