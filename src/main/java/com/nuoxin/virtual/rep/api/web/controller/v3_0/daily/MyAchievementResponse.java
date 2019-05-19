package com.nuoxin.virtual.rep.api.web.controller.v3_0.daily;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 我的业绩
 * @author tiancun
 * @date 2019-05-17
 */
@Data
@ApiModel(value = "我的业绩")
public class MyAchievementResponse implements Serializable {
    private static final long serialVersionUID = -7407442321745726144L;

    @ApiModelProperty(value = "招募医生数")
    private Integer recruitDoctorNum;

    @ApiModelProperty(value = "有收益的活跃覆盖医生数")
    private Integer activeCoverDoctorNum;

    @ApiModelProperty(value = "多渠道覆盖医生")
    private Integer mulChannelDoctorNum;


}
