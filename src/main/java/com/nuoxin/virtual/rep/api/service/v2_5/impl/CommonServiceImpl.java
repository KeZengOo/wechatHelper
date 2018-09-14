package com.nuoxin.virtual.rep.api.service.v2_5.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.nuoxin.virtual.rep.api.mybatis.DrugUserMapper;
import com.nuoxin.virtual.rep.api.service.v2_5.CommonService;

@Service
public class CommonServiceImpl implements CommonService {
	
	@Resource
	private DrugUserMapper drugUserMapper;

	@Override
	public List<Long> getSubordinateIds(String leaderPath) {
		return drugUserMapper.getSubordinateIdsByLeaderPath(leaderPath);
	}
}
