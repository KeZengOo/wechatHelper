package com.nuoxin.virtual.rep.api.mybatis;

import com.nuoxin.virtual.rep.api.common.util.MyMapper;
import com.nuoxin.virtual.rep.api.web.controller.request.WorkStationRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.work.CustomerSumResponseBean;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Create by tiancun on 2017/10/12
 */
public interface DoctorMapper{



    List<CustomerSumResponseBean> getCustomerSumStatistic(WorkStationRequestBean bean);

    List<CustomerSumResponseBean> getCustomerAddStatistic(WorkStationRequestBean bean);

    List<CustomerSumResponseBean> getCustomerCoveredStatistic(WorkStationRequestBean bean);



    /**
     * 修改动态字段时候更新医生固定的四个字段
     * @param doctorId
     * @param name
     * @param telephone
     * @param depart
     * @param hospital
     */
    void updateFixedField(@Param(value = "doctorId") Long doctorId,@Param(value = "name") String name,
                @Param(value = "telephone") String telephone,@Param(value = "depart") String depart,
                @Param(value = "hospital") String hospital);
}
