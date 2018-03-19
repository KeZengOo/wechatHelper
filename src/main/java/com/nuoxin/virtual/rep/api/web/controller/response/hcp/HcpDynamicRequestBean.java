package com.nuoxin.virtual.rep.api.web.controller.response.hcp;

import java.io.Serializable;

/**
 * Create by tiancun on 2017/12/26
 */
public class HcpDynamicRequestBean implements Serializable{
    private static final long serialVersionUID = -1451597248661715004L;


    private Long doctorId;


    private String name;

    private String depart;


    private String telephone;

    private String hospital;


    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepart() {
        return depart;
    }

    public void setDepart(String depart) {
        this.depart = depart;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }
}
