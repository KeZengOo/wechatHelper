package com.nuoxin.virtual.rep.api.service.v2_5.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.nuoxin.virtual.rep.api.service.v2_5.CommonService;
import com.nuoxin.virtual.rep.api.service.v2_5.VirtualDoctorPushService;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.push.PushListRequestBean;

@Service
public class VirtualDoctorPushServiceImpl implements VirtualDoctorPushService{
	
	@Resource
	private CommonService commonService;
	
	@Override
	public List<Object> getPushList(String leaderPath, PushListRequestBean pushRequest) {
		List<Long> subDrugUserIds = commonService.getSubordinateIds(leaderPath);
		return null;
	}

}
