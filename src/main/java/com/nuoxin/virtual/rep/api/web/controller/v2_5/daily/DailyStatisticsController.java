package com.nuoxin.virtual.rep.api.web.controller.v2_5.daily;

import com.nuoxin.virtual.rep.api.common.bean.DefaultResponseBean;
import com.nuoxin.virtual.rep.api.common.bean.PageResponseBean;
import com.nuoxin.virtual.rep.api.common.enums.ErrorEnum;
import com.nuoxin.virtual.rep.api.common.exception.BusinessException;
import com.nuoxin.virtual.rep.api.entity.DrugUser;
import com.nuoxin.virtual.rep.api.enums.RoleTypeEnum;
import com.nuoxin.virtual.rep.api.service.v2_5.DailyStatisticsService;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.share.ShareRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.statistics.DailyStatisticsRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.ContentShareResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.statistics.DailyStatisticsResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.v2_5.NewBaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.builder.BuilderException;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 日报统计
 * @author tiancun
 * @date 2019-02-26
 */
@Api(value = "V2.5工作台-日报统计接口")
@RequestMapping(value = "/daily/statistics")
@RestController
public class DailyStatisticsController  extends NewBaseController {

    @Resource
    private DailyStatisticsService dailyStatisticsService;


    @ApiOperation(value = "日报统计", notes = "日报统计")
    @PostMapping(value = "/detail")
    public DefaultResponseBean<DailyStatisticsResponseBean> getDailyStatistics(HttpServletRequest request, @RequestBody DailyStatisticsRequestBean bean) {

        DailyStatisticsResponseBean dailyStatistics = dailyStatisticsService.getDailyStatistics(bean);

        DefaultResponseBean<DailyStatisticsResponseBean> responseBean = new DefaultResponseBean<>();
        responseBean.setData(dailyStatistics);

        return responseBean;
    }


    @ApiOperation(value = "医院目标修改", notes = "医院目标修改")
    @PostMapping(value = "/hospital/target/{productId}/{targetHospital}")
    public DefaultResponseBean<Boolean> updateTargetHospital(HttpServletRequest request, @PathVariable(value = "productId") Long productId,@PathVariable(value = "targetHospital") Integer targetHospital) {

        DrugUser user = super.getDrugUser(request);
        if(user == null) {
            return super.getLoginErrorResponse();
        }

        if (!user.getRoleId().equals(RoleTypeEnum.MANAGER.getType())){
            throw new BusinessException(ErrorEnum.ERROR, "只有管理员才能修改!");
        }


        dailyStatisticsService.updateTargetHospital(productId, targetHospital);

        DefaultResponseBean<Boolean> responseBean = new DefaultResponseBean<>();
        responseBean.setData(true);

        return responseBean;
    }


    @ApiOperation(value = "医生目标修改", notes = "医生目标修改")
    @PostMapping(value = "/doctor/target/{productId}/{targetDoctor}")
    public DefaultResponseBean<Boolean> updateTargetDoctor(HttpServletRequest request, @PathVariable(value = "productId") Long productId,@PathVariable(value = "targetDoctor") Integer targetDoctor) {

        DrugUser user = super.getDrugUser(request);
        if(user == null) {
            return super.getLoginErrorResponse();
        }

        if (!user.getRoleId().equals(RoleTypeEnum.MANAGER.getType())){
            throw new BusinessException(ErrorEnum.ERROR, "只有管理员才能修改!");
        }


        dailyStatisticsService.updateTargetDoctor(productId, targetDoctor);

        DefaultResponseBean<Boolean> responseBean = new DefaultResponseBean<>();
        responseBean.setData(true);

        return responseBean;
    }




}
