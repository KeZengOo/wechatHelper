package com.nuoxin.virtual.rep.api.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.nuoxin.virtual.rep.api.common.bean.PageResponseBean;
import com.nuoxin.virtual.rep.api.common.service.BaseService;
import com.nuoxin.virtual.rep.api.dao.QuestionRepository;
import com.nuoxin.virtual.rep.api.dao.QuestionnaireRepository;
import com.nuoxin.virtual.rep.api.entity.Doctor;
import com.nuoxin.virtual.rep.api.entity.Question;
import com.nuoxin.virtual.rep.api.entity.Questionnaire;
import com.nuoxin.virtual.rep.api.web.controller.request.question.OptionsRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.question.QuestionQueryRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.question.QuestionRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.question.QuestionnaireRequestBean;
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

    @Transactional(readOnly = false)
    public Boolean save(QuestionnaireRequestBean bean){
        Questionnaire questionnaire = new Questionnaire();
        questionnaire.setCreateTime(new Date());
        questionnaire.setTitle(bean.getTitle());
        questionnaire.setCreateId(bean.getDrugUserId());

        questionnaireRepository.saveAndFlush(questionnaire);
        List<QuestionRequestBean> list = bean.getQuestions();
        if(list!=null && !list.isEmpty()){
            for (QuestionRequestBean questionBean:list) {
                Question question = new Question();
                question.setAnswer(questionBean.getAnswer());
                question.setTitle(questionBean.getTitle());
                question.setQuestionnaireId(questionnaire.getId());
                question.setOptions(JSONObject.toJSONString(questionBean.getOptioons()));
                question.setCreateTime(new Date());
                question.setType(questionBean.getType());
                questionRepository.saveAndFlush(question);
            }

        }

        return true;
    }

    @Transactional(readOnly = false)
    public Boolean update(QuestionnaireRequestBean bean){
        Questionnaire questionnaire = new Questionnaire();
        questionnaire.setCreateTime(new Date());
        questionnaire.setTitle(bean.getTitle());
        questionnaire.setCreateId(bean.getDrugUserId());
        questionnaire.setId(bean.getId());

        questionnaireRepository.saveAndFlush(questionnaire);
        List<QuestionRequestBean> list = bean.getQuestions();
        if(list!=null && !list.isEmpty()){
            questionRepository.deleteByQuestionnaireId(questionnaire.getId());
            for (QuestionRequestBean questionBean:list) {
                Question question = new Question();
                question.setAnswer(questionBean.getAnswer());
                question.setTitle(questionBean.getTitle());
                question.setQuestionnaireId(questionnaire.getId());
                question.setOptions(JSONObject.toJSONString(questionBean.getOptioons()));
                question.setCreateTime(new Date());
                question.setType(questionBean.getType());
                questionRepository.saveAndFlush(question);
            }

        }
        return true;
    }

    public QuestionnaireRequestBean findById(Long id){
        Questionnaire questionnaire = questionnaireRepository.findOne(id);
        return this._getQuestionnaire(questionnaire);
    }

    @Transactional(readOnly = false)
    public Boolean delete(Long id){
        questionnaireRepository.delete(id);
        questionRepository.deleteByQuestionnaireId(id);
        return true;
    }

    public PageResponseBean<QuestionnaireRequestBean> page(QuestionQueryRequestBean bean){
        PageRequest pageable = super.getPage(bean);
        Specification<Questionnaire> spec = new Specification<Questionnaire>() {
            @Override
            public Predicate toPredicate(Root<Questionnaire> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
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

    private QuestionnaireRequestBean _getQuestionnaire(Questionnaire questionnaire){
        QuestionnaireRequestBean requestBean = new QuestionnaireRequestBean();
        if(questionnaire==null){return requestBean;}
        requestBean.setDrugUserId(questionnaire.getCreateId());
        requestBean.setId(questionnaire.getId());
        requestBean.setTitle(questionnaire.getTitle());
        requestBean.setQuestions(this._getQuestions(questionnaire.getId()));
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
                    bean.setOptioons(JSON.parseArray(question.getOptions(), OptionsRequestBean.class));
                }
            }
            return  requestBeans;
        }
        return null;
    }

}
