package com.nuoxin.virtual.rep.api.service.v2_5.impl;

import com.nuoxin.virtual.rep.api.common.enums.ErrorEnum;
import com.nuoxin.virtual.rep.api.common.exception.FileFormatException;
import com.nuoxin.virtual.rep.api.dao.DoctorRepository;
import com.nuoxin.virtual.rep.api.dao.DrugUserRepository;
import com.nuoxin.virtual.rep.api.entity.Doctor;
import com.nuoxin.virtual.rep.api.entity.DrugUser;
import com.nuoxin.virtual.rep.api.enums.MessageTypeEnum;
import com.nuoxin.virtual.rep.api.enums.UserTypeEnum;
import com.nuoxin.virtual.rep.api.mybatis.DoctorMapper;
import com.nuoxin.virtual.rep.api.mybatis.DrugUserWechatMapper;
import com.nuoxin.virtual.rep.api.mybatis.MessageMapper;
import com.nuoxin.virtual.rep.api.mybatis.WechatContactMapper;
import com.nuoxin.virtual.rep.api.service.v2_5.WechatService;
import com.nuoxin.virtual.rep.api.utils.*;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.wechat.WechatAndroidContactRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.wechat.WechatAndroidMessageRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.wechat.WechatMessageRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.wechat.WechatAndroidContactResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.wechat.WechatAndroidUploadTimeResponseBean;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import org.apache.commons.csv.CSVRecord;
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

/**
 * @author tiancun
 * @date 2018-12-03
 */
@Service
public class WechatServiceImpl implements WechatService {

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
        List<CSVRecord> contactStrList = this.handleCsvFile(inputStream, new String[]{"userName","nickName","alias","conRemark","type"});
        List<WechatAndroidContactRequestBean> contactList = this.getWechatContactList(contactStrList);

        if (CollectionsUtil.isNotEmptyList(contactList)){

            this.saveOrUpdateContactList(drugUserId,bean.getUploadFileTime(), contactList);
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
        List<CSVRecord> contactStrList = this.handleCsvFile(inputStream, new String[]{"talker","content","createTime","imgPath","isSend","type"});

        List<WechatMessageRequestBean> wechatMessageList = this.getWechatMessageList(drugUserId,bean.getUploadFileTime(),contactStrList);
        this.saveOrUpdateWechatMessageList(wechatMessageList);


    }

    @Override
    public WechatAndroidUploadTimeResponseBean getWechatAndroidUploadTime(String wechatNumber) {
        WechatAndroidUploadTimeResponseBean wechatAndroidUploadTimeResponseBean = new WechatAndroidUploadTimeResponseBean();
        WechatAndroidUploadTimeResponseBean uploadTime = wechatContactMapper.getUploadTime(wechatNumber);
        if (uploadTime == null){
            return wechatAndroidUploadTimeResponseBean;
        }


        Long contactUploadTimeValue = uploadTime.getContactUploadTimeValue();
        Long messageUploadTimeValue = uploadTime.getMessageUploadTimeValue();
        if (contactUploadTimeValue != null && contactUploadTimeValue > 0){
            Date contactDate = new Date(contactUploadTimeValue);
            String contactDateMillisecond = DateUtil.getDateMillisecondString(contactDate);
            uploadTime.setContactUploadTime(contactDateMillisecond);
        }

        if (messageUploadTimeValue != null && messageUploadTimeValue > 0){
            Date messageDate = new Date(messageUploadTimeValue);
            String messageDateMillisecond = DateUtil.getDateMillisecondString(messageDate);
            uploadTime.setMessageUploadTime(messageDateMillisecond);
        }


        return uploadTime;
    }


    private void saveOrUpdateWechatMessageList(List<WechatMessageRequestBean> wechatMessageList) {
        if (CollectionsUtil.isEmptyList(wechatMessageList)){
            return;
        }

        /**
         * 得到去重后的消息列表
         */
        List<WechatMessageRequestBean> duplicateRemovalWechatMessageList = this.getDuplicateRemovalWechatMessageList(wechatMessageList);
        if (CollectionsUtil.isEmptyList(duplicateRemovalWechatMessageList)){
            return;
        }

        int size = duplicateRemovalWechatMessageList.size();
        // 每次最多插入1000条
        int totalPage = PageUtil.getTotalPage(size, BATCH_INSERT_SIZE);
        List<WechatMessageRequestBean> subWechatMessageRequestBean = null;
        for (int i = 0; i < totalPage; i++){
            if (i==(totalPage-1)){

                subWechatMessageRequestBean = duplicateRemovalWechatMessageList.subList(i * BATCH_INSERT_SIZE, i * BATCH_INSERT_SIZE + (size - i *BATCH_INSERT_SIZE));
            }else{
                subWechatMessageRequestBean = duplicateRemovalWechatMessageList.subList(i * BATCH_INSERT_SIZE, i * BATCH_INSERT_SIZE + BATCH_INSERT_SIZE);
            }

            List<WechatMessageRequestBean> addWechatMessageList= new ArrayList<>(subWechatMessageRequestBean);
            messageMapper.batchInsertWechatMessage(addWechatMessageList);

        }

    }

