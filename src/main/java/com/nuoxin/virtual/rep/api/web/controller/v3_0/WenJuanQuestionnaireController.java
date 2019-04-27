package com.nuoxin.virtual.rep.api.web.controller.v3_0;

import com.nuoxin.virtual.rep.api.common.constant.TimeCronConstant;
import com.nuoxin.virtual.rep.api.common.constant.WenJuanApiConstant;
import com.nuoxin.virtual.rep.api.entity.v3_0.ScheduleResult;
import com.nuoxin.virtual.rep.api.service.v3_0.WenJuanQuestionnaireService;
import com.nuoxin.virtual.rep.api.utils.MD5Util;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.scheduling.annotation.Scheduled;
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

    @ApiOperation(value = "问卷网登录接口")
    @RequestMapping(value = "/wenJuanLogin", method = { RequestMethod.GET })
    public String wenJuanLogin(){
//        //当前时间戳
//        long timestamp = System.currentTimeMillis()/1000;
//        //问卷网登录接口
//        //生产MD5_signature签名
//        String md5Signature = WenJuanApiConstant.WJ_APPKEY_VALUE
//                +timestamp+WenJuanApiConstant.WJ_USER_VALUE
//                +WenJuanApiConstant.WJ_APPSECRET_VALUE;
//        String md5Result = MD5Util.MD5Encode(md5Signature,"utf8");
//
//        String url =WenJuanApiConstant.URL+WenJuanApiConstant.LOGIN+"?"+WenJuanApiConstant.WJ_APPKEY+"="+WenJuanApiConstant.WJ_APPKEY_VALUE
//                +"&"+WenJuanApiConstant.WJ_USER+"="+WenJuanApiConstant.WJ_USER_VALUE
//                +"&"+WenJuanApiConstant.WJ_TIMESTAMP+"="+timestamp
//                +"&"+WenJuanApiConstant.WJ_SIGNATURE+"="+md5Result;
//        String result = restTemplate.getForObject(url,String.class);

        return "";
    }

    @ApiOperation(value = "问卷网项目接口")
    @RequestMapping(value = "/wenJuanProjectApi", method = { RequestMethod.GET })
    public ScheduleResult wenJuanProjectApi(){
//        //当前时间戳
//        long timestamp = System.currentTimeMillis()/1000;
//
//        //问卷网项目接口
//        String projectMd5Signature = WenJuanApiConstant.WJ_APPKEY_VALUE
//                +WenJuanApiConstant.WJ_DATATYPE_JSON
//                +timestamp
//                +WenJuanApiConstant.WJ_APPSECRET_VALUE;
//        String projectMd5SignatureResult = MD5Util.MD5Encode(projectMd5Signature,"utf8");
//
//        String projectUrl = WenJuanApiConstant.URL+WenJuanApiConstant.GET_PROJ_LIST+"?"+WenJuanApiConstant.WJ_APPKEY+"="+WenJuanApiConstant.WJ_APPKEY_VALUE
//                +"&"+WenJuanApiConstant.WJ_DATATYPE+"="+WenJuanApiConstant.WJ_DATATYPE_JSON
//                +"&"+WenJuanApiConstant.WJ_TIMESTAMP+"="+timestamp+"&"+WenJuanApiConstant.WJ_SIGNATURE+"="+projectMd5SignatureResult;
//
//        String projectResult = restTemplate.getForObject(projectUrl,String.class);

        ScheduleResult scheduleResult = wenJuanQuestionnaireService.saveWenJuanProject();

        return scheduleResult;
    }



}
