package com.nuoxin.virtual.rep.api.web.controller.response.v3_0.monthly;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 月报招募导出
 * @author tiancun
 * @date 2019-06-12
 */
@Data
@ApiModel(value = "月报招募导出")
public class MonthlyRecruitExportResponse implements Serializable {
    private static final long serialVersionUID = -5679484851053919836L;

    @ApiModelProperty(value = "阶段")
    private String stage;

    @ApiModelProperty(value = "医院数量")
    private Integer hospitalNum;

    @ApiModelProperty(value = "医院转化率")
    private String hospitalRate;

    @ApiModelProperty(value = "医生数量")
    private Integer doctorNum;

    @ApiModelProperty(value = "医生转化率")
    private String doctorRate;


}
