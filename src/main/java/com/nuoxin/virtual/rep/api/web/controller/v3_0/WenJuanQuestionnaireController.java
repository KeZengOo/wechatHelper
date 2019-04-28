package com.nuoxin.virtual.rep.api.web.controller.v3_0;

import com.nuoxin.virtual.rep.api.entity.v3_0.ScheduleResult;
import com.nuoxin.virtual.rep.api.service.v3_0.WenJuanQuestionnaireService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import javax.annotation.Resource;

/**
 * 问卷网 Controller 类
 * @author wujiang
 * @date 20190425
 */
@Api(value = "V3_0 问卷网相关接口")
@RequestMapping(value = "/questionnaireApi")
@RestController
public class WenJuanQuestionnaireController {

    @Resource
    private RestTemplate restTemplate;
    @Resource
    private WenJuanQuestionnaireService wenJuanQuestionnaireService;

    @ApiOperation(value = "问卷网项目接口")
    @RequestMapping(value = "/wenJuanProjectApi", method = { RequestMethod.GET })
    public ScheduleResult wenJuanProjectApi(){
        ScheduleResult scheduleResult = wenJuanQuestionnaireService.saveWenJuanProject();
        return scheduleResult;
    }

    /**
     * 获取答卷详情列表
     * @return ScheduleResult
     */
    @ApiOperation(value = "获取答卷详情列表接口")
    @RequestMapping(value = "/saveWenJuanAnswerSheetInfo", method = { RequestMethod.GET })
    public ScheduleResult saveWenJuanAnswerSheetInfo(){
        ScheduleResult scheduleResult = wenJuanQuestionnaireService.saveWenJuanAnswerSheetInfo();
        return scheduleResult;
    }

    /**
     * 问卷网查看答题者最新一条答卷详情
     * @return ScheduleResult
     */
    @ApiOperation(value = "问卷网查看答题者最新一条答卷详情接口")
    @RequestMapping(value = "/saveWenJuanNewAnswerSheetInfo", method = { RequestMethod.GET })
    public ScheduleResult saveWenJuanNewAnswerSheetInfo(){
        ScheduleResult scheduleResult = wenJuanQuestionnaireService.saveWenJuanNewAnswerSheetInfo();
        return scheduleResult;
    }
}
