package com.nuoxin.virtual.rep.api.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Cookie 工具
 * 
 * @author xiekaiyu
 */
public class CookieUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(CookieUtil.class);

	/**
	 * 保存至 Cookie
	 * 
	 * @param key
	 * @param value
	 * @param request
	 * @param response
	 */
	public static void setCookie(String key, String value, HttpServletRequest request, HttpServletResponse response) {
		Cookie cookie = new Cookie(key, value);
		cookie.setMaxAge(900);
		// Cookie 路径全为根路径
		cookie.setPath("/");
		response.addCookie(cookie);

		LOGGER.debug("写入Cookie, key:{}, value:{}", key, value);
	}

	/**
	 * 根据 identifier 获取 Cookie
	 * 
	 * @param request
	 * @param identifier
	 * @return
	 */
	public static Cookie getCookie(HttpServletRequest request, String identifier) {
		Cookie cookie = null;
		Cookie[] cookies = request.getCookies();
		if (CollectionsUtil.isNotEmptyArray(cookies)) {
			for (int i = 0; i < cookies.length; i++) {
				cookie = cookies[i];
				if (cookie != null && identifier.equalsIgnoreCase(cookie.getName())) {
					return cookie;
				} else {
					cookie = null;
				}
			}
		}

		return cookie;
	}
}
