package com.nuoxin.virtual.rep.api.service;

import com.nuoxin.virtual.rep.api.dao.DoctorDynamicFieldRepository;
import com.nuoxin.virtual.rep.api.dao.DoctorDynamicFieldValueRepository;
import com.nuoxin.virtual.rep.api.entity.DoctorDynamicField;
import com.nuoxin.virtual.rep.api.entity.DoctorDynamicFieldValue;
import com.nuoxin.virtual.rep.api.web.controller.request.customer.DoctorDynamicFieldValueRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.customer.DoctorDymamicFieldValueResponseBean;
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

    @Autowired
    private DoctorDynamicFieldRepository doctorDynamicFieldRepository;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Boolean add (Long doctorId, List<DoctorDynamicFieldValueRequestBean> doctorDynamicFieldValueList){
        if (doctorDynamicFieldValueList == null || doctorDynamicFieldValueList.isEmpty()){
            return true;
        }

        Boolean flag = false;


        doctorDynamicFieldValueRepository.deleteByDoctorId(doctorId);
        List<DoctorDynamicFieldValue> list = new ArrayList<>();


            for (DoctorDynamicFieldValueRequestBean bean:doctorDynamicFieldValueList){
                DoctorDynamicFieldValue doctorDynamicFieldValue = new DoctorDynamicFieldValue();
                doctorDynamicFieldValue.setDoctorId(doctorId);
                Long dynamicFieldId = bean.getDynamicFieldId();
                doctorDynamicFieldValue.setDynamicFieldId(dynamicFieldId);
                DoctorDynamicField doctorDynamicField = doctorDynamicFieldRepository.findById(dynamicFieldId);
                if (null != doctorDynamicField){
                    doctorDynamicFieldValue.setDynamicFieldName(doctorDynamicField.getName());
                }
                doctorDynamicFieldValue.setDynamicFieldValue(bean.getDynamicFieldValue());
                doctorDynamicFieldValue.setCreateTime(new Date());
                doctorDynamicFieldValue.setUpdateTime(new Date());
                list.add(doctorDynamicFieldValue);
            }


        doctorDynamicFieldValueRepository.save(list);

        flag = true;
        return flag;

    }







    public List<DoctorDymamicFieldValueResponseBean> getDoctorDymamicFieldValueList(Long doctorId){

        List<DoctorDynamicFieldValue> doctorDynamicFieldValueList = doctorDynamicFieldValueRepository.findByDoctorId(doctorId);

        if (null == doctorDynamicFieldValueList || doctorDynamicFieldValueList.isEmpty()){
            return null;
        }

        List<DoctorDymamicFieldValueResponseBean> list = new ArrayList<>();
        for (DoctorDynamicFieldValue doctorDynamicFieldValue:doctorDynamicFieldValueList){
            if (doctorDynamicFieldValue != null){
                DoctorDynamicField doctorDynamicField = doctorDynamicFieldRepository.getOne(doctorDynamicFieldValue.getDynamicFieldId());

                DoctorDymamicFieldValueResponseBean doctorDymamicFieldValueResponseBean = new DoctorDymamicFieldValueResponseBean();
                doctorDymamicFieldValueResponseBean.setFieldName(doctorDynamicFieldValue.getDynamicFieldName());
                doctorDymamicFieldValueResponseBean.setFieldValue(doctorDynamicFieldValue.getDynamicFieldValue());
                if (null != doctorDynamicField){
                    doctorDymamicFieldValueResponseBean.setType(doctorDynamicField.getType());
                    doctorDymamicFieldValueResponseBean.setFieldTypeValue(doctorDynamicField.getValue());
                }
                list.add(doctorDymamicFieldValueResponseBean);

            }
        }

        return list;
    }



}
