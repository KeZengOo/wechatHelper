package com.nuoxin.virtual.rep.api.web.controller.response.v3_0.monthly;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 每月招募医生有微信的医生
 * @author tiancun
 * @date 2019-06-17
 */
@ApiModel(value = "每月招募医生有微信的医生")
@Data
public class MonthlyRecruitWechatDetailResponse implements Serializable {
    private static final long serialVersionUID = -7004143904059011649L;

    @ApiModelProperty(value = "当月")
    private String month;

    @ApiModelProperty(value = "当月招募成功医生中有微信的医生")
    private Integer hasWechatDoctor;


}
