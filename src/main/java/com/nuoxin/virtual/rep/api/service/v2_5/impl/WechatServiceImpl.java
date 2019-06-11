package com.nuoxin.virtual.rep.api.service.v2_5.impl;

import com.nuoxin.virtual.rep.api.common.bean.PageResponseBean;
import com.nuoxin.virtual.rep.api.common.enums.ErrorEnum;
import com.nuoxin.virtual.rep.api.common.exception.BusinessException;
import com.nuoxin.virtual.rep.api.common.exception.FileFormatException;
import com.nuoxin.virtual.rep.api.dao.DoctorRepository;
import com.nuoxin.virtual.rep.api.dao.DrugUserRepository;
import com.nuoxin.virtual.rep.api.entity.Doctor;
import com.nuoxin.virtual.rep.api.entity.DrugUser;
import com.nuoxin.virtual.rep.api.entity.v2_5.ProductBean;
import com.nuoxin.virtual.rep.api.enums.MessageTypeEnum;
import com.nuoxin.virtual.rep.api.enums.UserTypeEnum;
import com.nuoxin.virtual.rep.api.mybatis.*;
import com.nuoxin.virtual.rep.api.service.v2_5.WechatService;
import com.nuoxin.virtual.rep.api.utils.*;
import com.nuoxin.virtual.rep.api.web.controller.request.call.CallRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.wechat.*;
import com.nuoxin.virtual.rep.api.web.controller.request.v3_0.WechatChatRoomMessageRequest;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.wechat.WechatAndroidContactResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.wechat.WechatAndroidUploadTimeResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.wechat.WechatChatRoomMemberResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.v3_0.WechatChatRoomMessageResponse;
import com.nuoxin.virtual.rep.api.web.controller.response.v3_0.WechatChatRoomResponse;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import com.vdurmont.emoji.EmojiParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author tiancun
 * @date 2018-12-03
 */
@Service
public class WechatServiceImpl implements WechatService {

    private static final Logger logger = LoggerFactory.getLogger(WechatServiceImpl.class);

    @Resource
    private DrugUserWechatMapper drugUserWechatMapper;

    @Resource
    private WechatContactMapper wechatContactMapper;

    @Resource
    private WechatChatRoomContactMapper wechatChatRoomContactMapper;

    @Resource
    private DrugUserRepository drugUserRepository;

    @Resource
    private DoctorRepository doctorRepository;

    @Resource
    private MessageMapper messageMapper;


    @Resource
    private DoctorMapper doctorMapper;

    @Resource
    private DrugUserDoctorMapper drugUserDoctorMapper;

    @Resource
    private VirtualDoctorCallInfoMapper virtualDoctorCallInfoMapper;

    @Resource
    private VirtualMessageChatRoomMapper virtualMessageChatRoomMapper;

    /**
     * 每次批量插入的数量
     */
    public static final int BATCH_INSERT_SIZE = 1000;



    @Override
    public void handleWechatUserFile(MultipartFile file) {
        Long drugUserId = this.checkWechatUserFile(file);

        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 格式 userName,nickName,alias,conRemark,type
        List<CSVRecord> contactStrList = this.handleCsvFile(inputStream, new String[]{"userName", "nickName", "alias", "conRemark", "type"});
        List<WechatAndroidContactRequestBean> contactList = this.getWechatContactList(contactStrList);

        if (CollectionsUtil.isNotEmptyList(contactList)) {

            this.saveOrUpdateContactList(drugUserId, contactList);
        }

        this.reUpdateContact();

    }

    @Override
    public void handleChatroomWechatUserFile(MultipartFile file) {

        Long drugUserId = this.checkWechatUserFile(file);
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 格式 chatroomname,memberlist,displayname,roomowner,selfDisplayName
        List<CSVRecord> chatroomContactStrList = this.handleCsvFile(inputStream, new String[]{"chatroomname", "memberlist", "displayname", "roomowner", "selfDisplayName"});
        List<WechatAndroidChatroomContactRequestBean> chatroomConcatList = this.getWechatChatroomConcatList(drugUserId,chatroomContactStrList);
        this.saveOrUpdateChatRoomContactList(chatroomConcatList);


    }


