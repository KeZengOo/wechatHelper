package com.nuoxin.virtual.rep.api.service;

import com.alibaba.fastjson.JSON;
import com.nuoxin.virtual.rep.api.common.bean.PageResponseBean;
import com.nuoxin.virtual.rep.api.common.util.StringUtils;
import com.nuoxin.virtual.rep.api.common.util.ValidationCode;
import com.nuoxin.virtual.rep.api.common.util.mem.SessionMemUtils;
import com.nuoxin.virtual.rep.api.dao.EmailRepository;
import com.nuoxin.virtual.rep.api.entity.Doctor;
import com.nuoxin.virtual.rep.api.entity.DrugUser;
import com.nuoxin.virtual.rep.api.entity.Email;
import com.nuoxin.virtual.rep.api.mybatis.EmailMapper;
import com.nuoxin.virtual.rep.api.utils.DateUtil;
import com.nuoxin.virtual.rep.api.web.controller.request.EmailQueryRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.EmailRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.EmailResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.call.CallHistoryResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.message.MessageResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.vo.Doc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestBody;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;


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
    @Autowired
    private EmailRepository emailRepository;
    @Autowired
    private EmailMapper emailMapper;
    @Autowired
    private DrugUserService drugUserService;

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

    @Transactional(readOnly = false)
    public boolean commonEmailSendIds(EmailRequestBean bean) throws MessagingException {
        String doctorIds = bean.getDoctorIds();
        List<Long> ids = new ArrayList<>();
        String[] doctorid = doctorIds.split(",");
        for (String id:doctorid) {
            if(StringUtils.isNotEmtity(id))
                ids.add(Long.valueOf(id));
        }
        List<Doctor> doctorList = doctorService.findByIdIn(ids);
        if(doctorList!=null && !doctorList.isEmpty()){
            for (int i = 0,leng=doctorList.size(); i < leng; i++) {
                Doctor doctor = doctorList.get(i);
                MimeMessage mimeMessage = this._getMimeMessage(bean,doctor.getEmail());
                mailSender.send(mimeMessage);
                //logger.info("common.id.send.message【{}】", JSON.toJSONString(mimeMessage));

                //保存邮件发送记录
                Email entity = new Email();
                entity.setContent(bean.getContent());
                entity.setCreateTime(new Date());
                entity.setDoctorId(doctor.getId());
                entity.setDrugUserId(bean.getDrugUserId());
                entity.setProductId(bean.getProductId());
                entity.setTitle(bean.getTitle());
                entity.setType(2);
                emailRepository.save(entity);
            }
        }
        return true;
    }

    @Transactional(readOnly = false)
    public boolean commonEmailSendTo(EmailRequestBean bean) throws MessagingException {
        String email = bean.getEmails();
        List<String> emails = new ArrayList<>();
        String[] doctorid = email.split(",");
        for (String to:doctorid) {
            if(StringUtils.isNotEmtity(to))
                emails.add(to);
        }
        List<Doctor> doctorList = doctorService.findByEmailIn(emails);
        if(emails!=null && !emails.isEmpty()){
            for (int i = 0,leng=emails.size(); i < leng; i++) {
                String toEmail = emails.get(i);
                Doctor doctor = this._getDoctorToEmail(doctorList,toEmail);
                MimeMessage mimeMessage = this._getMimeMessage(bean,toEmail);
                mailSender.send(mimeMessage);
                logger.info("common.email.send.message【{}】", JSON.toJSONString(mimeMessage));

                //保存邮件发送记录
                Email entity = new Email();
                entity.setContent(bean.getContent());
                entity.setCreateTime(new Date());
                if(doctor!=null){
                    entity.setDoctorId(doctor.getId());
                }
                entity.setDrugUserId(bean.getDrugUserId());
                entity.setProductId(bean.getProductId());
                entity.setTitle(bean.getTitle());
                entity.setType(2);
                emailRepository.save(entity);
            }
        }
        return true;
    }

    public PageResponseBean<MessageResponseBean> page(EmailQueryRequestBean bean){
        bean.setCurrentSize(bean.getPageSize()*bean.getPage());
        List<MessageResponseBean> result = new ArrayList<>();
        List<EmailResponseBean> list = emailMapper.historyPage(bean);
        if(list!=null && !list.isEmpty()){
            for (EmailResponseBean e : list) {
                MessageResponseBean mbean = new MessageResponseBean();
                mbean.setId(e.getId());
                mbean.setMessage(e.getContent());
                mbean.setUserType(2);
                mbean.setMessageType(3);
                mbean.setTitle(e.getTitle());
                mbean.setMessageTime(e.getDate());
                mbean.setNickname(e.getDrugUserName());
                result.add(mbean);
            }
        }
        Integer count = emailMapper.historyPageCount(bean);
        PageResponseBean<MessageResponseBean> responseBean = new PageResponseBean<>(bean,count,result);
        return responseBean;
    }

    private Doctor _getDoctorToEmail(List<Doctor> doctorList,String email){
        if(doctorList!=null && !doctorList.isEmpty()){
            for (Doctor d : doctorList) {
                if(StringUtils.isNotEmtity(d.getEmail()) && email.equals(d.getEmail())){
                    return d;
                }
            }
        }
        return null;
    }

    private MimeMessage _getMimeMessage(EmailRequestBean bean,String email) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
        messageHelper.setFrom(Sender);
        messageHelper.setTo(email); //自己给自己发送邮件
        messageHelper.setSubject(bean.getTitle());
        messageHelper.setText( bean.getContent(),true);
        return mimeMessage;
    }

}
