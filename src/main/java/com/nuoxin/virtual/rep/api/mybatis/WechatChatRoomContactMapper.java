package com.nuoxin.virtual.rep.api.mybatis;

import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.wechat.WechatAndroidChatroomContactRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.wechat.WechatAndroidContactRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.wechat.WechatAndroidContactResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.wechat.WechatAndroidUploadTimeResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.wechat.WechatChatRoomMemberResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.v3_0.WechatChatRoomResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 微信群聊联系人
 * @author tiancun
 * @date 2018-12-04
 */
@Repository
public interface WechatChatRoomContactMapper {

    /**
     * 删除微信群聊存在的联系
     * @param chatRoomIdList
     */
    void deleteByChatRoomIdList(@Param(value = "chatRoomIdList") List<String> chatRoomIdList);

    /**
     * 批量插入
     * @param list
     */
    void batchInsert(List<WechatAndroidChatroomContactRequestBean> list);

    /**
     * 得到微信群成员
     * @param chatRoomId
     * @return
     */
    List<WechatChatRoomMemberResponseBean> getWechatChatRoomMemberList(@Param(value = "chatRoomId") String chatRoomId);


    /**
     * 群列表
     * @param drugUserId
     * @param doctorId
     * @return
     */
    List<WechatChatRoomResponse> getWechatChatRoomList(@Param(value = "drugUserId") Long drugUserId,@Param(value = "doctorId") Long doctorId);

    /**
     * 更新群的名称
     */
    void updateAllChatRoomName();

}