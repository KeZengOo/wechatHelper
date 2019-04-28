package com.nuoxin.virtual.rep.api.entity.v3_0;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 问卷网API返回值类
 * @author wujiang
 * @date 2019-04-27
 */
@ApiModel("问卷网API返回值类")
@Data
public class ScheduleResult {
    @ApiModelProperty(value = "插入返回值")
    private boolean result;

    @ApiModelProperty(value = "插入答卷详情列表返回值")
    private boolean answerSheetResult;

    @ApiModelProperty(value = "插入答卷答案表返回值")
    private boolean answerResult;
}
