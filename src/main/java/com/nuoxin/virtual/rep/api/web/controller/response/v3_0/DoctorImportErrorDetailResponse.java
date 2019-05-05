package com.nuoxin.virtual.rep.api.web.controller.response.v3_0;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 医生导入返回的错误明细
 * @author tiancun
 * @date 2019-04-30
 */
@Data
@ApiModel(value = "医生导入返回的错误明细")
public class DoctorImportErrorDetailResponse implements Serializable {
    private static final long serialVersionUID = 6698690987539253161L;

    @ApiModelProperty(value = "行号")
    private Integer rowNum;

    @ApiModelProperty(value = "错误")
    private String error;

}
