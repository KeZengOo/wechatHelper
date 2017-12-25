package com.nuoxin.virtual.rep.api.service;

import com.nuoxin.virtual.rep.api.dao.DoctorDynamicFieldRepository;
import com.nuoxin.virtual.rep.api.dao.DoctorDynamicFieldValueRepository;
import com.nuoxin.virtual.rep.api.entity.DoctorDynamicField;
import com.nuoxin.virtual.rep.api.entity.DoctorDynamicFieldValue;
import com.nuoxin.virtual.rep.api.web.controller.request.customer.DoctorDynamicFieldRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.customer.DoctorDynamicFieldResponseBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Create by tiancun on 2017/9/22
 */
@Service
public class DoctorDynamicFieldService {

    @Autowired
    private DoctorDynamicFieldRepository doctorDynamicFieldRepository;

    @Autowired
    private DoctorDynamicFieldValueRepository doctorDynamicFieldValueRepository;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Boolean add(List<DoctorDynamicFieldRequestBean> list){
        if (list == null || list.isEmpty()){
            return false;
        }

        Boolean flag = false;

        List<DoctorDynamicField> doctorDynamicFieldList = new ArrayList<>();
        for (DoctorDynamicFieldRequestBean bean:list){
            String name = bean.getName();
            Integer type = bean.getType();
            String value = bean.getValue();
            Integer classification = bean.getClassification();

            DoctorDynamicField doctorDynamicField = new DoctorDynamicField();

            doctorDynamicField.setName(name);
            doctorDynamicField.setType(type);
            doctorDynamicField.setValue(value);
            doctorDynamicField.setClassification(classification);
            doctorDynamicField.setCreateTime(new Date());
            doctorDynamicField.setUpdateTime(new Date());

            doctorDynamicFieldList.add(doctorDynamicField);

        }

       doctorDynamicFieldRepository.save(doctorDynamicFieldList);

        flag = true;
        return flag;

    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Boolean delete(Long id){
        Boolean flag = false;
        doctorDynamicFieldRepository.delete(id);
        doctorDynamicFieldValueRepository.deleteByDynamicFieldId(id);
        flag=true;

        return flag;

    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Boolean update(DoctorDynamicFieldRequestBean bean){
        Boolean flag = false;
        DoctorDynamicField doctorDynamicField = new DoctorDynamicField();
        Long id = bean.getId();
        //DoctorDynamicField dynamicFieldRepositoryOne = doctorDynamicFieldRepository.findById(id);
        doctorDynamicField.setId(id);
        doctorDynamicField.setName(bean.getName());
        doctorDynamicField.setValue(bean.getValue());
        doctorDynamicField.setType(bean.getType());
        doctorDynamicField.setClassification(bean.getClassification());
        doctorDynamicField.setUpdateTime(new Date());
        //doctorDynamicField.setCreateTime(dynamicFieldRepositoryOne.getCreateTime());
        doctorDynamicField.setCreateTime(new Date());
        doctorDynamicFieldRepository.saveAndFlush(doctorDynamicField);

        flag = true;

        return flag;
    }



    public List<DoctorDynamicFieldResponseBean> getList(){

        Specification<DoctorDynamicField> specification = new Specification<DoctorDynamicField>() {
            @Override
            public Predicate toPredicate(Root<DoctorDynamicField> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

                return null;
            }
        };

        List<DoctorDynamicField> doctorDynamicFieldList = doctorDynamicFieldRepository.findAll(specification);

        List<DoctorDynamicFieldResponseBean> list = new ArrayList<>();
        if (null != doctorDynamicFieldList && !doctorDynamicFieldList.isEmpty()){
            for (DoctorDynamicField doctorDynamicField:doctorDynamicFieldList){

                if (null != doctorDynamicField){
                    DoctorDynamicFieldResponseBean doctorDynamicFieldResponseBean = new DoctorDynamicFieldResponseBean();
                    doctorDynamicFieldResponseBean.setId(doctorDynamicField.getId());
                    doctorDynamicFieldResponseBean.setAlias(doctorDynamicField.getAlias());
                    doctorDynamicFieldResponseBean.setName(doctorDynamicField.getName());
                    doctorDynamicFieldResponseBean.setType(doctorDynamicField.getType());
                    doctorDynamicFieldResponseBean.setValue(doctorDynamicField.getValue());
                    doctorDynamicFieldResponseBean.setClassification(doctorDynamicField.getClassification());
                    list.add(doctorDynamicFieldResponseBean);
                }

            }

        }

        return list;
    }

}
