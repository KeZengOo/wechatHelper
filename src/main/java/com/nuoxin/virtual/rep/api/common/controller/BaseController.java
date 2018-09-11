package com.nuoxin.virtual.rep.api.common.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nuoxin.virtual.rep.api.common.bean.DefaultResponseBean;
import com.nuoxin.virtual.rep.api.common.enums.ErrorEnum;
import com.nuoxin.virtual.rep.api.common.exception.BusinessException;
import com.nuoxin.virtual.rep.api.common.util.StringUtils;
import com.nuoxin.virtual.rep.api.config.SessionConfig;
import com.nuoxin.virtual.rep.api.entity.DrugUser;
import com.nuoxin.virtual.rep.api.web.controller.CallBackController;
import com.nuoxin.virtual.rep.api.web.controller.request.analysis.TargetAnalysisRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.analysis.TrendAnalysisRequestBean;
import com.nuoxin.virtual.rep.api.web.controller.request.call.CallRequestBean;

/**
 * Created by fenggang on 9/11/17.
 */
public class BaseController {

	protected static final Logger logger = LoggerFactory.getLogger(CallBackController.class);

	/**
	 * 获取 drugUserId
	 * @param request
	 * @return  成功返回 drugUserId,否则返回0L
	 */
	protected Long getLoginId(HttpServletRequest request) {
		Long drugUserId = 0L;

		DrugUser drugUser = (DrugUser) request.getAttribute(SessionConfig.DEFAULT_REQUEST_DRUG_USER);
		if (drugUser != null) {
			drugUserId = drugUser.getId();
		}

		return drugUserId;
	}

	/**
	 * 获取 DrugUser 对象
	 * @param request
	 * @return 成功返回 DrugUser 对象,否则返回 null
	 */
	protected DrugUser getLoginUser(HttpServletRequest request) {
		DrugUser drugUser = (DrugUser) request.getAttribute(SessionConfig.DEFAULT_REQUEST_DRUG_USER);
		if (drugUser == null) {
			throw new BusinessException(ErrorEnum.LOGIN_NO);
		}

		return drugUser;
	}

	protected String checkoutDate(TrendAnalysisRequestBean bean) {
		if (!StringUtils.isNotEmtity(bean.getDate())) {
			return "日期格式不能为空";
		}

		if (bean.getDateType() == null) {
			return "日期类型错误";
		}

		return "";
	}

	protected String checkoutDate(TargetAnalysisRequestBean bean) {
		if (!StringUtils.isNotEmtity(bean.getDate())) {
			return "日期格式不能为空";
		}

		if (bean.getDateType() == null) {
			return "日期类型错误";
		}

		return "";
	}

	/**
	 * 电话呼叫状态转换:dealing -> answer,notDeal -> incall
	 * @param bean
	 */
	protected void convertCallStatus(CallRequestBean bean) {
		String statusName = bean.getStatusName();
		if (StringUtils.isNotBlank(statusName)) {
			// dealing -> answer
			if ("dealing".equalsIgnoreCase(statusName)) {
				bean.setStatusName("answer");
			} 
			// notDeal -> incall
			else if ("notDeal".equalsIgnoreCase(statusName)) {
				bean.setStatusName("incall");
			}
		}
	}
	
	@SuppressWarnings("rawtypes")
	protected DefaultResponseBean getLoginErrorResponse() {
		DefaultResponseBean responseBean = new DefaultResponseBean<>();
		ErrorEnum loginError = ErrorEnum.LOGIN_NO;
		responseBean.setCode(loginError.getStatus());
		responseBean.setMessage(loginError.getMessage());
		responseBean.setDescription(loginError.getMessage());
		
		return responseBean;
	}

}
