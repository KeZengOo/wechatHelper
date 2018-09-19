package com.nuoxin.virtual.rep.api.service;

import com.alibaba.fastjson.JSONObject;
import com.nuoxin.virtual.rep.api.common.bean.PageResponseBean;
import com.nuoxin.virtual.rep.api.common.enums.ErrorEnum;
import com.nuoxin.virtual.rep.api.common.exception.BusinessException;
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
import com.nuoxin.virtual.rep.api.mybatis.MessageMapper;
import com.nuoxin.virtual.rep.api.utils.*;
import com.nuoxin.virtual.rep.api.web.controller.request.message.MessageRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.vo.WechatMessageVo;
import com.nuoxin.virtual.rep.api.web.controller.response.message.MessageLinkmanResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.message.MessageResponseBean;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;
import java.util.regex.Matcher;

/**
 * 微信相关接口
 */
@Service
public class MessageService extends BaseService {

    private static final String DRUG_USER_NICKNAME = "我";
    private static final String filePath = "exceltemplate/wechatMessage.xls";
    private static final String filename = "wechatMessage.xls";

    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private DrugUserRepository drugUserRepository;
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private MessageMapper messageMapper;

    public void downloadExcel(HttpServletResponse response) {
        response.setHeader("content-Type", "application/vnd.ms-excel");
        // 下载文件的默认名称
        response.setHeader("Content-Disposition", "attachment;filename=" + filename);
        response.setContentType("application/octet-stream");

        InputStream systemResourceAsStream = ClassLoader.getSystemResourceAsStream(filePath);
        System.out.println(systemResourceAsStream);
        byte[] buff = new byte[1024];
        BufferedInputStream bis = null;
        OutputStream os = null;
        try {
            os = response.getOutputStream();
            bis = new BufferedInputStream(systemResourceAsStream);
            int i = bis.read(buff);
            while (i != -1) {
                os.write(buff, 0, buff.length);
                os.flush();
                i = bis.read(buff);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException(ErrorEnum.ERROR);
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new BusinessException(ErrorEnum.ERROR);
                }
            }
        }
    }

    /**
     * 导入微信聊天消息
     * @param file 消息的excel文件
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean importExcel(MultipartFile file, DrugUser drugUser) {
        boolean success = false;

        // 检查文件
        Doctor doctor = checkWechatFile(file);

        ExcelUtils<WechatMessageVo> excelUtils = new ExcelUtils<>(new WechatMessageVo());
        List<WechatMessageVo> wechatMessageVos = null;
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
            wechatMessageVos = excelUtils.readFromFile(null, inputStream);
            if (CollectionsUtil.isEmptyList(wechatMessageVos)) {
                logger.warn("微信聊天excel文件读取失败：wechatMessageVos={}", JSONObject.toJSONString(wechatMessageVos));
                throw new FileFormatException(ErrorEnum.ERROR,"Excel读取为空！");
            }
        }catch (InvalidFormatException e){
            throw new FileFormatException(ErrorEnum.ERROR,e.getMessage());
        } catch (Exception e) {
            logger.error("读取上传的excel文件失败。。", e);
            throw new FileFormatException(ErrorEnum.ERROR, e.getMessage() );
        } finally {
        	if(inputStream != null) {
        		try {
					inputStream.close();
				} catch (IOException e) {
					logger.error("IOException", e);
				}
        	}
        }

        // 得到去重后的聊天消息
        List<Message> list = getDuplicateRemovalMessageList(wechatMessageVos, doctor,  drugUser);
        //批量保存微信聊天消息
        logger.info("开始批量插入聊天记录drugUserId={}, drugUserName={}, doctorId={}, doctorName={}, size={}", drugUser.getId(), drugUser.getName(), doctor.getId(), doctor.getName(), list.size());
        messageRepository.save(list);
        logger.info("批量插入聊天记录结束！！");
        success = true;
        return success;
    }

    /**
     * 得到去重后的消息列表
     * @param wechatMessageVos
     * @return
     */
    private List<Message> getDuplicateRemovalMessageList(List<WechatMessageVo> wechatMessageVos,Doctor doctor, DrugUser drugUser) {
        if (CollectionsUtil.isEmptyList(wechatMessageVos)){
            return null;
        }

        List<Message> list = new ArrayList<>();
        for (WechatMessageVo wechatMessageVo : wechatMessageVos) {
            if (null != wechatMessageVo) {
                String id = wechatMessageVo.getId();
                if (StringUtils.isEmpty(id)) {
                    logger.warn("WechatId 是{} 过滤掉这条！" ,id );
                    continue;
                }

                Date wechatTimeDate = wechatMessageVo.getWechatTime();
                String wechatTime = DateUtil.getDateTimeString(wechatTimeDate);
                String wechatNickName = wechatMessageVo.getNickname();
                String wechatNumber = wechatMessageVo.getWechatNumber();
                String wechatMessageStatus = wechatMessageVo.getMessageStatus();
                String wechatMessageType = wechatMessageVo.getMessageType();
                String message = wechatMessageVo.getMessage();

                //判断数据库中是否存在该条数据
                Integer count = messageMapper.getCountByTypeAndWechatNumAndTime(MessageTypeEnum.WECHAT.getMessageType(), wechatNumber, wechatTime);
                if (count != null && count > 0){
                    //数据库存在该条数据
                    logger.warn("数据库存在该条数据，过滤掉这条！");
                    continue;
                }

                int userType = 0;
                String nickname = "";
                String telephone = "";
                Long userId = 0L;

                Long drugUserId = drugUser.getId();
                Long doctorId = doctor.getId();
                if (StringUtils.isEmpty(wechatNickName)){
                    throw new FileFormatException(ErrorEnum.ERROR.getStatus(), "文件中昵称不能为空！");
                }

                if (DRUG_USER_NICKNAME.equals(wechatNickName)) {
                    userType = UserTypeEnum.DRUG_USER.getUserType();
                    nickname = drugUser.getName();
                    telephone = drugUser.getMobile();
                    userId = drugUserId;
                } else{
                    userType = UserTypeEnum.DOCTOR.getUserType();
                    nickname = doctor.getName();
                    telephone = doctor.getMobile();
                    userId = doctorId;
                }

                Message wechatMessage = new Message();
                wechatMessage.setUserId(userId);
                wechatMessage.setUserType(userType);
                wechatMessage.setNickname(nickname);
                wechatMessage.setDrugUserId(drugUserId);
                wechatMessage.setDoctorId(doctorId);
                wechatMessage.setWechatId(id);
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

        return list;
    }

    /**
     * 检查导入的的微信消息文件是否合法，不合法会抛出异常，合法返回医生信息
     * @param file
     */
    private Doctor checkWechatFile(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        if (StringUtils.isEmpty(originalFilename)){
            throw new FileFormatException(ErrorEnum.FILE_FORMAT_ERROR, "文件名不能为空");
        }

        if (!originalFilename.endsWith(RegularUtils.EXTENSION_XLS) && !originalFilename.endsWith(RegularUtils.EXTENSION_XLSX)) {
            throw new FileFormatException(ErrorEnum.FILE_FORMAT_ERROR);
        }


        String fileName = originalFilename.substring(0,originalFilename.lastIndexOf("."));
        Matcher matcher = RegularUtils.getMatcher(RegularUtils.MATCH_ELEVEN_NUM, fileName);
        if (matcher.find()){
            String telephone = matcher.group();
            Doctor doctor = doctorRepository.findTopByMobile(telephone);
            if (doctor == null){
                throw new FileFormatException(ErrorEnum.FILE_FORMAT_ERROR, "文件名中包含的手机号匹配不到医生！");
            }
            return doctor;
        }else {
            throw new FileFormatException(ErrorEnum.FILE_FORMAT_ERROR, "文件名中没有包含手机号！");
        }

    }

    //mybatis的写法
    public PageResponseBean<MessageResponseBean> getMessageList(MessageRequestBean bean) {
        DrugUser drugUser = drugUserRepository.findFirstById(bean.getDrugUserId());
        String leaderPath = drugUser.getLeaderPath();
        if (leaderPath == null){
            leaderPath = "";
        }
        bean.setLeaderPath(leaderPath +"%");
        Integer page = bean.getPage();
        Integer pageSize = bean.getPageSize();
        bean.setCurrentSize(page  * pageSize);

        List<MessageResponseBean> messageList = null;
        Integer messageListCount = 0;
        Integer messageType = bean.getMessageType();
        if (messageType != null){
            if (messageType == MessageTypeEnum.IM.getMessageType() || messageType == MessageTypeEnum.WECHAT.getMessageType()){
                messageListCount = messageMapper.getMessageListCount(bean);
                if (messageListCount != null && messageListCount > 0){
                    messageList = messageMapper.getMessageList(bean);
                }
            }

            if (messageType == MessageTypeEnum.EMAIL.getMessageType()){
                messageListCount = messageMapper.getEmailMessageListCount(bean);
                if (messageListCount != null && messageListCount > 0){
                    messageList = messageMapper.getEmailMessageList(bean);
                }
            }
        }

        if (messageListCount ==null){
            messageListCount = 0;
        }
        PageResponseBean<MessageResponseBean> pageResponseBean = new PageResponseBean<>(bean, messageListCount, messageList);

        return pageResponseBean;
    }

    /**
     * 今日会话统计
     * @param bean
     * @return
     */
    public Map<String, Integer> getMessageCountList(MessageRequestBean bean) {
        Map<String, Integer> map = new HashMap<>();
        DrugUser drugUser = drugUserRepository.findFirstById(bean.getDrugUserId());
        String leaderPath = drugUser.getLeaderPath();
        if (leaderPath == null){
            leaderPath = "";
        }
        bean.setLeaderPath(leaderPath +"%");
        bean.setMessageType(MessageTypeEnum.WECHAT.getMessageType());
        Integer wechatCount = messageMapper.messageCount(bean);
//        bean.setMessageType(MessageTypeEnum.IM.getMessageType());
//        Integer imCount = messageMapper.messageCount(bean);
//        Integer emailMessageCount = messageMapper.emailMessageCount(bean);
//        if (emailMessageCount == null){
//            emailMessageCount=0;
//        }

        // 现在只有微信
        /*map.put("wechat", wechatCount);
        map.put("im", imCount);
        map.put("email",emailMessageCount);*/

        map.put("wechat", wechatCount);
        map.put("im", 0);
        map.put("email",0);

        return map;
    }

    /**
     * 微信消息联系人(mybatis)
     * @return
     */
    public PageResponseBean<MessageLinkmanResponseBean> getMessageLinkmanList(MessageRequestBean bean) {
        DrugUser drugUser = drugUserRepository.findFirstById(bean.getDrugUserId());
        String leaderPath = drugUser.getLeaderPath();
        if (leaderPath == null){
            leaderPath = "";
        }
        bean.setLeaderPath(leaderPath+"%");
        Integer page = bean.getPage();
        Integer pageSize = bean.getPageSize();
        bean.setCurrentSize(page * pageSize);

        Integer messageLinkmanListCount = messageMapper.getMessageLinkmanListCount(bean);
        List<MessageLinkmanResponseBean> messageLinkmanList = null;
        if (messageLinkmanListCount !=null && messageLinkmanListCount > 0){
            messageLinkmanList = messageMapper.getMessageLinkmanList(bean);
            if (null != messageLinkmanList && !messageLinkmanList.isEmpty()){
                for (MessageLinkmanResponseBean messageLinkmanResponseBean:messageLinkmanList){
                    Integer messageType = messageLinkmanResponseBean.getMessageType();
                    if (messageType != null){
                        if (messageType == MessageTypeEnum.WECHAT.getMessageType() || messageType == MessageTypeEnum.IM.getMessageType()){
                            String lastMessage = messageMapper.getLastMessage(messageType, messageLinkmanResponseBean.getDoctorId(), messageLinkmanResponseBean.getLastTime());
                            messageLinkmanResponseBean.setLastMessage(lastMessage);
                        }
                        if (messageType == MessageTypeEnum.EMAIL.getMessageType()){
                            String lastEmailMessage = messageMapper.getLastEmailMessage(messageLinkmanResponseBean.getDoctorId(), messageLinkmanResponseBean.getLastTime());
                            messageLinkmanResponseBean.setLastMessage(lastEmailMessage);
                        }
                    }
                }
            }
        }



        PageResponseBean<MessageLinkmanResponseBean> pageResponseBean = new PageResponseBean<>(bean,messageLinkmanListCount, messageLinkmanList);

        return pageResponseBean;
    }

    public void test() {
        List<Message> messageList = messageRepository.test();
        System.out.println(messageList.size());
        System.out.println(messageList);
    }

}
