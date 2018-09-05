package com.nuoxin.virtual.rep.api.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.VirtualQuestionRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.VirtualQuestionnaireRecordResponse;

/**
 * 虚拟代表问卷 Mapper
 * @author xiekaiyu
 */
public interface QuestionnaireMapper {

	/**
	 * 根据问卷ID, 产品线ID获取问卷标题
	 * @param id 问卷ID
	 * @param productId 产品线ID
	 * @return 成功返回标题
	 */
	@Deprecated
	String getTitle(@Param(value = "id")Long id, @Param(value = "productId")Long productId);
	
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
	 * @param virtualDoctorId
	 * @param virtualDrugUserId
	 * @param virtualQuestionaireId
	 * @param callId
	 * @param questions
	 * @return
	 */
	int saveQuestionnaire(@Param(value = "virtualDoctorId") Long virtualDoctorId,
			                          @Param(value = "virtualDrugUserId") Long virtualDrugUserId,
			                          @Param(value = "virtualQuestionnaireId") Long virtualQuestionnaireId, 
			                          @Param(value = "callId") Long callId,
			                          @Param(value = "questions") List<VirtualQuestionRequestBean> questions);
}
