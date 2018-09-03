package com.nuoxin.virtual.rep.api;

import com.nuoxin.virtual.rep.api.service.DoctorCallService;
import com.nuoxin.virtual.rep.api.service.SmsSendService;
import com.nuoxin.virtual.rep.api.web.controller.request.SmsSendRequestBean;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("dev")
public class VirtualRepApiApplicationTests {

	@Autowired
	private DoctorCallService doctorCallService;

	@Test
	public void contextLoads() {
	}

}
