package com.nuoxin.virtual.rep.api.service.v2_5;

import java.util.List;

/**
 * 通用业务接口
 * @author xiekaiyu
 */
public interface CommonService {
	
	/**
	 * 根据 leaderPath 获取所有的
	 * @param leaderPath
	 * @return
	 */
	 List<Long> getSubordinateIds(String leaderPath);
}
