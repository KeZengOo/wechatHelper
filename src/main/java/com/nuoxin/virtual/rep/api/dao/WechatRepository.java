package com.nuoxin.virtual.rep.api.dao;

import com.nuoxin.virtual.rep.api.entity.WechatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 微信消息相关
 * @author tiancun
 */
public interface WechatRepository extends JpaRepository<WechatMessage, Long>{



}
