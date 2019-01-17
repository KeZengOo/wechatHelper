package com.nuoxin.virtual.rep.api.service.v2_5.impl;

import com.nuoxin.virtual.rep.api.entity.Doctor;
import com.nuoxin.virtual.rep.api.mybatis.DoctorMapper;
import com.nuoxin.virtual.rep.api.service.v2_5.NewDoctorService;
import com.nuoxin.virtual.rep.api.utils.CollectionsUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author tiancun
 * @date 2018-11-06
 */
@Service(value = "doctorServiceNew")
public class NewDoctorServiceImpl implements NewDoctorService {

    @Resource
    private DoctorMapper doctorMapper;


    @Override
    public Doctor findFirstByMobile(String mobile) {

        Doctor doctor = doctorMapper.findTopByMobile(mobile);
        if (doctor != null){
            List<String> doctorTelephoneList = doctorMapper.getDoctorTelephone(doctor.getId());
            if (CollectionsUtil.isNotEmptyList(doctorTelephoneList)){
                doctor.setTelephoneList(doctorTelephoneList);
            }
        }
        return doctor;
    }
}
