package com.nuoxin.virtual.rep.api.web.controller.v2_5.schedule;

import com.nuoxin.virtual.rep.api.common.bean.DefaultResponseBean;
import com.nuoxin.virtual.rep.api.common.bean.PageResponseBean;
import com.nuoxin.virtual.rep.api.entity.DrugUser;
import com.nuoxin.virtual.rep.api.service.CallBackService;
import com.nuoxin.virtual.rep.api.utils.DateUtil;
import com.nuoxin.virtual.rep.api.utils.StringUtil;
import com.nuoxin.virtual.rep.api.web.controller.request.call.Call7mmorRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.share.ShareRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.ContentShareResponseBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.Date;

/**
 * V2.5电话记录补偿
 * @author tiancun
 * @date 2018-09-30
 */
@RestController
@Api(value = "V2.5电话记录补偿")
@RequestMapping(value = "/schedule/call/info")
public class CallInfoSchedule {

    @Resource
    private CallBackService callBackService;

    @ApiOperation(value = "没有回调的电话记录重试", notes = "没有回调的电话记录重试")
    @PostMapping(value = "/retry")
    @Scheduled(cron = "0 0 23 * * ?")
    public DefaultResponseBean<String> repeatSaveOrUpdateCall(HttpServletRequest request, @RequestBody Call7mmorRequestBean bean) {

        callBackService.repeatSaveOrUpdateCall(bean);
        DefaultResponseBean defaultResponseBean = new DefaultResponseBean();
        defaultResponseBean.setData("success");
        return defaultResponseBean;
    }

}
