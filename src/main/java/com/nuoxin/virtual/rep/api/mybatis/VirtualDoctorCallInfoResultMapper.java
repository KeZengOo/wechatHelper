package com.nuoxin.virtual.rep.api.mybatis;


import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.VisitResultResponseBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 电话拜访结果
 * @author tiancun
 * @date 2018-11-19
 */
public interface VirtualDoctorCallInfoResultMapper {

	/**
	 * 批量插入
	 * @param mendId
	 * @param resultIdList
	 */
	void batchInsert(@Param(value = "mendId") Long mendId,@Param(value = "resultIdList") List<Long> resultIdList);


	/**
	 * 获取拜访结果
	 * @param callId
	 * @return
	 */
	List<String> getVisitResultByCallId(@Param(value = "callId") Long callId);

	/**
	 * 获取所有的拜访结果
	 * @param callIdList
	 * @return
	 */
	List<VisitResultResponseBean> getVisitResultList(@Param(value = "callIdList") List<Long> callIdList);

}

