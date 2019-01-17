package com.nuoxin.virtual.rep.api.service.v2_5;

import com.nuoxin.virtual.rep.api.common.bean.PageResponseBean;
import com.nuoxin.virtual.rep.api.entity.DrugUser;
import com.nuoxin.virtual.rep.api.entity.v2_5.DynamicFieldResponse;
import com.nuoxin.virtual.rep.api.entity.v2_5.StatisticsParams;
import com.nuoxin.virtual.rep.api.entity.v2_5.StatisticsResponse;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.CallTimeResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.DoctorDetailsResponseBean;

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
	 * 得到电话统计
	 * @param statisticsParams
	 * @return
	 */
	CallTimeResponseBean getCallTime(StatisticsParams statisticsParams);

	/**
	 * 动态字段获取
	 * @param productId
	 * @param productName
	 * @return
	 */
	List<DynamicFieldResponse> getDynamicFieldByProductId(Long productId, String productName);


	/**
	 * 得到医生列表，用于下拉列表
	 * @param drugUserId
	 * @param productId
	 * @param doctorName
	 * @param limitNum
	 * @return
	 */
	List<DoctorDetailsResponseBean> getDoctorList(Long drugUserId, Long productId,String doctorName, Integer limitNum);
}
