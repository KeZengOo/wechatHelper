package com.nuoxin.virtual.rep.api.web.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nuoxin.virtual.rep.api.entity.v2_5.DoctorExcelBean;
import com.nuoxin.virtual.rep.api.enums.RoleTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.nuoxin.virtual.rep.api.common.bean.DefaultResponseBean;
import com.nuoxin.virtual.rep.api.common.bean.DoctorExcel;
import com.nuoxin.virtual.rep.api.common.bean.PageResponseBean;
import com.nuoxin.virtual.rep.api.common.controller.BaseController;
import com.nuoxin.virtual.rep.api.common.util.StringUtils;
import com.nuoxin.virtual.rep.api.entity.Doctor;
import com.nuoxin.virtual.rep.api.entity.DrugUser;
import com.nuoxin.virtual.rep.api.service.ContactPlanService;
import com.nuoxin.virtual.rep.api.service.DoctorService;
import com.nuoxin.virtual.rep.api.service.DrugUserService;
import com.nuoxin.virtual.rep.api.utils.ExcelUtils;
import com.nuoxin.virtual.rep.api.web.controller.request.ContactPlanQueryRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.ContactPlanRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.QueryRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.doctor.DoctorRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.doctor.DoctorUpdateRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.doctor.RelationRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.ContactPlanResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.DrugUserResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.doctor.DoctorDetailsResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.doctor.DoctorResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.doctor.DoctorStatResponseBean;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

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
    @Value("${excel.maxNum}")
    private Integer maxNum;

    @ApiOperation(value = "获取医生信息", notes = "获取医生信息")
    @GetMapping("/details/{doctorId}")
    public DefaultResponseBean<DoctorDetailsResponseBean> doctorDetails(@PathVariable Long doctorId,
                                                                        HttpServletRequest request, HttpServletResponse response){
        DefaultResponseBean responseBean = new DefaultResponseBean();
        if(doctorId==0){
            return responseBean;
        }
        DoctorDetailsResponseBean bean = doctorService.details(doctorId);
        responseBean.setData(bean);
        return responseBean;
    }
    
    @ApiOperation(value = "根据产品获取医生信息", notes = "根据产品获取医生信息")
    @GetMapping("/details/{doctorId}/{productId}")
    public DefaultResponseBean<DoctorDetailsResponseBean> doctorProductDetails(@PathVariable Long doctorId,@PathVariable Long productId,
                                                                        HttpServletRequest request, HttpServletResponse response){
        DefaultResponseBean responseBean = new DefaultResponseBean();
        if(doctorId==0){
            return responseBean;
        }
        DoctorDetailsResponseBean bean = doctorService.details(doctorId);
        bean.setProductId(productId);
        responseBean.setData(bean);
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
        DrugUser user = super.getLoginUser(request);
        if(user==null){
            responseBean.setCode(300);
            responseBean.setMessage("登录失效");
            return responseBean;
        }
        responseBean.setData(doctorService.stat(user.getId(),user.getLeaderPath()));
        return responseBean;
    }

//    @ApiOperation(value = "医生保存", notes = "医生保存")
//    @PostMapping("/save")
//    public DefaultResponseBean<Boolean> save(@RequestBody DoctorRequestBean bean,
//                                             HttpServletRequest request, HttpServletResponse response){
//        DefaultResponseBean responseBean = new DefaultResponseBean();
//        DrugUser user = super.getLoginUser(request);
//        if(user==null){
//            responseBean.setCode(300);
//            responseBean.setMessage("登录失效");
//            return responseBean;
//        }
//        bean.setDrugUserId(user.getId());
//        bean.setLeaderPath(user.getLeaderPath());
//        responseBean.setData(doctorService.save(bean));
//        return responseBean;
//    }

