package com.nuoxin.virtual.rep.api.web.controller.response.v3_0.daily;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 拜访类型医院数量统计
 * @author tiancun
 * @date 2019-05-15
 */
@Data
@ApiModel(value = "拜访类型医院数量统计")
public class VisitTypeHospitalNumStatisticsResponse implements Serializable {

    private static final long serialVersionUID = -1378230024709317460L;


    @ApiModelProperty(value = "拜访类型")
    private Integer visitType;

    @ApiModelProperty(value = "拜访类型说明")
    private String visitTypeStr;

    @ApiModelProperty(value = "医院数量")
    private Integer hospitalNum;

}
