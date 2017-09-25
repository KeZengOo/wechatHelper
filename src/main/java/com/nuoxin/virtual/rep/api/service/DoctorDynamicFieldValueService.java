package com.nuoxin.virtual.rep.api.service;

import com.nuoxin.virtual.rep.api.dao.DoctorDynamicFieldValueRepository;
import com.nuoxin.virtual.rep.api.entity.DoctorDynamicFieldValue;
import com.nuoxin.virtual.rep.api.web.controller.request.customer.DoctorDynamicFieldValueRequestBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Create by tiancun on 2017/9/22
 */
@Service
public class DoctorDynamicFieldValueService {

    @Autowired
    private DoctorDynamicFieldValueRepository doctorDynamicFieldValueRepository;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Boolean add (Long doctorId, List<DoctorDynamicFieldValueRequestBean> doctorDynamicFieldValueList){
        if (doctorDynamicFieldValueList == null || doctorDynamicFieldValueList.isEmpty()){
            return true;
        }

        Boolean flag = false;



        List<DoctorDynamicFieldValue> list = new ArrayList<>();


            for (DoctorDynamicFieldValueRequestBean bean:doctorDynamicFieldValueList){
                DoctorDynamicFieldValue doctorDynamicFieldValue = new DoctorDynamicFieldValue();
                doctorDynamicFieldValue.setDoctorId(doctorId);
                doctorDynamicFieldValue.setDynamicFieldId(bean.getDynamicFieldId());
                doctorDynamicFieldValue.setDynamicFieldValue(bean.getDynamicFieldValue());
                doctorDynamicFieldValue.setCreateTime(new Date());
                doctorDynamicFieldValue.setUpdateTime(new Date());
                list.add(doctorDynamicFieldValue);
            }


        doctorDynamicFieldValueRepository.save(list);

        flag = true;
        return flag;

    }

}
