package com.nuoxin.virtual.rep.api.service.v3_0;

import com.nuoxin.virtual.rep.api.common.bean.PageResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.request.v3_0.DrugUserDoctorCallRequest;
import com.nuoxin.virtual.rep.api.web.controller.response.v3_0.DrugUserDoctorCallResponse;

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

}
