package com.nuoxin.virtual.rep.api.dao;


import com.nuoxin.virtual.rep.api.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 微信消息相关
 * @author tiancun
 */
public interface MessageRepository extends JpaRepository<Message, Long>,JpaSpecificationExecutor<Message> {



}
