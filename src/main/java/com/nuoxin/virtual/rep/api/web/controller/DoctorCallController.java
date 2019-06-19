package com.nuoxin.virtual.rep.api.web.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.nuoxin.virtual.rep.api.utils.StringUtil;
import com.nuoxin.virtual.rep.api.web.controller.response.call.CallDoctorResponseBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.nuoxin.virtual.rep.api.common.bean.DefaultResponseBean;
import com.nuoxin.virtual.rep.api.common.bean.PageResponseBean;
import com.nuoxin.virtual.rep.api.common.bean.ResponseObj;
import com.nuoxin.virtual.rep.api.common.controller.BaseController;
import com.nuoxin.virtual.rep.api.common.util.StringUtils;
import com.nuoxin.virtual.rep.api.entity.DoctorCallInfo;
import com.nuoxin.virtual.rep.api.entity.DrugUser;
import com.nuoxin.virtual.rep.api.service.DoctorCallService;
import com.nuoxin.virtual.rep.api.service.FollowUpTypeService;
import com.nuoxin.virtual.rep.api.service.QuestionService;
import com.nuoxin.virtual.rep.api.web.controller.request.CallbackRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.QueryRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.call.CallHistoryRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.call.CallInfoRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.call.CallRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.question.QuestionQueryRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.question.QuestionnaireRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.DrugUserCallDetaiBean;
import com.nuoxin.virtual.rep.api.web.controller.response.FollowUpTypResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.LoginResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.call.CallHistoryResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.call.CallResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.call.CallStatResponseBean;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Created by fenggang on 9/11/17.
 */
@Api(value = "电话", description = "电话页面接口")
@RestController
@RequestMapping(value = "/call")
public class DoctorCallController extends BaseController {

    @Autowired
    private DoctorCallService doctorCallService;
    @Autowired
    private FollowUpTypeService followUpTypeService;
    @Autowired
    private QuestionService questionService;

