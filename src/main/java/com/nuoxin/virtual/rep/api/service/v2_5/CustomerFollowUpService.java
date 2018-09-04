package com.nuoxin.virtual.rep.api.service.v2_5;

import java.util.List;

import com.nuoxin.virtual.rep.api.common.bean.PageResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.CustomerFollowListRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.doctor.CustomerFollowListBean;

/**
 * @author xiekaiyu
 */
public interface CustomerFollowUpService {
	
	/**
	 * 客户跟进列表实现
	 * @param request
	 * @return
	 */
	PageResponseBean<List<CustomerFollowListBean>> list(CustomerFollowListRequestBean request);
	
	/**
	 * 客户跟进搜索实现
	 * @param request
	 * @return
	 */
	PageResponseBean<List<CustomerFollowListBean>> search(CustomerFollowListRequestBean request);
	
	/**
	 * 客户跟进筛选实现
	 * @param request
	 * @return
	 */
	PageResponseBean<List<CustomerFollowListBean>> screen(CustomerFollowListRequestBean request);
}
