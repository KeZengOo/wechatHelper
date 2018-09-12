package com.nuoxin.virtual.rep.api.web.intercept;

import com.nuoxin.virtual.rep.api.common.enums.ErrorEnum;
import com.nuoxin.virtual.rep.api.common.exception.BusinessException;
import com.nuoxin.virtual.rep.api.config.SessionConfig;
import com.nuoxin.virtual.rep.api.entity.DrugUser;
import com.nuoxin.virtual.rep.api.service.SecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by fenggang on 7/28/17.
 */
@Component
public class LoginValidationInterceptor extends HandlerInterceptorAdapter {

	private static final Logger logger = LoggerFactory.getLogger(LoginValidationInterceptor.class);
	
	@Autowired
	private SecurityService sercurityService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if(request.getMethod().equals("OPTIONS")){
			return true;
		}
		
		if(request.getServletPath().equals("/error")){
			throw new BusinessException(ErrorEnum.ERROR);
		}
		
		logger.info("接口【{}】请求开始登录验证",request.getServletPath());
		sercurityService.sessionValidation(request);
		DrugUser user = sercurityService.getDrugUser(request);
		logger.info("用户【"+user.getName()+"】操作了接口：【{}】",request.getServletPath());
		 //设置运营人员ID
		request.setAttribute(SessionConfig.DEFAULT_REQUEST_DRUG_USER,user);
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
			throws Exception {}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {}
}
