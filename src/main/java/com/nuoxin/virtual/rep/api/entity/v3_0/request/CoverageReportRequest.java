package com.nuoxin.virtual.rep.api.entity.v3_0.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName CoverageReportRequest
 * @Description 拜访数据汇总-覆盖月报请求对象
 * @Author dangjunhui
 * @Date 2019/6/14 17:06
 * @Version 1.0
 */
@Data
@ApiModel("拜访数据汇总-覆盖月报请求对象")
public class CoverageReportRequest {

    @ApiModelProperty(value = "产品ID")
    private Long productId;

    @ApiModelProperty(value = "开始时间")
    private String startTime;

    @ApiModelProperty(value = "结束时间")
    private String endTime;

}
