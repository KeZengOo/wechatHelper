package com.nuoxin.virtual.rep.api.web.controller.v2_5.daily;

import com.nuoxin.virtual.rep.api.common.bean.DefaultResponseBean;
import com.nuoxin.virtual.rep.api.common.bean.PageResponseBean;
import com.nuoxin.virtual.rep.api.common.enums.ErrorEnum;
import com.nuoxin.virtual.rep.api.common.exception.BusinessException;
import com.nuoxin.virtual.rep.api.entity.DrugUser;
import com.nuoxin.virtual.rep.api.enums.RoleTypeEnum;
import com.nuoxin.virtual.rep.api.service.v2_5.DailyStatisticsService;
import com.nuoxin.virtual.rep.api.utils.CollectionsUtil;
import com.nuoxin.virtual.rep.api.utils.ExportExcel;
import com.nuoxin.virtual.rep.api.utils.ExportExcelTitle;
import com.nuoxin.virtual.rep.api.utils.StringUtil;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.share.ShareRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.statistics.DailyStatisticsRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.ContentShareResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.statistics.DailyStatisticsResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.v2_5.NewBaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.builder.BuilderException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 日报统计
 * @author tiancun
 * @date 2019-02-26
 */
@Api(value = "V2.5工作台-日报统计接口")
@RequestMapping(value = "/daily/statistics")
@Controller
public class DailyStatisticsController  extends NewBaseController {

    @Resource
    private DailyStatisticsService dailyStatisticsService;


    @ApiOperation(value = "日报统计", notes = "日报统计")
    @PostMapping(value = "/detail")
    @ResponseBody
    public DefaultResponseBean<DailyStatisticsResponseBean> getDailyStatistics(HttpServletRequest request, @RequestBody DailyStatisticsRequestBean bean) {

        DailyStatisticsResponseBean dailyStatistics = dailyStatisticsService.getDailyStatistics(bean);

        DefaultResponseBean<DailyStatisticsResponseBean> responseBean = new DefaultResponseBean<>();
        responseBean.setData(dailyStatistics);

        return responseBean;
    }



    @ApiOperation(value = "日报下载", notes = "日报下载")
    @GetMapping(value = "/download")
    public void download(HttpServletRequest request, HttpServletResponse response,Long productId,String drugUserIds, String startTime, String endTime) {
        if (StringUtil.isEmpty(drugUserIds)){
            throw new BusinessException(ErrorEnum.ERROR, "代表ID不能为空！");
        }

        DailyStatisticsRequestBean bean = new DailyStatisticsRequestBean();
        bean.setProductId(productId);
        bean.setStartTime(startTime);
        bean.setEndTime(endTime);
        if (drugUserIds.contains("，")){
            drugUserIds = drugUserIds.replace("，", ",");
        }


        String[] drugUserIdArray = drugUserIds.split(",");
        if (CollectionsUtil.isEmptyArray((drugUserIdArray))){
            throw new BusinessException(ErrorEnum.ERROR, "代表ID不能为空！");
        }

        List<String> drugUserIdStrList = Arrays.asList(drugUserIdArray);
        if (CollectionsUtil.isEmptyList((drugUserIdStrList))){
            throw new BusinessException(ErrorEnum.ERROR, "代表ID不能为空！");
        }
        List<Long> drugUserIdList = new ArrayList<>();
        for (String drugUserIdStr: drugUserIdStrList){
            drugUserIdList.add(Long.parseLong(drugUserIdStr));
        }

        bean.setDrugUserIdList(drugUserIdList);

        String downloadDesc = dailyStatisticsService.getDownloadDesc(bean);
        List<List<String>> downloadDailyStatisticsData = dailyStatisticsService.getDownloadDailyStatisticsData(bean);


        HSSFWorkbook wb= ExportExcel.excelExport(downloadDailyStatisticsData, downloadDesc,"日报统计");
        OutputStream ouputStream = null;
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode("日报统计.xls","UTF-8"));
            response.setHeader("Pragma", "No-cache");
            ouputStream = response.getOutputStream();
            if(ouputStream!=null){
                wb.write(ouputStream);
            }
            ouputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                ouputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    @ApiOperation(value = "医院目标修改", notes = "医院目标修改")
    @PostMapping(value = "/hospital/target/{productId}/{targetHospital}")
    @ResponseBody
    public DefaultResponseBean<Boolean> updateTargetHospital(HttpServletRequest request, @PathVariable(value = "productId") Long productId,@PathVariable(value = "targetHospital") Integer targetHospital) {

        DrugUser user = super.getDrugUser(request);
        if(user == null) {
            return super.getLoginErrorResponse();
        }

        List<Long> roleIdList = user.getRoleIdList();
        if (!roleIdList.contains(RoleTypeEnum.MANAGER.getType())){
            throw new BusinessException(ErrorEnum.ERROR, "只有管理员才能修改!");
        }


        dailyStatisticsService.updateTargetHospital(productId, targetHospital);

        DefaultResponseBean<Boolean> responseBean = new DefaultResponseBean<>();
        responseBean.setData(true);

        return responseBean;
    }


    @ApiOperation(value = "医生目标修改", notes = "医生目标修改")
    @GetMapping(value = "/doctor/target/{productId}/{targetDoctor}")
    @ResponseBody
    public DefaultResponseBean<Boolean> updateTargetDoctor(HttpServletRequest request, @PathVariable(value = "productId") Long productId,@PathVariable(value = "targetDoctor") Integer targetDoctor) {

        DrugUser user = super.getDrugUser(request);
        if(user == null) {
            return super.getLoginErrorResponse();
        }

        List<Long> roleIdList = user.getRoleIdList();
        if (!roleIdList.contains(RoleTypeEnum.MANAGER.getType())){
            throw new BusinessException(ErrorEnum.ERROR, "只有管理员才能修改!");
        }


        dailyStatisticsService.updateTargetDoctor(productId, targetDoctor);

        DefaultResponseBean<Boolean> responseBean = new DefaultResponseBean<>();
        responseBean.setData(true);

        return responseBean;
    }




}
