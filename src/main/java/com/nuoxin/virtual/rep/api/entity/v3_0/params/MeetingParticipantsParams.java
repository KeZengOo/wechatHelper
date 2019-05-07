package com.nuoxin.virtual.rep.api.entity.v3_0.params;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("会议参会导入方法获取医生和会议项目params")
public class MeetingParticipantsParams {
    @ApiModelProperty(value = "医生ID")
    private Long doctorId;
    @ApiModelProperty(value = "医生姓名")
    private String name;
    @ApiModelProperty(value = "项目ID")
    private Integer itemId;
}
