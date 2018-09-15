package com.nuoxin.virtual.rep.api.web.controller.v2_5;

import com.nuoxin.virtual.rep.api.common.bean.DefaultResponseBean;
import com.nuoxin.virtual.rep.api.common.bean.PageResponseBean;
import com.nuoxin.virtual.rep.api.entity.DrugUser;
import com.nuoxin.virtual.rep.api.service.v2_5.CustomerSetService;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.followup.ListRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.customer.DoctorDynamicFieldResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.CustomerFollowListBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 新版客户设置相关接口
 * @author tiancun
 * @date 2018-09-12
 */
@Api(value = "V2.5客户设置相关接口")
@RequestMapping(value = "/customer/set")
@RestController
public class CustomerSetController extends NewBaseController{

    @Resource
    private CustomerSetService customerSetService;


    @ApiOperation(value = "基本信息和医院信息新增的动态字段列表", notes = "基本信息和医院信息新增的动态字段列表")
    @RequestMapping(value = "/field/list", method = { RequestMethod.GET })
    public DefaultResponseBean<List<DoctorDynamicFieldResponseBean>> getBasicAndHospitalFieldList(HttpServletRequest request) {
        DrugUser user = super.getDrugUser(request);
        if(user == null) {
            return super.getLoginErrorResponse();
        }

        List<DoctorDynamicFieldResponseBean> basicAndHospitalFieldList = customerSetService.getBasicAndHospitalFieldList();
        DefaultResponseBean<List<DoctorDynamicFieldResponseBean>> responseBean = new DefaultResponseBean<>();
        responseBean.setData(basicAndHospitalFieldList);

        return responseBean;
    }

}
