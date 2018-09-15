package com.nuoxin.virtual.rep.api.service.v2_5.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.nuoxin.virtual.rep.api.common.bean.PageRequestBean;
import com.nuoxin.virtual.rep.api.common.bean.PageResponseBean;
import com.nuoxin.virtual.rep.api.mybatis.DoctorMapper;
import com.nuoxin.virtual.rep.api.service.v2_5.CommonService;
import com.nuoxin.virtual.rep.api.service.v2_5.CustomerFollowUpService;
import com.nuoxin.virtual.rep.api.utils.CollectionsUtil;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.followup.ListRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.followup.ScreenRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.followup.SearchRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.CustomerFollowListBean;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.TableHeader;

import shaded.org.apache.commons.lang3.StringUtils;

/**
 * 客户跟进实现类
 * @author xiekaiyu
 */
@Service
public class CustomerFollowUpServiceImpl implements CustomerFollowUpService{
	
	private static final Logger logger = LoggerFactory.getLogger(CustomerFollowUpServiceImpl.class);
	
	private static final List<TableHeader> tableHeaders = new ArrayList<>(15);
	
	/**
	 * 初始化表头信息
	 */
	static {
		CustomerFollowUpServiceImpl.getTableHeaders();
	}
	
	@Resource
	private DoctorMapper doctorMapper;
	@Resource
	private CommonService commonService;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public PageResponseBean<List<CustomerFollowListBean>> list(ListRequestBean pageRequest, String leaderPath) {
		int count = 0;
		PageResponseBean pageResponse = null;
		// 获取所有下属(直接&间接) virtualDrugUserIds
		List<Long> virtualDrugUserIds = commonService.getSubordinateIds(leaderPath);
		if (CollectionsUtil.isNotEmptyList(virtualDrugUserIds)) {
			count = doctorMapper.getListCount(virtualDrugUserIds, null, null);
			if(count > 0) {
				int currentSize = pageRequest.getCurrentSize();
				int pageSize = pageRequest.getPageSize();
				long startTime = System.currentTimeMillis();
				List<CustomerFollowListBean> list = doctorMapper.getList(virtualDrugUserIds, currentSize, pageSize, null, null);
				long endTime = System.currentTimeMillis();
				logger.info("list 耗时{}", (endTime-startTime) ,"ms");
				
				pageResponse = this.getDoctorsList(count, list, pageRequest);
			}
		} 
		
		this.compensate(pageRequest, pageResponse);
		return pageResponse;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public PageResponseBean<List<CustomerFollowListBean>> search(SearchRequestBean request, String leaderPath) {
		PageResponseBean pageResponseBean = null;
		int count = 0;
		 // 获取所有下属(直接&间接) virtualDrugUserIds
		List<Long> virtualDrugUserIds = commonService.getSubordinateIds(leaderPath);
		if (CollectionsUtil.isNotEmptyList(virtualDrugUserIds)) {
			String search = request.getSearch();
			count = doctorMapper.getListCount(virtualDrugUserIds, search, null);
			if(count > 0) {
				int currentSize = request.getCurrentSize();
				int pageSize = request.getPageSize();
				List<CustomerFollowListBean> list = doctorMapper.getList(virtualDrugUserIds, currentSize, pageSize, search, null);
				pageResponseBean = this.getDoctorsList(count, list, request);
			}
		} 
		
		this.compensate(request, pageResponseBean);
		return pageResponseBean;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public PageResponseBean<List<CustomerFollowListBean>> screen(ScreenRequestBean request) {
		PageResponseBean pageResponseBean = null;
		List<Long> virtualDrugUserIds = request.getVirtualDrugUserIds();
		List<Integer> productLineIds = request.getProductLineIds();
		
		int count = doctorMapper.getListCount(virtualDrugUserIds, null, productLineIds);
		if(count > 0 ) {
			int currentSize = request.getCurrentSize();
			int pageSize = request.getPageSize();
			List<CustomerFollowListBean> list = doctorMapper.getList(virtualDrugUserIds, currentSize, pageSize, null,
					productLineIds);
			pageResponseBean = this.getDoctorsList(count, list, request);
		}
		
		this.compensate(request, pageResponseBean);
		return pageResponseBean;
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * 医生列表信息处理(重要) TODO 补充对应的产品信息 @田存
	 * @param count int
	 * @param list List<CustomerFollowListBean>
	 * @param pageRequestBean
	 * @return PageResponseBean<List<CustomerFollowListBean>>
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private PageResponseBean<List<CustomerFollowListBean>> getDoctorsList(int count, List<CustomerFollowListBean> list,
			PageRequestBean pageRequestBean) {
		if (CollectionsUtil.isNotEmptyList(list)) {
			list.forEach(doctor -> {
				this.alterCustomerFollowListBean(doctor);
				// TODO 补充对应的产品信息 @田存
			});
		}

		if (CollectionsUtil.isEmptyList(list)) {
			list = Collections.emptyList();
		}

		return new PageResponseBean(pageRequestBean, count, list);
	}
	
	/**
	 * 修正列表信息
	 * @param doctor
	 */
	private void alterCustomerFollowListBean(CustomerFollowListBean doctor) {
		// 手机号s
		List<String> mobiles = doctor.getMobiles();
		mobiles.add(doctor.getDoctorMobile()); // 主要
		String secondary = doctor.getSecondaryDoctorMobile(); // 次要
		if(StringUtils.isNotBlank(secondary)) {
			mobiles.add(doctor.getSecondaryDoctorMobile());
		}
		String thirdary = doctor.getThirdaryDoctorMobile();// 三要
		if(StringUtils.isNotBlank(thirdary)) {
			mobiles.add(doctor.getThirdaryDoctorMobile());
		}
		
		// 拜访时间
		Date visitTime = doctor.getVisitTime();
		if (visitTime != null) {
			long visitTimeDelta = System.currentTimeMillis() - visitTime.getTime();
			String lastVisitTime = commonService.alterVisitTimeContent(visitTimeDelta);
			doctor.setVisitTimeStr(lastVisitTime);
		} else {
			doctor.setVisitTimeStr("无");
		}

		// 下次拜访时间
		Date nextVisitTime = doctor.getNextVisitTime();
		if (nextVisitTime != null) {
			long nextTimeDelta = System.currentTimeMillis() - nextVisitTime.getTime();
			String nextVisitTimeStr = commonService.alterVisitTimeContent(nextTimeDelta);
			doctor.setNextVisitTimeStr(nextVisitTimeStr);
		} else {
			doctor.setNextVisitTimeStr("无");
		}
		
		// 拜访结果
		String visitResult = doctor.getVisitResult();
		if (StringUtils.isNotBlank(visitResult)) {
			JSONArray jsonArr = JSONObject.parseArray(visitResult);
			doctor.setVisitResultObj(jsonArr);
		}
		
		// 是否有微信
		String wechat = doctor.getWechat();
		if (StringUtils.isNotBlank(wechat)) {
			doctor.setIsHasWeChat(1); // 有
		} else {
			doctor.setIsHasWeChat(0); // 无
		}
	}
	
	/**
	 * PageResponseBean 为null 时的补偿
	 * @param pageResponseBean
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void compensate(PageRequestBean request, PageResponseBean pageResponseBean) {
		if (pageResponseBean == null) {
			int count = 0;
			pageResponseBean = new PageResponseBean(request, count, Collections.emptyList());
		}
		pageResponseBean.setTableHeaders(tableHeaders);
	}
	
	private static  void getTableHeaders () {
		TableHeader id = new TableHeader();
		id.setLabel("ID");
		id.setName("doctorId");
		tableHeaders.add(id);
		
		TableHeader doctorName = new TableHeader();
		doctorName.setLabel("客户姓名");
		doctorName.setName("doctorName");
		tableHeaders.add(doctorName);
		
		TableHeader mobile = new TableHeader();
		mobile.setLabel("手机号");
		mobile.setName("mobiles");
		tableHeaders.add(mobile);
		
		TableHeader gender = new TableHeader();
		gender.setLabel("性别");
		gender.setName("gender");
		tableHeaders.add(gender);
		
		TableHeader department = new TableHeader();
		department.setLabel("科室");
		department.setName("department");
		tableHeaders.add(department);
		
		TableHeader isHasWeChat = new TableHeader();
		isHasWeChat.setLabel("是否添加微信");
		isHasWeChat.setName("isHasWeChat");
		tableHeaders.add(isHasWeChat);
		
		TableHeader hospitalName = new TableHeader();
		hospitalName.setLabel("医院");
		hospitalName.setName("hospitalName");
		tableHeaders.add(hospitalName);
		
		TableHeader visitTime = new TableHeader();
		visitTime.setLabel("上次拜访时间");
		visitTime.setName("visitTimeStr");
		tableHeaders.add(visitTime);
		
		TableHeader visitResult = new TableHeader();
		visitResult.setLabel("拜访结果");
		visitResult.setName("visitResultObj");
		tableHeaders.add(visitResult);
		
		TableHeader nextVisitTime = new TableHeader();
		nextVisitTime.setLabel("下次拜访时间");
		nextVisitTime.setName("nextVisitTimeStr");
		tableHeaders.add(nextVisitTime);

		// TODO 补全产品信息 @田存
	}
}
