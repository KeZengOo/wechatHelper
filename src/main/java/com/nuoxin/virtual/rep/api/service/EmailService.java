package com.nuoxin.virtual.rep.api.service;

import com.nuoxin.virtual.rep.api.common.util.mem.SessionMemUtils;
import com.nuoxin.virtual.rep.api.entity.DrugUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

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
    SessionMemUtils memUtils;

    private Logger logger = LoggerFactory.getLogger(getClass());

    //发送邮箱验证码
    public void sendEmailCode(DrugUser drugUser){
        String msg = "<html><body><h3><b>尊敬的客户${userName}，您好！</b></h3><div>您在${time}提交了找回密码申请操作,你的验证码为${code}，为了安全请您尽快修改密码</div></body></html>";
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(Sender);
        message.setTo("gang.feng@naxions.com"); //自己给自己发送邮件
        message.setSubject("找回密码");
//        message.setText("测试邮件内容");
        message.setText( msg.replace("${userName}","")
                .replace("${code}","")
                .replace("${time}",""));
        mailSender.send(message);
        logger.info("retrieve.password.send.message【{}】",message.getText());

    }

    private String getEmailPwdSendMsg(){
        StringBuffer sb = new StringBuffer("");
        BufferedReader reader = null;
        try {
            File file = ResourceUtils.getFile("classpath:1pwd_email.vm");
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 1;
            while ((tempString = reader.readLine()) != null) {
                sb.append(tempString);
                line++;
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        return sb.toString();
    }

}
