package com.nuoxin.virtual.rep.api.web.controller.request.meeting;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * 会议不需要提醒的医生
 * @author tiancun
 * @date 2019-01-11
 */
@Data
@ApiModel(value = "会议不需要提醒的医生")
public class MeetingNoRemindRequestBean implements Serializable {
    private static final long serialVersionUID = 726209228984725012L;

    @NotNull(message = "产品ID不能为空")
    @ApiModelProperty(value = "产品ID")
    private Long productId;

    @ApiModelProperty(value = "医生ID")
    private List<Long> doctorIdList;




}
