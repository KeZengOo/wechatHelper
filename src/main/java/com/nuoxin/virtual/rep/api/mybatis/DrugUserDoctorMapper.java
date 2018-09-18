package com.nuoxin.virtual.rep.api.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.nuoxin.virtual.rep.api.entity.v2_5.DrugUserDoctorOneToOneParams;
import com.nuoxin.virtual.rep.api.entity.v2_5.DrugUserDoctorParams;
import com.nuoxin.virtual.rep.api.entity.v2_5.ProductBean;
import com.nuoxin.virtual.rep.api.entity.v2_5.StatisticsParams;
import com.nuoxin.virtual.rep.api.entity.v2_5.StatisticsResponse;

/**
 * drug_user_doctor Mapper 类
 * @author xiekaiyu
 */
public interface DrugUserDoctorMapper {

	/**
	 * 指保保存至 drug_user_doctor 表
	 * @param list
	 * @return
	 */
	int saveDrugUserDoctors(List<DrugUserDoctorParams> list);
	
	/**
	 * 保存代表医生一对一关联关系
	 */
	int saveDrugUserDoctorsOneToOne (List<DrugUserDoctorOneToOneParams> list);

	/**
	 * 根据参数查询销售代表列表
	 * @param statisticsParams
	 * @return
	 */
	List<StatisticsResponse> selectDrugUserDoctors(StatisticsParams statisticsParams);
	/**
	 * 根据参数查询销售代表列表总数
	 * @param statisticsParams
	 * @return
	 */
	Integer selectDrugUserDoctorsCount(StatisticsParams statisticsParams);
	
	List<ProductBean>getProducts(@Param(value = "virtualDrugUserId")Long virtualDrugUserId, 
			                                          @Param(value = "virtualDoctorId")Long virtualDoctorId);
	
}

