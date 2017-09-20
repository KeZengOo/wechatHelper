package com.nuoxin.virtual.rep.api;

import com.nuoxin.virtual.rep.api.service.SmsSendService;
import com.nuoxin.virtual.rep.api.web.controller.request.SmsSendRequestBean;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class VirtualRepApiApplicationTests {

	@Autowired
	private SmsSendService smsSendService;

	@Test
	public void contextLoads() {
		SmsSendRequestBean bean = new SmsSendRequestBean();
		bean.setDrugUserId(0l);
		bean.setMobile("13581720607");
		bean.setTemplateId(1l);
		smsSendService.send(bean);
	}

}