    @Override
    public void handleWechatMessageFile(MultipartFile file, WechatAndroidMessageRequestBean bean) {

        Long drugUserId = this.checkWechatUserFile(file);
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 格式 talker,content,createTime,imgPath,isSend,type
        List<CSVRecord> messageStrList = this.handleCsvFile(inputStream, new String[]{"talker", "content", "createTime", "imgPath", "isSend", "type"});

        // 提取出群聊的消息
        if (CollectionsUtil.isNotEmptyList(messageStrList)){
            List<CSVRecord> chatRoomMessage = messageStrList.stream().filter(c -> c.get("talker").contains("@chatroom")).collect(Collectors.toList());
            if (CollectionsUtil.isNotEmptyList(chatRoomMessage)){
                // 根据文件得到微信号
                String drugUserWechat = this.getWechatNumberByFile(file);
                List<WechatChatRoomMessageRequestBean> wechatChatRoomMessageList = this.getWechatChatRoomMessageList(drugUserId, drugUserWechat, chatRoomMessage);
                if (CollectionsUtil.isNotEmptyList(wechatChatRoomMessageList)){
                    virtualMessageChatRoomMapper.batchInsert(wechatChatRoomMessageList);
                    // 修改群消息中的全成员
                    virtualMessageChatRoomMapper.updateWechatChatroomMember();
                }
            }
        }

        List<WechatMessageRequestBean> wechatMessageList = this.getWechatMessageList(drugUserId, bean.getUploadFileTime(), messageStrList);
        this.saveOrUpdateWechatMessageList(wechatMessageList);


        this.reUpdateMessage();
    }


    @Override
    public void reHandleWechatChatRoomMessage() {


        List<WechatChatRoomMessageRequestBean> wechatChatRoomMessageList = virtualMessageChatRoomMapper.getReHandleWechatChatRoomMessageList();
        if (CollectionsUtil.isEmptyList(wechatChatRoomMessageList)){
            return;
        }

        List<WechatChatRoomMessageRequestBean> addList = new ArrayList<>();
        for (WechatChatRoomMessageRequestBean wechatChatRoomMessage : wechatChatRoomMessageList) {

            String chatRoomId = wechatChatRoomMessage.getChatRoomId();
            Long drugUserId = wechatChatRoomMessage.getDrugUserId();

            String wechatMessageStatus = wechatChatRoomMessage.getWechatMessageStatus();
            String wechatMessage = wechatChatRoomMessage.getWechatMessage();

            String memberId = "";
            String memberName = "";

            List<WechatChatRoomMemberResponseBean> wechatChatRoomMemberList = wechatChatRoomContactMapper.getWechatChatRoomMemberList(chatRoomId);
            if (StringUtil.isNotEmpty(wechatMessageStatus) && "发送".equals(wechatMessageStatus)){

                List<String> drugUserWechat = drugUserWechatMapper.getDrugUserWechat(drugUserId);
                if (CollectionsUtil.isEmptyList(drugUserWechat)){
                    throw new BusinessException(ErrorEnum.ERROR, "代表还没有绑定微信号！");
                }
                DrugUser drugUser = drugUserRepository.findFirstById(drugUserId);
                memberId = drugUserWechat.get(0);
                memberName = drugUser.getName();
            }

            if (StringUtil.isNotEmpty(wechatMessageStatus) && "接收".equals(wechatMessageStatus)){
                if (CollectionsUtil.isNotEmptyList(wechatChatRoomMemberList)){
                    final String memberIdStr = wechatMessage.split(":")[0];
                    memberId = wechatChatRoomMessage.getMemberId();
                    Optional<String> first = wechatChatRoomMemberList.stream().filter(m -> (memberIdStr.equals(m.getMemberId()))).map(WechatChatRoomMemberResponseBean::getMemberName).findFirst();
                    if (first.isPresent()){
                        memberName = first.get();
                    }
                }
                wechatMessage = wechatMessage.substring(wechatMessage.indexOf(":") +1);
                wechatChatRoomMessage.setWechatMessage(wechatMessage);

            }

            Integer messageCount = virtualMessageChatRoomMapper.getMessageCount(chatRoomId, memberId, wechatMessage, wechatChatRoomMessage.getMessageTime());
            if (messageCount !=null && messageCount > 0){
                continue;
            }

            wechatChatRoomMessage.setMemberId(memberId);
            wechatChatRoomMessage.setMemberName(memberName);

            addList.add(wechatChatRoomMessage);
        }


        if (CollectionsUtil.isNotEmptyList(addList)){
            int size = addList.size();
            int totalPage = PageUtil.getTotalPage(size, BATCH_INSERT_SIZE);
            List<WechatChatRoomMessageRequestBean> subWechatChatRoomMessageRequestBean = null;
            for (int i = 0; i < totalPage; i++) {
                if (i == (totalPage - 1)) {

                    subWechatChatRoomMessageRequestBean = addList.subList(i * BATCH_INSERT_SIZE, i * BATCH_INSERT_SIZE + (size - i * BATCH_INSERT_SIZE));
                } else {
                    subWechatChatRoomMessageRequestBean = addList.subList(i * BATCH_INSERT_SIZE, i * BATCH_INSERT_SIZE + BATCH_INSERT_SIZE);
                }

                List<WechatChatRoomMessageRequestBean> addWechatMessageList = new ArrayList<>(subWechatChatRoomMessageRequestBean);
                virtualMessageChatRoomMapper.batchInsert(addWechatMessageList);

            }
        }


    }

