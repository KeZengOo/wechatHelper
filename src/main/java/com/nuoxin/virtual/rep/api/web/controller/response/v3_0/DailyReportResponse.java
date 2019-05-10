package com.nuoxin.virtual.rep.api.web.controller.response.v3_0;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 日报返回数据
 * @author tiancun
 * @date 2019-05-09
 */
@Data
@ApiModel(value = "日报返回数据")
public class DailyReportResponse implements Serializable {
    private static final long serialVersionUID = -6958934923081128467L;

    @ApiModelProperty(value = "活跃覆盖医生")
    private Integer activeCoverDoctorNum;


}
