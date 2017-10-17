package com.nuoxin.virtual.rep.api.web.controller;

import com.alibaba.fastjson.JSON;
import com.nuoxin.virtual.rep.api.common.bean.DefaultResponseBean;
import com.nuoxin.virtual.rep.api.common.controller.BaseController;
import com.nuoxin.virtual.rep.api.common.util.StringUtils;
import com.nuoxin.virtual.rep.api.entity.DrugUser;
import com.nuoxin.virtual.rep.api.entity.Questionnaire;
import com.nuoxin.virtual.rep.api.service.analysis.QuestionnaireAnalysisService;
import com.nuoxin.virtual.rep.api.utils.DateUtil;
import com.nuoxin.virtual.rep.api.web.controller.request.analysis.QuestionnaireAnalysisRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.analysis.q.QuestionStatResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.analysis.q.QuestionnaireStatResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.question.QuestionnaireResponseBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * Created by fenggang on 10/11/17.
 */
@Api(value = "问卷分析", description = "问卷分析接口")
@RestController
@RequestMapping(value = "/questionnaire/analysis")
public class QuestionnaireAnalysisController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private QuestionnaireAnalysisService questionnaireAnalysisService;

    @ApiOperation(value = "问卷次数，参与人数，图表接口", notes = "问卷次数，参与人数，图表接口")
    @PostMapping("/summation")
    public DefaultResponseBean<QuestionnaireStatResponseBean> summation(@RequestBody QuestionnaireAnalysisRequestBean bean,
                                                                        HttpServletRequest request, HttpServletResponse response) {
        logger.info("{}-接口的请求参数【{}】",request.getServletPath(), JSON.toJSONString(bean));
        DefaultResponseBean<QuestionnaireStatResponseBean> responseBean = new DefaultResponseBean();
        if(!this._checkoutDate(bean)){
            responseBean.setCode(500);
            responseBean.setMessage("时间选择错误");
            return responseBean;
        }
        DrugUser user = super.getLoginUser(request);
        if(user==null){
            responseBean.setCode(300);
            responseBean.setMessage("登录失效");
            return responseBean;
        }
        bean.setDrugUserId(user.getId());
        bean.setLeaderPath(user.getLeaderPath());
        responseBean.setData(questionnaireAnalysisService.summation(bean));
        return responseBean;
    }

    @ApiOperation(value = "问卷列表接口", notes = "问卷列接口")
    @PostMapping("/list")
    public DefaultResponseBean<List<QuestionnaireResponseBean>> list(@RequestBody QuestionnaireAnalysisRequestBean bean,
                                                         HttpServletRequest request, HttpServletResponse response) {
        logger.info("{}-接口的请求参数【{}】",request.getServletPath(), JSON.toJSONString(bean));
        DefaultResponseBean<List<QuestionnaireResponseBean>> responseBean = new DefaultResponseBean();
        if(!this._checkoutDate(bean)){
            responseBean.setCode(500);
            responseBean.setMessage("时间选择错误");
            return responseBean;
        }
        DrugUser user = super.getLoginUser(request);
        if(user==null){
            responseBean.setCode(300);
            responseBean.setMessage("登录失效");
            return responseBean;
        }
        bean.setDrugUserId(user.getId());
        bean.setLeaderPath(user.getLeaderPath());
        responseBean.setData(questionnaireAnalysisService.list(bean));
        return responseBean;
    }

    @ApiOperation(value = "问卷统计详情接口", notes = "图表问卷统计详情接口")
    @PostMapping("/details/options")
    public DefaultResponseBean<List<QuestionStatResponseBean>> details(@RequestBody QuestionnaireAnalysisRequestBean bean,
                                                                       HttpServletRequest request, HttpServletResponse response) {
        logger.info("{}-接口的请求参数【{}】",request.getServletPath(), JSON.toJSONString(bean));
        DefaultResponseBean<List<QuestionStatResponseBean>> responseBean = new DefaultResponseBean();
        if(!this._checkoutDate(bean)){
            responseBean.setCode(500);
            responseBean.setMessage("时间选择错误");
            return responseBean;
        }
        DrugUser user = super.getLoginUser(request);
        if(user==null){
            responseBean.setCode(300);
            responseBean.setMessage("登录失效");
            return responseBean;
        }
        bean.setDrugUserId(user.getId());
        bean.setLeaderPath(user.getLeaderPath());
        responseBean.setData(questionnaireAnalysisService.details(bean));
        return responseBean;
    }

    /**
     * 检查传入进来的时间是否符合时间格式
     *
     * @return
     */
    private boolean _checkoutDate(QuestionnaireAnalysisRequestBean bean) {
        String startDate = bean.getStartDate();
        String endDate = bean.getEndDate();
        if (!StringUtils.isNotEmtity(startDate)) {
            startDate = "2017-10-01";
            return false;
        }
        if (!StringUtils.isNotEmtity(endDate)) {
            endDate = DateUtil.gettDateStrFromSpecialDate(new Date(), DateUtil.DATE_FORMAT_YMD);
            return false;
        }

        startDate = startDate + " 00:00:00";
        endDate = endDate + " 23:59:59";

        Date start = DateUtil.getDateFromStr(startDate);
        Date end = DateUtil.getDateFromStr(endDate);
        if (start != null && end != null) {
            if (start.getTime() < end.getTime()) {
                return true;
            }
            return false;
        } else {
            return false;
        }
    }

}
