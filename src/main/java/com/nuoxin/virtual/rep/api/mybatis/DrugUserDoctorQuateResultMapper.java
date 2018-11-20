package com.nuoxin.virtual.rep.api.mybatis;

import com.nuoxin.virtual.rep.api.entity.v2_5.DrugUserDoctorQuateParams;
import com.nuoxin.virtual.rep.api.entity.v2_5.StatisticsDrugNumResponse;
import com.nuoxin.virtual.rep.api.entity.v2_5.StatisticsParams;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.ProductInfoResponse;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * drug_user_doctor Mapper 类
 * @author xiekaiyu
 */
public interface DrugUserDoctorQuateResultMapper {

	/**
	 * 批量插入拜访结果
	 * @param quateId
	 * @param resultIdList
	 */
	void batchInsert(@Param(value = "quateId") Long quateId,@Param(value = "resultIdList") List<Long> resultIdList);

}

