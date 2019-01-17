package com.nuoxin.virtual.rep.api.service.v2_5.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	
	private static final Logger logger = LoggerFactory.getLogger(CommonServiceImpl.class);
	private static final SimpleDateFormat FMT=new SimpleDateFormat("yyyy-MM-dd");
	
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
	
	@Override
	public String getLeaderPathById(Long drugUserId) {
		return drugUserMapper.getLeaderPathById(drugUserId);
	}
	
	@Override
	public String alterLastVisitTimeContent(long interval) {
		String str = "";
		long minuteInterval = interval / 60000;
		if (minuteInterval < 60) { // 小于 60 显示分钟
			str = str.concat(String.valueOf(minuteInterval)).concat("分钟");
		} else if (minuteInterval < 1440) { // 小于 1440 显示 小时
			str = str.concat(String.valueOf(minuteInterval / 60)).concat("小时");
		} else { // 超过 1440 显示 天
			str = str.concat(String.valueOf(minuteInterval / 1440)).concat("天");
		}

		return str.concat("前");
	}
	
	@Override
	public String alterNextVisitTimeContent(Date nextVisitTime) {
		String str = "";
		long interval = this.getDelta(nextVisitTime) / 86400000; // 转换成天
		if (interval > 0) {
			str = str.concat(String.valueOf(interval)).concat("天后");
		} else if (interval < 0) {
			str = str.concat(String.valueOf(interval * -1)).concat("天前");
		} else {
			str = "今天";
		}
		
		return str;
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////
	
	private long getDelta(Date nextVisitTime) {
		String next = FMT.format(nextVisitTime);
		String current = FMT.format(new Date(System.currentTimeMillis()));

		long delta = 0;
		try {
			Date currentDate = FMT.parse(current);
			Date nextVisitDate = FMT.parse(next);
			delta = nextVisitDate.getTime() - currentDate.getTime();
		} catch (ParseException e) {
			logger.error("时间格式转换异常", e);
		}

		return delta;
	}
	
	public static void main(String[] args) throws ParseException {
		CommonService s = new CommonServiceImpl();
		
		String result = s.alterNextVisitTimeContent(new Date());
		System.out.println(result);
		
		result = s.alterNextVisitTimeContent(FMT.parse("2018-10-12 23:59:59"));
		System.out.println(result);
		
		result = s.alterNextVisitTimeContent(FMT.parse("2018-10-09 23:59:59"));
		System.out.println(result);
	}
}
