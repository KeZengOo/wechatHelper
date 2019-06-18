package com.nuoxin.virtual.rep.api.mybatis;

import com.nuoxin.virtual.rep.api.web.controller.request.v3_0.MonthlyCommonRequest;
import com.nuoxin.virtual.rep.api.web.controller.response.v3_0.monthly.*;

import java.util.List;

/**
 * 月报招募相关
 * @author tiancun
 * @date 2019-06-12
 */
public interface MonthlyRecruitMapper {

    /**
     * 联系医院数量
     * @param request
     * @return
     */
    Integer getContactHospital(MonthlyCommonRequest request);


    /**
     * 联系医生数量
     * @param request
     * @return
     */
    Integer getContactDoctor(MonthlyCommonRequest request);



    /**
     * 接触医院数量
     * @param request
     * @return
     */
    Integer getTouchHospital(MonthlyCommonRequest request);


    /**
     * 接触医生数量
     * @param request
     * @return
     */
    Integer getTouchDoctor(MonthlyCommonRequest request);



    /**
     * 招募医院数量
     * @param request
     * @return
     */
    Integer getRecruitHospital(MonthlyCommonRequest request);


    /**
     * 招募医生数量
     * @param request
     * @return
     */
    Integer getRecruitDoctor(MonthlyCommonRequest request);


    /**
     * 每月招募医生数量
     * @param request
     * @return
     */
    List<MonthlyRecruitDetailResponse> getMonthRecruitDoctorList(MonthlyCommonRequest request);


    /**
     * 成功招募的医生中，有手机号的医生
     * @param request
     * @return
     */
    Integer getHasMobileDoctor(MonthlyCommonRequest request);

    /**
     * 每月成功招募的医生中，有手机号的医生
     * @param request
     * @return
     */
    List<MonthlyRecruitMobileDetailResponse> getMonthHasMobileDoctorList(MonthlyCommonRequest request);

    /**
     * 成功招募的医生中，有微信的医生
     * @param request
     * @return
     */
    Integer getHasWechatDoctor(MonthlyCommonRequest request);


    /**
     * 每月成功招募的医生中，有微信的医生
     * @param request
     * @return
     */
    List<MonthlyRecruitWechatDetailResponse> getMonthHasWechatDoctorList(MonthlyCommonRequest request);



    /**
     * 成功招募的医生中，添加微信的医生
     * @param request
     * @return
     */
    Integer getAddWechatDoctor(MonthlyCommonRequest request);


    /**
     * 每月成功招募的医生中，添加微信的医生
     * @param request
     * @return
     */
    List<MonthlyRecruitAddWechatDetailResponse> getMonthAddWechatDoctorList(MonthlyCommonRequest request);


    /**
     * 得到每个省份招募的医生数，医院数
     * @param request
     * @return
     */
    List<MonthlyProvinceRecruitResponse> getMonthlyProvinceRecruitList(MonthlyCommonRequest request);


    /**
     * 每个医院级别招募的医生数，对应的医院数
     * @param request
     * @return
     */
    List<MonthlyHospitalLevelRecruitResponse> getMonthlyHospitalLevelRecruitList(MonthlyCommonRequest request);


    /**
     * 每个科室医生招募数据
     * @param request
     * @return
     */
    List<MonthlyDepartRecruitResponse> getMonthlyDepartRecruitList(MonthlyCommonRequest request);


    /**
     * 每个医生级别招募医生数据
     * @param request
     * @return
     */
    List<MonthlyDoctorLevelRecruitResponse> getMonthlyDoctorLevelRecruitList(MonthlyCommonRequest request);

    /**
     * 得到医生动态添加的医生级别
     * @return
     */
    String getDoctorDynamicLevelStr();
}
