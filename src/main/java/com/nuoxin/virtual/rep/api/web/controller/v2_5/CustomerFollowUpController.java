package com.nuoxin.virtual.rep.api.web.controller.v2_5;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nuoxin.virtual.rep.api.common.bean.DefaultResponseBean;
import com.nuoxin.virtual.rep.api.common.bean.PageResponseBean;
import com.nuoxin.virtual.rep.api.common.util.mem.SessionMemUtils;
import com.nuoxin.virtual.rep.api.entity.DrugUser;
import com.nuoxin.virtual.rep.api.service.v2_5.CustomerFollowUpService;
import com.nuoxin.virtual.rep.api.service.v2_5.DrugUserService;
import com.nuoxin.virtual.rep.api.utils.CollectionsUtil;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.followup.ListRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.followup.ScreenRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.v2_5.followup.SearchRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.response.DrugUserResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.product.ProductResponseBean;
import com.nuoxin.virtual.rep.api.web.controller.response.v2_5.CustomerFollowListBean;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import shaded.org.apache.commons.lang3.StringUtils;

/**
 * 客户跟进-首页 Controller 类
 * @author xiekaiyu
 */
@Api(value = "客户跟进首页相关接口")
@RequestMapping(value = "/customer/followup/index")
@RestController
public class CustomerFollowUpController extends BaseController {
	
	@Resource
	private SessionMemUtils memUtils;
	@Resource(name="drugUserServiceImpl")
	private DrugUserService drugUserService;
	@Resource
	private CustomerFollowUpService customerFollowService;
	
	@SuppressWarnings("unchecked")
	@ApiOperation(value = "列表", notes = "列表")
	@RequestMapping(value = "/list", method = { RequestMethod.POST })
	public DefaultResponseBean<PageResponseBean<List<CustomerFollowListBean>>> list(HttpServletRequest request,
			@RequestBody ListRequestBean indexRequest) {
		DrugUser user = this.getDrugUser(request);
		if(user == null) {
			return this.getLoginErrorResponse();
		} 
		
		DefaultResponseBean<PageResponseBean<List<CustomerFollowListBean>>> responseBean = new DefaultResponseBean<>();
		PageResponseBean<List<CustomerFollowListBean>> pageResponse = customerFollowService.list(indexRequest, user.getLeaderPath());
		responseBean.setData(pageResponse);
		
		return responseBean;
	}
	
	@SuppressWarnings("unchecked")
	@ApiOperation(value = "搜索接口", notes = "搜索接口")
	@RequestMapping(value = "/search", method = { RequestMethod.POST })
	public DefaultResponseBean<PageResponseBean<List<CustomerFollowListBean>>> search(
			@RequestBody SearchRequestBean searchRequest, HttpServletRequest request) {
		DrugUser user = this.getDrugUser(request);
		if(user == null) {
			return this.getLoginErrorResponse();
		} 
		
		String search = searchRequest.getSearch();
		if (StringUtils.isBlank(search)) {
			return this.getParamsErrorResponse("search is null");
		}
		
		DefaultResponseBean<PageResponseBean<List<CustomerFollowListBean>>> responseBean = new DefaultResponseBean<>();
		PageResponseBean<List<CustomerFollowListBean>> pageResponse = customerFollowService.search(searchRequest,user.getLeaderPath());
		responseBean.setData(pageResponse);
		return responseBean;
	}
	
	@SuppressWarnings("unchecked")
	@ApiOperation(value = "筛选接口", notes = "筛选接口")
	@RequestMapping(value = "/screen", method = { RequestMethod.POST })
	public DefaultResponseBean<PageResponseBean<List<CustomerFollowListBean>>> screen(
			@RequestBody ScreenRequestBean screenRequest, HttpServletRequest request) {
		DrugUser user = this.getDrugUser(request);
		if(user == null) {
			return this.getLoginErrorResponse();
		} 
		
		List<Long> virtualDrugUserIds = screenRequest.getVirtualDrugUserIds();
		if (CollectionsUtil.isEmptyList(virtualDrugUserIds)) {
			return this.getParamsErrorResponse("virtualDrugUserIds is null");
		}
		
		List<Integer> productLineIds = screenRequest.getProductLineIds();
		if (CollectionsUtil.isEmptyList(productLineIds)) {
			return this.getParamsErrorResponse("productLineIds is null");
		}
		
		
		DefaultResponseBean<PageResponseBean<List<CustomerFollowListBean>>> responseBean = new DefaultResponseBean<>();
		PageResponseBean<List<CustomerFollowListBean>> pageResponse = customerFollowService.screen(screenRequest);
		responseBean.setData(pageResponse);
		return responseBean;
	}
	
	@ApiOperation(value = "更多筛选接口", notes = "更多筛选接口")
	@RequestMapping(value = "/search/more", method = { RequestMethod.POST })
	public ResponseEntity<DefaultResponseBean<Boolean>> searchMore(@RequestBody Object object) {
		// TODO
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@ApiOperation(value = "根据 leaderPath 获取所有下属医药代表信息", notes = "根据 leaderPath 获取所有下属医药代表信息")
	@RequestMapping(value = "/drug_users/get", method = { RequestMethod.GET })
	public DefaultResponseBean<List<DrugUserResponseBean>> getSubordinates(HttpServletRequest request) {
		DrugUser user = this.getDrugUser(request);
		if(user == null) {
			return this.getLoginErrorResponse();
		} 
		
		DefaultResponseBean<List<DrugUserResponseBean>> responseBean = new DefaultResponseBean<>();
		List<DrugUserResponseBean> list = drugUserService.getSubordinates(user.getLeaderPath());
		responseBean.setData(list);
		return responseBean;
	}
	
	@SuppressWarnings("unchecked")
	@ApiOperation(value = "根据 leaderPath 获取所有产品线信息", notes = "根据 leaderPath 获取所有产品线信息")
	@RequestMapping(value = "/product_lines/get", method = { RequestMethod.GET })
	public DefaultResponseBean<List<ProductResponseBean>> getAllProductLines(HttpServletRequest request) {
		DrugUser user = this.getDrugUser(request);
		if(user == null) {
			return this.getLoginErrorResponse();
		} 
		
		DefaultResponseBean<List<ProductResponseBean>> responseBean = new DefaultResponseBean<>();
		List<ProductResponseBean> list = drugUserService.getProductsByDrugUserId(user.getLeaderPath());
		responseBean.setData(list);
		return responseBean;
	}

}
