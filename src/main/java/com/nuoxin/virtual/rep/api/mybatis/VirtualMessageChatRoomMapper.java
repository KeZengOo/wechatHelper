package com.nuoxin.virtual.rep.api.mybatis;

import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.wechat.WechatChatRoomMessageRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.v3_0.WechatChatRoomMessageRequest;
import com.nuoxin.virtual.rep.api.web.controller.response.v3_0.WechatChatRoomMessageResponse;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 微信群聊相关
 * @author tiancun
 * @date 2019-04-26
 */
public interface VirtualMessageChatRoomMapper {

    /**
     * 得到总数，判断是否存在
     * @param chatRoomId
     * @param memberId
     * @param wechatMessage
     * @param messageTime
     * @return
     */
    Integer getMessageCount(@Param(value = "chatRoomId") String chatRoomId,
                            @Param(value = "memberId") String memberId,
                            @Param(value = "wechatMessage") String wechatMessage,
                            @Param(value = "messageTime") String messageTime);


    /**
     * 批量插入微信群聊消息
     * @param list
     */
    void batchInsert(List<WechatChatRoomMessageRequestBean> list);

    /**
     * 更新群消息中的群成员
     */
    void updateWechatChatroomMember();

    /**
     * 群消息
     * @param request
     * @return
     */
    List<WechatChatRoomMessageResponse> getWechatChatRoomMessageList(WechatChatRoomMessageRequest request);


    /**
     * 群消息总数
     * @param request
     * @return
     */
    Integer getWechatChatRoomMessageListCount(WechatChatRoomMessageRequest request);


    /**
     * 查询历史的群聊消息
     * @return
     */
    List<WechatChatRoomMessageRequestBean> getReHandleWechatChatRoomMessageList();

}
