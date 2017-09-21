package com.nuoxin.virtual.rep.api.common.bean;

import com.nuoxin.virtual.rep.api.common.annotations.Excel;

import java.io.Serializable;

/**
 * Created by fenggang on 9/20/17.
 */
public class DoctorExcel implements Serializable {

    private static final long serialVersionUID = -8101729441861723175L;

    @Excel(name = "医生手机号",width=100)
    private String mobile;
    @Excel(name = "省",width=100)
    private String province;
    @Excel(name = "市",width=100)
    private String city;
    @Excel(name = "区",width=100)
    private String area;
    @Excel(name = "医院名称",width=500)
    private String hospitalName;
    @Excel(name = "医生姓名",width=100)
    private String doctorName;
    @Excel(name = "性别",width=100)
    private String sex;
    @Excel(name = "科室",width=100)
    private String department;
    @Excel(name = "细分科室",width=100)
    private String dept;
    @Excel(name = "职务",width=200)
    private String position;
    @Excel(name = "医生邮箱",width=100)
    private String doctorEmail;
    @Excel(name = "销售代表姓名",width=500)
    private String drugUserName;
    @Excel(name = "销售代表邮箱",width=500)
    private String drugUserEmail;
    @Excel(name = "产品组",width=500)
    private String productName;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
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

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDoctorEmail() {
        return doctorEmail;
    }

    public void setDoctorEmail(String doctorEmail) {
        this.doctorEmail = doctorEmail;
    }

    public String getDrugUserName() {
        return drugUserName;
    }

    public void setDrugUserName(String drugUserName) {
        this.drugUserName = drugUserName;
    }

    public String getDrugUserEmail() {
        return drugUserEmail;
    }

    public void setDrugUserEmail(String drugUserEmail) {
        this.drugUserEmail = drugUserEmail;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
