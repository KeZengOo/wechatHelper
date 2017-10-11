package com.nuoxin.virtual.rep.api.service;

import com.nuoxin.virtual.rep.api.dao.DoctorQuestionnaireRepository;
import com.nuoxin.virtual.rep.api.dao.QuestionnaireRepository;
import com.nuoxin.virtual.rep.api.entity.DoctorQuestionnaire;
import com.nuoxin.virtual.rep.api.web.controller.request.question.QuestionRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.question.QuestionnaireRequestBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fenggang on 9/19/17.
 */
@Service
@Transactional(readOnly = true)
public class DoctorQuestionnaireService {

    @Autowired
    private DoctorQuestionnaireRepository doctorQuestionnaireRepository;
    @Autowired
    private QuestionService questionService;

    public List<QuestionnaireRequestBean> findByCallId(Long callId){
        List<QuestionnaireRequestBean> requestBeanList = new ArrayList<>();
        List<Long> ids = new ArrayList<>();
        List<DoctorQuestionnaire> list = doctorQuestionnaireRepository.findByCallId(callId);
        if(list!=null && !list.isEmpty()){
            for (int i = 0,leng=list.size(); i < leng; i++) {
                DoctorQuestionnaire dq = list.get(i);
                if(ids!=null && !ids.isEmpty()){
                    if(ids.contains(dq.getQuestionnaireId())){
                        continue;
                    }
                }
                ids.add(dq.getQuestionnaireId());
                QuestionnaireRequestBean bean = questionService.findById(dq.getQuestionnaireId());
                List<QuestionRequestBean> qrList = bean.getQuestions();
                if(qrList!=null && !qrList.isEmpty()){
                    for (QuestionRequestBean qrb:qrList) {
                        for (int j = 0,jleng=list.size(); j < jleng; j++) {
                            DoctorQuestionnaire dq2 = list.get(j);
                            if (dq2.getQuestionId().equals(qrb.getId())){
                                qrb.setAnswer(dq2.getAnswer());
                                //jleng=list.size();
                            }
                        }
                    }
//                    bean.setQuestions(qrList);
                    requestBeanList.add(bean);
                }
            }
        }
        return requestBeanList;
    }

    @Transactional(readOnly = false)
    public void save(List<DoctorQuestionnaire> list){
        doctorQuestionnaireRepository.save(list);
    }
}
