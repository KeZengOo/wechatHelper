package com.nuoxin.virtual.rep.api.service;

import com.alibaba.fastjson.JSONObject;
import com.nuoxin.virtual.rep.api.common.enums.ErrorEnum;
import com.nuoxin.virtual.rep.api.common.exception.NeedLoginException;
import com.nuoxin.virtual.rep.api.common.util.MySession;
import com.nuoxin.virtual.rep.api.common.util.mem.SessionMemUtils;
import com.nuoxin.virtual.rep.api.config.SessionConfig;
import com.nuoxin.virtual.rep.api.entity.DrugUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by fenggang on 7/28/17.
 */
@Service
public class SecurityService {

	private static final Logger logger = LoggerFactory.getLogger(SecurityService.class);

	@Autowired
	private SessionMemUtils memUtils;

	/**
	 * 保存session
	 * @param request
	 * @param response
	 * @param user
	 */
	public void saveSession(HttpServletRequest request, HttpServletResponse response, DrugUser user) {

//		Cookie[] cookies = request.getCookies();
		String sessionId = request.getSession().getId();
//		if ((cookies != null) && (cookies.length > 0)) {
//			for (Cookie cookie : cookies) {
//				String name = cookie.getName();
//				String value = cookie.getValue();
//				if (SessionConfig.DEFAULT_SESSION_COOKIE_NAME.equals(name)) {
//					sessionId = value;
//					break;
//				}
//			}
//		}

		MySession session = new MySession(sessionId);
		session.setAttribute(SessionConfig.DEFAULT_OPERATOR_REQUEST_ATTRIBUTE_NAME, user);
		memUtils.setSession(session.getId(), session);

//		Cookie cookie = new Cookie(SessionConfig.DEFAULT_SESSION_COOKIE_NAME, session.getId());
//		cookie.setPath("/");
//		// 失效时间
//		cookie.setMaxAge(-1);
//		response.addCookie(cookie);

	}

	/**
	 * 清除 session
	 * @param request
	 * @throws NeedLoginException
	 */
	public void cleanSession(HttpServletRequest request) throws NeedLoginException {
		String sessionId = getSessionId(request);
		if (!StringUtils.isEmpty(sessionId)) {
			memUtils.delSession(sessionId);
		}
	}

	/**
	 * 获取 MySession
	 * @param request HttpServletRequest 对象
	 * @return MySession
	 * @throws NeedLoginException
	 */
	public MySession getSession(HttpServletRequest request) throws NeedLoginException {
		String sessionId = getSessionId(request);
		MySession session = memUtils.getSession(sessionId);
		if (session == null || session.getAttribute(SessionConfig.DEFAULT_OPERATOR_REQUEST_ATTRIBUTE_NAME) == null) {
			throw new NeedLoginException(ErrorEnum.LOGIN_NO);
		}
		return session;
	}

	/**
	 * 根据 request 获取 DrugUser
	 * @param request HttpServletRequest 对象
	 * @return MySession
	 * @throws NeedLoginException
	 */
	public DrugUser getDrugUser(HttpServletRequest request) throws NeedLoginException {
		MySession session = getSession(request);
		Object obj = session.getAttribute(SessionConfig.DEFAULT_OPERATOR_REQUEST_ATTRIBUTE_NAME);
		return JSONObject.parseObject(obj.toString(), DrugUser.class);
	}

	/**
	 * Session 校验
	 * @param request
	 * @throws NeedLoginException
	 */
	public void sessionValidation(HttpServletRequest request) throws NeedLoginException {
		String sessionId = getSessionId(request);
		// 检测mem
		MySession session = memUtils.getSession(sessionId);
		if (session == null || session.getAttribute(SessionConfig.DEFAULT_OPERATOR_REQUEST_ATTRIBUTE_NAME) == null) {
			logger.debug("Session is out of time");
			throw new NeedLoginException(ErrorEnum.LOGIN_NO);
		}
	}

	/**
	 * 刷新 Cookie
	 * @param sessionId
	 * @param response
	 */
	public void flushCookie(String sessionId, HttpServletResponse response) {
		Cookie cookie = new Cookie(SessionConfig.DEFAULT_SESSION_COOKIE_NAME, sessionId);
		cookie.setPath("/");
		cookie.setMaxAge(-1);
		response.addCookie(cookie);
	}

	/**
	 * 刷新 session
	 * @param user
	 * @param request
	 * @throws NeedLoginException
	 */
	public void refreshSession(DrugUser user, HttpServletRequest request) throws NeedLoginException {
		// 找到当前session
		MySession session = this.getSession(request);
		// 把新用户信息保存到session中
		session.setAttribute(SessionConfig.DEFAULT_OPERATOR_REQUEST_ATTRIBUTE_NAME, user);
		// 把session保存到缓存中
		memUtils.setSession(session.getId(), session);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private String getSessionId(HttpServletRequest request) throws NeedLoginException {
		Cookie[] cookies = request.getCookies();
		String sessionId = request.getSession().getId();
//		if ((cookies != null) && (cookies.length > 0)) {
//			for (Cookie cookie : cookies) {
//				String name = cookie.getName();
//				String value = cookie.getValue();
//				if (SessionConfig.DEFAULT_SESSION_COOKIE_NAME.equals(name)) {
//					sessionId = value;
//					break;
//				}
//			}
//		}

		if (StringUtils.isEmpty(sessionId)) {
			throw new NeedLoginException(ErrorEnum.LOGIN_NO);
		}

		return sessionId;
	}
}