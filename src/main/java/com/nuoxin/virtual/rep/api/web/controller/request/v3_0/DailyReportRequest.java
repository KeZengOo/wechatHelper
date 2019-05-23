package com.nuoxin.virtual.rep.api.web.controller.request.v3_0;

import com.nuoxin.virtual.rep.api.utils.DateUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * V3.0日报请求参数
 * @author tiancun
 * @date 2019-05-09
 */
@ApiModel(value = "V3.0日报请求参数")
public class DailyReportRequest implements Serializable {
    private static final long serialVersionUID = -8473359742664477431L;

    @ApiModelProperty(value = "产品ID列表")
    private List<Long> productIdList;

    @ApiModelProperty(value = "代表ID列表")
    private List<Long> drugUserIdList;

    @ApiModelProperty(value = "开始时间")
    private Date startTime;

    @ApiModelProperty(value = "结束时间")
    private Date endTime;


    public List<Long> getProductIdList() {
        return productIdList;
    }

    public void setProductIdList(List<Long> productIdList) {
        this.productIdList = productIdList;
    }

    public List<Long> getDrugUserIdList() {
        return drugUserIdList;
    }

    public void setDrugUserIdList(List<Long> drugUserIdList) {
        this.drugUserIdList = drugUserIdList;
    }

    public Date getStartTime() {
        return startTime;
    }

    /**
     * 前端传的YYYY-MM-DD 需要转换
     * @param startTime
     */
    public void setStartTime(Date startTime) {
        if (startTime != null){
            String dateString = DateUtil.getDateString(startTime);
            String dateTimeString = dateString.concat(" 00:00:00");
            Date date = DateUtil.stringToDate(dateTimeString, DateUtil.DATE_FORMAT_YYYY_MM_DD_HH_MM_SS);
            this.startTime = date;
        }else {
            this.startTime = startTime;
        }

    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {

        if (endTime != null){
            String dateString = DateUtil.getDateString(endTime);
            String dateTimeString = dateString.concat(" 59:59:59");
            Date date = DateUtil.stringToDate(dateTimeString, DateUtil.DATE_FORMAT_YYYY_MM_DD_HH_MM_SS);
            this.endTime = date;
        }else{
            this.endTime = endTime;
        }


    }
}
