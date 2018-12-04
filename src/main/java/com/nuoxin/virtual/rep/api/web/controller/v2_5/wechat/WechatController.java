package com.nuoxin.virtual.rep.api.web.controller.v2_5.wechat;

import com.nuoxin.virtual.rep.api.common.bean.DefaultResponseBean;
import com.nuoxin.virtual.rep.api.common.bean.PageResponseBean;
import com.nuoxin.virtual.rep.api.common.enums.ErrorEnum;
import com.nuoxin.virtual.rep.api.common.exception.FileFormatException;
import com.nuoxin.virtual.rep.api.entity.DrugUser;
import com.nuoxin.virtual.rep.api.service.v2_5.WechatService;
import com.nuoxin.virtual.rep.api.utils.StringUtil;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.wechat.WechatAndroidMessageRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.ContentShareResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.wechat.WechatAndroidUploadTimeResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.v2_5.NewBaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author tiancun
 * @date 2018-12-03
 */
@RestController
@Api(value = "V2.5微信聊天消息导入，目前只是基于安卓的")
@RequestMapping(value = "/android/wechat")
public class WechatController extends NewBaseController {

    @Resource
    private WechatService wechatService;


    @ApiOperation(value = "安卓手机微信联系人导入接口", notes = "安卓手机微信联系人导入接口")
    @PostMapping("/contact/import")
    @ResponseBody
    public DefaultResponseBean<Boolean> importWechatUserFile(HttpServletRequest request, MultipartFile file){

        String uploadTime = request.getParameter("uploadTime");
        if (StringUtil.isEmpty(uploadTime)){
            throw new FileFormatException(ErrorEnum.ERROR, "上传时间uploadTime不能为空");
        }


        WechatAndroidMessageRequestBean bean = new WechatAndroidMessageRequestBean();
        bean.setUploadFileTime(uploadTime);
        wechatService.handleWechatUserFile(file,bean);

        DefaultResponseBean<Boolean> responseBean = new DefaultResponseBean<>();
        responseBean.setData(true);

        return responseBean;
    }


    @ApiOperation(value = "安卓手机微信消息导入接口", notes = "安卓手机微信消息导入接口")
    @PostMapping("/message/import")
    @ResponseBody
    public DefaultResponseBean<Boolean> importWechatMessageFile(HttpServletRequest request, MultipartFile file){

        String uploadTime = request.getParameter("uploadTime");
        if (StringUtil.isEmpty(uploadTime)){
            throw new FileFormatException(ErrorEnum.ERROR, "上传时间uploadTime不能为空");
        }


        WechatAndroidMessageRequestBean bean = new WechatAndroidMessageRequestBean();
        bean.setUploadFileTime(uploadTime);
        wechatService.handleWechatMessageFile(file,bean);

        DefaultResponseBean<Boolean> responseBean = new DefaultResponseBean<>();
        responseBean.setData(true);

        return responseBean;
    }



    @ApiOperation(value = "安卓手机微信消息导入接口", notes = "安卓手机微信消息导入接口")
    @GetMapping("/upload/time/get/{wechatNumber}")
    @ResponseBody
    public DefaultResponseBean<WechatAndroidUploadTimeResponseBean> getWechatAndroidUploadTime(HttpServletRequest request,@PathVariable(value = "wechatNumber") String wechatNumber){
        WechatAndroidUploadTimeResponseBean wechatAndroidUploadTime = wechatService.getWechatAndroidUploadTime(wechatNumber);
        DefaultResponseBean<WechatAndroidUploadTimeResponseBean> responseBean = new DefaultResponseBean<>();
        responseBean.setData(wechatAndroidUploadTime);

        return responseBean;
    }


}
