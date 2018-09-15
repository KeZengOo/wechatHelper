package com.nuoxin.virtual.rep.api.service.v2_5.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.nuoxin.virtual.rep.api.common.bean.PageResponseBean;
import com.nuoxin.virtual.rep.api.entity.v2_5.CallVisitBean;
import com.nuoxin.virtual.rep.api.entity.v2_5.CallVisitMendBean;
import com.nuoxin.virtual.rep.api.entity.v2_5.DrugUserDoctorQuateParams;
import com.nuoxin.virtual.rep.api.entity.v2_5.VirtualDoctorCallInfoParams;
import com.nuoxin.virtual.rep.api.mybatis.DrugUserDoctorQuateMapper;
import com.nuoxin.virtual.rep.api.mybatis.VirtualDoctorCallInfoMapper;
import com.nuoxin.virtual.rep.api.mybatis.VirtualDoctorCallInfoMendMapper;
import com.nuoxin.virtual.rep.api.service.v2_5.CommonService;
import com.nuoxin.virtual.rep.api.service.v2_5.VirtualDoctorCallInfoService;
import com.nuoxin.virtual.rep.api.service.v2_5.VirtualQuestionnaireService;
import com.nuoxin.virtual.rep.api.utils.CollectionsUtil;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.callinfo.BaseCallInfoRequest;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.callinfo.CallInfoListRequest;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.callinfo.SaveCallInfoRequest;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.callinfo.SaveCallInfoUnConnectedRequest;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.questionnaire.SaveVirtualQuestionnaireRecordRequestBean;

/**
 * 电话拜访实现类
 * @author xiekaiyu
 */
@Service
public class VirtualDoctorCallInfoServiceImpl implements VirtualDoctorCallInfoService {

	@Resource
	private VirtualQuestionnaireService questionnaireService;
	@Resource
	private VirtualDoctorCallInfoMapper callInfoMapper;
	@Resource
	private VirtualDoctorCallInfoMendMapper callInfoMendMapper;
	@Resource
	private DrugUserDoctorQuateMapper drugUserDoctorQuateMapper;
	@Resource
	private CommonService commonService;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public PageResponseBean<List<CallVisitBean>> getCallVisitList(CallInfoListRequest request, String leaderPath) {
		PageResponseBean<List<CallVisitBean>> pageResponse = null;

		Long virtualDoctorId = request.getVirtualDoctorId();
		int count = callInfoMapper.getCallVisitCount(leaderPath, virtualDoctorId);
		if (count > 0) {
			int currentSize = request.getCurrentSize();
			int pageSize = request.getPageSize();
			List<CallVisitBean> list = callInfoMapper.getCallVisitList(leaderPath, virtualDoctorId, currentSize, pageSize);
			if (CollectionsUtil.isNotEmptyList(list)) {
				List<Long> callIds = new ArrayList<>(list.size());
				list.forEach(visitBean ->{
					callIds.add(visitBean.getCallId());
				});
				
				// 补充 VirtualDoctorCallInfoMend 信息
				List<CallVisitMendBean> callInfoMends = callInfoMendMapper.getCallVisitMendList(callIds);
				if (CollectionsUtil.isNotEmptyList(callInfoMends)) {
					ConcurrentMap<Long, CallVisitMendBean> map = new ConcurrentHashMap<>(callInfoMends.size());
					callInfoMends.forEach(mend -> {
						map.put(mend.getCallId(), mend);
					});

					list.forEach(visit -> {
						Long callId = visit.getCallId();
						CallVisitMendBean mend = map.get(callId);
						if (mend != null) {
							visit.setAttitude(mend.getAttitude());
							String visitResultStr = mend.getVisitResult();
							JSONArray visitResult = JSONObject.parseArray(visitResultStr);
							visit.setVisitResult(visitResult);
						}
					});
				}
			}
			pageResponse = new PageResponseBean(request, count, list);
		} 
		
		if (pageResponse == null) {
			pageResponse = new PageResponseBean(request, 0, Collections.emptyList());
		}
		
		return pageResponse;
	}

	@Transactional(value = TxType.REQUIRED, rollbackOn = Exception.class)
	@Override
	public boolean connectedSaveCallInfo(SaveCallInfoRequest saveRequest) {
		long callId = this.doSaveCallInfo(saveRequest);
		int effectNum = 0;
		if (callId > 0) {
			effectNum = this.doSaveVirtualQuestionnaireRecord(saveRequest, callId);
		}
		
		if (effectNum > 0) {
			this.changeRelationShip(saveRequest);
			return true;
		}

		return false;
	}
	
