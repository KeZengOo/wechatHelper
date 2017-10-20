package com.nuoxin.virtual.rep.api.service;

import com.nuoxin.virtual.rep.api.common.bean.PageResponseBean;
import com.nuoxin.virtual.rep.api.common.service.BaseService;
import com.nuoxin.virtual.rep.api.dao.ContactPlanRepository;
import com.nuoxin.virtual.rep.api.entity.ContactPlan;
import com.nuoxin.virtual.rep.api.entity.DoctorCallInfo;
import com.nuoxin.virtual.rep.api.entity.DrugUser;
import com.nuoxin.virtual.rep.api.utils.DateUtil;
import com.nuoxin.virtual.rep.api.web.controller.request.ContactPlanQueryRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.ContactPlanRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.ContactPlanResponseBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;

/**
 * Created by fenggang on 10/18/17.
 */
@Service
@Transactional(readOnly = true)
public class ContactPlanService extends BaseService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ContactPlanRepository contactPlanRepository;
    @Autowired
    private DrugUserService drugUserService;

    @Transactional(readOnly = false)
    public void save(ContactPlanRequestBean bean){
        if(bean.getId()==null || bean.getId()<1){
            ContactPlan contactPlan = new ContactPlan();
            contactPlan.setContent(bean.getContent());
            contactPlan.setCreateTime(new Date());
            contactPlan.setDateTime(DateUtil.getDateFromStr(bean.getDate()));
            contactPlan.setDrugUserId(bean.getDrugUserId());
            contactPlan.setDoctorId(bean.getDoctorId());
            contactPlan.setStatus(0);
            contactPlanRepository.saveAndFlush(contactPlan);
        }else{
            ContactPlan contactPlan = contactPlanRepository.findOne(bean.getId());
            contactPlan.setContent(bean.getContent());
            contactPlan.setCreateTime(new Date());
            contactPlan.setDateTime(DateUtil.getDateFromStr(bean.getDate()));
            contactPlan.setDrugUserId(bean.getDrugUserId());
            contactPlan.setDoctorId(bean.getDoctorId());
            contactPlanRepository.saveAndFlush(contactPlan);
        }
    }

    @Transactional(readOnly = false)
    public void updateStatus(Long id){
        ContactPlan contactPlan = contactPlanRepository.findOne(id);
        contactPlan.setStatus(1);
        contactPlan.setFinishTime(new Date());
        contactPlanRepository.saveAndFlush(contactPlan);
    }

    public PageResponseBean<ContactPlanResponseBean> page(ContactPlanQueryRequestBean bean){
        Sort sort = new Sort(Sort.Direction.DESC, "dateTime");
        PageRequest pagetable = super.getPage(bean,sort);
        Specification<ContactPlan> spec = new Specification<ContactPlan>() {
            @Override
            public Predicate toPredicate(Root<ContactPlan> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if(bean.getDoctorId()!=null && bean.getDoctorId()>0){
                    predicates.add(cb.equal(root.get("doctorId").as(Long.class),bean.getDoctorId()));
                }
                if(bean.getStatus()!=null){
                    predicates.add(cb.equal(root.get("status").as(Long.class),bean.getStatus()));
                }
                predicates.add(cb.equal(root.get("drugUserId").as(Long.class),bean.getDrugUserId()));
                query.where(cb.and(cb.and(predicates.toArray(new Predicate[0]))));
                return query.getRestriction();
            }
        };
        Page<ContactPlan> page = contactPlanRepository.findAll(spec,pagetable);
        PageResponseBean<ContactPlanResponseBean> responseBean = new PageResponseBean<>(page);
        List<ContactPlan> list = page.getContent();
        Map<Long,DrugUser> map = new HashMap<>();
        if(list!=null && !list.isEmpty()){
            List<ContactPlanResponseBean> beans = new ArrayList<>();
            for (ContactPlan contactPlan :list) {
                ContactPlanResponseBean contact = new ContactPlanResponseBean();
                contact.setContent(contactPlan.getContent());
                contact.setDate(DateUtil.getDateTimeString(contactPlan.getDateTime()));
                contact.setDoctorId(contactPlan.getDoctorId());
                contact.setDrugUserId(contactPlan.getDrugUserId());
                contact.setId(contactPlan.getId());
                contact.setStatus(contactPlan.getStatus());
                DrugUser drugUser = map.get(contactPlan.getDrugUserId());
                if(drugUser==null){
                    drugUser = drugUserService.findById(contactPlan.getDrugUserId());
                    map.put(contactPlan.getDrugUserId(),drugUser);
                }
                if(drugUser!=null){
                    contact.setDrugUserName(drugUser.getName());
                }

                beans.add(contact);
            }
            responseBean.setContent(beans);
        }
        return responseBean;
    }
}
