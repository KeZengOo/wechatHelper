package com.nuoxin.virtual.rep.api.mybatis;

import com.nuoxin.virtual.rep.api.web.controller.request.EmailQueryRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.WorkStationRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.EmailResponseBean;

import java.util.List;

/**
 * Create by tiancun on 2017/10/16
 */
public interface EmailMapper {

    Integer getTodayEmailNum(WorkStationRequestBean bean);

    Integer getTodayEmailCount(WorkStationRequestBean bean);

    List<EmailResponseBean> historyPage(EmailQueryRequestBean bean);
    Integer historyPageCount(EmailQueryRequestBean bean);

}
