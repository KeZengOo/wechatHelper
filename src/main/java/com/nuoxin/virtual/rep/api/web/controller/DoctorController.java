package com.nuoxin.virtual.rep.api.web.controller;

import com.nuoxin.virtual.rep.api.common.bean.DefaultResponseBean;
import com.nuoxin.virtual.rep.api.common.bean.DoctorExcel;
import com.nuoxin.virtual.rep.api.common.bean.PageResponseBean;
import com.nuoxin.virtual.rep.api.common.controller.BaseController;
import com.nuoxin.virtual.rep.api.common.util.StringUtils;
import com.nuoxin.virtual.rep.api.entity.ContactPlan;
import com.nuoxin.virtual.rep.api.entity.DrugUser;
import com.nuoxin.virtual.rep.api.service.ContactPlanService;
import com.nuoxin.virtual.rep.api.service.DoctorService;
import com.nuoxin.virtual.rep.api.service.DrugUserService;
import com.nuoxin.virtual.rep.api.utils.ExcelUtils;
import com.nuoxin.virtual.rep.api.web.controller.request.ContactPlanQueryRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.ContactPlanRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.QueryRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.doctor.DoctorRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.doctor.RelationRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.DrugUserResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.doctor.DoctorDetailsResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.doctor.DoctorResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.doctor.DoctorStatResponseBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fenggang on 9/11/17.
 */
@Api(value = "客户信息",description = "客户信息接口")
@RestController
@RequestMapping(value = "/doctor")
public class DoctorController extends BaseController {

    @Autowired
    private DoctorService doctorService;
    @Autowired
    private DrugUserService drugUserService;
    @Autowired
    private ContactPlanService contactPlanService;

    @ApiOperation(value = "获取医生信息", notes = "获取医生信息")
    @GetMapping("/details/{doctorId}")
    public DefaultResponseBean<DoctorDetailsResponseBean> doctorDetails(@PathVariable Long doctorId,
                                                                        HttpServletRequest request, HttpServletResponse response){
        DefaultResponseBean responseBean = new DefaultResponseBean();
        responseBean.setData(doctorService.findById(doctorId));
        return responseBean;
    }

    @ApiOperation(value = "获取医生信息", notes = "获取医生信息")
    @GetMapping("/mobile/{mobile}")
    public DefaultResponseBean<DoctorDetailsResponseBean> doctorDetails(@PathVariable String mobile,
                                                                        HttpServletRequest request, HttpServletResponse response){
        DefaultResponseBean responseBean = new DefaultResponseBean();
        responseBean.setData(doctorService.findByMobile(mobile));
        return responseBean;
    }

    @ApiOperation(value = "获取医生列表", notes = "获取医生列表")
    @PostMapping("/page")
    public DefaultResponseBean<PageResponseBean<DoctorResponseBean>> page(@RequestBody QueryRequestBean bean,
                                                                          HttpServletRequest request, HttpServletResponse response){
        DefaultResponseBean responseBean = new DefaultResponseBean();
        DrugUser user = super.getLoginUser(request);
        if(user==null){
            responseBean.setCode(300);
            responseBean.setMessage("登录失效");
            return responseBean;
        }
        bean.setDrugUserId(user.getId());
        bean.setLeaderPath(user.getLeaderPath());
        responseBean.setData(doctorService.page(bean));
        return responseBean;
    }

    @ApiOperation(value = "客户头部统计", notes = "客户头部统计")
    @PostMapping("/top/stat")
    public DefaultResponseBean<DoctorStatResponseBean> stat(HttpServletRequest request, HttpServletResponse response){
        DefaultResponseBean responseBean = new DefaultResponseBean();
        responseBean.setData(doctorService.stat(super.getLoginId(request)));
        return responseBean;
    }

    @ApiOperation(value = "医生保存", notes = "医生保存")
    @PostMapping("/save")
    public DefaultResponseBean<Boolean> save(@RequestBody DoctorRequestBean bean,
                                             HttpServletRequest request, HttpServletResponse response){
        DefaultResponseBean responseBean = new DefaultResponseBean();
        DrugUser user = super.getLoginUser(request);
        if(user==null){
            responseBean.setCode(300);
            responseBean.setMessage("登录失效");
            return responseBean;
        }
        bean.setDrugUserId(user.getId());
        bean.setLeaderPath(user.getLeaderPath());
        responseBean.setData(doctorService.save(bean));
        return responseBean;
    }

