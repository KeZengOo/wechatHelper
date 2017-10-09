//package com.nuoxin.virtual.rep.api.entity;
//
//import com.nuoxin.virtual.rep.api.common.annotations.Excel;
//import com.nuoxin.virtual.rep.api.common.entity.IdEntity;
//
//import javax.persistence.Entity;
//import javax.persistence.Table;
//
///**
// * Created by fenggang on 10/1/17.
// */
//@Entity
//@Table(name = "dra_table")
//public class DraTable extends IdEntity {
//    private static final long serialVersionUID = 1322713455919634479L;
//    /**
//     * doctor` varchar(255) DEFAULT NULL,
//     `name` varchar(255) DEFAULT NULL,
//     `code` varchar(255) DEFAULT NULL,
//     `hospital` varchar(255) DEFAULT NULL,
//     `dept` varchar(255) DEFAULT NULL,
//     `level
//     */
//    @Excel(name = "Doctor Code",width=500)
//    private String doctor;
//    @Excel(name = "ChineseName",width=500)
//    private String name;
//    @Excel(name = "Hospital Code",width=500)
//    private String code;
//    @Excel(name = "Hopital Name",width=500)
//    private String hospital;
//    @Excel(name = "StandardDepartmentName",width=500)
//    private String dept;
//    @Excel(name = "MedicalTitleLovItemName",width=500)
//    private String level;
//
//    public String getDoctor() {
//        return doctor;
//    }
//
//    public void setDoctor(String doctor) {
//        this.doctor = doctor;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getCode() {
//        return code;
//    }
//
//    public void setCode(String code) {
//        this.code = code;
//    }
//
//    public String getHospital() {
//        return hospital;
//    }
//
//    public void setHospital(String hospital) {
//        this.hospital = hospital;
//    }
//
//    public String getDept() {
//        return dept;
//    }
//
//    public void setDept(String dept) {
//        this.dept = dept;
//    }
//
//    public String getLevel() {
//        return level;
//    }
//
//    public void setLevel(String level) {
//        this.level = level;
//    }
//}
