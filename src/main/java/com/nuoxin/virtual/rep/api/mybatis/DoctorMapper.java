package com.nuoxin.virtual.rep.api.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.nuoxin.virtual.rep.api.web.controller.request.WorkStationRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.hcp.HcpDynamicRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.CustomerFollowListBean;
import com.nuoxin.virtual.rep.api.web.controller.response.work.CustomerSumResponseBean;

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



    void updateFixedName(HcpDynamicRequestBean bean);

    void updateFixedTelephone(HcpDynamicRequestBean bean);


    void updateFixedDepart(HcpDynamicRequestBean bean);

    void updateFixedHospital(HcpDynamicRequestBean bean);

	int getDoctorsCount(@Param(value = "virtualDrugUserIds") List<Long> virtualDrugUserIds);
    
    List<CustomerFollowListBean> getDoctors(@Param(value = "doctorIds") List<Long> doctorIds,
    		@Param(value = "virtualDrugUserIds") List<Long> virtualDrugUserIds,
    		@Param(value = "currentSize")int currentSize, 
    		@Param(value = "pageSize")int pageSize);
}
