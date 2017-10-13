package com.nuoxin.virtual.rep.api.web.controller.request.analysis;

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
    @ApiModelProperty(value = "问卷id")
    private Long questionnaireId;

    @ApiModelProperty(value = "不用传")
    private Long drugUserId;
    @ApiModelProperty(value = "不用传")
    private String leaderPath = "%";

    public Long getDrugUserId() {
        return drugUserId;
    }

    public void setDrugUserId(Long drugUserId) {
        this.drugUserId = drugUserId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Long getQuestionnaireId() {
        return questionnaireId;
    }

    public void setQuestionnaireId(Long questionnaireId) {
        this.questionnaireId = questionnaireId;
    }

    public String getLeaderPath() {
        return leaderPath;
    }

    public void setLeaderPath(String leaderPath) {
        this.leaderPath = leaderPath;
    }
}
