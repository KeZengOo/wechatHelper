package com.nuoxin.virtual.rep.api.dao;

import com.nuoxin.virtual.rep.api.entity.Email;
import com.nuoxin.virtual.rep.api.entity.SmsTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by fenggang on 9/18/17.
 */
public interface EmailRepository extends JpaRepository<Email,Long>,JpaSpecificationExecutor<Email> {

}
