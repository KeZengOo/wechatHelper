package com.nuoxin.virtual.rep.api.web.controller.request.doctor;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fenggang on 10/11/17.
 */
@ApiModel
public class RelationRequestBean implements Serializable {
    private static final long serialVersionUID = -1785817470770737537L;

    @ApiModelProperty(value = "医生id（多个医生中间用英文逗号分隔）")
    private String doctorIds;
    @ApiModelProperty(value = "坐席id(删除接口调用时不用传)")
    private Long newDrugUserId;
    @ApiModelProperty(value = "不用传")
    private Long oldDrugUserId;
    @ApiModelProperty(value = "不用传")
    private List<Long> ids;

    public String getDoctorIds() {
        return doctorIds;
    }

    public void setDoctorIds(String doctorIds) {
        this.doctorIds = doctorIds;
    }

    public Long getNewDrugUserId() {
        return newDrugUserId;
    }

    public void setNewDrugUserId(Long newDrugUserId) {
        this.newDrugUserId = newDrugUserId;
    }

    public Long getOldDrugUserId() {
        return oldDrugUserId;
    }

    public void setOldDrugUserId(Long oldDrugUserId) {
        this.oldDrugUserId = oldDrugUserId;
    }

    public List<Long> getIds() {
        ids = new ArrayList<>();
        String[] d_ids = getDoctorIds().split(",");
        for (String s:d_ids) {
            ids.add(Long.valueOf(s));
        }
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }
}
