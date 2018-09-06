package com.nuoxin.virtual.rep.api.service.v2_5.impl;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.nuoxin.virtual.rep.api.entity.v2_5.UpdateVirtualDrugUserDoctorRelationship;
import com.nuoxin.virtual.rep.api.entity.v2_5.VirtualDoctorCallInfoParams;
import com.nuoxin.virtual.rep.api.mybatis.DrugUserDoctorQuateMapper;
import com.nuoxin.virtual.rep.api.mybatis.VirtualDoctorCallInfoMapper;
import com.nuoxin.virtual.rep.api.service.v2_5.VirtualDoctorlCallInfoService;
import com.nuoxin.virtual.rep.api.service.v2_5.VirtualQuestionnaireService;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.SaveVirtualQuestionnaireRecordRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.callinfo.SaveCallInfoRequest;

/**
 * 电话拜访实现类
 * @author xiekaiyu
 */
@Service
public class VirtualDoctorCallInfoServiceImpl implements VirtualDoctorlCallInfoService {

	@Resource
	private VirtualQuestionnaireService questionnaireService;
	@Resource
	private VirtualDoctorCallInfoMapper callInfoMapper;
	@Resource
	private DrugUserDoctorQuateMapper drugUserDoctorQuateMapper;

	@Transactional(value = TxType.REQUIRED, rollbackOn = Exception.class)
	@Override
	public boolean saveCallInfo(SaveCallInfoRequest saveRequest) {
		long callId = this.doSaveCallInfo(saveRequest);
		int effectNum = 0;
		if (callId > 0) {
			effectNum = this.doSaveVirtualQuestionnaireRecord(saveRequest, callId);
		}
		
		this.updateRelationShip(saveRequest);

		if (effectNum > 0) {
			return true;
		}

		return false;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * 保存电话拜访信息
	 * @param saveRequest
	 * @return 返回 callId
	 */
	private long doSaveCallInfo(SaveCallInfoRequest saveRequest) {
		VirtualDoctorCallInfoParams callVisitParams = new VirtualDoctorCallInfoParams();
		callVisitParams.setSinToken(saveRequest.getSinToken());
		callVisitParams.setVirtualDoctorId(saveRequest.getVirtualDoctorId());
		callVisitParams.setVirtualDrugUserId(saveRequest.getVirtualDrugUserId());
		callVisitParams.setProductId(saveRequest.getProductId());
		callVisitParams.setType(saveRequest.getType());
		callVisitParams.setMobile(saveRequest.getMobile());

		String visitResult = JSONObject.toJSONString(saveRequest.getVisitResult());
		callVisitParams.setVisitResult(visitResult);
		callVisitParams.setAttitude(saveRequest.getAttitude());
		callVisitParams.setDoctorQuestionnaireId(saveRequest.getVirtualQuestionaireId());
		callVisitParams.setNextVisitTime(saveRequest.getNextVisitTime());
		callVisitParams.setRemark(saveRequest.getRemark());
		
		callInfoMapper.saveVirtualDoctorCallInfo(callVisitParams);

		Long calld = callVisitParams.getCallId();
		if (calld == null) {
			calld = 0L;
		}

		return calld;
	}

	/**
	 * 保存 问卷做题结果
	 * @param saveRequest
	 * @param callId
	 * @return 返回影响条数
	 */
	private int doSaveVirtualQuestionnaireRecord(SaveCallInfoRequest saveRequest, Long callId) {
		SaveVirtualQuestionnaireRecordRequestBean questionParams = new SaveVirtualQuestionnaireRecordRequestBean();
		questionParams.setVirtualDoctorId(saveRequest.getVirtualDoctorId());
		questionParams.setVirtualDrugUserId(saveRequest.getVirtualDrugUserId());
		questionParams.setVirtualQuestionaireId(saveRequest.getVirtualQuestionaireId());
		questionParams.setQuestions(saveRequest.getQuestions());
		questionParams.setCallId(callId);
		
		return questionnaireService.saveQuestionnaire(questionParams);
	}
	
	/**
	 * 变更虚拟代表关联的医生关系信息:是否有药,是否是目标客户,是否有AE
	 * @param saveRequest
	 */
	private void updateRelationShip(SaveCallInfoRequest saveRequest) {
		UpdateVirtualDrugUserDoctorRelationship relationShipParams = new UpdateVirtualDrugUserDoctorRelationship();
		
		relationShipParams.setVirtualDrugUserId(saveRequest.getVirtualDrugUserId());
		relationShipParams.setDoctorId(saveRequest.getVirtualDoctorId());
		relationShipParams.setProductLineId(saveRequest.getProductId());
		relationShipParams.setIsHasDrug(saveRequest.getIsHasDrug());
		relationShipParams.setIsTarget(saveRequest.getIsTarget());
		relationShipParams.setIsHasAe(saveRequest.getIsHasAe());
		
		// 备份关系
		drugUserDoctorQuateMapper.backRelationShipInfo(relationShipParams);
		// 变更关系
		drugUserDoctorQuateMapper.replaceRelationShipInfo(relationShipParams);
	}
	
}
