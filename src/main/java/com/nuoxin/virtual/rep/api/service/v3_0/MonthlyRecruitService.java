package com.nuoxin.virtual.rep.api.service.v3_0;

import com.nuoxin.virtual.rep.api.web.controller.request.v3_0.MonthlyRecruitRequest;
import com.nuoxin.virtual.rep.api.web.controller.response.v3_0.monthly.MonthlyDoctorRecruitResponse;
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

}
