package com.nuoxin.virtual.rep.api.web.controller.v2_5.wechat;

import com.nuoxin.virtual.rep.api.common.bean.DefaultResponseBean;
import com.nuoxin.virtual.rep.api.common.enums.ErrorEnum;
import com.nuoxin.virtual.rep.api.common.exception.FileFormatException;
import com.nuoxin.virtual.rep.api.service.v2_5.ActivityWechatShareService;
import com.nuoxin.virtual.rep.api.utils.StringUtil;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.share.WechatShareContentRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.wechat.WechatAndroidMessageRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.wechat.ActivityWechatShareRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.content.ContentResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.v2_5.NewBaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author tiancun
 * @date 2019-01-02
 */
@RestController
@Api(value = "V2.5手动记录分享记录")
@RequestMapping(value = "/wechat/share")
public class ActivityWechatShareController extends NewBaseController {

    @Resource
    private ActivityWechatShareService activityWechatShareService;


    @ApiOperation(value = "批量新增", notes = "批量新增")
    @PostMapping("/batch/add")
    @ResponseBody
    public DefaultResponseBean<Boolean> batchInsert(HttpServletRequest request, @RequestBody WechatShareContentRequestBean bean){

        activityWechatShareService.batchInsert(bean);

        DefaultResponseBean<Boolean> responseBean = new DefaultResponseBean<Boolean>();
        responseBean.setData(true);
        return responseBean;
    }



    @ApiOperation(value = "内容列表", notes = "内容列表")
    @PostMapping("/content/list")
    @ResponseBody
    public DefaultResponseBean<List<ContentResponseBean>> getProductContentList(){

        List<ContentResponseBean> productContentList = activityWechatShareService.getProductContentList();
        DefaultResponseBean<List<ContentResponseBean>> responseBean = new DefaultResponseBean<>();
        responseBean.setData(productContentList);
        return responseBean;
    }


}