	@Transactional(value = TxType.REQUIRED, rollbackOn = Exception.class)
	@Override
	public boolean unconnectedSaveCallInfo(SaveCallInfoUnConnectedRequest saveRequest) {
		if("emptynumber".equals(saveRequest.getStatuaName())) {
			saveRequest.setIsBreakOff(1);
		} else {
			saveRequest.setIsBreakOff(0);
		}
		
		long callId = this.doSaveCallInfo(saveRequest);
		if(callId > 0) {
			this.changeRelationShip(saveRequest);
			return true;
		}
		
		return false;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * 保存电话拜访信息及扩展信息<br>
	 * 写入 virtual_doctor_call_info,virtual_doctor_call_info_mend 表
	 * @param saveRequest
	 * @return 返回 callId
	 */
	private long doSaveCallInfo(BaseCallInfoRequest saveRequest) {
		VirtualDoctorCallInfoParams callVisitParams = this.getVirtualDoctorCallInfoParams(saveRequest);
		// 写入 virtual_doctor_call_info 表
		callInfoMapper.saveVirtualDoctorCallInfo(callVisitParams);

		long calld = callVisitParams.getCallId();
		if (calld > 0L) {
			// 写入 virtual_doctor_call_info_mend 表
			callInfoMendMapper.saveVirtualDoctorCallInfoMend(callVisitParams);
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
	 * 变更虚拟代医生扩展关系信息
	 * @param saveRequest
	 */
	private void changeRelationShip(BaseCallInfoRequest request) {
		DrugUserDoctorQuateParams relationShipParams = new DrugUserDoctorQuateParams();
		relationShipParams.setVirtualDrugUserId(request.getVirtualDrugUserId());
		relationShipParams.setDoctorId(request.getVirtualDoctorId());
		relationShipParams.setProductLineId(request.getProductId());
		
		if (request instanceof SaveCallInfoRequest) {
			SaveCallInfoRequest saveRequest = (SaveCallInfoRequest)request;
			relationShipParams.setIsHasDrug(saveRequest.getIsHasDrug());
			relationShipParams.setIsTarget(saveRequest.getIsTarget());
			relationShipParams.setIsHasAe(saveRequest.getIsHasAe());
			relationShipParams.setHcpPotential(saveRequest.getHcpPotential());
		} else if (request instanceof SaveCallInfoUnConnectedRequest) {
			SaveCallInfoUnConnectedRequest saveRequest = (SaveCallInfoUnConnectedRequest)request;
			relationShipParams.setIsBreakOff(saveRequest.getIsBreakOff());
		}
		
		// 备份关系
		drugUserDoctorQuateMapper.backupRelationShipInfo(relationShipParams);
		// 变更关系
		drugUserDoctorQuateMapper.replaceRelationShipInfo(relationShipParams);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private VirtualDoctorCallInfoParams getVirtualDoctorCallInfoParams (BaseCallInfoRequest saveRequest) {
		VirtualDoctorCallInfoParams callVisitParams = new VirtualDoctorCallInfoParams();
		callVisitParams.setSinToken(saveRequest.getSinToken());
		callVisitParams.setVirtualDoctorId(saveRequest.getVirtualDoctorId());
		callVisitParams.setVirtualDrugUserId(saveRequest.getVirtualDrugUserId());
		callVisitParams.setProductId(saveRequest.getProductId());
		callVisitParams.setMobile(saveRequest.getMobile());
		// 只能是1 或者 2在前面的参数校验中加了限制
		callVisitParams.setType(saveRequest.getType());
		callVisitParams.setRemark(saveRequest.getRemark());
		callVisitParams.setStatus(saveRequest.getStatus());
		callVisitParams.setStatusName(saveRequest.getStatuaName());
		callVisitParams.setNextVisitTime(saveRequest.getNextVisitTime());
		
		Integer virtualQuestinairedId = null;
		if (saveRequest instanceof SaveCallInfoRequest) {
			SaveCallInfoRequest saveCallInfoRequest = (SaveCallInfoRequest) saveRequest;
		
			String visitResult = JSONObject.toJSONString(saveCallInfoRequest.getVisitResult());
			callVisitParams.setVisitResult(visitResult);
			callVisitParams.setAttitude(saveCallInfoRequest.getAttitude());

			virtualQuestinairedId = saveCallInfoRequest.getVirtualQuestionaireId();
		} else { 
			virtualQuestinairedId = 0;
		}
		if (virtualQuestinairedId == null) {
			virtualQuestinairedId = 0;
		}
		callVisitParams.setDoctorQuestionnaireId(virtualQuestinairedId);
		
		return callVisitParams;
	}
	
}
