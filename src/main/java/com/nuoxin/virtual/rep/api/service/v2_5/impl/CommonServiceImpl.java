package com.nuoxin.virtual.rep.api.service.v2_5.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.nuoxin.virtual.rep.api.mybatis.DrugUserMapper;
import com.nuoxin.virtual.rep.api.service.v2_5.CommonService;
import com.nuoxin.virtual.rep.api.web.controller.response.DrugUserResponseBean;

/**
 * 通用接口实现类
 * @author xiekaiyu
 */
@Service
public class CommonServiceImpl implements CommonService {
	
	@Resource
	private DrugUserMapper drugUserMapper;

	@Override
	public List<Long> getSubordinateIds(String leaderPath) {
		return drugUserMapper.getSubordinateIdsByLeaderPath(leaderPath);
	}

	@Override
	public List<DrugUserResponseBean> getSubordinates(String leaderPath) {
		return drugUserMapper.getSubordinatesByLeaderPath(leaderPath);
	}
}
