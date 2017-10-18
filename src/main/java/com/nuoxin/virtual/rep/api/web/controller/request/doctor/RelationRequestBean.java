package com.nuoxin.virtual.rep.api.web.controller.request.doctor;

import com.nuoxin.virtual.rep.api.common.util.StringUtils;
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
    @ApiModelProperty(value = "产品id(多个中间用英文逗号分隔）")
    private String productIds;
    @ApiModelProperty(value = "不用传")
    private Long oldDrugUserId;
    @ApiModelProperty(value = "不用传")
    private Long drugUserId;
    @ApiModelProperty(value = "不用传")
    private List<Long> ids;
    @ApiModelProperty(value = "不用传")
    private List<Long> pIds;

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
            if(StringUtils.isNotEmtity(s))
                ids.add(Long.valueOf(s));
        }
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }

    public Long getDrugUserId() {
        return drugUserId;
    }

    public void setDrugUserId(Long drugUserId) {
        this.drugUserId = drugUserId;
    }

    public String getProductIds() {
        return productIds;
    }

    public void setProductIds(String productIds) {
        this.productIds = productIds;
    }

    public List<Long> getpIds() {
        pIds = new ArrayList<>();
        String[] d_ids = getProductIds().split(",");
        for (String s:d_ids) {
            if(StringUtils.isNotEmtity(s))
                pIds.add(Long.valueOf(s));
        }
        return pIds;
    }

    public void setpIds(List<Long> pIds) {
        this.pIds = pIds;
    }
}
