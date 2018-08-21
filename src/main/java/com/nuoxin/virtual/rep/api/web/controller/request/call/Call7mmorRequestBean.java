package com.nuoxin.virtual.rep.api.web.controller.request.call;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * 荣联七陌查询通话记录接口请求接口
 * @author tiancun
 * @date 2018-08-20
 */
@Data
public class Call7mmorRequestBean implements Serializable {
    private static final long serialVersionUID = 97776060650473361L;

    /**
     * 获取通话记录时间段的开始时间，时间格式为 yyyy­MM­dd HH:mm:ss
     * 必填
     */
    private String beginTime;

    /**
     * 获取通话记录时间段的结束时间，时间格式为 yyyy­MM­dd HH:mm:ss
     * 必填
     */
    private String endTime;

    /**
     * 主叫号码
     * 非必填
     */
    private String callNo;

    /**
     * 被叫号码
     * 非必填
     */
    private String calledNo;

    /**
     * 呼叫类型（值可以是normal（普通来电）、 dialout（外呼去电）、transfer（来电转接）、dialTransfer（外呼转接）
     * 非必填
     */
    private String connectType;

    /**
     * 处理状态（值可以是dealing（已接听）、 notDeal（振铃未接听）、queueLeak（排队放弃）、voicemail （已留言）、leak（IVR放弃） 、blackList（黑名单））
     * 非必填
     */
    private String status;

    /**
     * 如果webcall接口传了 cdrVar，那么这里可以传变量的名字，来获 取值。（若传了该值，则时间条件可以不传）
     * 非必填
     */
    private String cdrVar;


    /**
     * 查询参数，多个参数用","号隔开。如果仅仅希望获取某些有用参数可以用这个字段，如传参数FILE_SERVER,RECORD_FILE_NAME就查询所有通话记录的录音地址
     * 非必填
     */
    private String resultParams;


}
