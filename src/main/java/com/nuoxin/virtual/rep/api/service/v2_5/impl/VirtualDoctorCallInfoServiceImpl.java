package com.nuoxin.virtual.rep.api.service.v2_5.impl;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import com.nuoxin.virtual.rep.api.entity.DrugUser;
import com.nuoxin.virtual.rep.api.enums.RecruitEnum;
import com.nuoxin.virtual.rep.api.enums.RoleTypeEnum;
import com.nuoxin.virtual.rep.api.enums.UserTypeEnum;
import com.nuoxin.virtual.rep.api.mybatis.*;
import com.nuoxin.virtual.rep.api.web.controller.request.call.CallRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.product.ProductResponseBean;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.nuoxin.virtual.rep.api.common.bean.PageResponseBean;
import com.nuoxin.virtual.rep.api.entity.v2_5.CallVisitBean;
import com.nuoxin.virtual.rep.api.entity.v2_5.CallVisitMendBean;
import com.nuoxin.virtual.rep.api.entity.v2_5.CallVisitStatisticsBean;
import com.nuoxin.virtual.rep.api.entity.v2_5.DrugUserDoctorQuateParams;
import com.nuoxin.virtual.rep.api.entity.v2_5.VirtualDoctorCallInfoParams;
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
	private CommonService commonService;
	@Resource
	private VirtualDoctorCallInfoMapper callInfoMapper;
	@Resource
	private DrugUserDoctorMapper drugUserDoctorMapper;
	@Resource
	private VirtualDoctorCallInfoMendMapper callInfoMendMapper;
	@Resource
	private DrugUserDoctorQuateMapper drugUserDoctorQuateMapper;
	@Resource
	private DrugUserDoctorQuateResultMapper drugUserDoctorQuateResultMapper;

	@Resource
	private VirtualDoctorCallInfoResultMapper virtualDoctorCallInfoResultMapper;

	@Resource
	private DrugUserMapper drugUserMapper;
	@Resource
	private ProductLineMapper productLineMapper;
	
	@Override
	public CallVisitStatisticsBean getCallVisitListStatistics(Long virtualDoctorId, String leaderPath) {
		// TODO @田存
		return null;
	}
	
	@Transactional(value = TxType.REQUIRED, rollbackOn = Exception.class)
	@Override
	public boolean saveConnectedCallInfo(SaveCallInfoRequest saveRequest) {
		this.configSaveCallInfoRequestValue(saveRequest);
		
		Long callId = saveRequest.getCallInfoId(); // 电话拜访主键值
		if (callId != null && callId > 0) {
			this.saveCallInfo(saveRequest);
			
			Integer virtualQuestionaireId = saveRequest.getVirtualQuestionaireId();
			if (virtualQuestionaireId != null && virtualQuestionaireId > 0){
				this.doSaveVirtualQuestionnaireRecord(saveRequest);
			}
			
			this.alterRelationShip(saveRequest);
			return true;
		}

		Integer productId = saveRequest.getProductId();

		callInfoMapper.updateCallProduct(callId, productId.longValue());

		return false;
	}
	
	@Transactional(value = TxType.REQUIRED, rollbackOn = Exception.class)
	@Override
	public boolean saveUnconnectedCallInfo(SaveCallInfoUnConnectedRequest saveRequest) {
		this.configSaveCallInfoUnConnectedRequest(saveRequest);
		
		Long callId = saveRequest.getCallInfoId();
		if (callId != null && callId > 0) {
			this.saveCallInfo(saveRequest);
			this.alterRelationShip(saveRequest);

			return true;
		}

		Integer productId = saveRequest.getProductId();
		callInfoMapper.updateCallProduct(callId, productId.longValue());

		return false;
	}

	@Override
	public void saveCallInfo(CallRequestBean bean) {

		Integer visitChannel = bean.getVisitChannel();
		if (visitChannel == null || visitChannel == 0){
			// 1 是电话
			visitChannel = 1;
		}

		if (visitChannel != null && visitChannel !=1){
			String uuid = UUID.randomUUID().toString();
			bean.setSinToken(uuid);
		}

		callInfoMapper.saveCallInfo(bean);

	}

	@Override
	public Integer getProductRecruit(Long productId, Long doctorId) {

		Integer productRecruit = drugUserDoctorQuateMapper.getProductRecruit(productId, doctorId);
		if (productRecruit == null){
			productRecruit = RecruitEnum.UNKOWN.getType();
		}

		return productRecruit;
	}

	@Override
	public List<ProductResponseBean> getProductList(DrugUser drugUser) {


		Long roleId = drugUser.getRoleId();
		Long drugUserId = drugUser.getId();
		if (RoleTypeEnum.SALE.getType().equals(roleId)){
			List<Long> idList = new ArrayList<>(1);
			idList.add(drugUserId);
			List<ProductResponseBean> productList = productLineMapper.getListByDrugUserId(idList);
			if (CollectionsUtil.isNotEmptyList(productList)){
				return productList;
			}
		}

		if (RoleTypeEnum.MANAGER.getType().equals(roleId)){
			List<Long> drugUserIdList = drugUserMapper.getSubordinateIdsByLeaderPath(drugUser.getLeaderPath());
			if (CollectionsUtil.isNotEmptyList(drugUserIdList)){
				List<ProductResponseBean> productList = productLineMapper.getListByDrugUserId(drugUserIdList);
				if (CollectionsUtil.isNotEmptyList(productList)){
					return productList;
				}
			}
		}

		List<ProductResponseBean> list = new ArrayList<>();
		return list;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public PageResponseBean<List<CallVisitBean>> getCallVisitList(CallInfoListRequest request, String leaderPath) {
		PageResponseBean<List<CallVisitBean>> pageResponse = null;

		Long virtualDoctorId = request.getVirtualDoctorId();
		int count = callInfoMapper.getCallVisitCount(leaderPath, virtualDoctorId);
		if (count > 0) {
			int currentSize = request.getCurrentSize();
			int pageSize = request.getPageSize();
			// 获取电话拜访信息
			List<CallVisitBean> list = callInfoMapper.getCallVisitList(leaderPath, virtualDoctorId, currentSize, pageSize);
			if (CollectionsUtil.isNotEmptyList(list)) {
				// 获取电话拜访扩展信息
				this.getVirtualDoctorCallInfoMend(list);
			}
			pageResponse = new PageResponseBean(request, count, list);
		} 
		
		if (pageResponse == null) {
			pageResponse = new PageResponseBean(request, 0, Collections.emptyList());
		}
		
		return pageResponse;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private void configSaveCallInfoRequestValue(SaveCallInfoRequest saveRequest) {
		if (saveRequest.getVirtualDoctorId() == null) { // 非库内医生
			// 将医生ID置为0
			saveRequest.setVirtualDoctorId(0L);
		}

//		List<String> results = saveRequest.getVisitResult();
//		if (CollectionsUtil.isNotEmptyList(results)) {
//			results.forEach(result -> {
//				if (StringUtils.isNotBlank(result)) {
//					if ("成功招募".equals(result)) {
//						// 设置为成功招募
//						saveRequest.setIsRecruit(1);
//					}
//				}
//			});
//
//			if (saveRequest.getIsRecruit() == null) {
//				// 未知
//				saveRequest.setIsRecruit(-1);
//			}
//		}

		Integer recruitStatus = saveRequest.getRecruitStatus();
		if (recruitStatus == null){
			recruitStatus = RecruitEnum.UNKOWN.getType();
		}

		// 校验招募状态是否输入的合法
		if (recruitStatus.equals(RecruitEnum.SUCCESS_RECRUIT.getType()) ||
			recruitStatus.equals(RecruitEnum.DROP_OUT.getType())){
			saveRequest.setIsRecruit(recruitStatus);
		}else {
			saveRequest.setIsRecruit(RecruitEnum.UNKOWN.getType());
		}


	}
	
	private void configSaveCallInfoUnConnectedRequest(SaveCallInfoUnConnectedRequest saveRequest) {
		// 如果没有传状态值则设置为 cancelmakecall
		if (StringUtils.isBlank(saveRequest.getStatuaName())) {
			saveRequest.setStatuaName("cancelmakecall");
		}

		// statusName 为 emptynumber 时将 isBreakOff 设置为1
		if ("emptynumber".equalsIgnoreCase(saveRequest.getStatuaName())) {
			saveRequest.setIsBreakOff(1);
		} else {
			saveRequest.setIsBreakOff(0);
		}
	}
	
	/**
	 * 根据 List<CallVisitBean> 获取电话拜访扩展信息
	 * @param list
	 */
	private void getVirtualDoctorCallInfoMend(List<CallVisitBean> list) {
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
					visit.setAttitude(mend.getAttitude()); // 医生态度
//					String visitResultStr = mend.getVisitResult(); // 拜访结果
//					JSONArray visitResult = JSONObject.parseArray(visitResultStr);
					List<String> visitResultList = virtualDoctorCallInfoResultMapper.getVisitResultByCallId(callId);
					if (CollectionsUtil.isNotEmptyList(visitResultList)){
						visit.setVisitResultList(visitResultList);
					}

				}
			});
		}
	}
	
	/**
	 * 修改电话拜访信息,保存扩展信息<br>
	 * @param saveRequest
	 * @return 返回 callId
	 */
	private void saveCallInfo(BaseCallInfoRequest saveRequest) {
		VirtualDoctorCallInfoParams callVisitParams = this.getVirtualDoctorCallInfoParams(saveRequest);
		// P.S :保存电话拜访信息前前端通过调用call/save 已经向数据库插入记录,因此走的是修改
		callInfoMapper.updateVirtualDoctorCallInfo(callVisitParams);
		String nextVisitTime = callVisitParams.getNextVisitTime();
		if (StringUtils.isBlank(nextVisitTime)){
			callVisitParams.setNextVisitTime(null);
		}
		callInfoMendMapper.saveVirtualDoctorCallInfoMend(callVisitParams);

		if (saveRequest instanceof SaveCallInfoRequest){
			SaveCallInfoRequest saveCallInfoRequest = (SaveCallInfoRequest) saveRequest;
			this.saveCallInfoResult(callVisitParams.getId(), saveCallInfoRequest.getVisitResultId());
		}


	}

	/**
	 * 保存通过记录拜访结果
	 * @param id
	 * @param visitResultId
	 */
	private void saveCallInfoResult(Long id, List<Long> visitResultId) {
		if (id !=null && id > 0 && CollectionsUtil.isNotEmptyList(visitResultId)){
			virtualDoctorCallInfoResultMapper.batchInsert(id, visitResultId);
		}

	}

	/**
	 * 保存问卷做题结果
	 * @param saveRequest
	 * @return 返回影响条数
	 */
	private int doSaveVirtualQuestionnaireRecord(SaveCallInfoRequest saveRequest) {
		SaveVirtualQuestionnaireRecordRequestBean questionParams = new SaveVirtualQuestionnaireRecordRequestBean();
		questionParams.setVirtualDoctorId(saveRequest.getVirtualDoctorId());
		questionParams.setVirtualDrugUserId(saveRequest.getVirtualDrugUserId());
		questionParams.setVirtualQuestionaireId(saveRequest.getVirtualQuestionaireId());
		questionParams.setQuestions(saveRequest.getQuestions());
		questionParams.setCallId(saveRequest.getCallInfoId());
		
		return questionnaireService.saveQuestionnaire(questionParams);
	}
	
	/**
	 * 变更虚拟代医生扩展关系信息
	 * @param request
	 */
	private void alterRelationShip(BaseCallInfoRequest request) {
		DrugUserDoctorQuateParams relationShipParams = new DrugUserDoctorQuateParams();
		relationShipParams.setVirtualDrugUserId(request.getVirtualDrugUserId());
		relationShipParams.setDoctorId(request.getVirtualDoctorId());
		relationShipParams.setIsBreakOff(request.getIsBreakOff()); // 是否脱落来自页面传值(接通/未接通)
		List<Long> visitResultId = null;
		if (request instanceof SaveCallInfoRequest) {
			SaveCallInfoRequest saveRequest = (SaveCallInfoRequest)request;
			
			relationShipParams.setIsRecruit(saveRequest.getIsRecruit()); // 是否有药
			relationShipParams.setIsHasDrug(saveRequest.getIsHasDrug()); // 是否有药
			relationShipParams.setIsTarget(saveRequest.getIsTarget()); // 是否目标客户
			relationShipParams.setIsHasAe(saveRequest.getIsHasAe()); //是否AE
			relationShipParams.setHcpPotential(saveRequest.getHcpPotential());
			visitResultId = saveRequest.getVisitResultId();

			Integer productId = saveRequest.getProductId();
			if (productId != null && productId > 0){
				relationShipParams.setProductLineId(productId);
			}
		} else if (request instanceof SaveCallInfoUnConnectedRequest) {
			SaveCallInfoUnConnectedRequest saveRequest = (SaveCallInfoUnConnectedRequest)request;
			relationShipParams.setIsBreakOff(saveRequest.getIsBreakOff());
			Integer productId = saveRequest.getProductId();
			if (productId != null && productId > 0){
				relationShipParams.setProductLineId(productId);
			}
		}
		
		// 变更关系
		drugUserDoctorQuateMapper.replaceRelationShipInfo(relationShipParams);
		this.saveDrugUserDoctorQuateResult(relationShipParams.getId(), visitResultId);
	}

	/**
	 * 保存关系的拜访结果
	 * @param id
	 * @param visitResultId
	 */
	private void saveDrugUserDoctorQuateResult(Long id, List<Long> visitResultId) {

		if (id !=null && id > 0 && CollectionsUtil.isNotEmptyList(visitResultId)){
			drugUserDoctorQuateResultMapper.batchInsert(id, visitResultId);
		}
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private VirtualDoctorCallInfoParams getVirtualDoctorCallInfoParams (BaseCallInfoRequest saveRequest) {
		VirtualDoctorCallInfoParams callVisitParams = new VirtualDoctorCallInfoParams();
		callVisitParams.setCallId(saveRequest.getCallInfoId());
		callVisitParams.setVirtualDoctorId(saveRequest.getVirtualDoctorId());
		callVisitParams.setVirtualDrugUserId(saveRequest.getVirtualDrugUserId());
		
		// 这里的 type 值只能是1 或者 2在前面的参数校验中加了限制
		Integer type = saveRequest.getType(); 
		callVisitParams.setType(type);
		callVisitParams.setMobile(saveRequest.getMobile());
		callVisitParams.setRemark(saveRequest.getRemark());
		callVisitParams.setStatus(saveRequest.getStatus());
		callVisitParams.setStatusName(saveRequest.getStatuaName());
		callVisitParams.setIsBreakOff(saveRequest.getIsBreakOff()); // 是否脱落来自页面传值(接通/未接通)
		
		String nextVisitTime = saveRequest.getNextVisitTime();
		if(StringUtils.isNotBlank(nextVisitTime)) {
			callVisitParams.setNextVisitTime(nextVisitTime.concat(" 23:59:59"));
		}
		
		Integer virtualQuestinairedId = null;
		if (saveRequest instanceof SaveCallInfoRequest) {
			SaveCallInfoRequest saveCallInfoRequest = (SaveCallInfoRequest) saveRequest;
			
			callVisitParams.setAttitude(saveCallInfoRequest.getAttitude());
			callVisitParams.setCallUrl(((SaveCallInfoRequest) saveRequest).getCallUrl());
			callVisitParams.setProductId(saveCallInfoRequest.getProductId());
			callVisitParams.setStatus(1); // 接通
			
			callVisitParams.setIsRecruit(saveCallInfoRequest.getIsRecruit()); // 是否招募
			callVisitParams.setIsHasAe(saveCallInfoRequest.getIsHasAe()); // 是否AE
			callVisitParams.setIsHasDrug(saveCallInfoRequest.getIsHasDrug()); // 是否有药
			callVisitParams.setIsTarget(saveCallInfoRequest.getIsTarget()); // 是否目标
			callVisitParams.setHcpPotential(saveCallInfoRequest.getHcpPotential()); // 潜力

			Integer recruit = saveCallInfoRequest.getIsRecruit();
			if (RecruitEnum.SUCCESS_RECRUIT.getType().equals(recruit)){
				callVisitParams.setRecruitTime(new Date());
			}

			if (RecruitEnum.DROP_OUT.getType().equals(recruit)){
				callVisitParams.setDropOutTime(new Date());
			}


//			String visitResult = JSONObject.toJSONString(saveCallInfoRequest.getVisitResult());
//			callVisitParams.setVisitResult(visitResult);
			
			if(type.equals(1)) { // 呼出
				callVisitParams.setStatusName("answer"); // 状态名
			} else if(type.equals(2)) { // 呼入
				callVisitParams.setStatusName("incall"); // 状态名
			}
			
			virtualQuestinairedId = saveCallInfoRequest.getVirtualQuestionaireId();
		} else { // 未接通
			SaveCallInfoUnConnectedRequest saveCallInfoRequest = (SaveCallInfoUnConnectedRequest) saveRequest;
			virtualQuestinairedId = 0;
			callVisitParams.setStatus(0); // 未接通
			callVisitParams.setStatusName(saveCallInfoRequest.getStatuaName()); // 状态名
			callVisitParams.setProductId(saveCallInfoRequest.getProductId());
		}
		
		if (virtualQuestinairedId == null) {
			virtualQuestinairedId = 0;
		}
		callVisitParams.setDoctorQuestionnaireId(virtualQuestinairedId);
		
		return callVisitParams;
	}
	
}
