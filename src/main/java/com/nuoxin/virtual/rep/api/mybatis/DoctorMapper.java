package com.nuoxin.virtual.rep.api.mybatis;

import com.nuoxin.virtual.rep.api.web.controller.request.WorkStationRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.work.CustomerSumResponseBean;

import java.util.List;

/**
 * Create by tiancun on 2017/10/12
 */
public interface DoctorMapper {

    List<CustomerSumResponseBean> getCustomerSumStatistic(WorkStationRequestBean bean);

    List<CustomerSumResponseBean> getCustomerAddStatistic(WorkStationRequestBean bean);

    List<CustomerSumResponseBean> getCustomerCoveredStatistic(WorkStationRequestBean bean);
}
