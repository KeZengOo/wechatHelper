package com.nuoxin.virtual.rep.api.entity.v3_0.request;

import com.nuoxin.virtual.rep.api.common.bean.PageRequestBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author wujiang
 * @date 20190502
 */
@ApiModel("参会分页传参")
@Data
public class MeetingAttendDetailsRequest extends PageRequestBean {
    @ApiModelProperty("会议ID")
    private String meetingId;
}
