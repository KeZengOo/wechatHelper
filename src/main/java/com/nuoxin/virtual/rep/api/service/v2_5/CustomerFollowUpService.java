package com.nuoxin.virtual.rep.api.service.v2_5;

import java.util.List;

import com.nuoxin.virtual.rep.api.common.bean.PageResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.followup.ListRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.followup.ScreenRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.followup.SearchRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.CustomerFollowListBean;

/**
 * @author xiekaiyu
 */
public interface CustomerFollowUpService {
	
	/**
	 * 客户跟进列表实现
	 * @param request
	 * @return
	 */
	PageResponseBean<List<CustomerFollowListBean>> list(ListRequestBean request, String leaderPath);
	
	/**
	 * 客户跟进搜索实现
	 * @param request
	 * @return
	 */
	PageResponseBean<List<CustomerFollowListBean>> search(SearchRequestBean request, String leaderPath);
	
	/**
	 * 客户跟进筛选实现
	 * @param request
	 * @return
	 */
	PageResponseBean<List<CustomerFollowListBean>> screen(ScreenRequestBean request);
}