package com.nuoxin.virtual.rep.api.service.v2_5;

import com.nuoxin.virtual.rep.api.common.bean.PageResponseBean;
import com.nuoxin.virtual.rep.api.entity.v2_5.StatisticsParams;
import com.nuoxin.virtual.rep.api.entity.v2_5.StatisticsResponse;

import java.util.List;

/**
 * 统计分析服务
 */
public interface StatisticalService {

	/**
	 * 拜访统计列表
	 * @param statisticsParams
	 * @return
	 */
	List<StatisticsResponse> visitStatisticsList(StatisticsParams statisticsParams);

	/**
	 * 拜访统计列表分页
	 * @param statisticsParams
	 * @return
	 */
	PageResponseBean<List<StatisticsResponse>> visitStatisticsPage(StatisticsParams statisticsParams);

}
