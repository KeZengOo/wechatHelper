package com.nuoxin.virtual.rep.api.service.v2_5;

import java.util.List;

import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.questionnaire.SaveVirtualQuestionnaireRecordRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.VirtualQuestionnaireRecordResponse;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.VirtualQuestionnaireResponse;

/**
 * 虚拟代表问卷业务接口
 * @author xiekaiyu
 */
public interface VirtualQuestionnaireService {
	
	/**
	 * 根据虚拟代表ID,医生ID,虚拟代表问卷ID获取最近一次作答
	 * @param virtualDrugUserId 虚拟代表ID
	 * @param virtualDoctorId 医生ID
	 * @param virtualQuestionnaireId 问卷ID
	 * @return 成功返回 List<VirtualQuestionnaireRecordResponse> 
	 */
	List<VirtualQuestionnaireRecordResponse> getLastQuestionnaireRecord(Long virtualDrugUserId, Long virtualDoctorId,
			Long virtualQuestionnaireId);
	
	/**
	 * 提交问卷作答结果
	 * @param request
	 * @return 返回影响条数
	 */
	int saveQuestionnaire(SaveVirtualQuestionnaireRecordRequestBean request);
	
	/**
	 * 根据 产品线 ID 获取问卷基本信息 
	 * @param productLineId
	 * @return
	 */
	List<VirtualQuestionnaireResponse>getQuestionnaireByProductLineId(Long productLineId);
}
