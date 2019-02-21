package com.nuoxin.virtual.rep.api.mybatis;

import java.util.List;

import com.nuoxin.virtual.rep.api.entity.Doctor;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.doctor.SaveDoctorTelephoneRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.followup.SearchRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.call.CallDoctorResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.doctor.DoctorResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.DoctorDetailsResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.plan.VisitDoctorResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.set.DoctorClassificationResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.set.DoctorPotentialResponseBean;
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
     * 根据手机号查找医生
     * @param mobile
     * @return
     */
    Doctor findTopByMobile(@Param(value = "mobile") String mobile);


//    /**
//     * 根据过滤条件获取医生总条数
//     * @param virtualDrugUserIds 虚拟代表 ID
//     * @param search 搜索内容(mobile,department,doctorName)
//     * @param productLineIds 产品线 IDs
//     * @return 返回符合过滤条件的医生总条
//     */
//    int getListCount(@Param(value = "virtualDrugUserIds") List<Long> virtualDrugUserIds,
//    		                  @Param(value = "search")  String search,
//    		                  @Param(value = "productLineIds") List<Long> productLineIds);
//
//    /**
//     * 根据过滤条件获取医生拜访列表信息(不含对应的产品信息)
//     * @param virtualDrugUserIds 虚拟代表 ID
//     * @param currentSize startIndex
//     * @param pageSize offset
//     * @param search  搜索内容(mobile,department,doctorName)
//     * @param productLineIds 产品线 IDs
//     * @param order  排序规则
//     * @return 返回符合过滤条件的医生列表信息
//     */
//    List<CustomerFollowListBean> getList(@Param(value = "virtualDrugUserIds") List<Long> virtualDrugUserIds,
//    		                                                    @Param(value = "currentSize")int currentSize,
//    		                                                    @Param(value = "pageSize")int pageSize,
//    		                                                    @Param(value = "search")String search,
//    		                                                    @Param(value = "productLineIds") List<Long> productLineIds,
//                                                                @Param(value = "order") Integer order,
//                                         @Param(value = "minValue") Integer minValue,
//                                         @Param(value = "drugUserId") Long drugUserId);



    /**
     * 根据过滤条件获取医生总条数
     * @param bean
     * @return 返回符合过滤条件的医生总条
     */
    int getListCount(SearchRequestBean bean);

    /**
     * 根据过滤条件获取医生拜访列表信息(不含对应的产品信息)
     * @param bean
     * @return 返回符合过滤条件的医生列表信息
     */
    List<CustomerFollowListBean> getList(SearchRequestBean bean);


    /**
     * 得到今日分型要拜访的医生列表
     * @param bean
     * @return
     */
    List<VisitDoctorResponseBean> getClassificationVisitDoctorList(SearchRequestBean bean);

    /**
     * 得到医生输入的潜力
     * @param doctorIdList
     * @param productIdList
     * @return
     */
    List<DoctorPotentialResponseBean> getDoctorPotentialList(@Param(value = "doctorIdList") List<Long> doctorIdList,
                                                             @Param(value = "productIdList") List<Long> productIdList);


    /**
     * 得到医生输入的分型
     * @param doctorIdList
     * @param productIdList
     * @return
     */
    List<DoctorClassificationResponseBean> getDoctorClassificationList(@Param(value = "doctorIdList") List<Long> doctorIdList,
                                                                                    @Param(value = "productIdList") List<Long> productIdList);

    /**
     * 得到设定了拜访频次要拜访的医生列表,单位天
     * @param bean
     * @return
     */
    List<VisitDoctorResponseBean> getVisitFrequencyDayDoctorList(SearchRequestBean bean);



    /**
     * 得到设定了转移拜访频次要拜访的医生列表,单位天
     * @param bean
     * @return
     */
    List<VisitDoctorResponseBean> getRetVisitFrequencyDayDoctorList(SearchRequestBean bean);

    /**
     * 得到设定了会前拜访的医生列表,单位天
     * @param bean
     * @return
     */
    List<VisitDoctorResponseBean> getBeforeMeetingDoctorList(SearchRequestBean bean);

    /**
     * 得到设定了会后拜访的医生列表,单位天
     * @param bean
     * @return
     */
    List<VisitDoctorResponseBean> getAfterMeetingDoctorList(SearchRequestBean bean);



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


    /**
     * 根据手机号查询医生姓名，校验医生是否存在
     * @param mobile
     * @return
     */
    List<String> doctorNameCountByMobile(@Param(value = "mobile") String mobile);


    /**
     * 保存医生的多个手机号
     * @param list
     */
    void insertDoctorTelephone(List<SaveDoctorTelephoneRequestBean> list);

    /**
     * 医生的基本信息
     * @param doctorId
     * @return
     */
    CallDoctorResponseBean doctorProductInfo(Long doctorId);


    /**
     * 得到医生的联系方式
     * @param doctorId
     * @return
     */
    List<String> getDoctorTelephone(@Param(value = "doctorId") Long doctorId);

    /**
     * 删除医生的多个手机号
     * @param doctorId
     */
    void deleteDoctorTelephone(@Param(value = "doctorId") Long doctorId);

    /**
     * 查询总数，用于校验医生是否在指定产品下
     * @param doctorId
     * @param productId
     * @return
     */
    Integer doctorProductCount(@Param(value = "doctorId") Long doctorId,@Param(value = "productId") Long productId);

    /**
     * 根据联系方式获取详情
     * @param telephone
     * @return
     */
    List<DoctorDetailsResponseBean> getDoctorListByTelephone(@Param(value = "telephone") String telephone);


    /**
     * 根据多个联系方式获取详情
     * @param telephones
     * @return
     */
    List<DoctorDetailsResponseBean> getDoctorListByTelephones(@Param(value = "telephones") List<String> telephones,@Param(value = "drugUserId") Long drugUserId);


    /**
     * 根据销售代表和产品获得医生列表
     * @param drugUserIdList
     * @param productId
     * @param limitNum 限制条数
     * @return
     */
    List<DoctorDetailsResponseBean> getDoctorList(@Param(value = "drugUserIdList") List<Long> drugUserIdList,
                                                  @Param(value = "productId") Long productId,
                                                  @Param(value = "doctorName") String doctorName,
                                                  @Param(value = "limitNum") Integer limitNum);


}
