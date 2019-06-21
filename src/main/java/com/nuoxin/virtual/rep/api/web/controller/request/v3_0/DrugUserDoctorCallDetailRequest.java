package com.nuoxin.virtual.rep.api.web.controller.request.v3_0;

import com.nuoxin.virtual.rep.api.common.bean.PageRequestBean;
import com.nuoxin.virtual.rep.api.utils.DateUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 拜访详情请求参数
 * @author tiancun
 * @date 2019-05-13
 */
@ApiModel
public class DrugUserDoctorCallDetailRequest extends PageRequestBean implements Serializable {


    private static final long serialVersionUID = 6299926110069901212L;

    @ApiModelProperty(value = "编号")
    private Long id;

    @ApiModelProperty(value = "产品ID")
    private Long productId;

    @ApiModelProperty(value = "代表ID")
    private Long drugUserId;

    @ApiModelProperty(value = "医生ID")
    private Long doctorId;

    @ApiModelProperty(value = "拜访渠道")
    private Integer visitChannel;

    @ApiModelProperty(value = "开始时间")
    private Date startTime;

    @ApiModelProperty(value = "结束时间")
    private Date endTime;

    @ApiModelProperty(value = "拜访结果ID")
    private List<Long> resultIdList;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getDrugUserId() {
        return drugUserId;
    }

    public void setDrugUserId(Long drugUserId) {
        this.drugUserId = drugUserId;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public Integer getVisitChannel() {
        return visitChannel;
    }

    public void setVisitChannel(Integer visitChannel) {
        this.visitChannel = visitChannel;
    }




    public Date getStartTime() {
        return startTime;
    }

    /**
     * 前端传的YYYY-MM-DD 需要转换
     * @param startTime
     */
    public void setStartTime(Date startTime) {

        if (startTime !=null){
            String dateString = DateUtil.getDateString(startTime);
            String dateTimeString = dateString.concat(" 00:00:00");
            Date date = DateUtil.stringToDate(dateTimeString, DateUtil.DATE_FORMAT_YYYY_MM_DD_HH_MM_SS);
            this.startTime = date;
        }else{
            this.startTime = startTime;
        }


    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {

        if (endTime != null){
            String dateString = DateUtil.getDateString(endTime);
            String dateTimeString = dateString.concat(" 23:59:59");
            Date date = DateUtil.stringToDate(dateTimeString, DateUtil.DATE_FORMAT_YYYY_MM_DD_HH_MM_SS);
            this.endTime = date;
        }else{
            this.endTime = endTime;

        }

    }


    public List<Long> getResultIdList() {
        return resultIdList;
    }

    public void setResultIdList(List<Long> resultIdList) {
        this.resultIdList = resultIdList;
    }
}
