package com.nuoxin.virtual.rep.api.service.v2_5;

import com.nuoxin.virtual.rep.api.common.bean.PageResponseBean;
import com.nuoxin.virtual.rep.api.entity.v2_5.DynamicFieldResponse;
import com.nuoxin.virtual.rep.api.entity.v2_5.StatisticsParams;
import com.nuoxin.virtual.rep.api.entity.v2_5.StatisticsResponse;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * 统计分析服务
 */
public interface StatisticalService {

	/**
	 * 拜访统计列表·列表
	 * @param statisticsParams
	 * @return
	 */
	List<StatisticsResponse> visitStatisticsList(StatisticsParams statisticsParams);

	/**
	 * 拜访统计列表·分页
	 * @param statisticsParams
	 * @return
	 */
	PageResponseBean<List<StatisticsResponse>> visitStatisticsPage(StatisticsParams statisticsParams);

	/**
	 * 医生拜访明细表·列表
	 * @param statisticsParams
	 * @return
	 */
	List<LinkedHashMap<String,Object>> doctorVisitDetailList(StatisticsParams statisticsParams);

	/**
	 * 医生拜访明细表·分页
	 * @param statisticsParams
	 * @return
	 */
	PageResponseBean<List<LinkedHashMap<String,Object>>> doctorVisitDetailPage(StatisticsParams statisticsParams);

	/**
	 * 动态字段获取
	 * @param productId
	 * @param productName
	 * @return
	 */
	List<DynamicFieldResponse> getDynamicFieldByProductId(Integer productId, String productName);
}
