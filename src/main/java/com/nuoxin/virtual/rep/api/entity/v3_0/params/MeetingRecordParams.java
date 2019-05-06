package com.nuoxin.virtual.rep.api.entity.v3_0.params;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 会议记录查询
 * @author wujiang
 * @date 20190429
 */
@Data
@ApiModel("会议记录查询")
public class MeetingRecordParams {
    @ApiModelProperty(value = "id")
    private Integer id;
    @ApiModelProperty(value = "产品id")
    private Integer productId;
    @ApiModelProperty(value = "产品名称")
    private String productName;
    @ApiModelProperty(value = "演讲主题")
    private String title;
    @ApiModelProperty(value = "演讲开始时间")
    private Date startTime;
    @ApiModelProperty(value = "演讲结束时间")
    private Date endTime;
    @ApiModelProperty(value = "参会人数")
    private Integer doctorCount;
    @ApiModelProperty(value = "参会率")
    private String attendanceRate;
}
