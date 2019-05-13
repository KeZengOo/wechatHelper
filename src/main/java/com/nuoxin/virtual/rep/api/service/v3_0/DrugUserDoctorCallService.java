package com.nuoxin.virtual.rep.api.service.v3_0;

import com.nuoxin.virtual.rep.api.common.bean.PageResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.request.v3_0.DrugUserDoctorCallDetailRequest;
import com.nuoxin.virtual.rep.api.web.controller.request.v3_0.DrugUserDoctorCallRequest;
import com.nuoxin.virtual.rep.api.web.controller.response.v3_0.DrugUserDoctorCallDetailResponse;
import com.nuoxin.virtual.rep.api.web.controller.response.v3_0.DrugUserDoctorCallResponse;

import javax.servlet.http.HttpServletResponse;

/**
 * 电话拜访记录相关接口
 * @author tiancun
 * @date 2019-05-10
 */
public interface DrugUserDoctorCallService {

    /**
     * 电话拜访记录查询分页
     * @param request
     * @return
     */
    PageResponseBean<DrugUserDoctorCallResponse> getDrugUserDoctorCallPage(DrugUserDoctorCallRequest request);


    /**
     * 拜访详情查询分页
     * @param request
     * @return
     */
    PageResponseBean<DrugUserDoctorCallDetailResponse> getDrugUserDoctorCallDetailPage(DrugUserDoctorCallDetailRequest request);


    /**
     * 电话拜访记录列表导出
     * @param response
     * @param request
     */
    void exportDrugUserDoctorCallList(HttpServletResponse response, DrugUserDoctorCallRequest request);


}
