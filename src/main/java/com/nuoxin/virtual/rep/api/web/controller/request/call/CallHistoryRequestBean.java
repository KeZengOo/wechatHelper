package com.nuoxin.virtual.rep.api.web.controller.request.call;

import com.nuoxin.virtual.rep.api.common.bean.PageRequestBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by fenggang on 9/13/17.
 */
@ApiModel
public class CallHistoryRequestBean extends PageRequestBean {

    private static final long serialVersionUID = -1945709942506086158L;

    @ApiModelProperty(value = "时间戳")
    private Long timeLong;
    @ApiModelProperty(value = "医生id")
    private Long doctorId;

    @ApiModelProperty(value = "不用传")
    private Long drugUserId;
    @ApiModelProperty(value = "不用传")
    private String leaderPath;

    public Long getDrugUserId() {
        return drugUserId;
    }

    public void setDrugUserId(Long drugUserId) {
        this.drugUserId = drugUserId;
    }

    public Long getTimeLong() {
        return timeLong;
    }

    public void setTimeLong(Long timeLong) {
        this.timeLong = timeLong;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public String getLeaderPath() {
        return leaderPath;
    }

    public void setLeaderPath(String leaderPath) {
        this.leaderPath = leaderPath;
    }
}
