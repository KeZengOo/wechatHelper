package com.nuoxin.virtual.rep.api.web.controller.request.v3_0;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 代表医生拜访请求参数
 * @author tiancun
 * @date 2019-05-09
 */
@Data
@ApiModel(value = "代表医生拜访请求参数")
public class DrugUserDoctorCallRequest extends DoctorBaseRequest implements Serializable {
    private static final long serialVersionUID = -4577313097920015193L;

    @ApiModelProperty(value = "开始时间")
    private Date startTime;

    @ApiModelProperty(value = "结束时间")
    private Date endTime;


}
