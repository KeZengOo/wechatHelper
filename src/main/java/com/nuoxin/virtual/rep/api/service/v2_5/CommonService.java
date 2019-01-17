package com.nuoxin.virtual.rep.api.service.v2_5;

import java.util.Date;
import java.util.List;

import com.nuoxin.virtual.rep.api.web.controller.response.DrugUserResponseBean;

/**
 * 通用业务接口
 * @author xiekaiyu
 */
public interface CommonService {
	
	/**
	 * 根据 leaderPath 获取所有的下属(直接 & 间接)  virtualDrugUserIds
	 * @param leaderPath
	 * @return
	 */
	 List<Long> getSubordinateIds(String leaderPath);
	 
	 /**
	  * 根据 leaderPath 获取所有的下属信息
	  * @param leaderPath
	  * @return
	  */
	 List<DrugUserResponseBean> getSubordinates(String leaderPath);
	 
	 /**
	  * 根据 drugUserId 获取 leaderPath
	  * @param drugUserId
	  * @return
	  */
	 String getLeaderPathById(Long drugUserId); 
	 
	/**
	 * 上次拜访时间,不保留小数<br>
	 * 按分钟,小时,天显示 
	 * @param minuteInterval
	 * @return
	 */
	String alterLastVisitTimeContent(long delta);

	/**
	 * 下次拜访时间<br>
	 * T = 下次跟进时间-当前日期。<br>
	 * 当T>0是，显示“T天后”；<br>
	 * 当T<0时，显示“T天前”；<br>
	 * 当T=0时，显示“今天”<br>
	 * @param nextVisitTime
	 * @return
	 */
	String alterNextVisitTimeContent(Date nextVisitTime);
}
