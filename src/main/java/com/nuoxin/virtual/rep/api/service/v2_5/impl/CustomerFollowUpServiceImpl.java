package com.nuoxin.virtual.rep.api.service.v2_5.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.nuoxin.virtual.rep.api.common.bean.PageRequestBean;
import com.nuoxin.virtual.rep.api.common.bean.PageResponseBean;
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
		TableHeader header = new TableHeader();
		header.setLabel("客户姓名");
		header.setName("doctorName");

		TableHeader header2 = new TableHeader();
		header2.setLabel("手机号");
		header2.setName("doctorMobile");

		tableHeaders.add(header);
		tableHeaders.add(header2);
		
		// TODO 补全 @谢开宇
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
				Date visitTime = doctor.getVisitTime();
				if (visitTime != null) {
					long lastVisitTimeInterval = System.currentTimeMillis() - visitTime.getTime();
					lastVisitTimeInterval = lastVisitTimeInterval / 60000;
					doctor.setLastVisitTimeInterval(lastVisitTimeInterval);
				} else {
					doctor.setLastVisitTimeInterval(-1);
				}
			});
		}

		if (CollectionsUtil.isEmptyList(list)) {
			list = Collections.emptyList();
		}

		return new PageResponseBean(pageRequestBean, count, list);
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
