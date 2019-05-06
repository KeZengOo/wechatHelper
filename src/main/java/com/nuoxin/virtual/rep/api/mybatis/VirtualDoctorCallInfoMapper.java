package com.nuoxin.virtual.rep.api.mybatis;

import java.util.List;

import com.nuoxin.virtual.rep.api.entity.v2_5.*;
import com.nuoxin.virtual.rep.api.web.controller.request.call.CallRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.call.VisitHistoryRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.callinfo.CallInfoListRequest;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.callinfo.VisitCountRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.statistics.DailyStatisticsRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.CallTelephoneReponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.VisitCountResponseBean;
import org.apache.ibatis.annotations.Param;

/**
 * 电话拜访 Mapper
 * @author xiekaiyu
 */
public interface VirtualDoctorCallInfoMapper {

	/**
	 * 根据过滤条件计算数据总数
	 * @param listRequest
	 * @return 返回记录总条数
	 */
	int getCallVisitCount(CallInfoListRequest listRequest);

	/**
	 * 根据过滤条件获取拜访列表信息
	 * @param listRequest
	 * @return
	 */
	List<CallVisitBean> getCallVisitList(CallInfoListRequest listRequest);
	
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
	String getCallTime(DailyStatisticsRequestBean bean);

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

	/**
	 * 保存分割音频左右声道后上传的阿里云地址
	 */
	Integer saveSplitSpeechAliyunPath(@Param(value = "sourceUrl") String sourceUrl, @Param(value = "targeUrl")String targeUrl, @Param(value = "type")Integer type);

	/**
	 * 根据左右声道的阿里云地址进行语音识别，进行入库
	 * @param list
	 * @return
	 */
	Integer saveSpeechRecognitionResultCallInfo(@Param(value = "list") List<VirtualSplitSpeechCallInfoParams> list);

	/**
	 * 根据sin_token获取virtual_doctor_call_info表中的id
	 * @param sinToken
	 * @return id
	 */
	VirtualSplitSpeechCallInfoParams getCallInfoBySinToken(@Param(value = "sinToken") String sinToken);

	/**
	 * 根据id获取virtual_doctor_call_info表中的sin_token
	 * @param id
	 * @return sinToken
	 */
	VirtualSplitSpeechCallInfoParams getCallInfoById(@Param(value = "id") Integer id);

	/**
	 * 查询总数，判断是否存在
	 * @param bean
	 * @return
	 */
	Integer getCountByCallRequest(CallRequestBean bean);


	/*********************************  V3.0.1相关  ******************************************/

	/**
	 * 新增拜访的目的
	 * @param callId
	 * @param purposeList
	 */
	void addCallPurpose(@Param(value = "callId") Long callId,@Param(value = "purposeList") List<String> purposeList);

}

