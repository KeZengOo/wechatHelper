package com.nuoxin.virtual.rep.api.service.v3_0;

import com.nuoxin.virtual.rep.api.common.bean.PageResponseBean;
import com.nuoxin.virtual.rep.api.entity.DrugUser;
import com.nuoxin.virtual.rep.api.web.controller.request.v3_0.CommonPoolRequest;
import com.nuoxin.virtual.rep.api.web.controller.response.v3_0.CommonPoolDoctorResponse;

import javax.servlet.http.HttpServletResponse;

/**
 * 公共池相关业务接口
 * @author tiancun
 * @date 2019-05-07
 */
public interface CommonPoolService {


    /**
     * 得到我的客户医生列表
     * @param drugUser 登录用户
     * @param request 请求参数
     * @return
     */
    PageResponseBean<CommonPoolDoctorResponse> getDoctorPage(DrugUser drugUser, CommonPoolRequest request);

    /**
     * 导出医生
     * @param response
     * @param request
     */
    void exportDoctorList(HttpServletResponse response, CommonPoolRequest request);

}
