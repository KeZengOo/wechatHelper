package com.nuoxin.virtual.rep.api.service.v2_5.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.nuoxin.virtual.rep.api.common.bean.PageRequestBean;
import com.nuoxin.virtual.rep.api.common.bean.PageResponseBean;
import com.nuoxin.virtual.rep.api.entity.Doctor;
import com.nuoxin.virtual.rep.api.mybatis.DoctorMapper;
import com.nuoxin.virtual.rep.api.mybatis.DrugUserMapper;
import com.nuoxin.virtual.rep.api.mybatis.ProductLineMapper;
import com.nuoxin.virtual.rep.api.service.v2_5.CustomerFollowUpService;
import com.nuoxin.virtual.rep.api.utils.CollectionsUtil;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.followup.ListRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.followup.ScreenRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.followup.SearchRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.CustomerFollowListBean;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.TableHeader;

import io.swagger.annotations.ApiModelProperty;
import shaded.org.apache.commons.lang3.StringUtils;

/**
 * 客户跟进实现类
 * @author xiekaiyu
 */
@Service
public class CustomerFollowUpServiceImpl implements CustomerFollowUpService{
	
	private static final List<TableHeader> tableHeaders = new ArrayList<>(15);
	
	@Resource
	private DrugUserMapper drugUserMapper;
	@Resource
	private ProductLineMapper productLineMapper;
	@Resource
	private DoctorMapper doctorMapper;
	
	static {
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

		// TODO 补全 @田存
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public PageResponseBean<List<CustomerFollowListBean>> list(ListRequestBean request, String leaderPath) {
		int count = 0;
		PageResponseBean pageResponseBean = null;
		
		// 获取所有下属(直接&间接) virtualDrugUserIds
		List<Long> virtualDrugUserIds = this.getSubordinateIds(leaderPath);
		if (CollectionsUtil.isNotEmptyList(virtualDrugUserIds)) {
			count = doctorMapper.getListCount(virtualDrugUserIds, null, null);
			if(count > 0) {
				int currentSize = request.getCurrentSize();
				int pageSize = request.getPageSize();
				List<CustomerFollowListBean> list = doctorMapper.getList(virtualDrugUserIds, currentSize, pageSize, null, null);
				pageResponseBean = this.getDoctorsList(count, list, request);
				
				// TODO 补充对应的产品信息 @田存
			}
		} 
		
		this.compensate(request, pageResponseBean);
		
		return pageResponseBean;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public PageResponseBean<List<CustomerFollowListBean>> search(SearchRequestBean request, String leaderPath) {
		PageResponseBean pageResponseBean = null;
		int count = 0;
		
		// 获取所有下属(直接&间接) virtualDrugUserIds
		List<Long> virtualDrugUserIds = this.getSubordinateIds(leaderPath);
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
	 * 获取所有下属(直接&间接) virtualDrugUserIds
	 * @param leaderPath 领导路径
	 * @return List<Long>
	 */
	private List<Long> getSubordinateIds(String leaderPath) {
		return drugUserMapper.getSubordinateIdsByLeaderPath(leaderPath);
	}
	
	/**
	 * 医生列表信息处理(重要)
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
				List<String> mobiles = doctor.getMobiles();
				mobiles.add(doctor.getDoctorMobile());
				String secondary = doctor.getSecondaryDoctorMobile();
				if(StringUtils.isNotBlank(secondary)) {
					mobiles.add(doctor.getSecondaryDoctorMobile());
				}
				String thirdary = doctor.getThirdaryDoctorMobile();
				if(StringUtils.isNotBlank(thirdary)) {
					mobiles.add(doctor.getThirdaryDoctorMobile());
				}
				
				Date visitTime = doctor.getVisitTime();
				if (visitTime != null) {
					long visitTimeDelta = System.currentTimeMillis() - visitTime.getTime();
					String lastVisitTime = this.getVisitStr(visitTimeDelta);
					doctor.setVisitTimeStr(lastVisitTime);
				} else {
					doctor.setVisitTimeStr("无");
				}

				Date nextVisitTime = doctor.getNextVisitTime();
				if (nextVisitTime != null) {
					long nextTimeDelta = System.currentTimeMillis() - nextVisitTime.getTime();
					String nextVisitTimeStr = this.getVisitStr(nextTimeDelta);
					doctor.setNextVisitTimeStr(nextVisitTimeStr);
				} else {
					doctor.setNextVisitTimeStr("无");
				}
				
				String visitResult = doctor.getVisitResult();
				if (StringUtils.isNotBlank(visitResult)) {
					JSONArray jsonArr = JSONObject.parseArray(visitResult);
					doctor.setVisitResultObj(jsonArr);
				}
				
				String wechat = doctor.getWechat();
				if (StringUtils.isNotBlank(wechat)) {
					doctor.setIsHasWeChat(1);
				} else {
					doctor.setIsHasWeChat(0);
				}
			});
		}

		if (CollectionsUtil.isEmptyList(list)) {
			list = Collections.emptyList();
		}

		return new PageResponseBean(pageRequestBean, count, list);
	}
	
	private String getVisitStr (long delta) {
		// 转换成分钟间隔
		long minuteInterval = delta / 60000;
		String str = "";
		if(minuteInterval > 0) {
			if (minuteInterval < 60) {
				str = str.concat(String.valueOf(minuteInterval)).concat("分钟");
			} else if (minuteInterval < 1440) {
				BigDecimal result = BigDecimal.valueOf(minuteInterval).divide(BigDecimal.valueOf(60), 2, RoundingMode.HALF_UP);
				str = str.concat(String.valueOf(result.doubleValue())).concat("小时");
			} else {
				BigDecimal result = BigDecimal.valueOf(minuteInterval).divide(BigDecimal.valueOf(1440), 2, RoundingMode.HALF_UP);
				str = str.concat(String.valueOf(result.doubleValue())).concat("天");
			}
			str = str.concat("前");
		} else {
			minuteInterval = -1 * minuteInterval;
			if (minuteInterval < 60) {
				str = str.concat(String.valueOf(minuteInterval)).concat("分钟");
			} else if (minuteInterval < 1440) {
				BigDecimal result = BigDecimal.valueOf(minuteInterval).divide(BigDecimal.valueOf(60), 2, RoundingMode.HALF_UP);
				str = str.concat(String.valueOf(result.doubleValue())).concat("小时");
			} else {
				BigDecimal result = BigDecimal.valueOf(minuteInterval).divide(BigDecimal.valueOf(1440), 2, RoundingMode.HALF_UP);
				str = str.concat(String.valueOf(result.doubleValue())).concat("天");
			}
			str = str.concat("后");
		}
		
		return str;
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
	
}