    /**
     * 从名字中获得微信号
     * @param file
     * @return
     */
    private String getWechatNumberByFile(MultipartFile file) {

        String originalFilename = file.getOriginalFilename();
        String fileName = originalFilename.substring(0, originalFilename.lastIndexOf("."));
        String wechat = fileName.split("Ξ")[0];
        return wechat;
    }


    /**
     * 保存群聊的群成员，如果群存在删除掉重新录入
     * @param chatroomConcatList
     */
    private void saveOrUpdateChatRoomContactList(List<WechatAndroidChatroomContactRequestBean> chatroomConcatList) {
        if (CollectionsUtil.isEmptyList(chatroomConcatList)){
            return;
        }

        List<String> chatRoomIdList = chatroomConcatList.stream().map(WechatAndroidChatroomContactRequestBean::getChatroomId).distinct().collect(Collectors.toList());
        if (CollectionsUtil.isEmptyList(chatRoomIdList)){
            return;
        }

        wechatChatRoomContactMapper.deleteByChatRoomIdList(chatRoomIdList);
        wechatChatRoomContactMapper.batchInsert(chatroomConcatList);

        // 更新群名称
        wechatChatRoomContactMapper.updateAllChatRoomName();
    }


    @Async
    public void reUpdateContact() {
        try {
            messageMapper.reUpdateMessageContact();
            messageMapper.reUpdateMessageContactAlias();
        }catch (Exception e){
            e.printStackTrace();
        }
    }



    /**
     * 处理微信群聊消息入库
     * @param drugUserId
     * @param drugUserWechat
     * @param chatRoomMessageList
     * @return
     */
    private List<WechatChatRoomMessageRequestBean> getWechatChatRoomMessageList(Long drugUserId,String drugUserWechat, List<CSVRecord> chatRoomMessageList) {
        if (CollectionsUtil.isEmptyList(chatRoomMessageList)){
            return null;
        }
        // "talker", "content", "createTime", "imgPath", "isSend", "type"
        List<WechatChatRoomMessageRequestBean> list = new ArrayList<>();
        for (CSVRecord csvRecord : chatRoomMessageList) {
            String chatRoomId = csvRecord.get("talker");
            String content = csvRecord.get("content");
            String createTime = csvRecord.get("createTime");
            String imgPath = csvRecord.get("imgPath");
            String isSend = csvRecord.get("isSend");
            String type = csvRecord.get("type");

            String memberId = "";
            String memberName = "";
            String wechatMessageStatus = "";
            String wechatMessage = "";

            if (StringUtil.isEmpty(content)){
                continue;
            }

            List<WechatChatRoomMemberResponseBean> wechatChatRoomMemberList = wechatChatRoomContactMapper.getWechatChatRoomMemberList(chatRoomId);

            if (StringUtil.isNotEmpty(isSend) && "1".equals(isSend)){
                wechatMessageStatus = "发送";
                DrugUser drugUser = drugUserRepository.findFirstById(drugUserId);
                memberName = drugUser.getName();
                memberId = drugUserWechat;
                wechatMessage = content;
            }

            if (StringUtil.isNotEmpty(isSend) && "0".equals(isSend)){
                wechatMessageStatus = "接收";
                if (CollectionsUtil.isNotEmptyList(wechatChatRoomMemberList)){
                    if (!content.contains(":")){
                        // 没有 ":" 的都是无效的消息
                        continue;
                    }

                    final String memberIdStr = content.split(":")[0];
                    memberId = content.split(":")[0];
                    Optional<String> first = wechatChatRoomMemberList.stream().filter(w -> w.getMemberId().equals(memberIdStr)).map(WechatChatRoomMemberResponseBean::getMemberName).findFirst();
                    if (first.isPresent()){
                        memberName = first.get();
                    }
                }

                wechatMessage = content.substring(content.indexOf(":") +1);
            }



            Integer messageCount = virtualMessageChatRoomMapper.getMessageCount(chatRoomId, memberId, wechatMessage, createTime);
            if (messageCount !=null && messageCount > 0){
                continue;
            }

            WechatChatRoomMessageRequestBean wechatChatRoomMessageRequestBean = new WechatChatRoomMessageRequestBean();
            wechatChatRoomMessageRequestBean.setChatRoomId(chatRoomId);
            wechatChatRoomMessageRequestBean.setDrugUserId(drugUserId);
            wechatChatRoomMessageRequestBean.setMemberId(memberId);
            wechatChatRoomMessageRequestBean.setMemberName(memberName);
            wechatChatRoomMessageRequestBean.setWechatMessageStatus(wechatMessageStatus);
            wechatChatRoomMessageRequestBean.setWechatMessageType(type);
            wechatChatRoomMessageRequestBean.setWechatMessage(wechatMessage);
            wechatChatRoomMessageRequestBean.setFilePath(imgPath);

            Date sd = DateUtil.stringToDate(createTime, DateUtil.DATE_FORMAT_YYYY_MM_DD_HH_MM_SS__SSS);
            String dateTimeString = DateUtil.getDateTimeString(sd);
            wechatChatRoomMessageRequestBean.setMessageTime(dateTimeString);
            list.add(wechatChatRoomMessageRequestBean);
        }

        return list;

    }

