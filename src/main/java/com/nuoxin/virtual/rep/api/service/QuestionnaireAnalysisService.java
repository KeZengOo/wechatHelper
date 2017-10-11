package com.nuoxin.virtual.rep.api.service;

import com.nuoxin.virtual.rep.api.common.service.BaseService;
import com.nuoxin.virtual.rep.api.mybatis.QuestionnaireAnalysisMapper;
import com.nuoxin.virtual.rep.api.web.controller.request.question.QuestionnaireAnalysisRequestBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Created by fenggang on 10/11/17.
 */
@Service
public class QuestionnaireAnalysisService extends BaseService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private QuestionnaireAnalysisMapper questionnaireAnalysisMapper;

    public void summation(QuestionnaireAnalysisRequestBean bean){

    }

    public void list(Long drugUserId){

    }

    public void details(Long questionId,Long drugUserId){

    }
}
