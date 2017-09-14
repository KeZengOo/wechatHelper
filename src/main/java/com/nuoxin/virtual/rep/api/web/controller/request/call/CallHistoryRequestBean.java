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

    private Long drugUserId;

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
}
