package com.nuoxin.virtual.rep.api.web.intercept;

import com.alibaba.fastjson.JSON;
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

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * web接口统一日志输出
 * Create by tiancun on 2017/9/25
 */
@Aspect
@Component
public class WebLogAop {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    ThreadLocal<Long> startTime = new ThreadLocal<>();

    @Pointcut("execution(public * com.nuoxin.virtual.rep.api.web.controller..*.*(..))")
    //@Pointcut("execution(public * com.tiancun.web.controller.*Controller.*(..))")
    public void webLog(){

    }

    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable{

        //请求开始时间
        startTime.set(System.currentTimeMillis());


        //接收请求记录下请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        //记录下请求的内容
        //{} slf4j比log4j强悍的地方，占位符
        logger.info("URL: {}", request.getRequestURL());
        logger.info("URI: {}", request.getRequestURI());
        logger.info("http_method: {}", request.getMethod());
        logger.info("IP: {}", request.getRemoteAddr());
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()){
            String args = parameterNames.nextElement();
            logger.info("args={}, value={}", args, request.getParameter(args));
        }

    }


    @AfterReturning(returning = "ret", pointcut = "webLog()")
    public void doAfter(Object ret) throws Throwable{

        //处理完请求，返回内容
        logger.info("response:" + JSON.toJSONString(ret));
        long endTime = System.currentTimeMillis();
        long start = startTime.get();
        //logger.info("spend time {}s", ((endTime - start)/1000) );
        logger.info("spend time {}ms", ((endTime - start)) );
    }


}