    @ApiOperation(value = "医生修改", notes = "医生修改")
    @PostMapping("/update")
    public DefaultResponseBean<Boolean> update(@RequestBody DoctorRequestBean bean,
                                             HttpServletRequest request, HttpServletResponse response){
        DefaultResponseBean responseBean = new DefaultResponseBean();
        DrugUser user = super.getLoginUser(request);
        if(user==null){
            responseBean.setCode(300);
            responseBean.setMessage("登录失效");
            return responseBean;
        }
        bean.setDrugUserId(user.getId());
        bean.setLeaderPath(user.getLeaderPath());
        responseBean.setData(doctorService.update(bean));
        return responseBean;
    }

    @ApiOperation(value = "医生excle导入", notes = "医生excle导入")
    @PostMapping("/excel")
    public DefaultResponseBean<Boolean> excel(MultipartFile file,
                                              HttpServletRequest request, HttpServletResponse response){
        DefaultResponseBean responseBean = new DefaultResponseBean();
        ExcelUtils<DoctorExcel> excelUtils = new ExcelUtils<>(new DoctorExcel());
        String productId = request.getParameter("productId");
        if(!StringUtils.isNotEmtity(productId)){
            responseBean.setCode(500);
            responseBean.setMessage("产品不能为空");
            return responseBean;
        }
        List<DoctorExcel> list = new ArrayList<>();
        try{
            list = excelUtils.readFromFile(null,file.getInputStream());
        }catch (Exception e){
            responseBean.setCode(500);
            responseBean.setMessage("excel解析失败");
            responseBean.setDescription(e.getMessage());
            return responseBean;
        }
        if(list==null || list.isEmpty()){
            responseBean.setCode(500);
            responseBean.setMessage("导入数据为空");
            return responseBean;
        }
        doctorService.saves(list,Long.valueOf(productId),getLoginUser(request));
        return responseBean;
    }

    @ApiOperation(value = "删除医生信息", notes = "删除医生信息")
    @PostMapping("/delete")
    public DefaultResponseBean<Boolean> delete(@RequestBody RelationRequestBean bean,
                                               HttpServletRequest request, HttpServletResponse response){
        DefaultResponseBean<Boolean> responseBean = new DefaultResponseBean();
        bean.setOldDrugUserId(getLoginId(request));
        if(!StringUtils.isNotEmtity(bean.getDoctorIds())){
            responseBean.setCode(500);
            responseBean.setMessage("医生id不能为空");
            return responseBean;
        }
        if(!StringUtils.isNotEmtity(bean.getProductIds())){
            responseBean.setCode(500);
            responseBean.setMessage("产品id不能为空");
            return responseBean;
        }
        if(bean.getIds().size()!=bean.getpIds().size()){
            responseBean.setCode(500);
            responseBean.setMessage("产品id跟医生id对不上");
            return responseBean;
        }
        responseBean.setData(doctorService.delete(bean));
        return responseBean;
    }

    @ApiOperation(value = "关系转移", notes = "关系转移")
    @PostMapping("/relation/update")
    public DefaultResponseBean<Boolean> relation(@RequestBody RelationRequestBean bean,
                                                 HttpServletRequest request, HttpServletResponse response){
        DefaultResponseBean<Boolean> responseBean = new DefaultResponseBean();
        bean.setOldDrugUserId(getLoginId(request));
        if(!StringUtils.isNotEmtity(bean.getDoctorIds())){
            responseBean.setCode(500);
            responseBean.setMessage("医生id不能为空");
            return responseBean;
        }
        if(bean.getNewDrugUserId()==null){
            responseBean.setCode(500);
            responseBean.setMessage("坐席id不能为空");
            return responseBean;
        }
        if(bean.getProductIds()==null){
            responseBean.setCode(500);
            responseBean.setMessage("产品id不能为空");
            return responseBean;
        }
        if(bean.getIds().size()!=bean.getpIds().size()){
            responseBean.setCode(500);
            responseBean.setMessage("产品id跟医生id对不上");
            return responseBean;
        }
        responseBean.setData(doctorService.relation(bean));
        return responseBean;
    }

