package com.nuoxin.virtual.rep.api.web.controller.v3_0;

import com.nuoxin.virtual.rep.api.common.bean.DefaultResponseBean;
import com.nuoxin.virtual.rep.api.common.bean.PageResponseBean;
import com.nuoxin.virtual.rep.api.entity.v3_0.ScheduleResult;
import com.nuoxin.virtual.rep.api.entity.v3_0.WenJuanProject;
import com.nuoxin.virtual.rep.api.entity.v3_0.params.ContentSharingParams;
import com.nuoxin.virtual.rep.api.entity.v3_0.params.WenJuanInfoParams;
import com.nuoxin.virtual.rep.api.entity.v3_0.request.ContentSharingRequest;
import com.nuoxin.virtual.rep.api.entity.v3_0.request.WenJuanInfoRequest;
import com.nuoxin.virtual.rep.api.entity.v3_0.request.WenJuanProjectRequest;
import com.nuoxin.virtual.rep.api.service.v3_0.WenJuanQuestionnaireService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

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

    @ApiOperation(value = "问卷列表")
    @RequestMapping(value = "/getWenJuanProjectListPage", method = {RequestMethod.POST})
    public DefaultResponseBean<PageResponseBean<List<WenJuanProject>>> getWenJuanProjectListPage(@RequestBody @Valid WenJuanProjectRequest wenJuanProjectRequest) {
        PageResponseBean<List<WenJuanProject>> list = wenJuanQuestionnaireService.getWenJuanProjectListPage(wenJuanProjectRequest);
        DefaultResponseBean<PageResponseBean<List<WenJuanProject>>> responseBean = new DefaultResponseBean<PageResponseBean<List<WenJuanProject>>>();
        responseBean.setData(list);
        return responseBean;
    }

    @ApiOperation(value = "编辑问卷所属产品")
    @RequestMapping(value = "/wenJuanProductIdAndNameUpdate", method = {RequestMethod.GET})
    public DefaultResponseBean<Boolean> wenJuanProductIdAndNameUpdate(@RequestParam("projectId") Integer projectId,@RequestParam("productId") Integer productId,@RequestParam("productName") String productName) {
        boolean result = wenJuanQuestionnaireService.wenJuanProductIdAndNameUpdate(projectId,productId,productName);
        DefaultResponseBean<Boolean> responseBean = new DefaultResponseBean<Boolean>();
        responseBean.setData(result);
        return responseBean;
    }

    @ApiOperation(value = "问卷详情列表")
    @RequestMapping(value = "/getWenJuanInfoListPage", method = {RequestMethod.POST})
    public DefaultResponseBean<PageResponseBean<List<WenJuanInfoParams>>> getWenJuanInfoListPage(@RequestBody @Valid WenJuanInfoRequest wenJuanInfoRequest) {
        PageResponseBean<List<WenJuanInfoParams>> list = wenJuanQuestionnaireService.getWenJuanInfoListPage(wenJuanInfoRequest);
        DefaultResponseBean<PageResponseBean<List<WenJuanInfoParams>>> responseBean = new DefaultResponseBean<PageResponseBean<List<WenJuanInfoParams>>>();
        responseBean.setData(list);
        return responseBean;
    }

    @ApiOperation(value = "问卷详情导出文件下載一体")
    @RequestMapping(value = "/wenJuanInfoExportFile", method = {RequestMethod.GET})
    public void wenJuanInfoExportFile(@RequestParam(value = "telPhone", required = false) String telPhone, @RequestParam(value = "shortId", required = false) String shortId, @RequestParam(value = "seq", required = false) Integer seq, HttpServletResponse response) {
        wenJuanQuestionnaireService.wenJuanInfoExportFile(telPhone,shortId,seq,response);
    }
}
