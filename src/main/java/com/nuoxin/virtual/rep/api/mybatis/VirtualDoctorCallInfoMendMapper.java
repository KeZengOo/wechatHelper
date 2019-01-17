package com.nuoxin.virtual.rep.api.mybatis;

import java.util.List;

import com.nuoxin.virtual.rep.api.entity.v2_5.StatisticsDrugNumResponse;
import com.nuoxin.virtual.rep.api.entity.v2_5.StatisticsParams;
import org.apache.ibatis.annotations.Param;

import com.nuoxin.virtual.rep.api.entity.v2_5.CallVisitMendBean;
import com.nuoxin.virtual.rep.api.entity.v2_5.VirtualDoctorCallInfoParams;

/**
 * 电话拜访 Mapper
 * @author xiekaiyu
 */
public interface VirtualDoctorCallInfoMendMapper {
	
	/**
	 * 保存扩展信息
	 * @param params
	 * @return
	 */
	int saveVirtualDoctorCallInfoMend(VirtualDoctorCallInfoParams params);
	
	/**
	 * 根据 callIds 获取扩展信息
	 * @param callIds
	 * @return
	 */
	List<CallVisitMendBean> getCallVisitMendList(@Param(value = "callIds") List<Long> callIds);

	/**
	 * 根据通话结果获取医生数
	 * @param statisticsParams
	 * @return
	 */
	List<StatisticsDrugNumResponse> getDoctorNum(@Param(value = "statisticsParams")StatisticsParams statisticsParams,@Param(value = "contents")List<String> contents);
}

