package com.nuoxin.virtual.rep.api.web.controller;

import com.nuoxin.virtual.rep.api.common.bean.DefaultResponseBean;
import com.nuoxin.virtual.rep.api.common.controller.BaseController;
import com.nuoxin.virtual.rep.api.service.WorkStationService;
import com.nuoxin.virtual.rep.api.web.controller.request.WorkStationRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.work.MonthTargetStatisticResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.work.TodayStatisticsResponseBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.hibernate.stat.Statistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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
        Long loginId = getLoginId(request);
        bean.setDrugUserId(loginId);
        TodayStatisticsResponseBean todayStatistic = workStationService.getTodayStatistic(bean);
        DefaultResponseBean<TodayStatisticsResponseBean> responseBean = new DefaultResponseBean<>();
        responseBean.setData(todayStatistic);
        return ResponseEntity.ok(responseBean);


    }


    @ApiOperation(value = "本月目标接口", notes = "本月目标接口")
    @PostMapping("/month/target/statistics")
    @ResponseBody
    public ResponseEntity<DefaultResponseBean<MonthTargetStatisticResponseBean>>  monthStatistics(@RequestBody WorkStationRequestBean bean, HttpServletRequest request){
        Long loginId = getLoginId(request);
        bean.setDrugUserId(loginId);
        MonthTargetStatisticResponseBean monthTargetStatistic = workStationService.getMonthTargetStatistic(bean);
        DefaultResponseBean<MonthTargetStatisticResponseBean> responseBean = new DefaultResponseBean<>();
        responseBean.setData(monthTargetStatistic);
        return ResponseEntity.ok(responseBean);


    }


}
