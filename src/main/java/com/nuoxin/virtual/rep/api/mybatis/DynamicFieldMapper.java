package com.nuoxin.virtual.rep.api.mybatis;

import com.nuoxin.virtual.rep.api.web.controller.response.doctor.DoctorBasicInfoResponseBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Create by tiancun on 2017/10/19
 */
public interface DynamicFieldMapper {

    //查询出动态添加的医生的基本信息
    List<DoctorBasicInfoResponseBean> getDoctorBasicInfo(@Param(value = "doctorId") Long doctorId);


}
