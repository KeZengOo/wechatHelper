package com.nuoxin.virtual.rep.api.web.controller.request.question;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * Created by fenggang on 9/11/17.
 */
@ApiModel
public class QuestionnaireAnalysisRequestBean implements Serializable {

    private static final long serialVersionUID = -955947577004247095L;

    @ApiModelProperty(value = "开始日期")
    private String startDate;
    @ApiModelProperty(value = "结束日期")
    private String endDate;

    @ApiModelProperty(value = "不用传")
    private Long drugUserId;

    public Long getDrugUserId() {
        return drugUserId;
    }

    public void setDrugUserId(Long drugUserId) {
        this.drugUserId = drugUserId;
    }
}
