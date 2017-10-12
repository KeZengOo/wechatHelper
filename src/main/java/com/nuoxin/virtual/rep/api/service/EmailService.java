package com.nuoxin.virtual.rep.api.service;

import com.nuoxin.virtual.rep.api.common.util.ValidationCode;
import com.nuoxin.virtual.rep.api.common.util.mem.SessionMemUtils;
import com.nuoxin.virtual.rep.api.entity.DrugUser;
import com.nuoxin.virtual.rep.api.utils.DateUtil;
import com.nuoxin.virtual.rep.api.web.controller.request.EmailRequestBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;


/**
 * Created by fenggang on 9/22/17.
 */
@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String Sender; //读取配置文件中的参数

    @Autowired
    private SessionMemUtils memUtils;
    @Autowired
    private DoctorService doctorService;

    private Logger logger = LoggerFactory.getLogger(getClass());

    //发送邮箱验证码
    public String sendEmailCode(DrugUser drugUser) throws MessagingException {
        String msg = "<html><body><h3><b>尊敬的客户${userName}，您好！</b></h3><div>您在${time}提交了找回密码申请操作,你的验证码为${code}，为了安全请您尽快修改密码</div></body></html>";
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
        messageHelper.setFrom(Sender);
        messageHelper.setTo(drugUser.getEmail()); //自己给自己发送邮件
        messageHelper.setSubject("找回密码");
        String code = ValidationCode.getCode();
//        message.setText("测试邮件内容");
        msg = msg.replace("${userName}",drugUser.getName())
                .replace("${code}",code)
                .replace("${time}", DateUtil.getDateTimeString(new Date()));
        messageHelper.setText( msg,true);
        String uuid = UUID.randomUUID().toString();
        memUtils.set(drugUser.getEmail(),30*60*1000,code);
        memUtils.set(uuid,30*60*1000,drugUser.getEmail());
        mailSender.send(mimeMessage);
        logger.info("retrieve.password.send.message【{}】",msg);
        return uuid;
    }

    public boolean commonEmailSend(EmailRequestBean bean){
        return true;
    }

}
