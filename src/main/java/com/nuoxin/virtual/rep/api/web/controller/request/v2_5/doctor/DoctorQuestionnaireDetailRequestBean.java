package com.nuoxin.virtual.rep.api.web.controller.request.v2_5.doctor;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 医生详情问卷详情请求参数
 * @author tiancun
 * @date 2018-09-18
 */
@ApiModel(value = "医生详情问卷详情请求参数")
@Data
public class DoctorQuestionnaireDetailRequestBean implements Serializable {

    private static final long serialVersionUID = 4338914997839645785L;

    @ApiModelProperty(value = "问卷ID")
    private Long questionnaireId;

    @ApiModelProperty(value = "医生ID")
    private Long doctorId;

    @ApiModelProperty(value = "问卷时间")
    private String answerTime;
}
