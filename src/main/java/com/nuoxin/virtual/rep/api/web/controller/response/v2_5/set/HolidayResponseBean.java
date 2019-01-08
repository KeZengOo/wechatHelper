package com.nuoxin.virtual.rep.api.web.controller.response.v2_5.set;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 法定节假日返回数据
 * @author tiancun
 * @date 2019-01-08
 */
@Data
@ApiModel(value = "法定节假日返回数据")
public class HolidayResponseBean implements Serializable {
    private static final long serialVersionUID = -3100302405793753077L;

    @ApiModelProperty(value = "批次ID,相当于这条数据的ID")
    private String batchNo;

    @ApiModelProperty(value = "法定节假日返回数据")
    private String name;

    @ApiModelProperty(value = "开始时间")
    private String startDate;

    @ApiModelProperty(value = "放假天数")
    private Integer days;

}
