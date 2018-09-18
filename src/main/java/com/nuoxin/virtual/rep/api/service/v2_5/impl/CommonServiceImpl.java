package com.nuoxin.virtual.rep.api.service.v2_5.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
	
	@Override
	public String getLeaderPathById(Long drugUserId) {
		return drugUserMapper.getLeaderPathById(drugUserId);
	}
	
	/**
	 * 将 long 类型拜访时间 -> 文字形式
	 * @param delta
	 * @return
	 */
	@Override
	public String alterVisitTimeContent (long delta) {
		// 转换成分钟间隔
		long minuteInterval = delta / 60000;
		String str = "";
		if (minuteInterval > 0) {
			str = this.doAlterVisitTimeContent(minuteInterval);
			str = str.concat("前");
		} else {
			// 负数转正数
			minuteInterval = -1 * minuteInterval;
			str = this.doAlterVisitTimeContent(minuteInterval);
			str = str.concat("后");
		}
		
		return str;
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 *  将 long 类型拜访时间 -> 文字形式 真正转换
	 * @param minuteInterval
	 * @return
	 */
	private String doAlterVisitTimeContent(long minuteInterval) {
		String str = "";
		if (minuteInterval < 60) {
			str = str.concat(String.valueOf(minuteInterval)).concat("分钟");
		} else if (minuteInterval < 1440) {
			BigDecimal result = BigDecimal.valueOf(minuteInterval).divide(BigDecimal.valueOf(60), 2, RoundingMode.HALF_UP);
			str = str.concat(String.valueOf(result.doubleValue())).concat("小时");
		} else {
			BigDecimal result = BigDecimal.valueOf(minuteInterval).divide(BigDecimal.valueOf(1440), 2, RoundingMode.HALF_UP);
			str = str.concat(String.valueOf(result.doubleValue())).concat("天");
		}
		
		return str;
	}
}
