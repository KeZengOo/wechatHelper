package com.nuoxin.virtual.rep.api.web.controller.v3_0;

import com.nuoxin.virtual.rep.api.common.bean.DefaultResponseBean;
import com.nuoxin.virtual.rep.api.common.bean.PageResponseBean;
import com.nuoxin.virtual.rep.api.common.enums.ErrorEnum;
import com.nuoxin.virtual.rep.api.common.exception.BusinessException;
import com.nuoxin.virtual.rep.api.entity.DrugUser;
import com.nuoxin.virtual.rep.api.service.v3_0.DrugUserDoctorCallService;
import com.nuoxin.virtual.rep.api.utils.DateUtil;
import com.nuoxin.virtual.rep.api.utils.StringUtil;

import com.nuoxin.virtual.rep.api.web.controller.request.v3_0.DrugUserDoctorCallDetailRequest;
import com.nuoxin.virtual.rep.api.web.controller.request.v3_0.DrugUserDoctorCallRequest;
import com.nuoxin.virtual.rep.api.web.controller.response.v3_0.DrugUserDoctorCallDetailResponse;
import com.nuoxin.virtual.rep.api.web.controller.response.v3_0.DrugUserDoctorCallResponse;
import com.nuoxin.virtual.rep.api.web.controller.v2_5.NewBaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * 电话拜访记录查询相关
 * @author tiancun
 * @date 2019-05-10
 */
@RestController
@Api(value = "V3.0.1电话拜访记录查询相关")
@RequestMapping(value = "/drug/user/doctor")
public class DrugUserDoctorCallController extends NewBaseController {

    @Resource
    private DrugUserDoctorCallService drugUserDoctorCallService;

    @ApiOperation(value = "电话拜访记录查询列表")
    @PostMapping(value = "/call/list")
    public DefaultResponseBean<PageResponseBean<DrugUserDoctorCallResponse>> getDrugUserDoctorCallPage(HttpServletRequest request, @RequestBody DrugUserDoctorCallRequest bean){
        DrugUser drugUser = this.getDrugUser(request);
        this.fillDrugUserIdListByRoleId(drugUser, bean);
        PageResponseBean<DrugUserDoctorCallResponse> drugUserDoctorCallPage = drugUserDoctorCallService.getDrugUserDoctorCallPage(bean);
        DefaultResponseBean<PageResponseBean<DrugUserDoctorCallResponse>> responseBean = new DefaultResponseBean<>();
        responseBean.setData(drugUserDoctorCallPage);
        return responseBean;
    }


    @ApiOperation(value = "拜访记录详情查询列表")
    @PostMapping(value = "/call/detail/list")
    public DefaultResponseBean<PageResponseBean<DrugUserDoctorCallDetailResponse>> getDrugUserDoctorCallDetailPage(@RequestBody DrugUserDoctorCallDetailRequest request){

        PageResponseBean<DrugUserDoctorCallDetailResponse> drugUserDoctorCallDetailPage = drugUserDoctorCallService.getDrugUserDoctorCallDetailPage(request);
        DefaultResponseBean<PageResponseBean<DrugUserDoctorCallDetailResponse>> responseBean = new DefaultResponseBean<>();
        responseBean.setData(drugUserDoctorCallDetailPage);
        return responseBean;
    }



    @ApiOperation(value = "电话拜访记录查询列表导出")
    @GetMapping(value = "/call/list/export")
    public void getDrugUserDoctorCallPage(HttpServletResponse response, HttpServletRequest request){

        DrugUserDoctorCallRequest bean = this.getExportParams(request);
        DrugUser drugUser = this.getDrugUser(request);
        this.fillDrugUserIdListByRoleId(drugUser, bean);
        drugUserDoctorCallService.exportDrugUserDoctorCallList(response, bean);

    }



    /**
     * 得到导出传入的参数
     * @param request
     * @return
     */
    private DrugUserDoctorCallRequest getExportParams(HttpServletRequest request) {
        String recruitStr = request.getParameter("recruit");
        String coverStr = request.getParameter("cover");
        String targetStr = request.getParameter("target");
        String hasDrugStr = request.getParameter("hasDrug");
        String searchKeyword = request.getParameter("searchKeyword");
        String productIdStr = request.getParameter("productIdStr");
        String drugUserIdStr = request.getParameter("drugUserIdStr");
        String startTimeStr = request.getParameter("startTime");
        String endTimeStr = request.getParameter("endTime");

        Integer recruit = null;
        Integer cover = null;
        Integer target = null;
        Integer hasDrug = null;
        List<Long> productIdList;
        List<Long> drugUserIdList;

        if (StringUtil.isNotEmpty(recruitStr)){
            try {
                recruit = Integer.valueOf(recruitStr);
            }catch (Exception e){
                throw new BusinessException(ErrorEnum.ERROR, "是否招募输入不合法");
            }

        }

        if (StringUtil.isNotEmpty(targetStr)){
            try {
                target = Integer.valueOf(targetStr);
            }catch (Exception e){
                throw new BusinessException(ErrorEnum.ERROR, "是否目标输入不合法");
            }
        }

        if (StringUtil.isNotEmpty(coverStr)){
            try {
                cover = Integer.valueOf(coverStr);
            }catch (Exception e){
                throw new BusinessException(ErrorEnum.ERROR, "是否覆盖输入不合法");
            }
        }

        if (StringUtil.isNotEmpty(hasDrugStr)){

            try {
                hasDrug = Integer.valueOf(hasDrugStr);
            }catch (Exception e){
                throw new BusinessException(ErrorEnum.ERROR, "是否有药输入不合法");
            }
        }



        try {
            productIdList = StringUtil.getIdList(productIdStr);
        }catch (Exception e){
            throw new BusinessException(ErrorEnum.ERROR, "产品ID输入不合法，多个以逗号分开");
        }

        try {
            drugUserIdList = StringUtil.getIdList(drugUserIdStr);
        }catch (Exception e){
            throw new BusinessException(ErrorEnum.ERROR, "代表ID输入不合法，多个以逗号分开");
        }


        Date startTime = DateUtil.stringToDate(startTimeStr, DateUtil.DATE_FORMAT_YMD);
        Date endTime = DateUtil.stringToDate(endTimeStr, DateUtil.DATE_FORMAT_YMD);

        DrugUserDoctorCallRequest drugUserDoctorCallRequest = new DrugUserDoctorCallRequest();
        drugUserDoctorCallRequest.setCover(cover);
        drugUserDoctorCallRequest.setDrugUserIdList(drugUserIdList);
        drugUserDoctorCallRequest.setHasDrug(hasDrug);
        drugUserDoctorCallRequest.setProductIdList(productIdList);
        drugUserDoctorCallRequest.setRecruit(recruit);
        drugUserDoctorCallRequest.setSearchKeyword(searchKeyword);
        drugUserDoctorCallRequest.setTarget(target);
        drugUserDoctorCallRequest.setStartTime(startTime);
        drugUserDoctorCallRequest.setEndTime(endTime);

        return drugUserDoctorCallRequest;

    }

}
