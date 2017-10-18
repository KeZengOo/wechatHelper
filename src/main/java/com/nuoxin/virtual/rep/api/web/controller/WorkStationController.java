package com.nuoxin.virtual.rep.api.web.controller;

import com.nuoxin.virtual.rep.api.common.bean.DefaultResponseBean;
import com.nuoxin.virtual.rep.api.common.bean.PageResponseBean;
import com.nuoxin.virtual.rep.api.common.controller.BaseController;
import com.nuoxin.virtual.rep.api.service.WorkStationService;
import com.nuoxin.virtual.rep.api.web.controller.request.WorkStationRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.work.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.hibernate.stat.Statistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Create by tiancun on 2017/10/12
 */
@Api(value = "工作台接口")
@RequestMapping(value = "/work/station")
@RestController
public class WorkStationController extends BaseController{

    @Autowired
    private WorkStationService workStationService;

    @ApiOperation(value = "今日统计接口", notes = "今日统计接口")
    @PostMapping("/today/statistics")
    @ResponseBody
    public ResponseEntity<DefaultResponseBean<TodayStatisticsResponseBean>>  todayStatistics(@RequestBody WorkStationRequestBean bean, HttpServletRequest request){
        Long drugUserId = bean.getDrugUserId();
        if (drugUserId == null || drugUserId == 0){
            drugUserId = getLoginId(request);
        }

        bean.setDrugUserId(drugUserId);
        TodayStatisticsResponseBean todayStatistic = workStationService.getTodayStatistic(bean);
        DefaultResponseBean<TodayStatisticsResponseBean> responseBean = new DefaultResponseBean<>();
        responseBean.setData(todayStatistic);
        return ResponseEntity.ok(responseBean);


    }


    @ApiOperation(value = "本月目标接口", notes = "本月目标接口")
    @PostMapping("/month/target/statistics")
    @ResponseBody
    public ResponseEntity<DefaultResponseBean<MonthTargetStatisticResponseBean>>  monthStatistics(@RequestBody WorkStationRequestBean bean, HttpServletRequest request){
        Long drugUserId = bean.getDrugUserId();
        if (drugUserId == null || drugUserId == 0){
            drugUserId = getLoginId(request);
        }

        bean.setDrugUserId(drugUserId);
        MonthTargetStatisticResponseBean monthTargetStatistic = workStationService.getMonthTargetStatistic(bean);
        DefaultResponseBean<MonthTargetStatisticResponseBean> responseBean = new DefaultResponseBean<>();
        responseBean.setData(monthTargetStatistic);
        return ResponseEntity.ok(responseBean);


    }


    @ApiOperation(value = "客户总数统计接口", notes = "客户统计接口")
    @PostMapping("/total/customer/statistic")
    @ResponseBody
    public ResponseEntity<DefaultResponseBean<List<CustomerSumResponseBean>>>  getTotalCustomerStatistic(@RequestBody WorkStationRequestBean bean, HttpServletRequest request){
        Long drugUserId = bean.getDrugUserId();
        if (drugUserId == null || drugUserId == 0){
            drugUserId = getLoginId(request);
        }

        bean.setDrugUserId(drugUserId);
        List<CustomerSumResponseBean> totalCustomerStatistic = workStationService.getTotalCustomerStatistic(bean);
        DefaultResponseBean<List<CustomerSumResponseBean>> responseBean = new DefaultResponseBean<>();
        responseBean.setData(totalCustomerStatistic);
        return ResponseEntity.ok(responseBean);

    }


    @ApiOperation(value = "当月新增客户统计接口", notes = "当月新增客户统计接口")
    @PostMapping("/add/customer/statistic")
    @ResponseBody
    public ResponseEntity<DefaultResponseBean<List<CustomerSumResponseBean>>>  getAddCustomerStatistic(@RequestBody WorkStationRequestBean bean, HttpServletRequest request){
        Long drugUserId = bean.getDrugUserId();
        if (drugUserId == null || drugUserId == 0){
            drugUserId = getLoginId(request);
        }

        bean.setDrugUserId(drugUserId);
        List<CustomerSumResponseBean> totalCustomerStatistic = workStationService.getAddCustomerStatistic(bean);
        DefaultResponseBean<List<CustomerSumResponseBean>> responseBean = new DefaultResponseBean<>();
        responseBean.setData(totalCustomerStatistic);
        return ResponseEntity.ok(responseBean);

    }


