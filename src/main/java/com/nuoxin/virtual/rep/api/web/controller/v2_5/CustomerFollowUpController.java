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
@Api(value = "V2.5客户跟进-首页相关接口")
@RequestMapping(value = "/customer/followup/index")
@RestController
public class CustomerFollowUpController extends NewBaseController {
	
	@Resource
	private SessionMemUtils memUtils;
	@Resource(name="drugUserServiceImpl")
	private DrugUserService drugUserService;
	@Resource
	private CustomerFollowUpService customerFollowService;
	
	@SuppressWarnings("unchecked")
	@ApiOperation(value = "客户医生拜访列表信息")
	@RequestMapping(value = "/list", method = { RequestMethod.POST })
	public DefaultResponseBean<PageResponseBean<List<CustomerFollowListBean>>> list(HttpServletRequest request,
			@RequestBody ListRequestBean indexRequest) {
		DrugUser user = super.getDrugUser(request);
		if(user == null) {
			return super.getLoginErrorResponse();
		} 
		
		// 从会话变量中获取 leaderPath
		String leaderPath = user.getLeaderPath();
		PageResponseBean<List<CustomerFollowListBean>> pageResponse = customerFollowService.list(indexRequest, leaderPath);
		DefaultResponseBean<PageResponseBean<List<CustomerFollowListBean>>> responseBean = new DefaultResponseBean<>();
		responseBean.setData(pageResponse);
		
		return responseBean;
	}
	
	@SuppressWarnings("unchecked")
	@ApiOperation(value = "搜索接口")
	@RequestMapping(value = "/search", method = { RequestMethod.POST })
	public DefaultResponseBean<PageResponseBean<List<CustomerFollowListBean>>> search(HttpServletRequest request,
			@RequestBody SearchRequestBean searchRequest) {
		DrugUser user = super.getDrugUser(request);
		if(user == null) {
			return super.getLoginErrorResponse();
		} 
		
		String search = searchRequest.getSearch();
		if(StringUtils.isBlank(search)) {
			return super.getParamsErrorResponse("search is blank");
		}

		PageResponseBean<List<CustomerFollowListBean>> pageResponse = customerFollowService.search(searchRequest,user.getLeaderPath());
		DefaultResponseBean<PageResponseBean<List<CustomerFollowListBean>>> responseBean = new DefaultResponseBean<>();
		responseBean.setData(pageResponse);
		return responseBean;
	}
	
	@SuppressWarnings("unchecked")
	@ApiOperation(value = "筛选接口")
	@RequestMapping(value = "/screen", method = { RequestMethod.POST })
	public DefaultResponseBean<PageResponseBean<List<CustomerFollowListBean>>> screen(
			@RequestBody ScreenRequestBean screenRequest, HttpServletRequest request) {
		DrugUser user = super.getDrugUser(request);
		if(user == null) {
			return super.getLoginErrorResponse();
		} 
		
		List<Long> virtualDrugUserIds = screenRequest.getVirtualDrugUserIds();
		if (CollectionsUtil.isEmptyList(virtualDrugUserIds)) {
			return super.getParamsErrorResponse("virtualDrugUserIds is empty list");
		}
		
		List<Integer> productLineIds = screenRequest.getProductLineIds();
		if (CollectionsUtil.isEmptyList(productLineIds)) {
			return super.getParamsErrorResponse("productLineIds is empty list");
		}
		
		PageResponseBean<List<CustomerFollowListBean>> pageResponse = customerFollowService.screen(screenRequest);
		DefaultResponseBean<PageResponseBean<List<CustomerFollowListBean>>> responseBean = new DefaultResponseBean<>();
		responseBean.setData(pageResponse);
		
		return responseBean;
	}
	
	@ApiOperation(value = "更多筛选接口 @田存补充")
	@RequestMapping(value = "/screen/more", method = { RequestMethod.POST })
	public ResponseEntity<DefaultResponseBean<Boolean>> searchMore(@RequestBody Object object) {
		// TODO @田存
		return null;
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@SuppressWarnings("unchecked")
	@ApiOperation(value = "获取所有下属医药代表信息(医药代表)")
	@RequestMapping(value = "/drug_users/get", method = { RequestMethod.GET })
	public DefaultResponseBean<List<DrugUserResponseBean>> getSubordinates(HttpServletRequest request) {
		DrugUser user = super.getDrugUser(request);
		if(user == null) {
			return super.getLoginErrorResponse();
		} 
		
		List<DrugUserResponseBean> list = drugUserService.getSubordinates(user.getLeaderPath());
		DefaultResponseBean<List<DrugUserResponseBean>> responseBean = new DefaultResponseBean<>();
		responseBean.setData(list);
		
		return responseBean;
	}
	
	@SuppressWarnings("unchecked")
	@ApiOperation(value = "根据获取所有产品线信息(产品)")
	@RequestMapping(value = "/product_lines/get", method = { RequestMethod.GET })
	public DefaultResponseBean<List<ProductResponseBean>> getAllProductLines(HttpServletRequest request) {
		DrugUser user = super.getDrugUser(request);
		if(user == null) {
			return super.getLoginErrorResponse();
		} 
		
		List<ProductResponseBean> list = drugUserService.getProductsByDrugUserId(user.getLeaderPath());
		DefaultResponseBean<List<ProductResponseBean>> responseBean = new DefaultResponseBean<>();
		responseBean.setData(list);
		
		return responseBean;
	}

}