    @Async
    public void reUpdateMessage() {
        try {
            messageMapper.reUpdateMessageContact();
            messageMapper.reUpdateMessageContactAlias();
            messageMapper.reUpdateSendMessage();
            messageMapper.reUpdateReceiveMessage();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public WechatAndroidUploadTimeResponseBean getWechatAndroidUploadTime(String wechatNumber) {
        WechatAndroidUploadTimeResponseBean wechatAndroidUploadTimeResponseBean = new WechatAndroidUploadTimeResponseBean();
        WechatAndroidUploadTimeResponseBean uploadTime = wechatContactMapper.getUploadTime(wechatNumber);
        if (uploadTime == null) {
            return wechatAndroidUploadTimeResponseBean;
        }


        Long contactUploadTimeValue = uploadTime.getContactUploadTimeValue();
        Long messageUploadTimeValue = uploadTime.getMessageUploadTimeValue();
        if (contactUploadTimeValue != null && contactUploadTimeValue > 0) {
            Date contactDate = new Date(contactUploadTimeValue);
            String contactDateMillisecond = DateUtil.getDateMillisecondString(contactDate);
            uploadTime.setContactUploadTime(contactDateMillisecond);
        }

        if (messageUploadTimeValue != null && messageUploadTimeValue > 0) {
            Date messageDate = new Date(messageUploadTimeValue);
            String messageDateMillisecond = DateUtil.getDateMillisecondString(messageDate);
            uploadTime.setMessageUploadTime(messageDateMillisecond);
        }


        return uploadTime;
    }

    @Override
    public List<WechatChatRoomResponse> getWechatChatRoomList(Long drugUserId, Long doctorId) {
        List<WechatChatRoomResponse> wechatChatRoomList = wechatChatRoomContactMapper.getWechatChatRoomList(drugUserId, doctorId);
        return wechatChatRoomList;

    }

    @Override
    public PageResponseBean<WechatChatRoomMessageResponse> getWechatChatRoomMessagePage(WechatChatRoomMessageRequest request) {

        Integer count = virtualMessageChatRoomMapper.getWechatChatRoomMessageListCount(request);
        if (count == null){
            count = 0;
        }
        List<WechatChatRoomMessageResponse> wechatChatRoomMessageList = null;
        if (count > 0){
            wechatChatRoomMessageList = virtualMessageChatRoomMapper.getWechatChatRoomMessageList(request);
        }

        return new PageResponseBean<>(request, count, wechatChatRoomMessageList);

    }


    private void saveOrUpdateWechatMessageList(List<WechatMessageRequestBean> wechatMessageList) {
        if (CollectionsUtil.isEmptyList(wechatMessageList)) {
            return;
        }

        /**
         * 得到去重后的消息列表
         */
        List<WechatMessageRequestBean> duplicateRemovalWechatMessageList = this.getDuplicateRemovalWechatMessageList(wechatMessageList);
        if (CollectionsUtil.isEmptyList(duplicateRemovalWechatMessageList)) {
            return;
        }

        int size = duplicateRemovalWechatMessageList.size();
        // 每次最多插入1000条
        int totalPage = PageUtil.getTotalPage(size, BATCH_INSERT_SIZE);
        List<WechatMessageRequestBean> subWechatMessageRequestBean = null;
        for (int i = 0; i < totalPage; i++) {
            if (i == (totalPage - 1)) {

                subWechatMessageRequestBean = duplicateRemovalWechatMessageList.subList(i * BATCH_INSERT_SIZE, i * BATCH_INSERT_SIZE + (size - i * BATCH_INSERT_SIZE));
            } else {
                subWechatMessageRequestBean = duplicateRemovalWechatMessageList.subList(i * BATCH_INSERT_SIZE, i * BATCH_INSERT_SIZE + BATCH_INSERT_SIZE);
            }

            List<WechatMessageRequestBean> addWechatMessageList = new ArrayList<>(subWechatMessageRequestBean);
            messageMapper.batchInsertWechatMessage(addWechatMessageList);

        }

        // 微信聊天导出成功之后，自动记录微信拜访次数
        List<CallRequestBean> callRequestBeanList = this.handleWechatMessageList(duplicateRemovalWechatMessageList);
        this.addWechatVisit(callRequestBeanList);
    }

    /**
     * 新增
     * @param callRequestBeanList
     */
    private void addWechatVisit(List<CallRequestBean> callRequestBeanList) {
        if (CollectionsUtil.isEmptyList(callRequestBeanList)){
            return;
        }

        callRequestBeanList.forEach(c->{
            Integer count = virtualDoctorCallInfoMapper.getCountByCallRequest(c);
            if (count == null || count == 0){
                virtualDoctorCallInfoMapper.saveCallInfo(c);
            }
        });

    }

    /**
     * 获得医生回复的数据，去重获得代表医生拜访数据
     * @param wechatMessageList
     */
    private List<CallRequestBean> handleWechatMessageList(List<WechatMessageRequestBean> wechatMessageList) {
        if (CollectionsUtil.isEmptyList(wechatMessageList)){
            return null;
        }

        // 2 代表是医生回复的，只有医生回复才算拜访
        List<WechatMessageRequestBean> doctorWechatMessageList = wechatMessageList.stream().filter(w -> w.getUserType().equals(2)).distinct().collect(Collectors.toList());
        if (CollectionsUtil.isEmptyList(doctorWechatMessageList)){
            return null;
        }

        List<WechatMessageRequestBean> handleWechatMessageList = new ArrayList<>();
        for (WechatMessageRequestBean wechatMessage : doctorWechatMessageList) {
            Long doctorId = wechatMessage.getDoctorId();
            Long drugUserId = wechatMessage.getDrugUserId();
            String messageTime = wechatMessage.getMessageTime();
            String dateString = "";
            try {
                Date date = DateUtil.stringToDate(messageTime, DateUtil.DATE_FORMAT_YYYY_MM_DD);
                dateString = DateUtil.getDateString(date);
            }catch (Exception e){
                logger.error("日期转换出错", e.getMessage(), e);
                continue;
            }

            WechatMessageRequestBean handleWechatMessage = new WechatMessageRequestBean();
            handleWechatMessage.setMessageTime(dateString);
            handleWechatMessage.setDoctorId(doctorId);
            handleWechatMessage.setDrugUserId(drugUserId);
            handleWechatMessageList.add(handleWechatMessage);
        }

        List<WechatMessageRequestBean> collect = handleWechatMessageList.stream().distinct().collect(Collectors.toList());
        List<CallRequestBean> list = this.getCallRequestList(collect);

        return list;



    }


    private List<CallRequestBean> getCallRequestList(List<WechatMessageRequestBean> messageList) {
        if (CollectionsUtil.isEmptyList(messageList)){
            return null;
        }

        List<CallRequestBean> list = new ArrayList<>();
        for (WechatMessageRequestBean wechatMessageRequestBean : messageList) {
            Long drugUserId = wechatMessageRequestBean.getDrugUserId();
            Long doctorId = wechatMessageRequestBean.getDoctorId();
            List<ProductBean> products = drugUserDoctorMapper.getProducts(drugUserId, doctorId);
            if (CollectionsUtil.isEmptyList(products)){
                continue;
            }

            products.forEach(p->{
                CallRequestBean callRequestBean = new CallRequestBean();
                callRequestBean.setSinToken(StringUtil.getUuid());
                // 2是微信拜访
                callRequestBean.setVisitChannel(2);
                callRequestBean.setDoctorId(doctorId);
                callRequestBean.setDrugUserId(drugUserId);
                callRequestBean.setCreateTime(wechatMessageRequestBean.getMessageTime());
                callRequestBean.setProductId(Long.valueOf(p.getProductId()));
                callRequestBean.setMessageTime(wechatMessageRequestBean.getMessageTime());
                list.add(callRequestBean);
            });

        }

        return list;


    }

    private List<WechatMessageRequestBean> getDuplicateRemovalWechatMessageList(List<WechatMessageRequestBean> wechatMessageList) {

        if (CollectionsUtil.isEmptyList(wechatMessageList)) {
            return wechatMessageList;
        }
        List<WechatMessageRequestBean> list = new ArrayList<>();
        for (WechatMessageRequestBean wechatMessage : wechatMessageList) {
            // 处理emoji表情
            String message = wechatMessage.getMessage();
            String s = EmojiParser.removeAllEmojis(message);
            wechatMessage.setMessage(s);
            Integer count = messageMapper.getCountByTypeAndWechatNumAndTime(MessageTypeEnum.WECHAT.getMessageType(), wechatMessage.getWechatNumber(), wechatMessage.getMessageTime());
            if (count != null && count > 0) {
                continue;
            }
            list.add(wechatMessage);
        }


        return list;
    }

    private List<WechatMessageRequestBean> getWechatMessageList(Long drugUserId, String uploadTime, List<CSVRecord> messageStrList) {
        if (CollectionsUtil.isEmptyList(messageStrList)) {
            return null;
        }

        List<String> drugUserWechat = drugUserWechatMapper.getDrugUserWechat(drugUserId);
        if (CollectionsUtil.isEmptyList(drugUserWechat)) {
            throw new FileFormatException(ErrorEnum.ERROR, "代表还没有绑定微信号");
        }


        List<WechatMessageRequestBean> list = new ArrayList<>();

        DrugUser drugUser = drugUserRepository.findFirstById(drugUserId);

        for (int i = 0; i < messageStrList.size(); i++) {
            if (i == 0) {
                continue;// 标题过滤
            }

            // 格式：talker,content,createTime,imgPath,isSend,type

            String talker = messageStrList.get(i).get("talker");
            String content = messageStrList.get(i).get("content");
            String createTime = messageStrList.get(i).get("createTime");
            String imgPath = messageStrList.get(i).get("imgPath");
            String isSend = messageStrList.get(i).get("isSend");
            String type = messageStrList.get(i).get("type");

            Long userId = 0L;
            Integer userType = 0;
            String nickname = "";
            Long doctorId = 0L;
            String telephone = "";
            String wechatMessageStatus = "";
            if (StringUtil.isEmpty(imgPath) || "0".equals(imgPath)) {
                imgPath = "";
            }

            Long doctorIdByTalker = wechatContactMapper.getDoctorIdByWechatNumber(talker);
            Doctor doctor = doctorRepository.findFirstById(doctorIdByTalker);
            if (doctor != null) {
                doctorId = doctorIdByTalker;
                telephone = doctor.getMobile();
            }

            if (StringUtil.isNotEmpty(isSend) && "1".equals(isSend)) {
                wechatMessageStatus = "发送";

                userId = drugUser.getId();
                userType = UserTypeEnum.DRUG_USER.getUserType();
                nickname = drugUser.getName();
            }

            if (StringUtil.isNotEmpty(isSend) && "0".equals(isSend)) {
                wechatMessageStatus = "接收";
                if (doctor != null) {
                    userId = doctorIdByTalker;
                    userType = UserTypeEnum.DOCTOR.getUserType();
                    nickname = doctor.getName();
                }
            }

            WechatMessageRequestBean wechatMessage = new WechatMessageRequestBean();
            wechatMessage.setUserId(userId);
            wechatMessage.setUserType(userType);
            wechatMessage.setNickname(nickname);
            wechatMessage.setDrugUserId(drugUserId);
            Date date = DateUtil.stringToDate(uploadTime, DateUtil.DATE_FORMAT_YYYY_MM_DD_HH_MM_SS__SSS);
            long time = date.getTime();
            wechatMessage.setUploadTime(time);
            wechatMessage.setDoctorId(doctorId);
            wechatMessage.setWechatNumber(talker);
            wechatMessage.setTelephone(telephone);
            wechatMessage.setWechatMessageStatus(wechatMessageStatus);

            String s = EmojiParser.removeAllEmojis(content);
            wechatMessage.setMessage(s);
            wechatMessage.setImgPath(imgPath);
            wechatMessage.setWechatMessageType(type);

            Date sd = DateUtil.stringToDate(createTime, DateUtil.DATE_FORMAT_YYYY_MM_DD_HH_MM_SS__SSS);
            String dateTimeString = DateUtil.getDateTimeString(sd);
            wechatMessage.setMessageTime(dateTimeString);
            Date messageDate = DateUtil.stringToDate(createTime, DateUtil.DATE_FORMAT_YYYY_MM_DD_HH_MM_SS__SSS);
            long messageDateTime = messageDate.getTime();
            wechatMessage.setMessageTimestamp(messageDateTime);

            list.add(wechatMessage);

        }


        return list;
    }

    /**
     * 保存或者更新
     *
     * @param id
     * @param contactList
     */
    private void saveOrUpdateContactList(Long id, List<WechatAndroidContactRequestBean> contactList) {
        if (CollectionsUtil.isEmptyList(contactList)) {
            return;
        }

        List<String> userNameList = contactList.stream().map(WechatAndroidContactRequestBean::getUserName).distinct().collect(Collectors.toList());
        if (CollectionsUtil.isEmptyList(userNameList)) {
            return;
        }

        List<WechatAndroidContactRequestBean> addContactList = new ArrayList<>();
        List<WechatAndroidContactRequestBean> updateContactList = new ArrayList<>();

        List<WechatAndroidContactResponseBean> wechatContactList = wechatContactMapper.getWechatAndroidContactList(userNameList);
        if (CollectionsUtil.isEmptyList(wechatContactList)) {
            addContactList = contactList;
        } else {
            List<String> collectWechatNumer = wechatContactList.stream().map(WechatAndroidContactResponseBean::getUserName).distinct().collect(Collectors.toList());
            for (WechatAndroidContactRequestBean c : contactList) {
                String userName = c.getUserName();
                if (collectWechatNumer.contains(userName)) {
                    updateContactList.add(c);
                } else {
                    addContactList.add(c);
                }
            }

        }



        if (CollectionsUtil.isNotEmptyList(addContactList)) {
            wechatContactMapper.batchInsert(id, addContactList);
        }


        if (CollectionsUtil.isNotEmptyList(updateContactList)) {
            updateContactList.forEach(c -> {
                wechatContactMapper.updateWechatContact(id, c);
            });
        }
    }

    /**
     * 得到联系人入库数据
     * @param contactList
     * @return
     */
    private List<WechatAndroidContactRequestBean> getWechatContactList(List<CSVRecord> contactList) {
        if (CollectionsUtil.isEmptyList(contactList)) {
            return null;
        }

        List<WechatAndroidContactRequestBean> wechatContactList = new ArrayList<>();
        for (int i = 0; i < contactList.size(); i++) {
            if (i == 0) {
                continue;// 标题过滤
            }


            // userName,nickName,alias,conRemark,type
            WechatAndroidContactRequestBean wechatAndroidContact = new WechatAndroidContactRequestBean();
            String userName = contactList.get(i).get("userName");
            wechatAndroidContact.setUserName(userName);
            String nickName = contactList.get(i).get("nickName");
            if (EmojiUtil.containsEmoji(nickName)) {
                String s = EmojiUtil.handleEmojiStr(nickName);
                wechatAndroidContact.setNickName(s);
            } else {
                wechatAndroidContact.setNickName(nickName);
            }

            wechatAndroidContact.setAlias(contactList.get(i).get("alias"));

            String conRemark = contactList.get(i).get("conRemark");
            Matcher matcher = RegularUtils.getMatcher(RegularUtils.MATCH_ELEVEN_NUM, conRemark);
            if (!matcher.find()) {
                wechatAndroidContact.setDoctorId(0L);
                wechatAndroidContact.setTelephone("");
            } else {
                String telephone = matcher.group();
                Long doctorId = doctorMapper.getDoctorIdByMobile(telephone);
                if (doctorId == null) {
                    doctorId = 0L;
                }
                wechatAndroidContact.setDoctorId(doctorId);
                wechatAndroidContact.setTelephone(telephone);
            }

            wechatAndroidContact.setConRemark(conRemark);
            wechatAndroidContact.setType(contactList.get(i).get("type"));
            wechatContactList.add(wechatAndroidContact);
        }

        return wechatContactList;
    }

    /**
     * 得到微信群联系人入库数据
     * @param chatroomContactStrList
     * @return
     */
    private List<WechatAndroidChatroomContactRequestBean> getWechatChatroomConcatList(Long drugUserId, List<CSVRecord> chatroomContactStrList) {
        if (CollectionsUtil.isEmptyList(chatroomContactStrList)){
            return null;
        }

        List<WechatAndroidChatroomContactRequestBean> list = new ArrayList<>();

        DrugUser drugUser = drugUserRepository.findFirstById(drugUserId);
        for (CSVRecord csvRecord : chatroomContactStrList) {
            String chatRoomId = csvRecord.get("chatroomname");
            String chatRoomName = wechatContactMapper.getNickNameByUseName(chatRoomId);
            String memberIdListStr = csvRecord.get("memberlist");
            String displayNameListStr = csvRecord.get("displayname");
            String roomOwnerId = csvRecord.get("roomowner");
            String selfDisplayName = csvRecord.get("selfDisplayName");
            if (StringUtil.isNotEmpty(chatRoomName) && EmojiUtil.containsEmoji(chatRoomName)){
                chatRoomName = EmojiUtil.filterEmoji(chatRoomName);
            }


            if (StringUtil.isNotEmpty(selfDisplayName) && EmojiUtil.containsEmoji(selfDisplayName)){
                selfDisplayName = EmojiUtil.filterEmoji(selfDisplayName);
            }






            
            // 群成员ID以 ; 分割，名字以 、 分割，两个数量如果不一致给出错误提示
            String[] memberIdList = this.checkMemberIdListStr(memberIdListStr);

            List<WechatAndroidContactResponseBean> wechatAndroidContactList = wechatContactMapper.getWechatAndroidContactList(Arrays.asList(memberIdList));
            if (CollectionsUtil.isEmptyList(wechatAndroidContactList)){
                throw new BusinessException(ErrorEnum.ERROR, "群联系人还未导入！");
            }

            // 群聊的昵称和群成员的昵称不在群里面获取，因为有的群备注获取不到，统一到联系人里面去获取
//            String[] displayNameList = this.checkDisplayNameListStr(displayNameListStr);
//            if (memberIdList.length !=displayNameList.length){
//                throw new BusinessException(ErrorEnum.ERROR, "群成员昵称和备注中不能包含符号、");
//            }

            for (int i = 0; i < memberIdList.length; i++) {
                String memberId = memberIdList[i];
                String displayName = "";
                Optional<String> first = wechatAndroidContactList.stream().filter(w -> (memberId.equals(w.getUserName()))).map(WechatAndroidContactResponseBean::getConRemark).findFirst();
                if (first.isPresent()){
                    displayName = first.get();
                }
                WechatAndroidChatroomContactRequestBean chatRoomConcat = new WechatAndroidChatroomContactRequestBean();
                chatRoomConcat.setChatroomId(chatRoomId);
                chatRoomConcat.setChatroomName(chatRoomName);
                chatRoomConcat.setMemberId(memberId);
                chatRoomConcat.setMemberName(displayName);
                chatRoomConcat.setRoomOwnerId(roomOwnerId);
                chatRoomConcat.setRoomDrugUserName(selfDisplayName);
                chatRoomConcat.setDrugUserId(drugUserId);
                chatRoomConcat.setDrugUserName(drugUser.getName());
                Long doctorId = 0L;
                String doctorName = "";
                String telephone = "";
                Matcher matcher = RegularUtils.getMatcher(RegularUtils.MATCH_ELEVEN_NUM, displayName);
                if (matcher.find()) {
                    telephone = matcher.group();
                    Doctor doctor = doctorMapper.findTopByMobile(telephone);
                    if (doctor != null) {
                        doctorId = doctor.getId();
                        doctorName = doctor.getName();
                    }
                }
                chatRoomConcat.setDoctorId(doctorId);
                chatRoomConcat.setDoctorName(doctorName);
                chatRoomConcat.setTelephone(telephone);

                list.add(chatRoomConcat);
            }
        }

        return list;

    }


    /**
     * 检查群成员ID字段，返回全成员ID数组
     * @param memberIdListStr
     * @return
     */
    private String[] checkMemberIdListStr(String memberIdListStr) {
        if (StringUtil.isEmpty(memberIdListStr)){
            throw new BusinessException(ErrorEnum.ERROR, "群成员ID字段不能为空！");
        }

        String[] memberIdList = memberIdListStr.split(";");
        if (CollectionsUtil.isEmptyArray(memberIdList)){
            throw new BusinessException(ErrorEnum.ERROR, "群成员ID不能为空！");
        }

        return memberIdList;

    }


    /**
     * 检查群成员ID字段，返回全成员ID数组
     * @param displayNameListStr
     * @return
     */
    private String[] checkDisplayNameListStr(String displayNameListStr) {
        if (StringUtil.isEmpty(displayNameListStr)){
            throw new BusinessException(ErrorEnum.ERROR, "群成员名字段不能为空！");
        }

        String[] displayNameList = displayNameListStr.split("、");
        if (CollectionsUtil.isEmptyArray(displayNameList)){
            throw new BusinessException(ErrorEnum.ERROR, "群成员名不能为空！");
        }

        return displayNameList;

    }


    /**
     * 检查文件
     *
     * @param file
     * @return 成功返回代表ID
     */
    private Long checkWechatUserFile(MultipartFile file) {

        String originalFilename = file.getOriginalFilename();
        if (StringUtil.isEmpty(originalFilename)) {
            throw new FileFormatException(ErrorEnum.ERROR, "文件名称不能为空");
        }

        if (!originalFilename.endsWith(RegularUtils.EXTENSION_CSV)) {
            throw new FileFormatException(ErrorEnum.ERROR, "只能上传CSV文件");
        }

        String fileName = originalFilename.substring(0, originalFilename.lastIndexOf("."));
        if (StringUtil.isEmpty(fileName)) {
            throw new FileFormatException(ErrorEnum.ERROR, "文件名称不能为空");
        }

        String[] splitStr = fileName.split("Ξ");
        if (CollectionsUtil.isEmptyArray(splitStr)) {
            throw new FileFormatException(ErrorEnum.ERROR, "不合法的文件命名，必须包含代表微信号");
        }
        String wechatNumer = splitStr[0];

        Long drugUserId = drugUserWechatMapper.getDrugUserIdByWechat(wechatNumer);
        if (drugUserId == null || drugUserId == 0) {
            throw new FileFormatException(ErrorEnum.ERROR, "代表的微信号还未进行绑定");
        }


        return drugUserId;
    }


    /**
     * CSV文件InputStream转成字符串list
     *
     * @param inputStream
     * @param headers     表头
     * @return
     */
    private List<CSVRecord> handleCsvFile(InputStream inputStream, String[] headers) {
        if (inputStream == null) {
            return null;
        }

        List<CSVRecord> records = CSVUtils.readCSV(inputStream, headers);

        // 第一行是标题，要去掉
        if (CollectionsUtil.isNotEmptyList(records)){
            records.remove(0);
        }


        return records;
    }

}
