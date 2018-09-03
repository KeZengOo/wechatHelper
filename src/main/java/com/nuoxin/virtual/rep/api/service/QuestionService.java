package com.nuoxin.virtual.rep.api.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.nuoxin.virtual.rep.api.common.bean.PageResponseBean;
import com.nuoxin.virtual.rep.api.common.service.BaseService;
import com.nuoxin.virtual.rep.api.common.util.StringUtils;
import com.nuoxin.virtual.rep.api.dao.DoctorQuestionnaireRepository;
import com.nuoxin.virtual.rep.api.dao.QuestionRepository;
import com.nuoxin.virtual.rep.api.dao.QuestionnaireRepository;
import com.nuoxin.virtual.rep.api.entity.*;
import com.nuoxin.virtual.rep.api.web.controller.request.question.OptionsRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.question.QuestionQueryRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.question.QuestionRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.question.QuestionnaireRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.doctor.DoctorDetailsResponseBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by fenggang on 9/13/17.
 */
@Service
@Transactional(readOnly = true)
public class QuestionService extends BaseService {

    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private QuestionnaireRepository questionnaireRepository;
    @Autowired
    private DoctorQuestionnaireRepository doctorQuestionnaireRepository;
    @Autowired
    private DoctorService doctorService;

    /**
     * 保存 问卷
     * @param bean
     * @return
     */
    @Transactional(readOnly = false)
    @CacheEvict(value = "virtual_rep_api_question",allEntries = true)
    public Boolean save(QuestionnaireRequestBean bean){
        Questionnaire questionnaire = new Questionnaire();
        questionnaire.setCreateTime(new Date());
        questionnaire.setTitle(bean.getTitle());
        questionnaire.setCreateId(bean.getDrugUserId());
        questionnaire.setProductId(bean.getProductId());

        questionnaireRepository.saveAndFlush(questionnaire);
        List<QuestionRequestBean> list = bean.getQuestions();
        if(list!=null && !list.isEmpty()){
            for (QuestionRequestBean questionBean:list) {
                Question question = new Question();
                question.setAnswer(questionBean.getAnswer());
                question.setTitle(questionBean.getTitle());
                question.setQuestionnaireId(questionnaire.getId());
                question.setOptions(JSONObject.toJSONString(questionBean.getOptions()));
                question.setCreateTime(new Date());
                question.setType(questionBean.getType());
                questionRepository.saveAndFlush(question);
            }

        }

        return true;
    }

    /**
     * 修改问卷
     * @param bean
     * @return
     */
    @Transactional(readOnly = false)
    @CacheEvict(value = "virtual_rep_api_question",allEntries = true)
    public Boolean update(QuestionnaireRequestBean bean){
        Questionnaire questionnaire = new Questionnaire();
        questionnaire.setCreateTime(new Date());
        questionnaire.setTitle(bean.getTitle());
        questionnaire.setCreateId(bean.getDrugUserId());
        questionnaire.setId(bean.getId());
        questionnaire.setProductId(bean.getProductId());

        questionnaireRepository.saveAndFlush(questionnaire);
        List<QuestionRequestBean> list = bean.getQuestions();
        if(list!=null && !list.isEmpty()){
            questionRepository.deleteByQuestionnaireId(questionnaire.getId());
            for (QuestionRequestBean questionBean:list) {
                Question question = new Question();
                question.setId(questionBean.getId());
                question.setAnswer(questionBean.getAnswer());
                question.setTitle(questionBean.getTitle());
                question.setQuestionnaireId(questionnaire.getId());
                question.setOptions(JSONObject.toJSONString(questionBean.getOptions()));
                question.setCreateTime(new Date());
                question.setType(questionBean.getType());
                questionRepository.saveAndFlush(question);
            }

        }
        return true;
    }

    /**
     * 获取问卷
     * @param id
     * @return
     */
    @Cacheable(value = "virtual_rep_api_question", key="'_details_'+#id" )
    public QuestionnaireRequestBean findById(Long id){
        Questionnaire questionnaire = questionnaireRepository.findOne(id);
        return this._getQuestionnaire(questionnaire);
    }

    /**
     * 删除问卷
     * @param id
     * @return
     */
    @Transactional(readOnly = false)
    @CacheEvict(value = "virtual_rep_api_question",allEntries = true)
    public Boolean delete(Long id){
        questionnaireRepository.updateDelFlag(id);
        questionRepository.deleteByQuestionnaireId(id);
        return true;
    }

