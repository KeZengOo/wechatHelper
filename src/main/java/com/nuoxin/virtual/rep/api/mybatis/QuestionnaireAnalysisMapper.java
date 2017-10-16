package com.nuoxin.virtual.rep.api.mybatis;

import com.nuoxin.virtual.rep.api.common.util.MyMapper;
import com.nuoxin.virtual.rep.api.entity.DoctorQuestionnaire;
import com.nuoxin.virtual.rep.api.entity.Question;
import com.nuoxin.virtual.rep.api.entity.Questionnaire;
import com.nuoxin.virtual.rep.api.web.controller.request.analysis.QuestionnaireAnalysisRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.analysis.q.QuestionDateResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.analysis.q.QuestionOptionsStatResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.analysis.q.QuestionStatResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.analysis.q.QuestionnaireStatResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.question.QuestionnaireResponseBean;

import java.util.List;

/**
 * Created by fenggang on 10/11/17.
 */
public interface QuestionnaireAnalysisMapper extends MyMapper<DoctorQuestionnaire> {

    List<QuestionnaireResponseBean> list(QuestionnaireAnalysisRequestBean bean);

    /**
     * 获取时间段内问卷的人数跟次数
     * @param bean
     * @return
     */
    QuestionnaireStatResponseBean summation(QuestionnaireAnalysisRequestBean bean);

    /**
     * 获取时间段内问卷的人数跟次数-按日期分类
     * @param bean
     * @return
     */
    List<QuestionDateResponseBean> summationList(QuestionnaireAnalysisRequestBean bean);

    /**
     * 问卷问题集合
     * @param bean
     * @return
     */
    List<QuestionStatResponseBean> questionList(QuestionnaireAnalysisRequestBean bean);

    /**
     * 问答题集合
     * @param bean
     * @return
     */
    List<QuestionOptionsStatResponseBean> questionUsreAnswerType1(QuestionnaireAnalysisRequestBean bean);

    /**
     * 选择题集合
     * @param bean
     * @return
     */
    List<QuestionOptionsStatResponseBean> questionUsreAnswerType0(QuestionnaireAnalysisRequestBean bean);

}
