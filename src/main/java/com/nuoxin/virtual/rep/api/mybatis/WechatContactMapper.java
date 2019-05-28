package com.nuoxin.virtual.rep.api.mybatis;

import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.wechat.WechatAndroidContactRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.wechat.WechatAndroidContactResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.wechat.WechatAndroidUploadTimeResponseBean;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * 微信联系人
 * @author tiancun
 * @date 2018-12-04
 */
@Repository
public interface WechatContactMapper {

    /**
     * 根据微信号查询联系人列表
     * @param userNameList
     * @return
     */
    List<WechatAndroidContactResponseBean> getWechatAndroidContactList(@Param(value = "userNameList") List<String> userNameList);

    /**
     * 批量插入
     * @param drugUserId
     * @param list
     */
    void batchInsert(@Param(value = "drugUserId") Long drugUserId,
                     @Param(value = "list") List<WechatAndroidContactRequestBean> list);

    /**
     * 更新联系人
     * @param drugUserId
     * @param bean
     */
    void updateWechatContact(@Param(value = "drugUserId") Long drugUserId,@Param(value = "bean") WechatAndroidContactRequestBean bean);

    /**
     * 根据微信号获得医生ID
     * @param wechatNumber
     * @return
     */
    Long getDoctorIdByWechatNumber(@Param(value = "wechatNumber") String wechatNumber);

    /**
     * 根据微信号查找上次导入的聊天时间
     * @param wechatNumber
     * @return
     */
    WechatAndroidUploadTimeResponseBean getUploadTime(@Param(value = "wechatNumber") String wechatNumber);

    /**
     * 根据微信号获得昵称
     * @param userName 微信号
     * @return
     */
    String getNickNameByUseName(@Param(value = "userName") String userName);




}
