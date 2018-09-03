package com.nuoxin.virtual.rep.api.dao;

import com.nuoxin.virtual.rep.api.entity.SmsTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 短信发送模板
 * Created by fenggang on 9/18/17.
 */
public interface SmsTemplateRepository extends JpaRepository<SmsTemplate,Long>,JpaSpecificationExecutor<SmsTemplate> {

}
