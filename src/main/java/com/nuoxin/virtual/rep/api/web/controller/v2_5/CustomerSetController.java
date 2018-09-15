package com.nuoxin.virtual.rep.api.web.controller.v2_5;

import com.nuoxin.virtual.rep.api.common.bean.DefaultResponseBean;
import com.nuoxin.virtual.rep.api.common.bean.PageResponseBean;
import com.nuoxin.virtual.rep.api.entity.DrugUser;
import com.nuoxin.virtual.rep.api.service.v2_5.CustomerSetService;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.followup.ListRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.set.DoctorDynamicFieldRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.customer.DoctorDynamicFieldResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.CustomerFollowListBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 新版客户设置相关接口
 * @author tiancun
 * @date 2018-09-12
 */
@Api(value = "V2.5客户设置或者产品设置相关接口")
@RestController
public class CustomerSetController extends NewBaseController{

    @Resource
    private CustomerSetService customerSetService;


    @ApiOperation(value = "客户设置动态字段列表", notes = "客户设置动态字段列表")
    @RequestMapping(value = "/customer/set/field/list", method = RequestMethod.GET)
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

    @ApiOperation(value = "客户设置动态字段新增", notes = "客户设置动态字段新增")
    @RequestMapping(value = "/customer/set/field/add", method = RequestMethod.POST)
    public DefaultResponseBean<Long> insertDoctorDynamicField(HttpServletRequest request,@RequestBody DoctorDynamicFieldRequestBean bean) {
        DrugUser user = super.getDrugUser(request);
        if(user == null) {
            return super.getLoginErrorResponse();
        }

        bean.setCreatorId(user.getId());
        bean.setCreatorName(user.getName());
        bean.setMenderId(user.getId());
        bean.setMenderName(user.getName());
        Long id = customerSetService.insertDoctorDynamicField(bean);
        DefaultResponseBean<Long> responseBean = new DefaultResponseBean<>();
        responseBean.setData(id);

        return responseBean;
    }



    @ApiOperation(value = "产品设置动态字段列表", notes = "产品设置动态字段列表")
    @RequestMapping(value = "/product/set/field/list/{productId}", method = RequestMethod.GET)
    public DefaultResponseBean<List<DoctorDynamicFieldResponseBean>> getProductFieldList(HttpServletRequest request, @PathVariable(value = "productId") Long productId) {
        DrugUser user = super.getDrugUser(request);
        if(user == null) {
            return super.getLoginErrorResponse();
        }

        List<DoctorDynamicFieldResponseBean> productFieldList = customerSetService.getProductFieldList(productId);
        DefaultResponseBean<List<DoctorDynamicFieldResponseBean>> responseBean = new DefaultResponseBean<>();
        responseBean.setData(productFieldList);
        return responseBean;
    }


    @ApiOperation(value = "产品设置动态字段新增", notes = "产品设置动态字段新增")
    @RequestMapping(value = "/product/set/field/add", method = RequestMethod.POST)
    public DefaultResponseBean<Long> insertProductDoctorDynamicField(HttpServletRequest request,@RequestBody DoctorDynamicFieldRequestBean bean) {
        DrugUser user = super.getDrugUser(request);
        if(user == null) {
            return super.getLoginErrorResponse();
        }

        bean.setCreatorId(user.getId());
        bean.setCreatorName(user.getName());
        bean.setMenderId(user.getId());
        bean.setMenderName(user.getName());
        Long id = customerSetService.insertProductDoctorDynamicField(bean);
        DefaultResponseBean<Long> responseBean = new DefaultResponseBean<>();
        responseBean.setData(id);

        return responseBean;
    }


    @ApiOperation(value = "客户设置或者产品设置动态字段修改", notes = "客户设置或者产品设置动态字段修改")
    @RequestMapping(value = "/set/field/update", method = RequestMethod.POST)
    public DefaultResponseBean<Boolean> updateDoctorDynamicField(HttpServletRequest request, @RequestBody DoctorDynamicFieldRequestBean bean) {
        DrugUser user = super.getDrugUser(request);
        if(user == null) {
            return super.getLoginErrorResponse();
        }

        bean.setMenderId(user.getId());
        bean.setMenderName(user.getName());
        customerSetService.updateDoctorDynamicField(bean);
        DefaultResponseBean<Boolean> responseBean = new DefaultResponseBean<>();
        responseBean.setData(true);
        return responseBean;
    }


    @ApiOperation(value = "客户设置或者产品设置动态字段修改", notes = "客户设置或者产品设置动态字段修改")
    @RequestMapping(value = "/set/field/delete/{id}", method = RequestMethod.GET)
    public DefaultResponseBean<Boolean> deleteDoctorDynamicField(HttpServletRequest request, @PathVariable(value = "id") Long id) {
        DrugUser user = super.getDrugUser(request);
        if(user == null) {
            return super.getLoginErrorResponse();
        }
        customerSetService.deleteDoctorDynamicField(id);
        DefaultResponseBean<Boolean> responseBean = new DefaultResponseBean<>();
        responseBean.setData(true);
        return responseBean;
    }

}
