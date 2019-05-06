package com.nuoxin.virtual.rep.api.entity.v3_0.params;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 内容阅读记录params
 * @author wujiang
 * @date 20190505
 */
@Data
@ApiModel("内容阅读记录params")
public class ContentReadLogsParams {
    @ApiModelProperty("id")
    private Long id;
    @ApiModelProperty("内容ID")
    private Long dataId;
    @ApiModelProperty("医生ID")
    private Long doctorId;
    @ApiModelProperty("医生姓名")
    private String doctorName;
    @ApiModelProperty("医院名称")
    private String hospitalName;
    @ApiModelProperty("医院科室")
    private String depart;
    @ApiModelProperty("阅读开始时间数组")
    private String[] createTime;
    @ApiModelProperty("阅读时长数组-秒")
    private String[] readTime;
    @ApiModelProperty("单次最多阅读时长")
    private String maxReadTime;
}
