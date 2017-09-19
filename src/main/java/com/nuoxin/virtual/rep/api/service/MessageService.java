package com.nuoxin.virtual.rep.api.service;

import com.nuoxin.virtual.rep.api.common.bean.PageResponseBean;
import com.nuoxin.virtual.rep.api.common.enums.ErrorEnum;
import com.nuoxin.virtual.rep.api.common.exception.FileFormatException;
import com.nuoxin.virtual.rep.api.common.service.BaseService;
import com.nuoxin.virtual.rep.api.dao.DoctorRepository;
import com.nuoxin.virtual.rep.api.dao.DrugUserRepository;
import com.nuoxin.virtual.rep.api.dao.MessageRepository;
import com.nuoxin.virtual.rep.api.entity.Doctor;
import com.nuoxin.virtual.rep.api.entity.DrugUser;
import com.nuoxin.virtual.rep.api.entity.Message;
import com.nuoxin.virtual.rep.api.enums.MessageTypeEnum;
import com.nuoxin.virtual.rep.api.enums.UserTypeEnum;
import com.nuoxin.virtual.rep.api.utils.ExcelUtils;
import com.nuoxin.virtual.rep.api.utils.RegularUtils;
import com.nuoxin.virtual.rep.api.web.controller.request.message.MessageRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.vo.WechatMessageVo;
import com.nuoxin.virtual.rep.api.web.controller.response.message.MessageResponseBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * 微信相关接口
 */
@Service
public class MessageService extends BaseService{

    private static final String EXTENSION_XLS = "xls";

    private static final String EXTENSION_XLSX = "xlsx";

    private static final String MATCH_TELEPHONE = "^1\\d{10}$";

    //excel从哪行开始读取，这里excel标题占两行
    private static final Integer beginReadRow = 2;

    private static final String DRUG_USER_NICKNAME = "我";

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private DrugUserRepository drugUserRepository;

    @Autowired
    private DoctorRepository doctorRepository;


