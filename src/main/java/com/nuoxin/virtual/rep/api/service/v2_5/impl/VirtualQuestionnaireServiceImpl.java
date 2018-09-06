package com.nuoxin.virtual.rep.api.service.v2_5.impl;

import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.nuoxin.virtual.rep.api.mybatis.VirtualQuestionnaireMapper;
import com.nuoxin.virtual.rep.api.service.v2_5.VirtualQuestionnaireService;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.SaveVirtualQuestionnaireRecordRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.VirtualQuestionnaireRecordResponse;

/**
 * 虚拟代表问题实现类
 * @author xiekaiyu
 */
@Service
public class VirtualQuestionnaireServiceImpl implements VirtualQuestionnaireService{
	
	@Resource
	private VirtualQuestionnaireMapper questionnaireMapper;

	@Override
	public List<VirtualQuestionnaireRecordResponse> getLastQuestionnaireRecord(Long virtualDrugUserId, Long virtualDoctorId,
			Long virtualQuestionnaireId) {
		List<VirtualQuestionnaireRecordResponse> list = questionnaireMapper.getLastQuestionnaireRecord(virtualDrugUserId,
				virtualDoctorId, virtualQuestionnaireId);
		if(list == null) {
			list = Collections.emptyList();
		}
		
		return list;
	}

	@Override
	public int saveQuestionnaire(SaveVirtualQuestionnaireRecordRequestBean request) {
		return questionnaireMapper.saveQuestionnaire(request.getVirtualDoctorId(), request.getVirtualDrugUserId(),
				request.getVirtualQuestionaireId(), request.getCallId(), request.getQuestions());
	}

}
