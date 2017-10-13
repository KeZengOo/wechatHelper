package com.nuoxin.virtual.rep.api.mybatis;

import com.nuoxin.virtual.rep.api.common.util.MyMapper;
import com.nuoxin.virtual.rep.api.entity.DoctorQuestionnaire;
import com.nuoxin.virtual.rep.api.entity.Questionnaire;
import com.nuoxin.virtual.rep.api.web.controller.request.analysis.QuestionnaireAnalysisRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.question.QuestionnaireResponseBean;

import java.util.List;

/**
 * Created by fenggang on 10/11/17.
 */
public interface QuestionnaireAnalysisMapper extends MyMapper<DoctorQuestionnaire> {

    List<QuestionnaireResponseBean> list(QuestionnaireAnalysisRequestBean bean);
}
