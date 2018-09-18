package com.nuoxin.virtual.rep.api.mybatis;

import com.nuoxin.virtual.rep.api.entity.v2_5.StatisticsDrugNumResponse;
import com.nuoxin.virtual.rep.api.entity.v2_5.StatisticsParams;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 分享记录
 */
@Component
public interface ActivityShareMapper {

	/**
	 * 医生短信拜访数
	 * @param statisticsParams
	 * @return 返回记录总条数
	 */
	List<StatisticsDrugNumResponse> getMessageDoctorVisitCount(StatisticsParams statisticsParams);

	/**
	 * 内容服务人数
	 * @param statisticsParams
	 * @return 返回记录总条数
	 */
	List<StatisticsDrugNumResponse> getContentServiceCount(StatisticsParams statisticsParams);

	/**
	 * 内容发送人数
	 * @param statisticsParams
	 * @return 返回记录总条数
	 */
	List<StatisticsDrugNumResponse> getContentSendCount(StatisticsParams statisticsParams);

	/**
	 * 内容阅读人数
	 * @param statisticsParams
	 * @return 返回记录总条数
	 */
	List<StatisticsDrugNumResponse> getContentReadCount(StatisticsParams statisticsParams);

	/**
	 * 内容阅读时长
	 * @param statisticsParams
	 * @return 返回记录总条数
	 */
	List<StatisticsDrugNumResponse> getContentReadTimeCount(StatisticsParams statisticsParams);
}

