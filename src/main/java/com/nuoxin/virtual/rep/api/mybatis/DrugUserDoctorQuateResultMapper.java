package com.nuoxin.virtual.rep.api.mybatis;

import com.nuoxin.virtual.rep.api.entity.v2_5.DrugUserDoctorQuateParams;
import com.nuoxin.virtual.rep.api.entity.v2_5.StatisticsDrugNumResponse;
import com.nuoxin.virtual.rep.api.entity.v2_5.StatisticsParams;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.statistics.DailyStatisticsRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.ProductInfoResponse;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.VisitResultResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.statistics.VisitResultDoctorResponseBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * drug_user_doctor Mapper 类
 * @author xiekaiyu
 */
public interface DrugUserDoctorQuateResultMapper {

	/**
	 * 批量插入拜访结果 代表医生产品
	 * @param quateId
	 * @param resultIdList
	 */
	void batchInsert(@Param(value = "quateId") Long quateId,@Param(value = "resultIdList") List<Long> resultIdList);


	/**
	 * 批量插入拜访结果，产品医生
	 * @param quateId
	 * @param resultIdList
	 */
	void batchInsertProductDoctor(@Param(value = "quateId") Long quateId,@Param(value = "resultIdList") List<Long> resultIdList);


	/**
	 * 获取拜访结果
	 * @param doctorId
	 * @param productList
	 * @return
	 */
	List<String> getVisitResult(@Param(value = "doctorId") Long doctorId,@Param(value = "productList") List<Long> productList);



	/**
	 * 获取拜访结果list
	 * @param doctorIdList
	 * @param productList
	 * @return
	 */
	List<VisitResultResponseBean> getVisitResultList(@Param(value = "doctorIdList") List<Long> doctorIdList, @Param(value = "productList") List<Long> productList);


	/**
	 * 得到不同拜访结果的医生
	 * @param bean
	 * @return
	 */
	List<VisitResultDoctorResponseBean> getVisitResultDoctorList(DailyStatisticsRequestBean bean);

}

