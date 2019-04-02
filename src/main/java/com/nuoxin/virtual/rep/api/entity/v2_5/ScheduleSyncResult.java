package com.nuoxin.virtual.rep.api.entity.v2_5;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 同步返回值类
 * @author wujiang
 * @date 2019-04-01
 */
@ApiModel("同步返回值类")
@Data
public class ScheduleSyncResult {
    @ApiModelProperty(value = "插入返回值")
    private boolean insertResult;
    @ApiModelProperty(value = "更新返回值")
    private boolean updateResult;
}