    private List<WechatMessageRequestBean> getDuplicateRemovalWechatMessageList(List<WechatMessageRequestBean> wechatMessageList) {

        if (CollectionsUtil.isEmptyList(wechatMessageList)){
            return wechatMessageList;
        }
        List<WechatMessageRequestBean> list = new ArrayList<>();
        for (WechatMessageRequestBean wechatMessage:wechatMessageList){
            // 处理emoji表情
            String message = wechatMessage.getMessage();
            if (EmojiUtil.containsEmoji(message)){
                final String s = EmojiUtil.handleEmojiStr(message);
                wechatMessage.setMessage(s);
            }
            Integer count = messageMapper.getCountByTypeAndWechatNumAndTime(MessageTypeEnum.WECHAT.getMessageType(), wechatMessage.getWechatNumber(), wechatMessage.getMessageTime());
            if (count !=null && count > 0){
                continue;
            }
            list.add(wechatMessage);
        }


        return list;
    }

    private List<WechatMessageRequestBean> getWechatMessageList(Long drugUserId,String uploadTime, List<CSVRecord> contactList) {
        if (CollectionsUtil.isEmptyList(contactList)){
            return null;
        }

        List<String> drugUserWechat = drugUserWechatMapper.getDrugUserWechat(drugUserId);
        if (CollectionsUtil.isEmptyList(drugUserWechat)){
            throw new FileFormatException(ErrorEnum.ERROR, "代表还没有绑定微信号");
        }


        List<WechatMessageRequestBean> list = new ArrayList<>();

        DrugUser drugUser = drugUserRepository.findFirstById(drugUserId);

        for (int i = 0; i < contactList.size(); i++){
            if (i == 0){
                continue;// 标题过滤
            }

            // 格式：talker,content,createTime,imgPath,isSend,type

            String talker = contactList.get(i).get("talker");
            String content = contactList.get(i).get("content");
            String createTime =contactList.get(i).get("createTime");
            String imgPath = contactList.get(i).get("imgPath");
            String isSend = contactList.get(i).get("isSend");
            String type = contactList.get(i).get("type");

            Long userId = 0L;
            Integer userType = 0;
            String nickname = "";
            Long doctorId = 0L;
            String telephone = "";
            String wechatMessageStatus = "";
            if (StringUtil.isEmpty(imgPath) || "0".equals(imgPath)){
                imgPath = "";
            }

            if (StringUtil.isNotEmpty(isSend) && "1".equals(isSend)){
                wechatMessageStatus = "发送";
            }

            if (StringUtil.isNotEmpty(isSend) && "0".equals(isSend)){
                wechatMessageStatus = "接收";
            }

            if (drugUserWechat.contains(talker)){
                userId = drugUser.getId();
                userType = UserTypeEnum.DRUG_USER.getUserType();
                nickname = drugUser.getName();

            }else {
                Long doctorIdByTalker = wechatContactMapper.getDoctorIdByWechatNumber(talker);
                Doctor doctor = doctorRepository.findFirstById(doctorId);
                if (doctor != null){
                    userId = doctorIdByTalker;
                    userType = UserTypeEnum.DOCTOR.getUserType();
                    nickname = doctor.getName();
                    doctorId = doctorIdByTalker;
                    telephone = doctor.getMobile();
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
            wechatMessage.setMessage(content);
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
     * @param id
     * @param contactList
     */
    private void saveOrUpdateContactList(Long id,String uploadTime, List<WechatAndroidContactRequestBean> contactList) {
        if (CollectionsUtil.isEmptyList(contactList)){
            return;
        }

        List<String> userNameList = contactList.stream().map(WechatAndroidContactRequestBean::getUserName).distinct().collect(Collectors.toList());
        if (CollectionsUtil.isEmptyList(userNameList)){
            return;
        }

        List<WechatAndroidContactRequestBean> addContactList = new ArrayList<>();
        List<WechatAndroidContactRequestBean> updateContactList = new ArrayList<>();

        List<WechatAndroidContactResponseBean> wechatContactList = wechatContactMapper.getWechatAndroidContactList(userNameList);
        if (CollectionsUtil.isEmptyList(wechatContactList)){
            addContactList = contactList;
        }else {
            List<String> collectWechatNumer = wechatContactList.stream().map(WechatAndroidContactResponseBean::getUserName).distinct().collect(Collectors.toList());
            for (WechatAndroidContactRequestBean c:contactList){
                String userName = c.getUserName();
                if (collectWechatNumer.contains(userName)){
                    updateContactList.add(c);
                }else {
                    addContactList.add(c);
                }
            }

        }


        Date date = DateUtil.stringToDate(uploadTime, DateUtil.DATE_FORMAT_YYYY_MM_DD_HH_MM_SS__SSS);
        long time = date.getTime();
        if (CollectionsUtil.isNotEmptyList(addContactList)){
            wechatContactMapper.batchInsert(id, time, addContactList);
        }


        if (CollectionsUtil.isNotEmptyList(updateContactList)){
            updateContactList.forEach(c->{
                wechatContactMapper.updateWechatContact(id, c);
            });
        }
    }


    private List<WechatAndroidContactRequestBean> getWechatContactList(List<CSVRecord> contactList) {
        if (CollectionsUtil.isEmptyList(contactList)){
            return null;
        }

        List<WechatAndroidContactRequestBean> wechatContactList = new ArrayList<>();
        for (int i = 0; i < contactList.size(); i++){
            if (i == 0){
                continue;// 标题过滤
            }


            // userName,nickName,alias,conRemark,type
            WechatAndroidContactRequestBean wechatAndroidContact = new WechatAndroidContactRequestBean();
            String userName = contactList.get(i).get("userName");
            wechatAndroidContact.setUserName(userName);
            wechatAndroidContact.setNickName(contactList.get(i).get("nickName"));
            wechatAndroidContact.setAlias(contactList.get(i).get("alias"));

            String conRemark = contactList.get(i).get("conRemark");
            Matcher matcher = RegularUtils.getMatcher(RegularUtils.MATCH_ELEVEN_NUM, conRemark);
            if (!matcher.find()){
                throw new FileFormatException(ErrorEnum.ERROR, "微信号为："+ userName + "联系人备注中没有包含手机号");
            }else {
                String telephone = matcher.group();
                Long doctorId = doctorMapper.getDoctorIdByMobile(telephone);
                if (doctorId == null || doctorId == 0){
                    throw new FileFormatException(ErrorEnum.ERROR, "微信号为："+ userName + "联系人备注中包含的手机号对应医生不存在");
                }
                wechatAndroidContact.setDoctorId(doctorId);
            }

            wechatAndroidContact.setConRemark(conRemark);
            wechatAndroidContact.setType(contactList.get(i).get("type"));
            wechatContactList.add(wechatAndroidContact);
        }

        return wechatContactList;
    }

    /**
     * 检查文件
     * @param file
     * @return 成功返回代表ID
     */
    private Long checkWechatUserFile(MultipartFile file) {


        String originalFilename = file.getOriginalFilename();
        if (StringUtil.isEmpty(originalFilename)){
            throw new FileFormatException(ErrorEnum.ERROR, "文件名称不能为空");
        }

        if (!originalFilename.endsWith(RegularUtils.EXTENSION_CSV)) {
            throw new FileFormatException(ErrorEnum.ERROR, "只能上传CSV文件");
        }

        String fileName = originalFilename.substring(0,originalFilename.lastIndexOf("."));
        if (StringUtil.isEmpty(fileName)){
            throw new FileFormatException(ErrorEnum.ERROR, "文件名称不能为空");
        }

        String[] splitStr = fileName.split("_");
        if (CollectionsUtil.isEmptyArray(splitStr)){
            throw new FileFormatException(ErrorEnum.ERROR, "不合法的文件命名，必须包含代表微信号");
        }
        String wechatNumer = splitStr[0];

        Long drugUserId = drugUserWechatMapper.getDrugUserIdByWechat(wechatNumer);
        if (drugUserId == null || drugUserId == 0){
            throw new FileFormatException(ErrorEnum.ERROR, "代表的微信号还未进行绑定");
        }


        return drugUserId;
    }


    /**
     * CSV文件InputStream转成字符串list
     * @param inputStream
     * @param headers 表头
     * @return
     */
    private List<CSVRecord> handleCsvFile(InputStream inputStream, String[] headers){
        if (inputStream == null){
            return null;
        }

        List<CSVRecord> records = CSVUtils.readCSV(inputStream, headers);
        return records;
    }

}