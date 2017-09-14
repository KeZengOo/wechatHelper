package com.nuoxin.virtual.rep.api.service;

import com.nuoxin.virtual.rep.api.common.bean.PageResponseBean;
import com.nuoxin.virtual.rep.api.common.service.BaseService;
import com.nuoxin.virtual.rep.api.dao.DoctorRepository;
import com.nuoxin.virtual.rep.api.entity.Doctor;
import com.nuoxin.virtual.rep.api.web.controller.request.QueryRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.doctor.DoctorRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.doctor.DoctorResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.doctor.DoctorStatResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.vo.Doc;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fenggang on 9/11/17.
 */
@Service
@Transactional(readOnly = true)
public class DoctorService extends BaseService {

    @Autowired
    private DoctorRepository doctorRepository;

    public Doctor findById(Long id){
        return doctorRepository.findOne(id);
    }

    public PageResponseBean<DoctorResponseBean> page(QueryRequestBean bean){
        PageRequest pageable = super.getPage(bean);
        Specification<Doctor> spec = new Specification<Doctor>() {
            @Override
            public Predicate toPredicate(Root<Doctor> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                query.where(cb.and(cb.and(predicates.toArray(new Predicate[0]))));
                return query.getRestriction();
            }
        };
        Page<Doctor> page = doctorRepository.findAll(spec,pageable);
        PageResponseBean<DoctorResponseBean> responseBean = new PageResponseBean<>(page);
        List<Doctor> pageList = page.getContent();
        if(pageList!=null && !pageList.isEmpty()){
            List<DoctorResponseBean> list = new ArrayList<>();
            for (Doctor doctor:pageList) {
                DoctorResponseBean listBean = new DoctorResponseBean();
                BeanUtils.copyProperties(doctor,listBean);
                listBean.setDoctorId(doctor.getId());
                listBean.setDoctorMobile(doctor.getMobile());
                listBean.setDoctorName(doctor.getName());
                list.add(listBean);
            }
            responseBean.setContent(list);
        }
        return null;
    }

    public DoctorStatResponseBean stat(Long drugUserId){

        return null;
    }

    @Transactional(readOnly = false)
    public Boolean save(DoctorRequestBean bean){
        Doctor doctor = new Doctor();
        BeanUtils.copyProperties(bean,doctor);
        doctor = doctorRepository.saveAndFlush(doctor);
        if(doctor.getId()!=null){
            return true;
        }
        return false;
    }
}
