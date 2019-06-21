package com.nuoxin.virtual.rep.api.web.schedule;

import com.nuoxin.virtual.rep.api.service.CallBackService;
import com.nuoxin.virtual.rep.api.web.controller.request.call.Call7mmorRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.call.IdentifyCallUrlRequestBean;
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
    @Scheduled(cron = "0 0 0 * * ?") // 每天晚上12点执行
    public void repeatSaveOrUpdateCall() {
        logger.info("CallInfoSchedule repeatSaveOrUpdateCall start....");
        long starTime = System.currentTimeMillis();
        Call7mmorRequestBean bean = new Call7mmorRequestBean();
        callBackService.repeatSaveOrUpdateCall(bean);
        long endTime = System.currentTimeMillis();
        logger.info("CallInfoSchedule repeatSaveOrUpdateCall end , cost {}s", (endTime-starTime)/1000);
    }



    @ApiOperation(value = "录音识别", notes = "录音识别")
    @PostMapping(value = "/url/identify")
    @Scheduled(cron = "0 0 23 * * ?") // 每天23点执行
    public void identifyCallUrl() {
        logger.info("CallInfoSchedule identifyCallUrl start....");
        long starTime = System.currentTimeMillis();

        IdentifyCallUrlRequestBean bean  = new IdentifyCallUrlRequestBean();
        // 限制处理300
        bean.setLimitNum(1000);
        callBackService.identifyCallUrl(bean);
        long endTime = System.currentTimeMillis();
        logger.info("CallInfoSchedule identifyCallUrl end , cost {}s", (endTime-starTime)/1000);

    }






    @ApiOperation(value = "问卷答案中更新医生的手机号", notes = "录音识别")
    @PostMapping(value = "/wj/telephone/update")
    @Scheduled(cron = "0 0 22 * * ?") // 每天22点执行
    public void updateWjTelephone() {
        logger.info("CallInfoSchedule updateWjTelephone start....");
        long starTime = System.currentTimeMillis();



        long endTime = System.currentTimeMillis();
        logger.info("CallInfoSchedule updateWjTelephone end , cost {}s", (endTime-starTime)/1000);

    }




}
