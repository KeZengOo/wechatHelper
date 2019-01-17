package com.nuoxin.virtual.rep.api.web.controller.request.v2_5.doctor;

import lombok.Data;

import java.io.Serializable;

/**
 * @author tiancun
 * @date 2018-10-29
 */
@Data
public class SaveDoctorTelephoneRequestBean implements Serializable {
    private static final long serialVersionUID = -5747564652732809113L;

    /**
     * 医生ID
     */
    private Long doctorId;


    /**
     * 医生联系方式
     */
    private String telephone;


    /**
     * 1是手机号，2是座机号
     */
    private Integer type;

}
