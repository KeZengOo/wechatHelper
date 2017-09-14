package com.nuoxin.virtual.rep.api.service;

import com.nuoxin.virtual.rep.api.common.bean.PageResponseBean;
import com.nuoxin.virtual.rep.api.common.service.BaseService;
import com.nuoxin.virtual.rep.api.dao.DoctorCallInfoRepository;
import com.nuoxin.virtual.rep.api.dao.DoctorRepository;
import com.nuoxin.virtual.rep.api.entity.Doctor;
import com.nuoxin.virtual.rep.api.entity.DoctorCallInfo;
import com.nuoxin.virtual.rep.api.utils.DateUtil;
import com.nuoxin.virtual.rep.api.web.controller.request.QueryRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.call.CallHistoryRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.call.CallInfoRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.call.CallRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.call.CallHistoryResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.call.CallResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.call.CallStatResponseBean;
import com.sun.org.apache.xpath.internal.operations.Bool;
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
 * Created by fenggang on 9/13/17.
 */
@Service
@Transactional(readOnly = true)
public class DoctorCallService extends BaseService {

    @Autowired
    private DoctorCallInfoRepository doctorCallInfoRepository;
    @Autowired
    private DoctorRepository doctorRepository;

    public PageResponseBean<CallResponseBean> doctorPage(QueryRequestBean bean){
        PageRequest pagetable = super.getPage(bean);
        Specification<DoctorCallInfo> spec = new Specification<DoctorCallInfo>() {
            @Override
            public Predicate toPredicate(Root<DoctorCallInfo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                query.where(cb.and(cb.and(predicates.toArray(new Predicate[0]))));
                return query.getRestriction();
            }
        };
        Page<DoctorCallInfo> page = doctorCallInfoRepository.findAll(spec,pagetable);
        PageResponseBean<CallResponseBean> responseBean = new PageResponseBean<>(page);
        List<DoctorCallInfo> list = page.getContent();
        if(list!=null && !list.isEmpty()){
            List<CallResponseBean> responseBeans = new ArrayList<>();
            for (DoctorCallInfo info:list) {
                responseBeans.add(this._getDoctorCallInfo(info));
            }
            responseBean.setContent(responseBeans);
        }
        return responseBean;
    }

    public PageResponseBean<CallHistoryResponseBean> doctorHistoryPage(CallHistoryRequestBean bean){

        return null;
    }

    public CallStatResponseBean stat(Long drugUserId){

        return null;
    }

    @Transactional(readOnly = false)
    public CallRequestBean save(CallRequestBean bean){
        DoctorCallInfo info = new DoctorCallInfo();
        info.setDoctor(doctorRepository.findTopByMobile(bean.getMobile()));
        info.setSinToken(bean.getSinToken());
        info.setStatus(bean.getStatus());
        info.setDrugUserId(bean.getDrugUserId());
        doctorCallInfoRepository.saveAndFlush(info);
        bean.setId(info.getId());
        return bean;
    }

    @Transactional(readOnly = false)
    public Boolean stopSave(CallInfoRequestBean bean){
        DoctorCallInfo info = doctorCallInfoRepository.findOne(bean.getId());
        return null;
    }

    private CallResponseBean _getDoctorCallInfo(DoctorCallInfo info){
        CallResponseBean responseBean = new CallResponseBean();
        if(info!=null){
            responseBean.setClientLevle(info.getDoctor().getClientLevle());
            responseBean.setDoctorId(info.getDoctor().getId());
            responseBean.setDoctorMobile(info.getDoctor().getMobile());
            responseBean.setDoctorName(info.getDoctor().getName());
            responseBean.setTimeLong(info.getCreateTime().getTime());
            responseBean.setTimeStr(DateUtil.getDateTimeString(info.getCreateTime()));
        }
        return responseBean;
    }
}
