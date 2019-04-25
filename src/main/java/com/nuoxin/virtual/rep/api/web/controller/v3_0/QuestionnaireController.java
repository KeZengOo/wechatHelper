package com.nuoxin.virtual.rep.api.web.controller.v3_0;

import com.nuoxin.virtual.rep.api.common.constant.WenJuanApiConstant;
import com.nuoxin.virtual.rep.api.utils.MD5Util;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 问卷网 Controller 类
 * @author wujiang
 * @date 20190425
 */
@Api(value = "V3_0 问卷网相关接口")
@RequestMapping(value = "/questionnaireApi")
@RestController
public class QuestionnaireController {

    @Resource
    private RestTemplate restTemplate;

    @ApiOperation(value = "添加单个客户医生信息")
    @RequestMapping(value = "/wenJuanLogin", method = { RequestMethod.GET })
    public String wenJuanLogin(){
        //当前时间戳
        long timestamp = System.currentTimeMillis()/1000;
        System.out.println("timestamp："+timestamp);

        //生产MD5_signature签名
        String md5Signature = WenJuanApiConstant.WJ_APPKEY_VALUE
                +timestamp+WenJuanApiConstant.WJ_USER_VALUE
                +WenJuanApiConstant.WJ_APPSECRET_VALUE;
        System.out.println("md5Signature："+md5Signature);
        String md5Result = MD5Util.MD5Encode(md5Signature,"utf8");
        System.out.println("md5Result："+md5Result);

        String url =WenJuanApiConstant.URL+WenJuanApiConstant.LOGIN+"?"+WenJuanApiConstant.WJ_APPKEY+"="+WenJuanApiConstant.WJ_APPKEY_VALUE
                +"&"+WenJuanApiConstant.WJ_USER+"="+WenJuanApiConstant.WJ_USER_VALUE
                +"&"+WenJuanApiConstant.WJ_TIMESTAMP+"="+timestamp
                +"&"+WenJuanApiConstant.WJ_SIGNATURE+"="+md5Result;
        System.out.println("url："+url);
        String result = restTemplate.getForObject(url,String.class);


        String projectMd5Signature = WenJuanApiConstant.WJ_APPKEY_VALUE
                +"json"
                +timestamp
                +WenJuanApiConstant.WJ_APPSECRET_VALUE;
        String projectMd5SignatureResult = MD5Util.MD5Encode(projectMd5Signature,"utf8");

        String projectUrl = WenJuanApiConstant.URL+WenJuanApiConstant.GET_PROJ_LIST+"?"+WenJuanApiConstant.WJ_APPKEY+"="+WenJuanApiConstant.WJ_APPKEY_VALUE
                +"&wj_datatype=json"
                +"&"+WenJuanApiConstant.WJ_TIMESTAMP+"="+timestamp+"&"+WenJuanApiConstant.WJ_SIGNATURE+"="+projectMd5SignatureResult;

        String projectResult = restTemplate.getForObject(projectUrl,String.class);
        return projectResult;
    }
}
