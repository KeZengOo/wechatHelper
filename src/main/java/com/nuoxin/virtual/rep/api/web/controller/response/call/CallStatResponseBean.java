package com.nuoxin.virtual.rep.api.web.controller.response.call;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Created by fenggang on 9/12/17.
 */
@ApiModel
public class CallStatResponseBean implements Serializable {

    private static final long serialVersionUID = -2522214759420367289L;

    @ApiModelProperty(value = "呼出总数")
    private Integer callOutAllNum;
    @ApiModelProperty(value = "呼出接通数")
    private Integer callOutNum;
    @ApiModelProperty(value = "呼出总时长")
    private Long callOutAllTimes;
    @ApiModelProperty(value = "呼出平均时长")
    private Long callOutAvgTimes;

    @ApiModelProperty(value = "呼入总数")
    private Integer inCallAllNum;
    @ApiModelProperty(value = "呼入接通总数")
    private Integer inCallNum;
    @ApiModelProperty(value = "呼入总时长")
    private Integer inCallAllTimes;
    @ApiModelProperty(value = "呼入平均时长")
    private Integer inCallTimes;

    public Integer getCallOutAllNum() {
        return callOutAllNum;
    }

    public void setCallOutAllNum(Integer callOutAllNum) {
        this.callOutAllNum = callOutAllNum;
    }

    public Integer getCallOutNum() {
        return callOutNum;
    }

    public void setCallOutNum(Integer callOutNum) {
        this.callOutNum = callOutNum;
    }

    public Long getCallOutAllTimes() {
        return callOutAllTimes;
    }

    public void setCallOutAllTimes(Long callOutAllTimes) {
        this.callOutAllTimes = callOutAllTimes;
    }

    public Long getCallOutAvgTimes() {
        return callOutAvgTimes;
    }

    public void setCallOutAvgTimes(Long callOutAvgTimes) {
        this.callOutAvgTimes = callOutAvgTimes;
    }

    public Integer getInCallAllNum() {
        return inCallAllNum;
    }

    public void setInCallAllNum(Integer inCallAllNum) {
        this.inCallAllNum = inCallAllNum;
    }

    public Integer getInCallNum() {
        return inCallNum;
    }

    public void setInCallNum(Integer inCallNum) {
        this.inCallNum = inCallNum;
    }

    public Integer getInCallAllTimes() {
        return inCallAllTimes;
    }

    public void setInCallAllTimes(Integer inCallAllTimes) {
        this.inCallAllTimes = inCallAllTimes;
    }

    public Integer getInCallTimes() {
        return inCallTimes;
    }

    public void setInCallTimes(Integer inCallTimes) {
        this.inCallTimes = inCallTimes;
    }
}
