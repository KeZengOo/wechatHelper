package com.nuoxin.virtual.rep.api.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.questionnaire.VirtualQuestionRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.VirtualQuestionnaireRecordResponse;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.VirtualQuestionnaireResponse;

/**
 * 虚拟代表问卷 Mapper
 * @author xiekaiyu
 */
public interface VirtualQuestionnaireMapper {

	/**
	 * 根据问卷ID, 产品线ID获取问卷标题
	 * @param id 问卷ID
	 * @param productId 产品线ID
	 * @return 成功返回标题
	 */
	List<VirtualQuestionnaireResponse> getQuestionnaire(@Param(value = "productLineId")Long productLineId);
	
	/**
	 * 根据虚拟代表ID,医生ID,虚拟代表问卷ID获取最近一次作答
	 * @param virtualDrugUserId 虚拟代表ID
	 * @param virtualDoctorId 医生ID
	 * @param virtualQuestionnaireId 问卷ID
	 * @return 成功返回 List<VirtualQuestionnaireRecordResponse> 
	 */
	List<VirtualQuestionnaireRecordResponse> getLastQuestionnaireRecord(@Param(value = "virtualDrugUserId")Long virtualDrugUserId, 
			                                                @Param(value = "virtualDoctorId")Long virtualDoctorId, 
			                                                @Param(value = "virtualQuestionnaireId")Long virtualQuestionnaireId);
	/**
	 * 保存问卷作答
	 * @param virtualDoctorId 医生ID
	 * @param virtualDrugUserId 虚拟代表ID
	 * @param virtualQuestionaireId 问卷ID
	 * @param callId 电话拜访 ID
	 * @param questions 问题作答
	 * @return 返回影响条数
	 */
	int saveQuestionnaire(@Param(value = "virtualDoctorId") Long virtualDoctorId,
			                          @Param(value = "virtualDrugUserId") Long virtualDrugUserId,
			                          @Param(value = "virtualQuestionnaireId") Integer virtualQuestionnaireId, 
			                          @Param(value = "callId") Long callId,
			                          @Param(value = "questions") List<VirtualQuestionRequestBean> questions);
}
