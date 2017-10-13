package com.nuoxin.virtual.rep.api.service.analysis;

import com.nuoxin.virtual.rep.api.common.service.BaseService;
import com.nuoxin.virtual.rep.api.mybatis.QuestionnaireAnalysisMapper;
import com.nuoxin.virtual.rep.api.web.controller.request.analysis.QuestionnaireAnalysisRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.analysis.q.QuestionStatResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.analysis.q.QuestionnaireStatResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.question.QuestionnaireResponseBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Created by fenggang on 10/11/17.
 */
@Service
public class QuestionnaireAnalysisService extends BaseService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private QuestionnaireAnalysisMapper questionnaireAnalysisMapper;

    public QuestionnaireStatResponseBean summation(QuestionnaireAnalysisRequestBean bean){

        return null;
    }

    public List<QuestionnaireResponseBean> list(QuestionnaireAnalysisRequestBean bean){
        return questionnaireAnalysisMapper.list(bean);
    }

    public List<QuestionStatResponseBean> details(QuestionnaireAnalysisRequestBean bean){

        return null;
    }
}
