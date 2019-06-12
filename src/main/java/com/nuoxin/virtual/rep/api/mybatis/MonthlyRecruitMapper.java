package com.nuoxin.virtual.rep.api.mybatis;

import com.nuoxin.virtual.rep.api.web.controller.request.v3_0.MonthlyCommonRequest;

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
}
