package com.nuoxin.virtual.rep.api.entity.v3_0.params;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 内容阅读记录时间和时长params
 * @author wujiang
 * @date 20190505
 */
@Data
@ApiModel("内容阅读记录时间和时长params")
public class ContentReadLogsTimeParams {
    @ApiModelProperty("阅读开始时间")
    private String createTime;
    @ApiModelProperty("阅读时长-秒")
    private String readTime;
}
