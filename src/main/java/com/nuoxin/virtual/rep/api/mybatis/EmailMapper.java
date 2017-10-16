package com.nuoxin.virtual.rep.api.mybatis;

import com.nuoxin.virtual.rep.api.web.controller.request.WorkStationRequestBean;

/**
 * Create by tiancun on 2017/10/16
 */
public interface EmailMapper {

    Integer getTodayEmailNum(WorkStationRequestBean bean);

    Integer getTodayEmailCount(WorkStationRequestBean bean);

}
