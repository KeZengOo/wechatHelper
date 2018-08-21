package com.nuoxin.virtual.rep.api.web.controller.response.call;

import lombok.Data;

import java.io.Serializable;

/**
 * 荣联七陌查询通话记录接口返回接口
 * @author tiancun
 * @date 2018-08-20
 */
@Data
public class Call7mmorResponseBean implements Serializable {
    private static final long serialVersionUID = 5582174754193733665L;

    /**
     * 本条通话记录的唯一ID
     */
    private String _id;

    /**
     * 本条通话记录的唯一ID（与_id值一致）
     */
    private String CALL_SHEET_ID;

    /**
     * 主叫号码
     */
    private String CALL_NO;

    /**
     * 被叫号码
     */
    private String CALLED_NO;


    /**
     * 结束时间
     */
    private String END_TIME;


    /**
     * 呼叫类型，值为 normal（普通来电）、dialout（外呼去 电）、transfer（来电转接）、dialTransfer（外呼转接）
     */
    private String CONNECT_TYPE;

    /**
     * 处理状态，值为dealing（已接听）、notDeal（振铃未接 听）、queueLeak（排队放弃）、voicemail（已留言）、 leak（IVR放弃） 、blackList（黑名单）
     */
    private String STATUS;


    /**
     * 处理座席工号
     */
    private String EXTEN;

    /**
     * 处理座席ID（历史原因创建的字段，如无用处可无视）
     */
    private String DISPOSAL_AGENT;


    /**
     * 通话开始时间（只有已接听状态的才有值）
     */
    private String BEGIN_TIME;


    /**
     * 呼叫发起时间
     */
    private String OFFERING_TIME;

    /**
     * 录音文件名
     */
    private String RECORD_FILE_NAME;


    /**
     * 定位客户名称
     */
    private String CUSTOMER_NAME;

    /**
     * 转接类型通话，此字段记录之前一通通话记录的ID
     */
    private String REF_CALL_SHEET_ID;

    /**
     * 通话产生所在PBX的ID
     */
    private String PBX;

    /**
     * 技能组名称
     */
    private String QUEUE_NAME;

    /**
     * 录音服务器地址
     */
    private String FILE_SERVER;

    /**
     * 省
     */
    private String PROVINCE;

    /**
     * 市
     */
    private String DISTRICT;

    /**
     * 城市区号
     */
    private String DISTRICT_CODE;

    /**
     * 是否标记
     */
    private String KEY_TAG;

    /**
     * 通话时长（未接通为0）
     */
    private String CALL_TIME_LENGTH;

    /**
     * 满意度调查
     */
    private String INVESTIGATE;

    /**
     * 该值为调用外呼接口时，传的ActionID
     */
    private String ACTION_ID;

}
