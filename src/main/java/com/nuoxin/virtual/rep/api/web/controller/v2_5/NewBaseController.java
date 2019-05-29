package com.nuoxin.virtual.rep.api.web.controller.v2_5;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nuoxin.virtual.rep.api.common.bean.DefaultResponseBean;
import com.nuoxin.virtual.rep.api.common.enums.ErrorEnum;
import com.nuoxin.virtual.rep.api.entity.DrugUser;
import com.nuoxin.virtual.rep.api.entity.ProductLine;
import com.nuoxin.virtual.rep.api.entity.Role;
import com.nuoxin.virtual.rep.api.enums.RoleTypeEnum;
import com.nuoxin.virtual.rep.api.mybatis.DrugUserMapper;
import com.nuoxin.virtual.rep.api.mybatis.ProductLineMapper;
import com.nuoxin.virtual.rep.api.service.SecurityService;
import com.nuoxin.virtual.rep.api.utils.CollectionsUtil;
import com.nuoxin.virtual.rep.api.web.controller.request.v3_0.CommonRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 新的 BaseController
 * @author xiekaiyu
 */
public class NewBaseController {
	
	@Resource
	private SecurityService sercurityService;

	@Resource
	private ProductLineMapper productLineMapper;
	
	/**
	 * 根据 request 从会话变量中获取 DrugUser 信息 
	 * @param request
	 * @return 成功返回 DrugUser 对象,否则返回 null
	 */
	protected DrugUser getDrugUser(HttpServletRequest request) {
		return sercurityService.getDrugUser(request);
	}

	/**
	 * 获取Request
	 * @author dangjunhui
	 * @return request
	 */
	protected HttpServletRequest getRequest() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	}

	/**
	 * 获取Response
	 * @author dangjunhui
	 * @return request
	 */
	protected HttpServletResponse getResponse() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
	}

	/**
	 * 根据 request 从会话变量中获取 DrugUser 信息
	 * @return 成功返回 DrugUser 对象,否则返回 null
	 */
	protected DrugUser getDrugUser() {
		return sercurityService.getDrugUser(getRequest());
	}

	/**
	 * 登录错误信息
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	protected DefaultResponseBean getLoginErrorResponse() {
		DefaultResponseBean responseBean = new DefaultResponseBean<>();
		ErrorEnum loginError = ErrorEnum.LOGIN_NO;
		responseBean.setCode(loginError.getStatus());
		responseBean.setMessage(loginError.getMessage());
		responseBean.setDescription(loginError.getMessage());
		return responseBean;
	}
	
	/**
	 * 返回带有错误信息
	 * @param msg
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	protected DefaultResponseBean getParamsErrorResponse(String msg) {
		DefaultResponseBean responseBean = new DefaultResponseBean<>();
		ErrorEnum loginError = ErrorEnum.SYSTEM_REQUEST_PARAM_ERROR;
		responseBean.setCode(loginError.getStatus());
		responseBean.setMessage(msg);
		responseBean.setDescription(msg);
		return responseBean;
	}


	/**
	 * 根据角色补充代表查询条件
	 * @param drugUser
	 * @param bean
	 * @return
	 */
	protected void fillDrugUserIdListByRoleId(DrugUser drugUser, CommonRequest bean){
		Long roleId = drugUser.getRoleId();
		if (RoleTypeEnum.SALE.getType().equals(roleId) || RoleTypeEnum.RECRUIT_SALE.getType().equals(roleId) || RoleTypeEnum.COVER_SALE.getType().equals(roleId)){
			List<Long> drugUserIdList = new ArrayList<>();
			drugUserIdList.add(drugUser.getId());
			bean.setDrugUserIdList(drugUserIdList);
		}

	}


	/**
	 * 获得代表下的产品
	 * @param drugUserId
	 * @return
	 */
	protected List<ProductLine> getProductLineByDrugUserId(Long drugUserId){
		List<ProductLine> productLineList = productLineMapper.findByDrugUserId(drugUserId);

		return productLineList;

	}




	/**
	 * 获得代表下的产品ID
	 * @param drugUserId
	 * @return
	 */
	protected List<Long> getProductIdByDrugUserId(Long drugUserId){
		List<ProductLine> productLineList = productLineMapper.findByDrugUserId(drugUserId);
		if (CollectionsUtil.isNotEmptyList(productLineList)){
			List<Long> productIdList = productLineList.stream().map(ProductLine::getId).distinct().collect(Collectors.toList());
			return productIdList;
		}

		return null;
	}


}
