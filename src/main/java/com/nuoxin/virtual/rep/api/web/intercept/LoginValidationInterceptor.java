package com.nuoxin.virtual.rep.api.web.intercept;

import com.nuoxin.virtual.rep.api.common.constant.user.UserConstant;
import com.nuoxin.virtual.rep.api.common.enums.ErrorEnum;
import com.nuoxin.virtual.rep.api.common.exception.BusinessException;
import com.nuoxin.virtual.rep.api.common.exception.NeedLoginException;
import com.nuoxin.virtual.rep.api.config.SessionConfig;
import com.nuoxin.virtual.rep.api.dao.DrugUserRepository;
import com.nuoxin.virtual.rep.api.entity.DrugUser;
import com.nuoxin.virtual.rep.api.mybatis.ShortUrlWhiteMapper;
import com.nuoxin.virtual.rep.api.service.RoleUserService;
import com.nuoxin.virtual.rep.api.service.SecurityService;
import com.nuoxin.virtual.rep.api.utils.Aes128Util;
import com.nuoxin.virtual.rep.api.utils.CookieUtil;
import com.nuoxin.virtual.rep.api.utils.StringUtil;
import com.nuoxin.virtual.rep.api.utils.UrlUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
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

	@Resource
	private DrugUserRepository drugUserRepository;

	@Resource
    private ShortUrlWhiteMapper shortUrlWhiteMapper;

	@Autowired
	private RoleUserService roleUserService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if(request.getMethod().equals("OPTIONS")){
			return true;
		}
		
//		if(request.getServletPath().equals("/error")){
//			throw new BusinessException(ErrorEnum.ERROR);
//		}
//

		//TODO 虚拟代表版HCP360 请求不需要验证，根据白名单判断
//        String requestURI = request.getRequestURI();
//        String domainNameByUrl = UrlUtil.getDomainNameByUrl(requestURI);
//        Integer urlWhiteCount = shortUrlWhiteMapper.getUrlWhiteCount(domainNameByUrl);
//        if (urlWhiteCount!=null && urlWhiteCount > 0){
//        }


        String encryptAuthToken = request.getParameter("auth-token");
        if (StringUtil.isEmpty(encryptAuthToken)){
            Cookie cookie = CookieUtil.getCookie(request, UserConstant.IDENTIFIER);
            if(cookie != null) {
                encryptAuthToken = cookie.getValue();
            }
        }

        if (StringUtil.isNotEmpty(encryptAuthToken)){
            String email = Aes128Util.decryptAES(encryptAuthToken);
            if (StringUtil.isNotEmpty(email)){
                DrugUser drugUser = drugUserRepository.findFirstByEmail(email);
                if (drugUser != null){
					drugUser.setLeaderPath(drugUser.getLeaderPath() + "%");
					drugUser.setRoleIdList(roleUserService.checkVirtualRole(drugUser.getId()));
					sercurityService.saveSession(request, response, drugUser);
                    request.setAttribute(SessionConfig.DEFAULT_REQUEST_DRUG_USER, drugUser);
                    return true;
                }
            }
        }

        String servletPath = request.getServletPath();
		logger.info("接口【{}】请求开始登录验证",servletPath);
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
