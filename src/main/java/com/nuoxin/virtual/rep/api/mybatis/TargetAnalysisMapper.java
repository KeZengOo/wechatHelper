package com.nuoxin.virtual.rep.api.mybatis;

import com.nuoxin.virtual.rep.api.web.controller.request.analysis.TargetAnalysisRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.analysis.ta.MettingTargetResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.analysis.ta.TargetResponseBean;

import java.util.List;
import java.util.Map;

/**
 * Created by fenggang on 10/12/17.
 */
public interface TargetAnalysisMapper {

    Integer summation(TargetAnalysisRequestBean bean);

    /**
     * 获取目标会议信息
     * @param bean
     * @return
     */
    MettingTargetResponseBean meeting(TargetAnalysisRequestBean bean);

    /**
     * 目标电话人数覆盖
     * @param bean
     * @return
     */
    TargetResponseBean customTelPerson(TargetAnalysisRequestBean bean);

    /**
     * 目标电话次数覆盖
     * @param bean
     * @return
     */
    TargetResponseBean customTelCount(TargetAnalysisRequestBean bean);

    /**
     * 目标短信人数覆盖
     * @param bean
     * @return
     */
    TargetResponseBean customSms(TargetAnalysisRequestBean bean);

    /**
     * 目标微信人数覆盖
     * @param bean
     * @return
     */
    TargetResponseBean customWechat(TargetAnalysisRequestBean bean);

    TargetResponseBean customTelSum(TargetAnalysisRequestBean bean);

    TargetResponseBean customTelAvg(TargetAnalysisRequestBean bean);

    List<Map<String,Object>> followUpType(TargetAnalysisRequestBean bean);
}
