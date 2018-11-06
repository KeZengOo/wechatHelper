package com.nuoxin.virtual.rep.api.mybatis;

import java.util.List;

import com.nuoxin.virtual.rep.api.entity.v2_5.StatisticsDrugNumResponse;
import com.nuoxin.virtual.rep.api.entity.v2_5.StatisticsParams;
import com.nuoxin.virtual.rep.api.web.controller.request.call.CallRequestBean;
import org.apache.ibatis.annotations.Param;

import com.nuoxin.virtual.rep.api.entity.v2_5.CallVisitBean;
import com.nuoxin.virtual.rep.api.entity.v2_5.VirtualDoctorCallInfoParams;

/**
 * 电话拜访 Mapper
 * @author xiekaiyu
 */
public interface VirtualDoctorCallInfoMapper {

	/**
	 * 根据过滤条件计算数据总数
	 * @param virtualDrugUserIds
	 * @param virtualDoctorId
	 * @return 返回记录总条数
	 */
	int getCallVisitCount(@Param(value = "leaderPath") String leaderPath,
			@Param(value = "virtualDoctorId") Long virtualDoctorId);

	/**
	 * 根据过滤条件获取拜访列表信息
	 * @param virtualDrugUserIds
	 * @param virtualDoctorId
	 * @param currentSize
	 * @param pageSize
	 * @return
	 */
	List<CallVisitBean> getCallVisitList(@Param(value = "leaderPath") String leaderPath,
			@Param(value = "virtualDoctorId") Long virtualDoctorId, @Param(value = "currentSize") Integer currentSize,
			@Param(value = "pageSize") Integer pageSize);
	
	/**
	 * 保存电话拜访信息
	 * @param params
	 * @return
	 */
	int saveVirtualDoctorCallInfo(VirtualDoctorCallInfoParams params);
	
	/**
	 * 修改电话拜访信息
	 * @param params
	 * @return 返回影响条数
	 */
	int updateVirtualDoctorCallInfo(VirtualDoctorCallInfoParams params);

	/**
	 * 医生拜访数
	 * @param statisticsParams
	 * @return 返回记录总条数
	 */
	List<StatisticsDrugNumResponse> geTelephoneDoctorVisitCount(StatisticsParams statisticsParams);


	/**
	 * 更新打电话的医生ID
	 * @param telephoneList
	 * @param doctorId
	 */
	void updateCallInfoDoctorId(@Param(value = "telephoneList") List<String> telephoneList,@Param(value = "doctorId") Long doctorId);


	/**
	 * 保存电话记录
	 * @param bean
	 */
	void saveCallInfo(CallRequestBean bean);


}

