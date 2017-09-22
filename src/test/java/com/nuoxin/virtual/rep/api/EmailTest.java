package com.nuoxin.virtual.rep.api;

import com.nuoxin.virtual.rep.api.service.EmailService;
import org.apache.ibatis.jdbc.Null;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by fenggang on 9/22/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class EmailTest {

    @Autowired
    private EmailService emailService;

    @Test
    public void email(){
        emailService.sendEmailCode(null);
    }
}
