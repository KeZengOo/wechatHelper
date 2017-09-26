package com.nuoxin.virtual.rep.api.dao;


import com.nuoxin.virtual.rep.api.entity.Message;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * 微信消息相关
 * @author tiancun
 */
public interface MessageRepository extends JpaRepository<Message, Long>,JpaSpecificationExecutor<Message> {



    @Query(value = "select count(distinct doctor_id) from virtual_message where drug_user_id = :drugUserId and message_type = :messageType and date_format(message_time,'%Y-%m-%d') = date_format(NOW(),'%Y-%m-%d') ", nativeQuery = true)
    Integer messageCount(@Param(value = "drugUserId") Long drugUserId, @Param(value = "messageType") Integer messageType);




    List<Message> findByUserIdAndUserTypeOrderByMessageTimeDesc(Pageable pageable, Long userId, Integer userType);


    /**
     * 消息联系人，微信和短信
     * @param drugUserId
     * @param offset
     * @param pageSize
     * @return
     */
    @Query(value = "select t.* from (\n" +
            "select * from virtual_message order by message_time desc\n" +
            ") t where t.drug_user_id = :drugUserId and doctor_id in (select id from virtual_doctor vd where vd.drug_user_ids like :drugUserIdStr) group by t.doctor_id, t.message_type order by t.message_time desc limit :offset,:pageSize ;\n" +
            "\n", nativeQuery = true)
    List<Message> getMessageList(@Param(value = "drugUserId") Long drugUserId,@Param(value = "drugUserIdStr") String drugUserIdStr,@Param(value = "offset") Integer offset, @Param(value = "pageSize") Integer pageSize);


    /**
     * 消息联系人总数，微信和短信
     * @param drugUserId
     * @return
     */
    @Query(value = "select count(1) from (\n" +
            "select * from (\n" +
            "select * from virtual_message order by message_time desc\n" +
            ") t where t.drug_user_id = :drugUserId and doctor_id in (select id from virtual_doctor vd where vd.drug_user_ids like :drugUserIdStr) group by t.doctor_id, t.message_type order by t.message_time desc \n" +
            "\n" +
            ") a" , nativeQuery = true)
    Integer getMessageListCount(@Param(value = "drugUserId") Long drugUserId,@Param(value = "drugUserIdStr") String drugUserIdStr);



    @Query(value = "select distinct vm.* from virtual_message vm ,(\n" +
            "select  MAX(message_time) as maxMessageTime,message_type,user_id,user_type as userType from virtual_message where user_type=2 GROUP BY user_id, message_type\n" +
            "\n" +
            ") t where vm.user_id = t.user_id and vm.message_time = t.maxMessageTime\n" +
            "and user_type=2\n" +
            "and vm.user_id in\n" +
            "(\n" +
            "select id from virtual_doctor vd where vd.drug_user_ids like '%1%'\n" +
            ")\n" +
            "order by message_time desc limit 0,1", nativeQuery = true)
    List<Message> test();
}
