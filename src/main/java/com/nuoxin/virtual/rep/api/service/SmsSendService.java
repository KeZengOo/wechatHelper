package com.nuoxin.virtual.rep.api.service;

import com.alibaba.fastjson.JSON;
import com.aliyun.mns.client.CloudAccount;
import com.aliyun.mns.client.CloudTopic;
import com.aliyun.mns.client.MNSClient;
import com.aliyun.mns.common.ServiceException;
import com.aliyun.mns.model.BatchSmsAttributes;
import com.aliyun.mns.model.MessageAttributes;
import com.aliyun.mns.model.RawTopicMessage;
import com.aliyun.mns.model.TopicMessage;
import com.nuoxin.virtual.rep.api.common.bean.SmsMassageBean;
import com.nuoxin.virtual.rep.api.config.AliyunConfig;
import com.nuoxin.virtual.rep.api.entity.Doctor;
import com.nuoxin.virtual.rep.api.entity.DrugUser;
import com.nuoxin.virtual.rep.api.entity.SmsTemplate;
import com.nuoxin.virtual.rep.api.web.controller.request.SmsSendRequestBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by fenggang on 9/18/17.
 */
@Service
public class SmsSendService {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private CloudAccount account;
    @Autowired
    private AliyunConfig aliyunConfig;
    @Autowired
    private DrugUserService drugUserService;
    @Autowired
    private DoctorService doctorService;
    @Autowired
    private SmsTemplateService smsTemplateService;

    /**
     * 发送短信
     * @param bean
     * @throws ServiceException
     * @throws Exception
     */
    private void sendSms(SmsMassageBean bean) throws ServiceException,Exception{
        /**
         * Step 1. 获取主题引用
         */
        MNSClient client = account.getMNSClient();
        CloudTopic topic = client.getTopicRef(bean.getTopic());

        /**
         * Step 2. 设置SMS消息体（必须）
         *
         * 注：目前暂时不支持消息内容为空，需要指定消息内容，不为空即可。
         */
        RawTopicMessage msg = new RawTopicMessage();
        msg.setMessageBody(bean.getMessage());
        /**
         * Step 3. 生成SMS消息属性
         */
        MessageAttributes messageAttributes = new MessageAttributes();
        BatchSmsAttributes batchSmsAttributes = new BatchSmsAttributes();
        // 3.1 设置发送短信的签名（SMSSignName）
        batchSmsAttributes.setFreeSignName(bean.getSignName());
        // 3.2 设置发送短信使用的模板（SMSTempateCode）
        batchSmsAttributes.setTemplateCode(bean.getTemplateCode());
        // 3.3 设置发送短信所使用的模板中参数对应的值（在短信模板中定义的，没有可以不用设置）
        BatchSmsAttributes.SmsReceiverParams smsReceiverParams = new BatchSmsAttributes.SmsReceiverParams();
        if(bean.getMap()!=null && !bean.getMap().isEmpty()){
            for (String key:bean.getMap().keySet()){
                smsReceiverParams.setParam(key, bean.getMap().get(key)+"");
            }
        }

        // 3.4 增加接收短信的号码
        if(bean.getMobiles()!=null && !bean.getMobiles().isEmpty()){
            for (String mobile:bean.getMobiles()) {
                batchSmsAttributes.addSmsReceiver(mobile, smsReceiverParams);
            }
            messageAttributes.setBatchSmsAttributes(batchSmsAttributes);
            try {
                /**
                 * Step 4. 发布SMS消息
                 */
                TopicMessage ret = topic.publishMessage(msg, messageAttributes);
                //System.out.println("MessageId: " + ret.getMessageId());
                logger.info("sms send messageId : {}",ret.getMessageId());
                logger.info("sms send message : {}", JSON.toJSON(ret));
            } catch (ServiceException se) {
                logger.debug("sms send error code ：{},requestId：{}",se.getErrorCode() , se.getRequestId());
                logger.debug("sms send error :",se);
                throw se;
            } catch (Exception e) {
                logger.debug("sms send error :",e);
                throw e;
            } finally {
                client.close();
            }
        }else{
            client.close();
        }

    }

    public Boolean send(SmsSendRequestBean bean) {
        SmsMassageBean smsBean = new SmsMassageBean();
        Doctor doctor = doctorService.findByMobile(bean.getMobile());
        DrugUser drugUser = drugUserService.findById(bean.getDrugUserId());
        SmsTemplate smsTemplate = smsTemplateService.fingById(bean.getTemplateId());
        try {
            this.sendSms(smsBean);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
