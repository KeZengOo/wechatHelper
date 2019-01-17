package com.nuoxin.virtual.rep.api.mybatis;

import com.nuoxin.virtual.rep.api.entity.v2_5.StatisticsDrugNumResponse;
import com.nuoxin.virtual.rep.api.entity.v2_5.StatisticsParams;
import com.nuoxin.virtual.rep.api.web.controller.request.WorkStationRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.message.MessageRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.wechat.WechatMessageRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.message.MessageLinkmanResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.message.MessageResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.work.TodayStatisticsResponseBean;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Create by tiancun on 2017/10/12
 */
@Component
public interface MessageMapper {


    TodayStatisticsResponseBean getMessageStatistics(WorkStationRequestBean bean);



    //微信和短信的联系人
    List<MessageLinkmanResponseBean> getMessageLinkmanList(MessageRequestBean bean);

    String getLastMessage(@Param(value = "type") Integer type,@Param(value = "doctorId") Long doctorId,@Param(value = "maxMessageTime") String maxMessageTime);

    String getLastEmailMessage(@Param(value = "doctorId") Long doctorId,@Param(value = "maxMessageTime") String maxMessageTime);
    //微信和短信的联系人总数
    Integer getMessageLinkmanListCount(MessageRequestBean bean);

    //得到消息微信或者短信，短信添加产品，微信没有加
    List<MessageResponseBean> getMessageList(MessageRequestBean bean);


    //消息总数
    Integer getMessageListCount(MessageRequestBean bean);

    //得到邮件的消息
    List<MessageResponseBean> getEmailMessageList(MessageRequestBean bean);

    //得到邮件的消息总数
    Integer getEmailMessageListCount(MessageRequestBean bean);


    //今日会话数(短信和微信)
    Integer messageCount(MessageRequestBean bean);

    //今日邮件会话数
    Integer emailMessageCount(MessageRequestBean bean);

    /**
     * 医生微信拜访数
     * @param  statisticsParams
     * @return
     */
    List<StatisticsDrugNumResponse> getWeiXinDoctorVisitCount(StatisticsParams statisticsParams);

    /**
     * 医生微信信息次数
     * @param  statisticsParams
     * @return
     */
    List<StatisticsDrugNumResponse> getWeiXinMessageCount(@Param(value = "statisticsParams")StatisticsParams statisticsParams,@Param(value = "wechatMessageStatus")String wechatMessageStatus);


    /**
     * 查询消息类型，判断是否重复
     * @param messageType
     * @param wechatNumber
     * @param wechatTime
     * @return
     */
    Integer getCountByTypeAndWechatNumAndTime(@Param(value = "messageType") Integer messageType,@Param(value = "wechatNumber") String wechatNumber,@Param(value = "wechatTime") String wechatTime);


    /**
     * 批量插入微信消息
     * @param list
     */
    void batchInsertWechatMessage(List<WechatMessageRequestBean> list);

}
