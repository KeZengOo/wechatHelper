package com.nuoxin.virtual.rep.api.mybatis;

import java.util.List;

import com.nuoxin.virtual.rep.api.entity.v2_5.StatisticsDrugNumResponse;
import com.nuoxin.virtual.rep.api.entity.v2_5.StatisticsParams;
import com.nuoxin.virtual.rep.api.web.controller.request.call.CallRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.call.VisitHistoryRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.callinfo.VisitCountRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.statistics.DailyStatisticsRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.CallTelephoneReponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.VisitCountResponseBean;
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
	 * 医生拜访数：医生ID列表
	 * @param statisticsParams
	 * @return 返回记录总条数
	 */
	List<Long> geTelephoneDoctorVisitDoctorIdList(StatisticsParams statisticsParams);


	/**
	 * 医生拜访数(拜访结果为（类型=接触医生）)：医生ID列表
	 * @param statisticsParams
	 * @return 返回记录总条数
	 */
	List<Long> getDoctorVisitContactDoctorIdList(StatisticsParams statisticsParams);



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

	/**
	 * 得到医生电话以及接通次数
	 * @param doctorId
	 * @return
	 */
	List<CallTelephoneReponseBean> getTelephoneCallCount(@Param(value = "doctorId") Long doctorId);


	/**
	 * 得到医生电话以及接通次数
	 * @param doctorIdList
	 * @return
	 */
	List<CallTelephoneReponseBean> getAllTelephoneCallCount(@Param(value = "doctorIdList") List<Long> doctorIdList);

	/**
	 * 更新电话记录产品
	 * @param callId
	 * @param productId
	 */
	void updateCallProduct(@Param(value = "callId") Long callId,@Param(value = "productId") Long productId);


	/**
	 * 更新录音文本
	 * @param sinToken
	 * @param callText
	 */
	void updateCallUrlText(@Param(value = "sinToken") String sinToken,@Param(value = "callText") String callText);

	/**
	 * 临时功能，导入森福罗历史拜访记录
	 * @return
	 */
	List<VisitHistoryRequestBean> getVisitHistoryList();

	void insertVisitHistory(VisitHistoryRequestBean bean);

	void insertVisitHistoryMend(VisitHistoryRequestBean bean);

	void insertVisitHistoryMendResult(VisitHistoryRequestBean bean);


	/**
	 * 查询指定日期的计划拜访人数
	 * @param bean
	 * @return
	 */
	List<VisitCountResponseBean> getVisitCountList(VisitCountRequestBean bean);

	/**
	 * 得到拜访医院数量
	 * @param bean
	 * @return
	 */
	Integer getVisitHospitalCount(DailyStatisticsRequestBean bean);


	/**
	 * 得到拜访医生数量
	 * @param bean
	 * @return
	 */
	Integer getVisitDoctorCount(DailyStatisticsRequestBean bean);

	/**
	 * 得到接触/覆盖/成功医院数量
	 * @param bean
	 * @return
	 */
	Integer getVisitTypeHospitalCount(DailyStatisticsRequestBean bean);


	/**
	 * 得到接触/覆盖/成功过医生数量
	 * @param bean
	 * @return
	 */
	Integer getVisitTypeDoctorCount(DailyStatisticsRequestBean bean);


	/**
	 * 电话外呼量
	 * @param bean
	 * @return
	 */
	Integer getCallCount(DailyStatisticsRequestBean bean);

	/**
	 * 电话接通量
	 * @param bean
	 * @return
	 */
	Integer getConnectCallCount(DailyStatisticsRequestBean bean);


	/**
	 * 通话时长
	 * @param bean
	 * @return
	 */
	Integer getCallTime(DailyStatisticsRequestBean bean);

	/**
	 * 面谈次数
	 * @param bean
	 * @return
	 */
	Integer interviewVisit(DailyStatisticsRequestBean bean);

	/**
	 * 除去电话、面谈其他拜访次数
	 * @param bean
	 * @return
	 */
	Integer otherVisit(DailyStatisticsRequestBean bean);

}

