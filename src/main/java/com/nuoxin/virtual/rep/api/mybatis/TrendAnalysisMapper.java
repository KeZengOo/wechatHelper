package com.nuoxin.virtual.rep.api.mybatis;

import com.nuoxin.virtual.rep.api.web.controller.request.analysis.TrendAnalysisRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.analysis.tr.TrendResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.analysis.tr.TrendStatResponseBean;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * Created by fenggang on 10/12/17.
 */
public interface TrendAnalysisMapper {

    /**
     * 呼出总时长
     * @param bean
     * @return
     */
    List<TrendResponseBean> summationCallout(TrendAnalysisRequestBean bean);

    /**
     * 呼出平均时长
     * @param bean
     * @return
     */
    List<TrendResponseBean> summationCalloutAvg(TrendAnalysisRequestBean bean);

    /**
     * 覆盖
     * @param bean
     * @return
     */
    List<TrendResponseBean> summationCover(TrendAnalysisRequestBean bean);
    List<TrendResponseBean> summationCalloutCount(TrendAnalysisRequestBean bean);

    /**
     * 会话
     * @param bean
     * @return
     */
    List<TrendResponseBean> summationSessionType1(TrendAnalysisRequestBean bean);
    List<TrendResponseBean> summationSessionType2(TrendAnalysisRequestBean bean);
    List<TrendResponseBean> summationSessionType3(TrendAnalysisRequestBean bean);

    /**
     * 呼出数
     *
     * @param bean
     * @return
     */
    List<TrendStatResponseBean> callOut(TrendAnalysisRequestBean bean);

    /**
     * 微信数
     *
     * @param bean
     * @return
     */
    List<TrendStatResponseBean> sessionType1(TrendAnalysisRequestBean bean);

    /**
     * 短信数
     *
     * @param bean
     * @return
     */
    List<TrendStatResponseBean> sessionType2(TrendAnalysisRequestBean bean);
    List<TrendStatResponseBean> sessionType3(TrendAnalysisRequestBean bean);
}
