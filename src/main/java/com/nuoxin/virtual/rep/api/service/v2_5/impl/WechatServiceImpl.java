package com.nuoxin.virtual.rep.api.service.v2_5.impl;

import com.nuoxin.virtual.rep.api.common.enums.ErrorEnum;
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
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.wechat.WechatAndroidContactRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.wechat.WechatAndroidMessageRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.wechat.WechatMessageRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.wechat.WechatAndroidContactResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.wechat.WechatAndroidUploadTimeResponseBean;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

    /**
     * 每次批量插入的数量
     */
    public static final int BATCH_INSERT_SIZE = 1000;



    @Override
    public void handleWechatUserFile(MultipartFile file, WechatAndroidMessageRequestBean bean) {
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

            this.saveOrUpdateContactList(drugUserId, bean.getUploadFileTime(), contactList);
        }

        this.reUpdateContact();

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
        List<CSVRecord> contactStrList = this.handleCsvFile(inputStream, new String[]{"talker", "content", "createTime", "imgPath", "isSend", "type"});

        List<WechatMessageRequestBean> wechatMessageList = this.getWechatMessageList(drugUserId, bean.getUploadFileTime(), contactStrList);
        this.saveOrUpdateWechatMessageList(wechatMessageList);


        this.reUpdateMessage();
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
//            if (EmojiUtil.containsEmoji(message)){
//                String s1 = EmojiParser.removeAllEmojis(message);
//                final String s = EmojiUtil.handleEmojiStr(message);
//                wechatMessage.setMessage(s);
//            }
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

    private List<WechatMessageRequestBean> getWechatMessageList(Long drugUserId, String uploadTime, List<CSVRecord> contactList) {
        if (CollectionsUtil.isEmptyList(contactList)) {
            return null;
        }

        List<String> drugUserWechat = drugUserWechatMapper.getDrugUserWechat(drugUserId);
        if (CollectionsUtil.isEmptyList(drugUserWechat)) {
            throw new FileFormatException(ErrorEnum.ERROR, "代表还没有绑定微信号");
        }


        List<WechatMessageRequestBean> list = new ArrayList<>();

        DrugUser drugUser = drugUserRepository.findFirstById(drugUserId);

        for (int i = 0; i < contactList.size(); i++) {
            if (i == 0) {
                continue;// 标题过滤
            }

            // 格式：talker,content,createTime,imgPath,isSend,type

            String talker = contactList.get(i).get("talker");
            String content = contactList.get(i).get("content");
            String createTime = contactList.get(i).get("createTime");
            String imgPath = contactList.get(i).get("imgPath");
            String isSend = contactList.get(i).get("isSend");
            String type = contactList.get(i).get("type");

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
//            if (EmojiUtil.containsEmoji(content)){
//                String s = EmojiUtil.handleEmojiStr(content);
//                wechatMessage.setMessage(s);
//            }else {
//                wechatMessage.setMessage(content);
//            }

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
    private void saveOrUpdateContactList(Long id, String uploadTime, List<WechatAndroidContactRequestBean> contactList) {
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


        Date date = DateUtil.stringToDate(uploadTime, DateUtil.DATE_FORMAT_YYYY_MM_DD_HH_MM_SS__SSS);
        long time = date.getTime();
        if (CollectionsUtil.isNotEmptyList(addContactList)) {
            wechatContactMapper.batchInsert(id, time, addContactList);
        }


        if (CollectionsUtil.isNotEmptyList(updateContactList)) {
            updateContactList.forEach(c -> {
                wechatContactMapper.updateWechatContact(id, c);
            });
        }
    }


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
                // 先去掉异常，因为现在好多医生还没有备注上手机号
                //throw new FileFormatException(ErrorEnum.ERROR, "微信号为："+ userName + "联系人备注中没有包含手机号");
                wechatAndroidContact.setDoctorId(0L);
                wechatAndroidContact.setTelephone("");
            } else {
                String telephone = matcher.group();
                Long doctorId = doctorMapper.getDoctorIdByMobile(telephone);
//                if (doctorId == null || doctorId == 0){
//                    throw new FileFormatException(ErrorEnum.ERROR, "微信号为："+ userName + "联系人备注中包含的手机号对应医生不存在");
//                }
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

        String[] splitStr = fileName.split("_");
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
        return records;
    }

}
