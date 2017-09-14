package com.nuoxin.virtual.rep.api.service;

import com.nuoxin.virtual.rep.api.common.enums.ErrorEnum;
import com.nuoxin.virtual.rep.api.common.exception.FileFormatException;
import com.nuoxin.virtual.rep.api.dao.WechatRepository;
import com.nuoxin.virtual.rep.api.entity.WechatMessage;
import com.nuoxin.virtual.rep.api.enums.UserTypeEnum;
import com.nuoxin.virtual.rep.api.utils.ExcelUtils;
import com.nuoxin.virtual.rep.api.utils.RegularUtils;
import com.nuoxin.virtual.rep.api.web.controller.request.vo.WechatMessageVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 微信相关接口
 */
@Service
public class WechatService {

    private static Logger logger = LoggerFactory.getLogger(ExcelUtils.class);

    private static final String EXTENSION_XLS = "xls";

    private static final String EXTENSION_XLSX = "xlsx";

    private static final String MATCH_TELEPHONE = "^1\\d{10}$";

    //excel从哪行开始读取，这里excel标题占两行
    private static final Integer beginReadRow = 2;

    private static final String DRUG_USER_NICKNAME = "我";

    @Autowired
    private WechatRepository wechatRepository;


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



        List<WechatMessage> list = new ArrayList<>();
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
                String messageStatus = wechatMessageVo.getMessageStatus();
                String messageType = wechatMessageVo.getMessageType();
                String message = wechatMessageVo.getMessage();

                int userType = 0;
                String nickname = "";
                String telephone = "";
                if (wechatNickName != null && DRUG_USER_NICKNAME.equals(wechatNickName)) {
                    userType = UserTypeEnum.DRUG_USER.getUserType();
                    nickname = drugUserNickname;
                    telephone = drugUserTelephone;
                } else if (wechatNickName != null && !DRUG_USER_NICKNAME.equals(wechatNickName)) {
                    userType = UserTypeEnum.DOCTOR.getUserType();
                    String[] nicknameArray = wechatNickName.split("-");
                    if (null == nicknameArray || nicknameArray.length < 2) {
                        throw new FileFormatException(ErrorEnum.FILE_FORMAT_ERROR);
                    }
                    nickname = nicknameArray[0];
                    telephone = nicknameArray[01];
                }

                WechatMessage wechatMessage = new WechatMessage();
                wechatMessage.setUserType(userType);
                wechatMessage.setNickname(nickname);
                wechatMessage.setWechatNumber(wechatNumber);
                wechatMessage.setTelephone(telephone);
                wechatMessage.setMessageStatus(messageStatus);
                wechatMessage.setMessage(message);
                wechatMessage.setMessageType(messageType);
                wechatMessage.setWechatTime(wechatTime);
                wechatMessage.setCreateTime(new Date());
                list.add(wechatMessage);

            }
        }

        //批量保存微信聊天消息
        wechatRepository.save(list);

        success = true;
        return success;
    }


}
