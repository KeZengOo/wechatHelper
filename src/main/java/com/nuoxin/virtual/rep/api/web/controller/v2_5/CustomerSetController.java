package com.nuoxin.virtual.rep.api.web.controller.v2_5;

import com.nuoxin.virtual.rep.api.common.bean.DefaultResponseBean;
import com.nuoxin.virtual.rep.api.common.bean.PageResponseBean;
import com.nuoxin.virtual.rep.api.entity.DrugUser;
import com.nuoxin.virtual.rep.api.entity.v2_5.ProductVisitResultParams;
import com.nuoxin.virtual.rep.api.entity.v2_5.ProductVisitResultResponse;
import com.nuoxin.virtual.rep.api.service.v2_5.CustomerSetService;
import com.nuoxin.virtual.rep.api.service.v2_5.ProductClassificationService;
import com.nuoxin.virtual.rep.api.service.v2_5.VirtualProductVisitResultService;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.set.DoctorDynamicFieldRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.set.DynamicFieldProductRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.set.ProductClassificationRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.customer.DoctorDynamicFieldResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.DynamicFieldProductResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.set.ProductClassificationFieldResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.set.ProductClassificationResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.set.ProductClassificationTypeResponseBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
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

    @Resource
    private VirtualProductVisitResultService virtualProductVisitResultService;

    @Resource
    private ProductClassificationService productClassificationService;


    @ApiOperation(value = "客户设置动态字段列表", notes = "客户设置动态字段列表")
    @GetMapping(value = "/customer/set/field/list")
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
    @PostMapping(value = "/customer/set/field/add")
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
    @GetMapping(value = "/product/set/field/list/{productId}")
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
    @PostMapping(value = "/product/set/field/add")
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
    @PostMapping(value = "/set/field/update")
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


    @ApiOperation(value = "客户设置或者产品设置动态字段删除", notes = "客户设置或者产品设置动态字段删除")
    @GetMapping(value = "/set/field/delete/{id}")
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



    @ApiOperation(value = "产品设置产品信息列表", notes = "产品设置产品信息列表")
    @PostMapping(value = "/product/set/list")
    public DefaultResponseBean<PageResponseBean<DynamicFieldProductResponseBean>> deleteDoctorDynamicField(HttpServletRequest request,@RequestBody DynamicFieldProductRequestBean bean) {
        DrugUser user = super.getDrugUser(request);
        if(user == null) {
            return super.getLoginErrorResponse();
        }
        PageResponseBean<DynamicFieldProductResponseBean> dynamicFieldProductPage = customerSetService.getDynamicFieldProductPage(user, bean);
        DefaultResponseBean<PageResponseBean<DynamicFieldProductResponseBean>> responseBean = new DefaultResponseBean<>();
        responseBean.setData(dynamicFieldProductPage);
        return responseBean;
    }


    @ApiOperation(value = "产品设置产品信息复制前检查", notes = "产品设置产品信息复制前检查")
    @PostMapping(value = "/product/set/copy/check/{productId}")
    public DefaultResponseBean<Integer> getProductDynamicFieldCount(HttpServletRequest request, @PathVariable(value = "productId") Long productId) {
        DrugUser user = super.getDrugUser(request);
        if(user == null) {
            return super.getLoginErrorResponse();
        }
        Integer fieldCount = customerSetService.getProductDynamicFieldCount(productId);
        DefaultResponseBean<Integer> responseBean = new DefaultResponseBean<>();
        responseBean.setData(fieldCount);
        return responseBean;
    }

    @ApiOperation(value = "产品设置产品信息复制", notes = "产品设置产品信息复制")
    @PostMapping(value = "/product/set/copy/{oldProductId}/{newProductId}")
    public DefaultResponseBean<Boolean> deleteDoctorDynamicField(HttpServletRequest request,@PathVariable(value = "oldProductId") Long oldProductId, @PathVariable(value = "newProductId") Long newProductId) {
        DrugUser user = super.getDrugUser(request);
        if(user == null) {
            return super.getLoginErrorResponse();
        }
        customerSetService.copyDynamicFieldByProductId(user, oldProductId, newProductId);
        DefaultResponseBean<Boolean> responseBean = new DefaultResponseBean<>();
        responseBean.setData(true);
        return responseBean;
    }

    @ApiOperation(value = "拜访结果列表", notes = "拜访结果列表")
    @GetMapping(value = "/product/visit/result/list/{productId}")
    public DefaultResponseBean<List<ProductVisitResultResponse>> visitResultList(@PathVariable(value = "productId") Long productId) {
        List<ProductVisitResultResponse> res=virtualProductVisitResultService.selectVisitResultList(productId);
        DefaultResponseBean<List<ProductVisitResultResponse>> responseBean = new DefaultResponseBean<>();
        responseBean.setData(res);
        return responseBean;
    }

    @ApiOperation(value = "拜访结果新增", notes = "拜访结果新增")
    @PostMapping(value = "/product/visit/result/add")
    public DefaultResponseBean<?> visitResultAdd(@Valid @RequestBody ProductVisitResultParams param) {
        int status=virtualProductVisitResultService.batchInsert(param);
        if(status==0){
            return super.getParamsErrorResponse("新增失败");
        }else if(status==-1){
            return super.getParamsErrorResponse("该产品下已经有此拜访结果");
        }
        DefaultResponseBean<Integer> responseBean = new DefaultResponseBean<>();
        responseBean.setData(status);
        return responseBean;
    }

    @ApiOperation(value = "拜访结果编辑", notes = "拜访结果编辑")
    @PostMapping(value = "/product/visit/result/update")
    public DefaultResponseBean<Integer> visitResultUpdate(@Valid @RequestBody ProductVisitResultParams param) {
        if(null!=param&&param.getId()==null){
            return super.getParamsErrorResponse("id is null");
        }
        int status=virtualProductVisitResultService.update(param);
        if(status==0){
            return super.getParamsErrorResponse("修改失败");
        }
        DefaultResponseBean<Integer> responseBean = new DefaultResponseBean<>();
        responseBean.setData(status);
        return responseBean;
    }

    @ApiOperation(value = "初始化拜访结果", notes = "初始化拜访结果")
    @GetMapping(value = "/product/visit/result/initialization/{productId}")
    public DefaultResponseBean<Integer> visitResultInitialization(@PathVariable(value = "productId") Long productId) {
        int status=virtualProductVisitResultService.visitResultInitialization(productId);
        if(status==0){
            return super.getParamsErrorResponse("初始化失败");
        }
        DefaultResponseBean<Integer> responseBean = new DefaultResponseBean<>();
        responseBean.setData(status);
        return responseBean;
    }




    @ApiOperation(value = "产品医生分型新增", notes = "产品医生分型新增")
    @PostMapping(value = "/product/classification")
    public DefaultResponseBean<Boolean> productClassificationAdd(@RequestBody ProductClassificationRequestBean bean) {
        productClassificationService.add(bean);
        DefaultResponseBean<Boolean> responseBean = new DefaultResponseBean<>();
        responseBean.setData(true);
        return responseBean;
    }



    @ApiOperation(value = "产品医生分型删除", notes = "产品医生分型删除")
    @GetMapping(value = "/product/classification/delete/{productId}")
    public DefaultResponseBean<Boolean> deleteByProductId(@PathVariable(value = "productId") Long productId) {
        productClassificationService.deleteByProductId(productId);
        DefaultResponseBean<Boolean> responseBean = new DefaultResponseBean<>();
        responseBean.setData(true);
        return responseBean;
    }


    @ApiOperation(value = "产品医生分型修改", notes = "产品医生分型修改")
    @PostMapping(value = "/product/classification/update")
    public DefaultResponseBean<Boolean> productClassificationUpdate(@RequestBody ProductClassificationRequestBean bean) {
        productClassificationService.update(bean);
        DefaultResponseBean<Boolean> responseBean = new DefaultResponseBean<>();
        responseBean.setData(true);
        return responseBean;
    }


    @ApiOperation(value = "产品医生分型，列表形式", notes = "产品医生分型，列表形式")
    @GetMapping(value = "/product/classification/list/{productId}")
    public DefaultResponseBean<ProductClassificationTypeResponseBean> getProductClassificationList(@PathVariable(value = "productId") Long productId) {
        ProductClassificationTypeResponseBean productClassificationList = productClassificationService.getProductClassificationList(productId);
        DefaultResponseBean<ProductClassificationTypeResponseBean> responseBean = new DefaultResponseBean<>();
        responseBean.setData(productClassificationList);
        return responseBean;
    }


    @ApiOperation(value = "产品医生分型，字符串形式", notes = "产品医生分型列表，字符串形式")
    @GetMapping(value = "/product/classification/field/{productId}")
    public DefaultResponseBean<ProductClassificationFieldResponseBean> getProductClassificationField(@PathVariable(value = "productId") Long productId) {
        ProductClassificationFieldResponseBean productClassificationField = productClassificationService.getProductClassificationField(productId);
        DefaultResponseBean<ProductClassificationFieldResponseBean> responseBean = new DefaultResponseBean<>();
        responseBean.setData(productClassificationField);
        return responseBean;
    }


}
