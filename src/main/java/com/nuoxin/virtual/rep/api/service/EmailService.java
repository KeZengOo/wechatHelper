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
import com.nuoxin.virtual.rep.api.utils.CollectionsUtil;
import com.nuoxin.virtual.rep.api.utils.DateUtil;
import com.nuoxin.virtual.rep.api.web.controller.request.EmailQueryRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.EmailRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.EmailResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.message.MessageResponseBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import java.util.*;


/**
 * Created by fenggang on 9/22/17.
 */
@Service
public class EmailService {
	
	private static final Logger logger = LoggerFactory.getLogger(EmailService.class);
	
	@Value("${spring.mail.username}")
	private String Sender;

	@Autowired
    private JavaMailSender mailSender;
    @Autowired
    private SessionMemUtils memUtils;
    @Autowired
    private DoctorService doctorService;
    @Autowired
    private EmailRepository emailRepository;
    @Autowired
    private EmailMapper emailMapper;

    //发送邮箱验证码
    public String sendEmailCode(DrugUser drugUser) throws MessagingException {
        String msg = "<html><body><h3><b>尊敬的客户${userName}，您好！</b></h3><div>您在${time}提交了找回密码申请操作,你的验证码为${code}，为了安全请您尽快修改密码</div></body></html>";
      
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
        messageHelper.setFrom(Sender);
        messageHelper.setTo(drugUser.getEmail()); //自己给自己发送邮件
        messageHelper.setSubject("找回密码");
        
        String code = ValidationCode.getCode();
		msg = msg.replace("${userName}", drugUser.getName()).replace("${code}", code).replace("${time}",
				DateUtil.getDateTimeString(new Date()));
        logger.info("retrieve.password.send.message【{}】",msg);

        messageHelper.setText( msg,true);
        String uuid = UUID.randomUUID().toString();
        memUtils.set(drugUser.getEmail(),30*60*1000,code);
        memUtils.set(uuid,30*60*1000,drugUser.getEmail());
        mailSender.send(mimeMessage);
        logger.info("retrieve.password.send.message【{}】",msg);
        return uuid;
    }
    
    @Transactional(readOnly = false)
    public String commonEmailSendIds(EmailRequestBean bean) throws MessagingException {
		List<Doctor> doctors = this.getDoctors(bean.getDoctorIds());
		if (CollectionsUtil.isNotEmptyList(doctors)) {
			for (int i = 0, size = doctors.size(); i < size; i++) {
				Doctor doctor = doctors.get(i);
				if (doctor == null || StringUtils.isBlank(doctor.getEmail())) {
					return "医生邮箱为空";
				}

				this.processSendEmail(bean, doctor);
			}
		}
        
        return "";
    }

    @Transactional(readOnly = false)
    public boolean commonEmailSendTo(EmailRequestBean bean) throws MessagingException {
    	List<String> emails = new ArrayList<>();
        String email = bean.getEmails();
        String[] emailArr = email.split(",");
        
       for(int i = 0;i < emailArr.length;i++) {
    	   String to = emailArr[i];
    	   if(StringUtils.isNotEmtity(to)) {
    		   emails.add(to);
    	   }
       }
        
        List<Doctor> doctorList = doctorService.findByEmailIn(emails);
        if(CollectionsUtil.isNotEmptyList(emails)){
            for (int i = 0,leng=emails.size(); i < leng; i++) {
                Doctor doctor = this.getDoctorToEmail(doctorList,emails.get(i));
                if (doctor == null || StringUtils.isBlank(doctor.getEmail())) {
					return false;
				}

                this.processSendEmail(bean, doctor);
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
    
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    /**
     * 根据 doctorIds 获取 List<Doctor>
     * @param doctorIds String 
     * @return 成功返回 List<Doctor>, 否则返回空集合
     */
	private List<Doctor> getDoctors(String doctorIds) {
		List<Doctor> doctors = Collections.emptyList();
		
		if (StringUtils.isNotBlank(doctorIds)) {
			List<Long> ids = new ArrayList<>();
			String[] doctorid = doctorIds.split(",");
			for (String id : doctorid) {
				if (StringUtils.isNotEmtity(id)) {
					ids.add(Long.valueOf(id));
				}
			}

			if (CollectionsUtil.isNotEmptyList(ids)) {
				doctors = doctorService.findByIdIn(ids);
			}
		}

		return doctors;
	}
   
	/**
	 * 发邮件 process 
	 * @param bean EmailRequestBean 对象
	 * @param doctor Doctor 对象
	 * @throws MessagingException
	 */
	private void processSendEmail(EmailRequestBean bean, Doctor doctor) throws MessagingException {
		MimeMessage mimeMessage = this.getMimeMessage(bean, doctor.getEmail());
		logger.info("common.email.send.message【{}】", JSON.toJSONString(mimeMessage));
		mailSender.send(mimeMessage);

		this.save(bean, doctor);
	}
   
	private void save(EmailRequestBean bean, Doctor doctor) {
		Email entity = new Email();
		entity.setContent(bean.getContent());
		entity.setCreateTime(new Date());
		if (doctor != null) {
			entity.setDoctorId(doctor.getId());
		}
		entity.setDrugUserId(bean.getDrugUserId());
		entity.setProductId(bean.getProductId());
		entity.setTitle(bean.getTitle());
		entity.setType(2);

		emailRepository.save(entity);
	}

    private Doctor getDoctorToEmail(List<Doctor> doctorList,String email){
		if (CollectionsUtil.isNotEmptyList(doctorList)) {
			for (Doctor d : doctorList) {
				if (StringUtils.isNotEmtity(d.getEmail()) && email.equals(d.getEmail())) {
					return d;
				}
			}
		}
        
        return null;
    }

    private MimeMessage getMimeMessage(EmailRequestBean bean,String email) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
        messageHelper.setFrom(Sender);
        messageHelper.setTo(email); //自己给自己发送邮件
        messageHelper.setSubject(bean.getTitle());
        messageHelper.setText( bean.getContent(),true);
        return mimeMessage;
    }

}
