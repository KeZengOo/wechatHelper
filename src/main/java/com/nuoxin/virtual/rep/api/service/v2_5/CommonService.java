package com.nuoxin.virtual.rep.api.service.v2_5;

import java.util.List;

import org.apache.ibatis.annotations.Param;

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
	 List<DrugUserResponseBean> getSubordinates(@Param("leaderPath") String leaderPath);
	 
	 /**
	  * 根据时间 delta 转换文字信息
	  * @param delta
	  * @return 字符串
	  */
	 String alterVisitTimeContent (long delta);
}