    /**
     * 因为换了供应商,因此标记为废弃
     * marked by Kaiyu Xie
     * 打电话获取录音文件回调接口,现在荣联那边每5分钟调用一次
     * @param request
     * @param response
     * @return
     */
    @Deprecated
    @RequestMapping("/callback")
    public DefaultResponseBean<Object> callback(HttpServletRequest request, HttpServletResponse response) {
        DefaultResponseBean<Object> responseBean = new DefaultResponseBean<>();
        response.setContentType("text/html");
        response.setCharacterEncoding("gb2312");
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();
        CallbackRequestBean bean = null;
        try {
            request.getInputStream();
            String line = "";
            br = new BufferedReader(new InputStreamReader(
                    request.getInputStream()));
            while ((line = br.readLine()) != null) {
                sb.append(line);
                logger.info("回调数据：【{}】", line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(br!=null){
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        if (sb != null && sb.toString().trim().length() > 0) {
            try {
                bean = JSON.parseObject(sb.toString(), CallbackRequestBean.class);
            } catch (Exception e) {
                e.printStackTrace();
                logger.debug("获转码回调返回数据失败");
                responseBean.setCode(500);
                responseBean.setData(bean);
                responseBean.setMessage("获转码回调返回数据失败");
                return responseBean;
            }
        }
        responseBean.setData(bean);
        doctorCallService.callback(bean);
        return responseBean;
    }

    @ApiOperation(value = "获取销售信息", notes = "获取销售信息")
    @GetMapping("/druguser/info")
    public DefaultResponseBean<LoginResponseBean> druguser(HttpServletRequest request, HttpServletResponse response) {
        DefaultResponseBean<LoginResponseBean> responseBean = new DefaultResponseBean();
        DrugUser drugUser = this.getLoginUser(request);
        if (drugUser == null) {
            responseBean.setCode(300);
            responseBean.setMessage("登录失效");
            return responseBean;
        }
        LoginResponseBean result = new LoginResponseBean();
        result.setName(drugUser.getName());
        result.setEmail(drugUser.getEmail());
        result.setCallBean(JSON.parseObject(drugUser.getCallInfo(), DrugUserCallDetaiBean.class));
        responseBean.setData(result);
        return responseBean;
    }

    @ApiOperation(value = "获取销售名下医生的产品及基本信息", notes = "获取销售名下医生的产品及基本信息")
    @PostMapping("/druguser/doctor/info")
    public DefaultResponseBean<CallDoctorResponseBean> drugUserDoctorInfo(HttpServletRequest request,@RequestBody Long doctorId) {
        DefaultResponseBean<CallDoctorResponseBean> responseBean = new DefaultResponseBean();
        DrugUser drugUser = this.getLoginUser(request);
        if (drugUser == null) {
            responseBean.setCode(300);
            responseBean.setMessage("登录失效");
            return responseBean;
        }
        CallDoctorResponseBean bean=doctorCallService.doctorProductInfo(drugUser.getId(),doctorId);
        responseBean.setData(bean);
        return responseBean;
    }


    @ApiOperation(value = "客户电话搜索列表", notes = "客户电话搜索列表")
    @PostMapping("/doctor/page")
    public DefaultResponseBean<PageResponseBean<CallResponseBean>> doctorPage(@RequestBody QueryRequestBean bean,
                                                                              HttpServletRequest request, HttpServletResponse response) {
        DefaultResponseBean responseBean = new DefaultResponseBean();
        DrugUser user = super.getLoginUser(request);
        if (user == null) {
            responseBean.setCode(300);
            responseBean.setMessage("登录失效");
            return responseBean;
        }
        bean.setDrugUserId(user.getId());
        bean.setLeaderPath(user.getLeaderPath());
        responseBean.setData(doctorCallService.doctorPage(bean));
        return responseBean;
    }

    @ApiOperation(value = "客户电话历史记录", notes = "客户电话历史记录")
    @PostMapping("/doctor/history/page")
    public DefaultResponseBean<PageResponseBean<CallHistoryResponseBean>> doctorHistoryPage(@RequestBody CallHistoryRequestBean bean,
                                                                                            HttpServletRequest request, HttpServletResponse response) {
        DefaultResponseBean responseBean = new DefaultResponseBean();
        DrugUser user = super.getLoginUser(request);
        if (user == null) {
            responseBean.setCode(300);
            responseBean.setMessage("登录失效");
            return responseBean;
        }
        if (bean.getDoctorId() != null && bean.getDoctorId() == 0) {
            return responseBean;
        }
        bean.setDrugUserId(user.getId());
        bean.setLeaderPath(user.getLeaderPath());
        responseBean.setData(doctorCallService.doctorHistoryPage(bean));
        return responseBean;
    }

    @ApiOperation(value = "电话顶部统计", notes = "电话顶部统计")
    @PostMapping("/stat")
    public DefaultResponseBean<CallStatResponseBean> stat(HttpServletRequest request, HttpServletResponse response) {
        DefaultResponseBean responseBean = new DefaultResponseBean();
        responseBean.setData(doctorCallService.stat(super.getLoginId(request)));
        return responseBean;
    }

    @ApiOperation(value = "拨号保存电话记录", notes = "拨号保存电话记录")
    @PostMapping("/save")
	public ResponseObj save(@RequestBody CallRequestBean bean, HttpServletRequest request, HttpServletResponse response) {
        long startTime = System.currentTimeMillis();
        logger.info("请求路径：{}", request.getServletPath());
        logger.info("请求参数：{}", JSON.toJSONString(bean));
        ResponseObj responseBean = new ResponseObj();
        DrugUser user = super.getLoginUser(request);
        if (user == null) {
            responseBean.setCode(300);
            responseBean.setMessage("登录失效");
            return responseBean;
        }

        // 1是电话
        Integer visitChannel = bean.getVisitChannel();
        if (visitChannel == null || visitChannel == 0){
            visitChannel = 1;
        }
        if(visitChannel !=null &&  visitChannel == 1 && StringUtil.isEmpty(bean.getSinToken())){
            responseBean.setCode(500);
            responseBean.setMessage("sinToken不能为空");
            return responseBean;
        }
        
        bean.setDrugUserId(user.getId());
        super.convertCallStatus(bean);
        
        synchronized (this){
        	DoctorCallInfo info = doctorCallService.checkoutSinToken(bean.getSinToken());
        	if(info!=null){
        		responseBean.setData(info);
        		return responseBean;
        	}

            try {
                responseBean.setData(doctorCallService.save(bean));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            long endTime = System.currentTimeMillis();
            logger.info("返回数据：{}", JSONObject.toJSONString(responseBean));
            logger.info("spend time {}ms", (endTime - startTime));

            return responseBean;
        }
    }

    @ApiOperation(value = "拨号电话记录修改", notes = "拨号电话记录修改")
    @PostMapping("/update")
	public ResponseObj update(@RequestBody CallRequestBean bean, HttpServletRequest request, HttpServletResponse response) {
        logger.info("{}接口请求数据{}", request.getServletPath(), JSON.toJSONString(bean));
        ResponseObj responseBean = new ResponseObj();
        
        Long id = bean.getId();
        if (id == null) {
            responseBean.setCode(500);
            responseBean.setMessage("请求参数id不能为空");
            return responseBean;
        }
        
        DrugUser user = super.getLoginUser(request);
        if (user == null) {
            responseBean.setCode(300);
            responseBean.setMessage("登录失效");
            return responseBean;
        }
        
        super.convertCallStatus(bean);
        bean.setDrugUserId(user.getId());
        
        bean = doctorCallService.update(bean);
        if (bean.getId() == null) {
            responseBean.setCode(500);
            responseBean.setMessage("状态更新失败");
            return responseBean;
        }
        responseBean.setData(bean);

        return responseBean;
    }

    @ApiOperation(value = "挂断保存电话记录", notes = "挂断保存电话记录")
    @PostMapping("/stop/update")
    public DefaultResponseBean<Boolean> stopSave(@RequestBody CallInfoRequestBean bean,
                                                 HttpServletRequest request, HttpServletResponse response) {

        logger.info("{}接口请求数据【】{}", request.getServletPath(), JSON.toJSONString(bean));
        DefaultResponseBean responseBean = new DefaultResponseBean();
        if (bean.getId() == null) {
            responseBean.setCode(500);
            responseBean.setMessage("请求参数id不能为空");
            return responseBean;
        }
        DrugUser user = super.getLoginUser(request);
        if (user == null) {
            responseBean.setCode(300);
            responseBean.setMessage("登录失效");
            return responseBean;
        }
        bean.setDrugUserId(user.getId());
        responseBean.setData(doctorCallService.stopUpdate(bean));
        return responseBean;
    }

    @ApiOperation(value = "获取跟进类型集合", notes = "获取跟进类型集合")
    @GetMapping("/type/list")
    public DefaultResponseBean<List<FollowUpTypResponseBean>> typeList(HttpServletRequest request, HttpServletResponse response) {
        DefaultResponseBean responseBean = new DefaultResponseBean();
        DrugUser user = this.getLoginUser(request);
        if (user == null) {
            responseBean.setCode(300);
            responseBean.setMessage("登录失效");
            return responseBean;
        }
        responseBean.setData(followUpTypeService.list(user.getLeaderPath()));
        return responseBean;
    }

    /**
     *  根据前端反馈,没有被用到因此标记为废弃
     *  marked by Kaiyu Xie
     */
    @Deprecated
    @ApiOperation(value = "uploadUrl", notes = "")
    @GetMapping("/uploadUrl")
    public DefaultResponseBean<String> uploadUrl(HttpServletRequest request, HttpServletResponse response){
        DefaultResponseBean responseBean = new DefaultResponseBean();
        try{
            String url = doctorCallService.uploadUrl();
            responseBean.setData(url);
        }catch (Exception e){
            e.printStackTrace();
        }

        return responseBean;
    }

    @PostMapping("/question/page")
    @ApiOperation(value = "问卷列表", notes = "问卷列表")
    public DefaultResponseBean<PageResponseBean<QuestionnaireRequestBean>> page(@RequestBody QuestionQueryRequestBean bean,
                                                                                HttpServletRequest request, HttpServletResponse response){
        DefaultResponseBean responseBean = new DefaultResponseBean();
        DrugUser user = this.getLoginUser(request);
        if (user == null) {
            responseBean.setCode(300);
            responseBean.setMessage("登录失效");
            return responseBean;
        }
        if (StringUtils.isBlank(bean.getMobile())) {
            responseBean.setCode(300);
            responseBean.setMessage("电话号码不能为空");
            return responseBean;
        }
        responseBean.setData(questionService.pageAnswer(bean,user));
        return responseBean;
    }

}