    @ApiOperation(value = "当月覆盖客户统计接口", notes = "当月覆盖客户统计接口")
    @PostMapping("/cover/customer/statistic")
    @ResponseBody
    public ResponseEntity<DefaultResponseBean<List<CustomerSumResponseBean>>>  getCoverCustomerStatistic(@RequestBody WorkStationRequestBean bean, HttpServletRequest request){
        Long drugUserId = bean.getDrugUserId();
        if (drugUserId == null || drugUserId == 0){
            drugUserId = getLoginId(request);
        }

        bean.setDrugUserId(drugUserId);
        List<CustomerSumResponseBean> totalCustomerStatistic = workStationService.getCoverCustomerStatistic(bean);
        DefaultResponseBean<List<CustomerSumResponseBean>> responseBean = new DefaultResponseBean<>();
        responseBean.setData(totalCustomerStatistic);
        return ResponseEntity.ok(responseBean);

    }


    @ApiOperation(value = "连续一个月无跟进接口", notes = "连续一个月无跟进接口")
    @PostMapping("/oneMonth/noFollow")
    @ResponseBody
    public ResponseEntity<DefaultResponseBean<PageResponseBean<OneMonthNoFollowCustomerResponseBean>>>  getOneMonthNoFollowCustomers(@RequestBody WorkStationRequestBean bean, HttpServletRequest request){
        Long drugUserId = bean.getDrugUserId();
        if (drugUserId == null || drugUserId == 0){
            drugUserId = getLoginId(request);
        }

        bean.setDrugUserId(drugUserId);
        PageResponseBean<OneMonthNoFollowCustomerResponseBean> oneMonthNoFollowCustomers = workStationService.getOneMonthNoFollowCustomers(bean);
        DefaultResponseBean<PageResponseBean<OneMonthNoFollowCustomerResponseBean>> responseBean = new DefaultResponseBean<>();
        responseBean.setData(oneMonthNoFollowCustomers);
        return ResponseEntity.ok(responseBean);

    }



    @ApiOperation(value = "坐席分析统计接口", notes = "坐席分析统计接口")
    @PostMapping("/drugUser/analysis")
    @ResponseBody
    public ResponseEntity<DefaultResponseBean<DrugUserAnalysisListResponseBean>>  getDrugUserAnalysisList(@RequestBody WorkStationRequestBean bean, HttpServletRequest request){

        Long drugUserId = bean.getDrugUserId();
        if (drugUserId == null || drugUserId == 0){
            drugUserId = getLoginId(request);
        }

        bean.setDrugUserId(drugUserId);

        DrugUserAnalysisListResponseBean drugUserAnalysisList = workStationService.getDrugUserAnalysisList(bean);
        DefaultResponseBean<DrugUserAnalysisListResponseBean> responseBean = new DefaultResponseBean<>();
        responseBean.setData(drugUserAnalysisList);
        return ResponseEntity.ok(responseBean);
    }


    @ApiOperation(value = "客户分析统计接口", notes = "客户分析统计接口")
    @PostMapping("/doctor/analysis")
    @ResponseBody
    public ResponseEntity<DefaultResponseBean<DoctorAnalysisListResponseBean>>  getDoctorAnalysisList(@RequestBody WorkStationRequestBean bean, HttpServletRequest request){

        Long drugUserId = bean.getDrugUserId();
        if (drugUserId == null || drugUserId == 0){
            drugUserId = getLoginId(request);
        }

        bean.setDrugUserId(drugUserId);

        DoctorAnalysisListResponseBean doctorAnalysisList = workStationService.getDoctorAnalysisList(bean);
        DefaultResponseBean<DoctorAnalysisListResponseBean> responseBean = new DefaultResponseBean<>();
        responseBean.setData(doctorAnalysisList);
        return ResponseEntity.ok(responseBean);
    }




}
