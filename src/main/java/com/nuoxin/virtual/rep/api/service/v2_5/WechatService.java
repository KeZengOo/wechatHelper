package com.nuoxin.virtual.rep.api.service.v2_5;

import com.nuoxin.virtual.rep.api.entity.DrugUser;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.wechat.WechatAndroidMessageRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.wechat.WechatAndroidUploadTimeResponseBean;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author tiancun
 * @date 2018-12-03
 */
public interface WechatService {

    /**
     * 处理安卓微信导入的联系人
     * @param file
     */
    void handleWechatUserFile(MultipartFile file);


    /**
     * 处理安卓群聊微信导入的联系人
     * @param file
     */
    void handleChatroomWechatUserFile(MultipartFile file);


    /**
     * 处理安卓微信导入的联系人
     * @param file
     * @param bean
     */
    void handleWechatMessageFile(MultipartFile file, WechatAndroidMessageRequestBean bean);


    WechatAndroidUploadTimeResponseBean getWechatAndroidUploadTime(String wechatNumber);

}
