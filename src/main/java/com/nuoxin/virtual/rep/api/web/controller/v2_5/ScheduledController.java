package com.nuoxin.virtual.rep.api.web.controller.v2_5;

import com.nuoxin.virtual.rep.api.common.bean.DefaultResponseBean;
import com.nuoxin.virtual.rep.api.entity.v2_5.ScheduleSyncResult;
import com.nuoxin.virtual.rep.api.service.ScheduledServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * 数据库主从同步Controller 类
 * @author lichengxin
 */
@Api(value = "数据库主从同步")
@RequestMapping(value = "/scheduled")
@Controller
public class ScheduledController {

    @Resource
    private ScheduledServiceImpl scheduledService;

    @ApiOperation(value = "角色同步")
    @ResponseBody
    @RequestMapping(value = "/roleSync", method = { RequestMethod.POST })
    public DefaultResponseBean<ScheduleSyncResult> roleSync() {
        ScheduleSyncResult scheduleSyncResult = scheduledService.roleSync();
        DefaultResponseBean<ScheduleSyncResult> responseBean = new DefaultResponseBean<ScheduleSyncResult>();
        responseBean.setData(scheduleSyncResult);
        return responseBean;
    }

    @ApiOperation(value = "销售与医生关系指标表同步")
    @ResponseBody
    @RequestMapping(value = "/drugUserDoctorQuateSync", method = { RequestMethod.POST })
    public DefaultResponseBean<ScheduleSyncResult> drugUserDoctorQuateSync() {
        ScheduleSyncResult scheduleSyncResult = scheduledService.drugUserDoctorQuateSync();
        DefaultResponseBean<ScheduleSyncResult> responseBean = new DefaultResponseBean<ScheduleSyncResult>();
        responseBean.setData(scheduleSyncResult);
        return responseBean;
    }

    @ApiOperation(value = "电话拜访扩展表同步")
    @ResponseBody
    @RequestMapping(value = "/virtualDoctorCallInfoMendSync", method = { RequestMethod.POST })
    public DefaultResponseBean<ScheduleSyncResult> virtualDoctorCallInfoMendSync() {
        ScheduleSyncResult scheduleSyncResult = scheduledService.virtualDoctorCallInfoMendSync();
        DefaultResponseBean<ScheduleSyncResult> responseBean = new DefaultResponseBean<ScheduleSyncResult>();
        responseBean.setData(scheduleSyncResult);
        return responseBean;
    }

    @ApiOperation(value = "销售代表给医生打电话表同步")
    @ResponseBody
    @RequestMapping(value = "/virtualDoctorCallInfoSync", method = { RequestMethod.POST })
    public DefaultResponseBean<ScheduleSyncResult> virtualDoctorCallInfoSync() {
        ScheduleSyncResult scheduleSyncResult = scheduledService.virtualDoctorCallInfoSync();
        DefaultResponseBean<ScheduleSyncResult> responseBean = new DefaultResponseBean<ScheduleSyncResult>();
        responseBean.setData(scheduleSyncResult);
        return responseBean;
    }

    @ApiOperation(value = "医院表同步")
    @ResponseBody
    @RequestMapping(value = "/enterpriseHciSync", method = { RequestMethod.POST })
    public DefaultResponseBean<ScheduleSyncResult> enterpriseHciSync() {
        ScheduleSyncResult scheduleSyncResult = scheduledService.enterpriseHciSync();
        DefaultResponseBean<ScheduleSyncResult> responseBean = new DefaultResponseBean<ScheduleSyncResult>();
        responseBean.setData(scheduleSyncResult);
        return responseBean;
    }

    @ApiOperation(value = "会议表同步")
    @ResponseBody
    @RequestMapping(value = "/enterpriseInternalMeetingSync", method = { RequestMethod.POST })
    public DefaultResponseBean<ScheduleSyncResult> enterpriseInternalMeetingSync() {
        ScheduleSyncResult scheduleSyncResult = scheduledService.enterpriseInternalMeetingSync();
        DefaultResponseBean<ScheduleSyncResult> responseBean = new DefaultResponseBean<ScheduleSyncResult>();
        responseBean.setData(scheduleSyncResult);
        return responseBean;
    }

    @ApiOperation(value = "会议参会信息同步")
    @ResponseBody
    @RequestMapping(value = "/enterpriseMeetingAttendDetailsSync", method = { RequestMethod.POST })
    public DefaultResponseBean<ScheduleSyncResult> enterpriseMeetingAttendDetailsSync() {
        ScheduleSyncResult scheduleSyncResult = scheduledService.enterpriseMeetingAttendDetailsSync();
        DefaultResponseBean<ScheduleSyncResult> responseBean = new DefaultResponseBean<ScheduleSyncResult>();
        responseBean.setData(scheduleSyncResult);
        return responseBean;
    }

    @ApiOperation(value = "医生信息同步")
    @ResponseBody
    @RequestMapping(value = "/enterpriseHcpSync", method = { RequestMethod.POST })
    public DefaultResponseBean<ScheduleSyncResult> enterpriseHcpSync() {
        ScheduleSyncResult scheduleSyncResult = scheduledService.enterpriseHcpSync();
        DefaultResponseBean<ScheduleSyncResult> responseBean = new DefaultResponseBean<ScheduleSyncResult>();
        responseBean.setData(scheduleSyncResult);
        return responseBean;
    }

    @ApiOperation(value = "角色用户映射表同步")
    @ResponseBody
    @RequestMapping(value = "/enterpriseSaleRepSync", method = { RequestMethod.POST })
    public DefaultResponseBean<ScheduleSyncResult> enterpriseSaleRepSync() {
        ScheduleSyncResult scheduleSyncResult = scheduledService.enterpriseSaleRepSync();
        DefaultResponseBean<ScheduleSyncResult> responseBean = new DefaultResponseBean<ScheduleSyncResult>();
        responseBean.setData(scheduleSyncResult);
        return responseBean;
    }

    @ApiOperation(value = "代表-医生-产品关联表同步")
    @ResponseBody
    @RequestMapping(value = "/enterpriseSaleRepProductHcpSync", method = { RequestMethod.POST })
    public DefaultResponseBean<ScheduleSyncResult> enterpriseSaleRepProductHcpSync() {
        ScheduleSyncResult scheduleSyncResult = scheduledService.enterpriseSaleRepProductHcpSync();
        DefaultResponseBean<ScheduleSyncResult> responseBean = new DefaultResponseBean<ScheduleSyncResult>();
        responseBean.setData(scheduleSyncResult);
        return responseBean;
    }

}
