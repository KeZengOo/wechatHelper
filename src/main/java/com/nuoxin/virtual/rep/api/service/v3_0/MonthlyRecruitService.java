package com.nuoxin.virtual.rep.api.service.v3_0;

import com.nuoxin.virtual.rep.api.web.controller.request.v3_0.MonthlyRecruitRequest;
import com.nuoxin.virtual.rep.api.web.controller.response.v3_0.monthly.MonthlyDoctorRecruitResponse;
import com.nuoxin.virtual.rep.api.web.controller.response.v3_0.monthly.MonthlyHistogramResponse;
import com.nuoxin.virtual.rep.api.web.controller.response.v3_0.monthly.MonthlyHospitalRecruitResponse;
import com.nuoxin.virtual.rep.api.web.controller.response.v3_0.monthly.MonthlyRecruitContactResponse;

import javax.servlet.http.HttpServletResponse;

/**
 * 日报招募相关业务接口
 * @author tiancun
 * @date 2019-06-12
 */
public interface MonthlyRecruitService {


    /**
     * 招募的医院数据
     * @param request
     * @return
     */
    MonthlyHospitalRecruitResponse getMonthlyHospitalRecruit(MonthlyRecruitRequest request);


    /**
     * 招募的医生数据
     * @param request
     * @return
     */
    MonthlyDoctorRecruitResponse getMonthlyDoctorRecruit(MonthlyRecruitRequest request);


    /**
     * 月报招募导出
     * @param request
     * @param response
     */
    void exportMonthlyRecruit(MonthlyRecruitRequest request, HttpServletResponse response);


    /**
     * 月报招募中有联系方式的医生统计
     * @param request
     * @return
     */
    MonthlyRecruitContactResponse getMonthlyRecruitContact(MonthlyRecruitRequest request);

    /**
     * 导出月报招募中有联系方式的医生统计
     * @param request
     * @param response
     */
    void exportMonthlyRecruitContact(MonthlyRecruitRequest request, HttpServletResponse response);


    /**
     * 月报省份招募统计
     * @param request
     * @return
     */
    MonthlyHistogramResponse getMonthlyProvinceRecruitList(MonthlyRecruitRequest request);

    /**
     * 导出月报省份招募统计
     * @param request
     * @param response
     */
    void exportMonthlyProvinceRecruit(MonthlyRecruitRequest request, HttpServletResponse response);


    /**
     * 医院级别医生招募统计
     * @param request
     * @return
     */
    MonthlyHistogramResponse getMonthlyHospitalLevelRecruitList(MonthlyRecruitRequest request);


    /**
     * 导出医院级别医生招募统计
     * @param request
     * @param response
     */
    void exportMonthlyHospitalLevelRecruit(MonthlyRecruitRequest request, HttpServletResponse response);

    /**
     * 月报科室的招募情况
     * @param request
     * @return
     */
    MonthlyHistogramResponse getMonthlyDepartRecruitList(MonthlyRecruitRequest request);

    /**
     * 导出月报科室的招募情况
     * @param request
     * @param response
     */
    void exportMonthlyDepartRecruit(MonthlyRecruitRequest request, HttpServletResponse response);


    /**
     * 月报医生级别招募统计情况
     * @param request
     * @return
     */
    MonthlyHistogramResponse getMonthlyDoctorLevelRecruitList(MonthlyRecruitRequest request);

    /**
     * 导出月报医生级别招募统计情况
     * @param request
     * @param response
     */
    void exportMonthlyDoctorLevelRecruit(MonthlyRecruitRequest request, HttpServletResponse response);

}
