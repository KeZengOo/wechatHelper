package com.nuoxin.virtual.rep.api.web.intercept;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;

/**
 * web接口统一日志输出
 * Create by tiancun on 2017/9/25
 */
@Aspect
@Component
public class WebLogAop {

	private static final Logger logger = LoggerFactory.getLogger(WebLogAop.class);

    private ThreadLocal<Long> startTime = new ThreadLocal<>();

    @Pointcut("execution(public * com.nuoxin.virtual.rep.api.web.controller..*.*(..))")
    public void webLog(){

    }

    @Before("webLog()")
	public void doBefore(JoinPoint joinPoint) throws Throwable {
		HttpServletRequest request = this.getRequest();

		String url = request.getRequestURL().toString();
		String uri = request.getRequestURI();
		String method = request.getMethod();
		String remoteIp = request.getRemoteAddr();
		String queryString =  request.getQueryString();

		logger.info("\n access_ip:{} \n http_method:{} \n URL: {} \n URI:{},Querystring:{}", remoteIp, method, url, uri, queryString);
		
		Enumeration<String> parameterNames = request.getParameterNames();
		while (parameterNames.hasMoreElements()) {
			String args = parameterNames.nextElement();
			logger.debug("args={}, value={}", args, request.getParameter(args));
		}

		startTime.set(System.currentTimeMillis());
	}

    @AfterReturning(returning = "ret", pointcut = "webLog()")
    public void doAfter(Object ret) throws Throwable{
        //处理完请求，返回内容
        logger.info("response:" + JSON.toJSONString(ret));
        long endTime = System.currentTimeMillis();
        long start = startTime.get();
        
        logger.warn("spend time {}ms", ((endTime - start)) );
        startTime.remove();
    }
    
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
	private HttpServletRequest getRequest() {
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		return attributes.getRequest();
	}

}
