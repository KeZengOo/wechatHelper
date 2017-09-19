package com.nuoxin.virtual.rep.api.dao;


import com.nuoxin.virtual.rep.api.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * 微信消息相关
 * @author tiancun
 */
public interface MessageRepository extends JpaRepository<Message, Long>,JpaSpecificationExecutor<Message> {



    @Query(value = "select count(distinct user_id) wechatCount\n" +
            "from virtual_message vm\n" +
            "where user_type = :userType\n" +
            "and message_type = :messageType\n" +
            "and DATE_FORMAT(message_time,'%Y-%m-%d')=DATE_FORMAT(NOW(),'%Y-%m-%d')\n" +
            "and user_id in (\n" +
            "select distinct vd.id from virtual_doctor vd \n" +
            "inner join virtual_drug_user_doctor vdud\n" +
            "on vd.id = vdud.virtual_doctor_id \n" +
            "inner join virtual_drug_user vdu\n" +
            "on vdud.virtual_drug_user_id = vdu.id\n" +
            "and vdu.id = :id\n" +
            ")", nativeQuery = true)
    Integer messageCount(@Param(value = "id") Long drugUserId, @Param(value = "messageType") Integer messageType,@Param(value = "userType") Integer userType);

}