    @ApiOperation(value = "关系转移的坐席", notes = "关系转移的坐席")
    @GetMapping("/relation/druguser/{productId}")
    public DefaultResponseBean<List<DrugUserResponseBean>> relationDrugUser(@PathVariable Long productId,
                                                                            HttpServletRequest request, HttpServletResponse response){
        DefaultResponseBean<List<DrugUserResponseBean>> responseBean = new DefaultResponseBean();
        DrugUser user = super.getLoginUser(request);
        if(user==null){
            responseBean.setCode(300);
            responseBean.setMessage("登录失效");
            return responseBean;
        }
        responseBean.setData(drugUserService.relationDrugUser(user.getLeaderPath(),productId));
        return responseBean;
    }

    @ApiOperation(value = "联系计划page", notes = "联系计划page")
    @PostMapping("/contact/plan/page")
    public DefaultResponseBean<PageResponseBean<ContactPlanRequestBean>> pageContactPlan(@RequestBody ContactPlanQueryRequestBean bean,
                                                                                         HttpServletRequest request, HttpServletResponse response){
        DefaultResponseBean<PageResponseBean<ContactPlanRequestBean>> responseBean = new DefaultResponseBean();
        DrugUser user = super.getLoginUser(request);
        if(user==null){
            responseBean.setCode(300);
            responseBean.setMessage("登录失效");
            return responseBean;
        }
        bean.setDrugUserId(user.getId());
        responseBean.setData(contactPlanService.page(bean));
        return responseBean;
    }


    @ApiOperation(value = "联系计划添加修改", notes = "联系计划添加修改")
    @PostMapping("/contact/plan/save")
    public DefaultResponseBean<Boolean> ContactPlanSave(@RequestBody ContactPlanRequestBean bean,
                                                                            HttpServletRequest request, HttpServletResponse response){
        DefaultResponseBean<Boolean> responseBean = new DefaultResponseBean();
        DrugUser user = super.getLoginUser(request);
        if(user==null){
            responseBean.setCode(300);
            responseBean.setMessage("登录失效");
            return responseBean;
        }
        bean.setDrugUserId(user.getId());
        contactPlanService.save(bean);
        responseBean.setData(true);
        return responseBean;
    }


    @ApiOperation(value = "联系计划状态修改", notes = "状态修改")
    @PostMapping("/contact/plan/update/status/{id}")
    public DefaultResponseBean<Boolean> ContactPlanUpdateStatus(@PathVariable Long id,
                                                                            HttpServletRequest request, HttpServletResponse response){
        DefaultResponseBean<Boolean> responseBean = new DefaultResponseBean();
        DrugUser user = super.getLoginUser(request);
        if(user==null){
            responseBean.setCode(300);
            responseBean.setMessage("登录失效");
            return responseBean;
        }
        contactPlanService.updateStatus(id);
        responseBean.setData(true);
        return responseBean;
    }

//    @ApiOperation(value = "dra医生excle导入", notes = "dra医生excle导入")
//    @PostMapping("/dra")
//    public DefaultResponseBean<Boolean> dra(MultipartFile file,
//                                              HttpServletRequest request, HttpServletResponse response){
//        DefaultResponseBean responseBean = new DefaultResponseBean();
//        ExcelUtils<DraTable> excelUtils = new ExcelUtils<>(new DraTable());
//        List<DraTable> list = new ArrayList<>();
//        try{
//            list = excelUtils.readFromFile(null,file.getInputStream());
//        }catch (Exception e){
//            responseBean.setCode(500);
//            responseBean.setMessage("excel解析失败");
//            responseBean.setDescription(e.getMessage());
//            return responseBean;
//        }
//        if(list==null || list.isEmpty()){
//            responseBean.setCode(500);
//            responseBean.setMessage("导入数据为空");
//            return responseBean;
//        }
//        doctorService.save(list);
//        return responseBean;
//    }
}