    /**
     * 获取问卷列表
     * @param bean
     * @return
     */
    @Cacheable(value = "virtual_rep_api_question", key="'_page_'+#bean" )
    public PageResponseBean<QuestionnaireRequestBean> page(QuestionQueryRequestBean bean){
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        PageRequest pageable = super.getPage(bean,sort);
        Specification<Questionnaire> spec = new Specification<Questionnaire>() {
            @Override
            public Predicate toPredicate(Root<Questionnaire> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if(bean.getProductId()!=null&&bean.getProductId()!=0){
                    predicates.add(cb.equal(root.get("productId").as(Long.class),bean.getProductId()));
                }
                if(StringUtils.isNotEmtity(bean.getQuery())){
                    predicates.add(cb.like(root.get("title").as(String.class),"%"+bean.getQuery()+"%"));
                }
                predicates.add(cb.equal(root.get("delFlag").as(Integer.class),0));
                query.where(cb.and(cb.and(predicates.toArray(new Predicate[0]))));
                return query.getRestriction();
            }
        };
        Page<Questionnaire> page = questionnaireRepository.findAll(spec,pageable);
        PageResponseBean<QuestionnaireRequestBean> responseBean = new PageResponseBean<QuestionnaireRequestBean>(page);
        List<Questionnaire> list = page.getContent();
        if(list!=null && !list.isEmpty()){
            List<QuestionnaireRequestBean> requestBeans = new ArrayList<>();
            for (Questionnaire questionnaire:list) {
                requestBeans.add(this._getQuestionnaire(questionnaire));
            }
            responseBean.setContent(requestBeans);
        }
        return responseBean;
    }

    /**
     * 获取问卷
     * @param bean
     * @param user
     * @return
     */
    public PageResponseBean<QuestionnaireRequestBean> pageAnswer(QuestionQueryRequestBean bean,DrugUser user){
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        PageRequest pageable = super.getPage(bean,sort);
        Specification<Questionnaire> spec = new Specification<Questionnaire>() {
            @Override
            public Predicate toPredicate(Root<Questionnaire> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if(bean.getProductId()!=null&&bean.getProductId()!=0){
                    predicates.add(cb.equal(root.get("productId").as(Long.class),bean.getProductId()));
                }
                if(StringUtils.isNotEmtity(bean.getQuery())){
                    predicates.add(cb.like(root.get("title").as(String.class),"%"+bean.getQuery()+"%"));
                }
                predicates.add(cb.equal(root.get("delFlag").as(Integer.class),0));
                query.where(cb.and(cb.and(predicates.toArray(new Predicate[0]))));
                return query.getRestriction();
            }
        };
        Page<Questionnaire> page = questionnaireRepository.findAll(spec,pageable);
        PageResponseBean<QuestionnaireRequestBean> responseBean = new PageResponseBean<QuestionnaireRequestBean>(page);
        List<Questionnaire> list = page.getContent();
        if(list!=null && !list.isEmpty()){
            DoctorDetailsResponseBean doctor = doctorService.findByMobile(bean.getMobile());
            List<QuestionnaireRequestBean> requestBeans = new ArrayList<>();
            for (Questionnaire questionnaire:list) {
                QuestionnaireRequestBean questionnaireRequestBean = this._getQuestionnaire(questionnaire);
                List<QuestionRequestBean> questionRequestBeanList = questionnaireRequestBean.getQuestions();
                if(questionRequestBeanList!=null && !questionRequestBeanList.isEmpty()){
                    for (QuestionRequestBean questionRequestBean :questionRequestBeanList) {
                        if(doctor!=null){
                            List<DoctorQuestionnaire> doctorQuestionnaireList = doctorQuestionnaireRepository.findByQuestionIdAndQuestionnaireId(questionRequestBean.getId(),user.getId(),doctor.getDoctorId());
                            if(doctorQuestionnaireList!=null && !doctorQuestionnaireList.isEmpty()){
                                questionRequestBean.setAnswer(doctorQuestionnaireList.get(0).getAnswer());
                            }
                        }
                    }
                    questionnaireRequestBean.setQuestions(questionRequestBeanList);
                }
                requestBeans.add(questionnaireRequestBean);
            }
            responseBean.setContent(requestBeans);
        }
        return responseBean;
    }
    
    private QuestionnaireRequestBean _getQuestionnaire(Questionnaire questionnaire){
        QuestionnaireRequestBean requestBean = new QuestionnaireRequestBean();
        if(questionnaire==null){return requestBean;}
        requestBean.setDrugUserId(questionnaire.getCreateId());
        requestBean.setId(questionnaire.getId());
        requestBean.setTitle(questionnaire.getTitle());
        requestBean.setQuestions(this._getQuestions(questionnaire.getId()));
        requestBean.setProductId(questionnaire.getProductId());
        return requestBean;
    }

    private List<QuestionRequestBean> _getQuestions(Long questionnaireId){
        List<Question> list = questionRepository.findByQuestionnaireId(questionnaireId);
        if(list!=null && !list.isEmpty()){
            List<QuestionRequestBean> requestBeans = new ArrayList<>();
            for (Question question:list) {
                QuestionRequestBean bean = new QuestionRequestBean();
                bean.setAnswer(question.getAnswer());
                bean.setId(question.getId());
                bean.setTitle(question.getTitle());
                bean.setType(question.getType());
                if(question.getOptions()!=null && !"".equals(question.getOptions())){
                    bean.setOptions(JSON.parseArray(question.getOptions(), OptionsRequestBean.class));
                }
                requestBeans.add(bean);
            }
            return requestBeans;
        }
        return null;
    }

}