    /**
     * 导入微信聊天消息
     * @param file 消息的excel文件
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean importExcel(MultipartFile file){
        boolean success = false;
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();

        } catch (IOException e) {
            logger.error("得到上传文件的输入流失败。。" + e);
            logger.error("得到上传文件的输入流失败。。" + e.getMessage());
            e.printStackTrace();
            throw new FileFormatException(ErrorEnum.FILE_FORMAT_ERROR);
        }

        String originalFilename = file.getOriginalFilename();
        File excelFile = new File(originalFilename);

        String name = excelFile.getName();
        System.out.println(name);
        if (!originalFilename.endsWith(EXTENSION_XLS) && !originalFilename.endsWith(EXTENSION_XLSX)){
            throw new FileFormatException(ErrorEnum.FILE_FORMAT_ERROR);
        }
        String[] split = originalFilename.split("-");
        if (null == split || split.length < 2){
            throw new FileFormatException(ErrorEnum.FILE_FORMAT_ERROR);
        }

        String drugUserNickname = split[0];
        String drugUserTelephone = split[1];
        drugUserTelephone = drugUserTelephone.substring(0, drugUserTelephone.indexOf("."));
        boolean matcher = RegularUtils.isMatcher(MATCH_TELEPHONE, drugUserTelephone);
        if (!matcher){
            throw new FileFormatException(ErrorEnum.FILE_FORMAT_ERROR);
        }



        List<Message> list = new ArrayList<>();
        ExcelUtils<WechatMessageVo> excelUtils = new ExcelUtils<>(new WechatMessageVo());

        List<WechatMessageVo> wechatMessageVos = null;

        try {
           wechatMessageVos = excelUtils.readFromFile(null, inputStream);
        } catch (Exception e) {
            logger.error("读取上传的excel文件失败。。" , e.getMessage());
            e.printStackTrace();
            throw new FileFormatException(ErrorEnum.FILE_FORMAT_ERROR);
        }

        if (null == wechatMessageVos || wechatMessageVos.size() <= 0){
            return false;
        }

        for (WechatMessageVo wechatMessageVo:wechatMessageVos) {
            if (null != wechatMessageVo) {
                String id = wechatMessageVo.getId();
                if (StringUtils.isEmpty(id)) {
                    continue;
                }


                String wechatTime = wechatMessageVo.getWechatTime();
                String wechatNickName = wechatMessageVo.getNickname();
                String wechatNumber = wechatMessageVo.getWechatNumber();
                String wechatMessageStatus = wechatMessageVo.getMessageStatus();
                String wechatMessageType = wechatMessageVo.getMessageType();
                String message = wechatMessageVo.getMessage();

                int userType = 0;
                String nickname = "";
                String telephone = "";
                Long userId = 0L;
                if (wechatNickName != null && DRUG_USER_NICKNAME.equals(wechatNickName)) {
                    userType = UserTypeEnum.DRUG_USER.getUserType();
                    nickname = drugUserNickname;
                    telephone = drugUserTelephone;
                    DrugUser drugUser = drugUserRepository.findFirstByMobile(telephone);
                    if (null != drugUser){
                        userId = drugUser.getId();
                    }

                } else if (wechatNickName != null && !DRUG_USER_NICKNAME.equals(wechatNickName)) {
                    userType = UserTypeEnum.DOCTOR.getUserType();
                    String[] nicknameArray = wechatNickName.split("-");
                    if (null == nicknameArray || nicknameArray.length < 2) {
                        throw new FileFormatException(ErrorEnum.FILE_FORMAT_ERROR);
                    }
                    nickname = nicknameArray[0];
                    telephone = nicknameArray[1];
                    boolean m = RegularUtils.isMatcher(MATCH_TELEPHONE, telephone);
                    if (!m){
                        throw new FileFormatException(ErrorEnum.FILE_FORMAT_ERROR);
                    }

                    Doctor doctor = doctorRepository.findTopByMobile(telephone);
                    if (null != doctor){
                        userId = doctor.getId();
                    }

                }


                Message wechatMessage = new Message();
                wechatMessage.setUserId(userId);
                wechatMessage.setUserType(userType);
                wechatMessage.setNickname(nickname);
                wechatMessage.setWechatNumber(wechatNumber);
                wechatMessage.setTelephone(telephone);
                wechatMessage.setWechatMessageStatus(wechatMessageStatus);
                wechatMessage.setMessage(message);
                wechatMessage.setWechatMessageType(wechatMessageType);
                wechatMessage.setMessageType(MessageTypeEnum.WECHAT.getMessageType());
                wechatMessage.setMessageTime(wechatTime);
                wechatMessage.setCreateTime(new Date());
                list.add(wechatMessage);

            }
        }

        //批量保存微信聊天消息
        messageRepository.save(list);

        success = true;
        return success;
    }




    public PageResponseBean<MessageResponseBean> getMessageList(MessageRequestBean bean){
        bean.setPage(bean.getPage()-1);
        Pageable pageable = super.getPage(bean);

        Specification<Message> specification = new Specification<Message>() {
            @Override
            public Predicate toPredicate(Root<Message> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                //Path path = root.get("id");
                //Predicate predicate = criteriaBuilder.equal(path, bean.getTelephone());
                //Predicate predicate = criteriaBuilder.ge(path,2);

//                Path<String> path = root.get("telephone");
//                Predicate predicate = criteriaBuilder.equal(path, bean.getTelephone());
//                criteriaBuilder.equal(path, bean.getTelephone());
//                return predicate;

                return null;
            }
        };


        Page<Message> page = messageRepository.findAll(specification, pageable);
        PageResponseBean<MessageResponseBean> messagePage = new PageResponseBean<>(page);
        List<MessageResponseBean> list = new ArrayList<>();
        List<Message> content = page.getContent();
        if (null != content && content.size() > 0){

            for (Message message:content){
                MessageResponseBean messageResponseBean = new MessageResponseBean();
                messageResponseBean.setId(message.getId());
                messageResponseBean.setUserId(message.getUserId());
                messageResponseBean.setUserType(message.getUserType());
                messageResponseBean.setNickname(message.getNickname());
                messageResponseBean.setWechatNumber(message.getWechatNumber());
                messageResponseBean.setTelephone(message.getTelephone());
                messageResponseBean.setWechatMessageStatus(message.getWechatMessageStatus());
                messageResponseBean.setMessage(message.getMessage());
                messageResponseBean.setWechatMessageType(message.getWechatMessageType());
                messageResponseBean.setMessageType(message.getMessageType());
                messageResponseBean.setMessageTime(message.getMessageTime());

                list.add(messageResponseBean);
            }

        }

        messagePage.setContent(list);

        return messagePage;
    }


    /**
     * 今日会话统计
     * @param drugUserId
     * @return
     */
    public Map<String,Integer> getMessageCountList(Long drugUserId){
        Map<String,Integer> map = new HashMap<>();
        Integer wechatCount = messageRepository.messageCount(drugUserId, MessageTypeEnum.WECHAT.getMessageType(), UserTypeEnum.DOCTOR.getUserType());

        Integer imCount = messageRepository.messageCount(drugUserId, MessageTypeEnum.IM.getMessageType(), UserTypeEnum.DOCTOR.getUserType());

        map.put("wechat", wechatCount);
        map.put("im", imCount);

        return map;
    }

}
