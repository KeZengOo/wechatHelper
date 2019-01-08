package com.nuoxin.virtual.rep.api.web.controller.request.v2_5.set;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 法定节假日请求参数
 * @author tiancun
 * @date 2019-01-08
 */
@Data
@ApiModel(value = "法定节假日请求参数")
public class HolidayRequestBean implements Serializable {
    private static final long serialVersionUID = -5239755974912009463L;

    @ApiModelProperty(value = "节假日名称")
    private String name;

    @ApiModelProperty(value = "开始日期，YYYY-MM-DD格式")
    private String startDate;

    @ApiModelProperty(value = "天数")
    private Integer days;

}
