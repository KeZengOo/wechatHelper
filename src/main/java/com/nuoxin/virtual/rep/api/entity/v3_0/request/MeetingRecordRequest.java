package com.nuoxin.virtual.rep.api.entity.v3_0.request;

import com.nuoxin.virtual.rep.api.common.bean.PageRequestBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 会议记录查询Request
 * @author wujiang
 * @date 20190429
 */
@Data
@ApiModel("会议记录查询Request")
public class MeetingRecordRequest extends PageRequestBean {

    @ApiModelProperty(value = "产品ID")
    private Integer productId;
    @ApiModelProperty(value = "会议标题")
    private String title;
    @ApiModelProperty(value = "第一个开始时间")
    private String startTimeBefore;
    @ApiModelProperty(value = "第二个开始时间")
    private String startTimeAfter;
}
