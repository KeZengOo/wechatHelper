package com.nuoxin.virtual.rep.api.mybatis;

import java.util.List;

import com.nuoxin.virtual.rep.api.entity.Doctor;
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
    
    ////////////////// 以下是 v2.5 使用到的//////////////////
    
	Long getDoctorIdByMobile(@Param(value = "mobile") String mobile);

    /**
     * 根据过滤条件获取医生总条数
     * @param virtualDrugUserIds 虚拟代表 ID
     * @param search 搜索内容(mobile,department,doctorName)
     * @param productLineIds 产品线 IDs
     * @return 返回符合过滤条件的医生总条
     */
    int getListCount(@Param(value = "virtualDrugUserIds") List<Long> virtualDrugUserIds,
    		                  @Param(value = "search")  String search,
    		                  @Param(value = "productLineIds") List<Long> productLineIds);
    
    /**
     * 根据过滤条件获取医生拜访列表信息(不含对应的产品信息)
     * @param virtualDrugUserIds 虚拟代表 ID
     * @param currentSize startIndex
     * @param pageSize offset
     * @param search  搜索内容(mobile,department,doctorName)
     * @param productLineIds 产品线 IDs
     * @return 返回符合过滤条件的医生列表信息
     */
    List<CustomerFollowListBean> getList(@Param(value = "virtualDrugUserIds") List<Long> virtualDrugUserIds,
    		                                                    @Param(value = "currentSize")int currentSize, 
    		                                                    @Param(value = "pageSize")int pageSize, 
    		                                                    @Param(value = "search")String search,
    		                                                    @Param(value = "productLineIds") List<Long> productLineIds);
    /**
     * 根据手机号列表查询医生
     * @param mobiles
     * @return
     */
    List<Doctor> selectDoctorByMobiles(@Param(value = "mobiles") List<String> mobiles);

    /**
     * 保存医生
     * @param doctor
     * @return
     */
    int saveDoctor(Doctor doctor);

    /**
     * 修改医生
     * @param doctor
     * @return
     */
    int updateDoctor(Doctor doctor);

    /**
     * 根据手机号查询医生，校验医生是否存在
     * @param mobile
     * @return
     */
    Integer doctorCountByMobile(@Param(value = "mobile") String mobile);

}
