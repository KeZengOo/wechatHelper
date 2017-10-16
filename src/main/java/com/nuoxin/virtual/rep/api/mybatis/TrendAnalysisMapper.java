package com.nuoxin.virtual.rep.api.mybatis;

import com.nuoxin.virtual.rep.api.web.controller.request.analysis.TrendAnalysisRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.analysis.tr.TrendStatResponseBean;

import java.util.List;

/**
 * Created by fenggang on 10/12/17.
 */
public interface TrendAnalysisMapper {

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
}
