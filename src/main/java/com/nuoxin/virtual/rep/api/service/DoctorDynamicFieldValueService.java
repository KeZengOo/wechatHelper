package com.nuoxin.virtual.rep.api.service;

import com.nuoxin.virtual.rep.api.dao.DoctorDynamicFieldRepository;
import com.nuoxin.virtual.rep.api.dao.DoctorDynamicFieldValueRepository;
import com.nuoxin.virtual.rep.api.dao.DoctorRepository;
import com.nuoxin.virtual.rep.api.entity.Doctor;
import com.nuoxin.virtual.rep.api.entity.DoctorDynamicField;
import com.nuoxin.virtual.rep.api.entity.DoctorDynamicFieldValue;
import com.nuoxin.virtual.rep.api.mybatis.DynamicFieldMapper;
import com.nuoxin.virtual.rep.api.web.controller.request.customer.DoctorDynamicFieldValueRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.customer.DoctorDymamicFieldValueResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.customer.DoctorDynamicFieldResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.doctor.DoctorBasicInfoResponseBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

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

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private DynamicFieldMapper dynamicFieldMapper;

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


        //先插入固定的4个字段
        List<DoctorBasicInfoResponseBean> fixedField = dynamicFieldMapper.getFixedField();
        if (fixedField !=null && !fixedField.isEmpty()){
            List<DoctorDynamicFieldValue> fixedList = new ArrayList<>();
            Doctor doctor = doctorRepository.findFirstById(doctorId);
            if (doctor !=null){
                for (DoctorBasicInfoResponseBean doctorBasicInfoResponseBean:fixedField){
                    DoctorDynamicFieldValue doctorDynamicFieldValue = new DoctorDynamicFieldValue();
                    doctorDynamicFieldValue.setDoctorId(doctorId);
                    doctorDynamicFieldValue.setDynamicFieldId(doctorBasicInfoResponseBean.getFieldId());
                    String field = doctorBasicInfoResponseBean.getField();
                    if (!StringUtils.isEmpty(field)){
                        doctorDynamicFieldValue.setDynamicFieldName(field);
                        if (field.equals("姓名")){
                            doctorDynamicFieldValue.setDynamicFieldValue(doctor.getName());
                        }

                        if (field.equals("电话")){
                            doctorDynamicFieldValue.setDynamicFieldValue(doctor.getMobile());
                        }

                        if (field.equals("科室")){
                            doctorDynamicFieldValue.setDynamicFieldValue(doctor.getDepartment());
                        }

                        if (field.equals("医院")){
                            doctorDynamicFieldValue.setDynamicFieldValue(doctor.getHospitalName());
                        }

                    }




                    doctorDynamicFieldValue.setCreateTime(new Date());
                    doctorDynamicFieldValue.setUpdateTime(new Date());
                    fixedList.add(doctorDynamicFieldValue);
                }
            }
            doctorDynamicFieldValueRepository.save(fixedList);
        }


        //插入其余的字段
        doctorDynamicFieldValueRepository.save(list);

        flag = true;
        return flag;

    }



    public List<DoctorDymamicFieldValueResponseBean> getDoctorDymamicFieldValueList(Long doctorId){
        List<DoctorDymamicFieldValueResponseBean> list = new ArrayList<>();
        //List<DoctorDynamicField> doctorDynamicFieldList = doctorDynamicFieldRepository.findAll();
//        if (null !=doctorDynamicFieldList && !doctorDynamicFieldList.isEmpty()){
//            for (DoctorDynamicField doctorDynamicField:doctorDynamicFieldList){
//
//                DoctorDymamicFieldValueResponseBean doctorDymamicFieldValueResponseBean = new DoctorDymamicFieldValueResponseBean();
//                doctorDymamicFieldValueResponseBean.setName(doctorDynamicField.getName());
//
//                doctorDymamicFieldValueResponseBean.setId(doctorDynamicField.getId());
//                doctorDymamicFieldValueResponseBean.setType(doctorDynamicField.getType());
//                doctorDymamicFieldValueResponseBean.setValue(doctorDynamicField.getValue());
//
//                DoctorDynamicFieldValue doctorDynamicFieldValue = doctorDynamicFieldValueRepository.findFirstByDynamicFieldIdAndDoctorId(doctorDynamicField.getId(), doctorId);
//                if (null != doctorDynamicFieldValue){
//                    doctorDymamicFieldValueResponseBean.setFieldValue(doctorDynamicFieldValue.getDynamicFieldValue());
//                }
//
//                list.add(doctorDymamicFieldValueResponseBean);
//            }
//        }


        List<DoctorDynamicFieldResponseBean> dynamicFieldMapperList = dynamicFieldMapper.getList();
        if (null !=dynamicFieldMapperList && !dynamicFieldMapperList.isEmpty()){
            for (DoctorDynamicFieldResponseBean doctorDynamicField:dynamicFieldMapperList){

                DoctorDymamicFieldValueResponseBean doctorDymamicFieldValueResponseBean = new DoctorDymamicFieldValueResponseBean();
                doctorDymamicFieldValueResponseBean.setName(doctorDynamicField.getName());

                doctorDymamicFieldValueResponseBean.setId(doctorDynamicField.getId());
                doctorDymamicFieldValueResponseBean.setType(doctorDynamicField.getType());
                doctorDymamicFieldValueResponseBean.setValue(doctorDynamicField.getValue());

                DoctorDynamicFieldValue doctorDynamicFieldValue = doctorDynamicFieldValueRepository.findFirstByDynamicFieldIdAndDoctorId(doctorDynamicField.getId(), doctorId);
                if (null != doctorDynamicFieldValue){
                    doctorDymamicFieldValueResponseBean.setFieldValue(doctorDynamicFieldValue.getDynamicFieldValue());
                }

                list.add(doctorDymamicFieldValueResponseBean);
            }
        }

        return list;
    }



}