//    @ApiOperation(value = "医生修改", notes = "医生修改")
//    @PostMapping("/update")
//    public DefaultResponseBean<Boolean> update(@RequestBody DoctorUpdateRequestBean bean,
//                                             HttpServletRequest request, HttpServletResponse response){
//        DefaultResponseBean responseBean = new DefaultResponseBean();
//        DrugUser user = super.getLoginUser(request);
//        if(user==null){
//            responseBean.setCode(300);
//            responseBean.setMessage("登录失效");
//            return responseBean;
//        }
//        if(bean.getDrugUserId()==null){
//            bean.setDrugUserId(user.getId());
//        }
//        bean.setLeaderPath(drugUserService.findById(bean.getDrugUserId()).getLeaderPath());
//        responseBean.setData(doctorService.update(bean));
//        return responseBean;
//    }

    @ApiOperation(value = "医生excle导入", notes = "医生excle导入")
    @PostMapping("/excel")
    public DefaultResponseBean<Boolean> excel(MultipartFile file,
                                              HttpServletRequest request, HttpServletResponse response){
        DefaultResponseBean responseBean = new DefaultResponseBean();
        ExcelUtils<DoctorExcelBean> excelUtils = new ExcelUtils<>(new DoctorExcelBean());
        String productId = request.getParameter("productId");
        if(!StringUtils.isNotEmtity(productId)){
            responseBean.setCode(500);
            responseBean.setMessage("产品不能为空");
            return responseBean;
        }
        InputStream inputStream = null;
        List<DoctorExcelBean> list = new ArrayList<>();
        try{
            inputStream = file.getInputStream();
			list = excelUtils.readFromFile(null, inputStream);
        }catch (Exception e){
        	logger.error("Exception", e);
            e.printStackTrace();
            responseBean.setCode(500);
            responseBean.setMessage("excel解析失败");
            responseBean.setDescription(e.getMessage());
            return responseBean;
        } finally {
        	if(inputStream != null) {
        		try {
					inputStream.close();
				} catch (IOException e) {
					logger.error("IOException", e);
				}
        	}
        }
        
        if(list==null || list.isEmpty()){
            responseBean.setCode(500);
            responseBean.setMessage("导入数据为空");
            return responseBean;
        }
        if(list.size()>maxNum){
            responseBean.setCode(500);
            responseBean.setMessage("导入数据大于"+maxNum+"条");
            return responseBean;
        }
        
        try {
            //doctorService.saves(list,Long.valueOf(productId),getLoginUser(request));
            doctorService.excelSaves(list,Long.valueOf(productId));
        } catch (Exception e) {
            e.printStackTrace();
            responseBean.setCode(500);
            responseBean.setMessage(e.getMessage());
            responseBean.setDescription(e.getMessage());
            return responseBean;
        }
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
        List<Long> ids = bean.getpIds();
        long check = 0;
        int index = 0;
        for (int i = 0,leng=ids.size(); i < leng ; i++) {
            if(check==ids.get(i)){
                index = i;
                break;
            }
            check=ids.get(i);
        }
        if(index!=0){
            Long doctorId = bean.getIds().get(index);
            Doctor doctor = doctorService.findById(doctorId);
            if(doctor!=null){
                responseBean.setCode(500);
                responseBean.setMessage(doctor.getName()+"医生的产品跟其他的不一样");
                return responseBean;
            }
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

        List<Long> roleIdList = user.getRoleIdList();
        if (roleIdList.contains(RoleTypeEnum.SALE.getType())
                || roleIdList.contains(RoleTypeEnum.RECRUIT_SALE.getType())
                || roleIdList.contains(RoleTypeEnum.MOBILE_COVER_SALE.getType())
                || roleIdList.contains(RoleTypeEnum.WECHAT_COVER_SALE.getType())){
            DrugUserResponseBean drugUserResponseBean = new DrugUserResponseBean();
            drugUserResponseBean.setId(user.getId());
            drugUserResponseBean.setName(user.getName());
            List<DrugUserResponseBean> list = new ArrayList<>();
            list.add(drugUserResponseBean);
            responseBean.setData(list);
            return responseBean;
        }


        responseBean.setData(drugUserService.relationDrugUser(user.getLeaderPath(),productId));
        return responseBean;
    }


    @ApiOperation(value = "查询产品下的代表", notes = "查询产品下的代表")
    @GetMapping("/relation/{productId}")
    public DefaultResponseBean<List<DrugUserResponseBean>> relationProductDrugUser(@PathVariable Long productId,
                                                                            HttpServletRequest request, HttpServletResponse response){
        DefaultResponseBean<List<DrugUserResponseBean>> responseBean = new DefaultResponseBean();
        DrugUser user = super.getLoginUser(request);
        if(user==null){
            responseBean.setCode(300);
            responseBean.setMessage("登录失效");
            return responseBean;
        }
        responseBean.setData(drugUserService.relationDrugUser(productId));
        return responseBean;
    }


    @ApiOperation(value = "联系计划page", notes = "联系计划page")
    @PostMapping("/contact/plan/page")
    public DefaultResponseBean<PageResponseBean<ContactPlanResponseBean>> pageContactPlan(@RequestBody ContactPlanQueryRequestBean bean,
                                                                                          HttpServletRequest request, HttpServletResponse response){
        DefaultResponseBean<PageResponseBean<ContactPlanResponseBean>> responseBean = new DefaultResponseBean();
        DrugUser user = super.getLoginUser(request);
        if(user==null){
            responseBean.setCode(300);
            responseBean.setMessage("登录失效");
            return responseBean;
        }
        if(bean.getDoctorId()!=null && bean.getDoctorId()==0){
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
        if(bean.getDoctorId()==null || bean.getDoctorId()==0){
            responseBean.setCode(500);
            responseBean.setMessage("医生id不能为空");
            return responseBean;
        }
        bean.setDrugUserId(user.getId());
        contactPlanService.save(bean);
        responseBean.setData(true);
        return responseBean;
    }


    @ApiOperation(value = "联系计划状态修改", notes = "状态修改")
    @PostMapping("/contact/plan/update/status/{id}")
    @ResponseBody
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

    @ApiOperation(value = "联系计划最近弹窗提醒数据", notes = "联系计划最近弹窗提醒数据")
    @PostMapping("/contact/plan/alert/data")
    public DefaultResponseBean<List<ContactPlanResponseBean>> contactPlan(HttpServletRequest request, HttpServletResponse response){
        DefaultResponseBean<List<ContactPlanResponseBean>> responseBean = new DefaultResponseBean();
        DrugUser user = super.getLoginUser(request);
        if(user==null){
            responseBean.setCode(300);
            responseBean.setMessage("登录失效");
            return responseBean;
        }
        responseBean.setData(contactPlanService.contactPlan(user.getLeaderPath(),user.getId()));
        return responseBean;
    }

}
