package com.nuoxin.virtual.rep.api.web.controller.request.doctor;

import com.nuoxin.virtual.rep.api.web.controller.request.customer.DoctorDynamicFieldValueRequestBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * Created by fenggang on 9/12/17.
 */
@ApiModel
public class DoctorUpdateRequestBean implements Serializable {

    private static final long serialVersionUID = 3730618881184904557L;
    @ApiModelProperty(value = "id")
    private Long doctorId;
    @ApiModelProperty(value = "姓名")
    private String doctorName;
    @ApiModelProperty(value = "电话")
    private String mobile;
    @ApiModelProperty(value = "客户等级")
    private String clientLevel;
    @ApiModelProperty(value = "医院名称")
    private String hospitalName;
    @ApiModelProperty(value = "医院级别")
    private String hospitalLevel;
    @ApiModelProperty(value = "科室")
    private String department;
    @ApiModelProperty(value = "省份")
    private String province;
    @ApiModelProperty(value = "城市")
    private String city;
    @ApiModelProperty(value = "医生级别")
    private String doctorLevel;
    @ApiModelProperty(value = "教授")
    private String professor;
    @ApiModelProperty(value = "产品id")
    private Long productId;
    @ApiModelProperty(value = "原来的产品id产品id")
    private Long oldProductId;

    @ApiModelProperty(value = "不用传")
    private Long drugUserId;
    @ApiModelProperty(value = "不用传")
    private String leaderPath;

    @ApiModelProperty(value = "分类，目前1基本信息，2医生的处方信息，3之前拜访记录，4分析")
    private Integer classification;

    @ApiModelProperty(value = "动态添加的字段")
    List<DoctorDynamicFieldValueRequestBean> list;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getClientLevel() {
        return clientLevel;
    }

    public void setClientLevel(String clientLevel) {
        this.clientLevel = clientLevel;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getHospitalLevel() {
        return hospitalLevel;
    }

    public void setHospitalLevel(String hospitalLevel) {
        this.hospitalLevel = hospitalLevel;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDoctorLevel() {
        return doctorLevel;
    }

    public void setDoctorLevel(String doctorLevel) {
        this.doctorLevel = doctorLevel;
    }

    public String getProfessor() {
        return professor;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }

    public Long getDrugUserId() {
        return drugUserId;
    }

    public void setDrugUserId(Long drugUserId) {
        this.drugUserId = drugUserId;
    }

    public List<DoctorDynamicFieldValueRequestBean> getList() {
        return list;
    }

    public void setList(List<DoctorDynamicFieldValueRequestBean> list) {
        this.list = list;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getLeaderPath() {
        return leaderPath;
    }

    public void setLeaderPath(String leaderPath) {
        this.leaderPath = leaderPath;
    }

    public Integer getClassification() {
        return classification;
    }

    public void setClassification(Integer classification) {
        this.classification = classification;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public Long getOldProductId() {
        return oldProductId;
    }

    public void setOldProductId(Long oldProductId) {
        this.oldProductId = oldProductId;
    }
}
