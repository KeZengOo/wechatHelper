package com.nuoxin.virtual.rep.api.entity.v2_5;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author tiancun
 * @date 2018-11-20
 */
@Data
public class StatisticsDrugDoctorResponse {


    @ApiModelProperty(value = "销售代表id")
    private Integer drugUserId;

    @ApiModelProperty(value = "医生ID列表")
    private List<Long> doctorIdList;
}
