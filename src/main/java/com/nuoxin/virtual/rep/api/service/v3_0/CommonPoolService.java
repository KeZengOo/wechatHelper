package com.nuoxin.virtual.rep.api.service.v3_0;

import com.nuoxin.virtual.rep.api.common.bean.PageResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.request.v3_0.CommonPoolRequest;
import com.nuoxin.virtual.rep.api.web.controller.response.v3_0.CommonPoolDoctorResponse;

/**
 * 公共池相关业务接口
 * @author tiancun
 * @date 2019-05-07
 */
public interface CommonPoolService {


    /**
     * 得到我的客户医生列表
     * @param request
     * @return
     */
    PageResponseBean<CommonPoolDoctorResponse> getDoctorPage(CommonPoolRequest request);

}
