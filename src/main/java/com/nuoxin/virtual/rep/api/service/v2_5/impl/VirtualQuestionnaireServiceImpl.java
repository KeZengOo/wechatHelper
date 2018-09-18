package com.nuoxin.virtual.rep.api.service.v2_5.impl;

import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.nuoxin.virtual.rep.api.mybatis.VirtualQuestionnaireMapper;
import com.nuoxin.virtual.rep.api.service.v2_5.VirtualQuestionnaireService;
import com.nuoxin.virtual.rep.api.utils.CollectionsUtil;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.questionnaire.SaveVirtualQuestionnaireRecordRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.questionnaire.VirtualQuestionRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.VirtualQuestionnaireRecordResponse;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.VirtualQuestionnaireResponse;

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
		if(CollectionsUtil.isNotEmptyList(list)) {
			list.forEach(question->{
				Object options = question.getOptions();
				if(options != null) {
					com.alibaba.fastjson.JSONArray jsonArray = JSONObject.parseArray(options.toString());
					question.setOptions(jsonArray);
				}
			});
		} else if (list == null) {
			list = Collections.emptyList();
		}
		
		return list;
	}

	@Override
	public int saveQuestionnaire(SaveVirtualQuestionnaireRecordRequestBean request) {
		int effectNum = 0;
		List<VirtualQuestionRequestBean> questions = request.getQuestions();
		if (CollectionsUtil.isNotEmptyList(questions)) {
			effectNum = questionnaireMapper.saveQuestionnaire(request.getVirtualDoctorId(), request.getVirtualDrugUserId(),
					request.getVirtualQuestionaireId(), request.getCallId(), questions);
		}
		
		return effectNum;
	}

	@Override
	public List<VirtualQuestionnaireResponse> getQuestionnaireByProductLineId(Long productLineId) {
		List<VirtualQuestionnaireResponse> list = questionnaireMapper.getQuestionnaire(productLineId);
		if (list == null) {
			list = Collections.emptyList();
		}

		return list;
	}

}
