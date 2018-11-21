package com.nuoxin.virtual.rep.api.web.schedule;

import com.nuoxin.virtual.rep.api.service.CallBackService;
import com.nuoxin.virtual.rep.api.web.controller.request.call.Call7mmorRequestBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * V2.5电话记录补偿,自动调用
 * @author tiancun
 * @date 2018-09-30
 */
@RestController
@Api(value = "V2.5电话记录补偿自动调用")
@RequestMapping(value = "/schedule/call/info")
public class CallInfoSchedule {

    private static final Logger logger = LoggerFactory.getLogger(CallInfoSchedule.class);

    @Resource
    private CallBackService callBackService;

    @ApiOperation(value = "没有回调的电话记录重试", notes = "没有回调的电话记录重试")
    @PostMapping(value = "/retry")
    @Scheduled(cron = "0 0 */4 * * ?") // 暂定每隔4小时执行一次
    public void repeatSaveOrUpdateCall() {
        logger.info("CallInfoSchedule repeatSaveOrUpdateCall start....");
        long starTime = System.currentTimeMillis();
        Call7mmorRequestBean bean = new Call7mmorRequestBean();
        callBackService.repeatSaveOrUpdateCall(bean);
        long endTime = System.currentTimeMillis();
        logger.info("CallInfoSchedule repeatSaveOrUpdateCall end , cost {}s", (endTime-starTime)/1000);
    }

}
