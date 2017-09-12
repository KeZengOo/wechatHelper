package com.nuoxin.virtual.rep.api.web.controller.response.call;

import com.nuoxin.virtual.rep.api.web.controller.request.question.QuestionnaireRequestBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * Created by fenggang on 9/12/17.
 */
@ApiModel
public class CallHistoryResponseBean implements Serializable {

    private static final long serialVersionUID = 7700413918193431527L;

    @ApiModelProperty(value = "id")
    private Long doctorId;
    @ApiModelProperty(value = "录音文件地址")
    private String dataUrl;
    @ApiModelProperty(value = "备注")
    private String remark;
    @ApiModelProperty(value = "通话时长（时间戳）")
    private Long times;
    @ApiModelProperty(value = "通话时间（yyyy-MM-dd HH:mm）")
    private String timeStr;
    @ApiModelProperty(value = "问卷信息")
    private List<QuestionnaireRequestBean> questions;

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public String getDataUrl() {
        return dataUrl;
    }

    public void setDataUrl(String dataUrl) {
        this.dataUrl = dataUrl;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getTimes() {
        return times;
    }

    public void setTimes(Long times) {
        this.times = times;
    }

    public String getTimeStr() {
        return timeStr;
    }

    public void setTimeStr(String timeStr) {
        this.timeStr = timeStr;
    }

    public List<QuestionnaireRequestBean> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionnaireRequestBean> questions) {
        this.questions = questions;
    }
}
