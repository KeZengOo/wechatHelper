package com.nuoxin.virtual.rep.api.web.controller.request;

import com.nuoxin.virtual.rep.api.common.bean.PageRequestBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by fenggang on 9/12/17.
 */
@ApiModel
public class QueryRequestBean extends PageRequestBean {

    private static final long serialVersionUID = -1755905567728004184L;
    @ApiModelProperty(value = "电话")
    private String mobile;
    @ApiModelProperty(value = "年")
    private Integer year = 0;
    @ApiModelProperty(value = "月")
    private Integer month = 0;
    @ApiModelProperty(value = "日")
    private Integer day = 0;
    @ApiModelProperty(value = "姓名")
    private String name;
    @ApiModelProperty(value = "医院")
    private String hospital;
    @ApiModelProperty(value = "科室")
    private String department;
    @ApiModelProperty(value = "医生级别")
    private String doctorLevel;
    @ApiModelProperty(value = "代表名称")
    private String drugUserName;
    @ApiModelProperty(value = "产品id")
    private Long productId;

    @ApiModelProperty(value = "不用传")
    private Long drugUserId;
    @ApiModelProperty(value = "不用传")
    private String leaderPath;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getYear() {
        return year==null?0:year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month==null?0:month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getDay() {
        return day==null?0:day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDoctorLevel() {
        return doctorLevel;
    }

    public void setDoctorLevel(String doctorLevel) {
        this.doctorLevel = doctorLevel;
    }

    public Long getDrugUserId() {
        return drugUserId;
    }

    public void setDrugUserId(Long drugUserId) {
        this.drugUserId = drugUserId;
    }

    @Override
    public String toString() {
        return mobile +  year + month + day + name + hospital +  department +
                doctorLevel +  drugUserId + drugUserName + super.toString();
    }

    public String getLeaderPath() {
        return leaderPath;
    }

    public void setLeaderPath(String leaderPath) {
        this.leaderPath = leaderPath;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getDrugUserName() {
        return drugUserName;
    }

    public void setDrugUserName(String drugUserName) {
        this.drugUserName = drugUserName;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}
