package com.nuoxin.virtual.rep.api.web.intercept;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

/**
 * Created by fenggang on 11/1/17.
 */
@Aspect
@Component
public class AdviceControllerAop {

    @Autowired
    private final static Logger logger = LoggerFactory.getLogger(AdviceControllerAop.class);

    /**
     * 在核心业务执行前执行，不能阻止核心业务的调用。
     * @param joinPoint
     */
    public void doBefore(JoinPoint joinPoint) {
        logger.debug("{} doBefore class-{} {}" +
                        " method-{} {}参数：{}", System.getProperty("path.separator"), joinPoint.getThis().toString(), System.getProperty("path.separator"),
                joinPoint.getSignature().getName(), System.getProperty("path.separator"), joinPoint.getArgs());
    }

    /**
     * 手动控制调用核心业务逻辑，以及调用前和调用后的处理,
     * <p>
     * 注意：当核心业务抛异常后，立即退出，转向After Advice
     * 执行完毕After Advice，再转到Throwing Advice
     *
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.debug("{} doAround class-{} {}" +
                        " method-{} {}参数：{}", System.getProperty("path.separator"), joinPoint.getThis().toString(), System.getProperty("path.separator"),
                joinPoint.getSignature().getName(), System.getProperty("path.separator"), joinPoint.getArgs());

        //调用核心逻辑
        Object retVal = joinPoint.proceed();
        return retVal;
    }

    /**
     * 核心业务逻辑退出后（包括正常执行结束和异常退出），执行此Advice
     * @param joinPoint
     */
    public void doAfter(JoinPoint joinPoint) {
        logger.debug("{} doAfter class-{} {}" +
                        " method-{} {}参数：{}", System.getProperty("path.separator"), joinPoint.getThis().toString(), System.getProperty("path.separator"),
                joinPoint.getSignature().getName(), System.getProperty("path.separator"), joinPoint.getArgs());
    }

    /**
     * 核心业务逻辑调用正常退出后，不管是否有返回值，正常退出后，均执行此Advice
     * @param joinPoint
     */
    @AfterReturning(pointcut="execution(* com.nuoxin.virtual.rep.api.service..*.*(..))", returning="retValue")
    public void doReturn(JoinPoint joinPoint, Object retValue) {
        logger.debug("{} doReturn class-{} {}" +
                        " method-{} {} 参数：{} {} 返回值：{}", System.getProperty("path.separator"), joinPoint.getThis().toString(), System.getProperty("path.separator"),
                joinPoint.getSignature().getName(), System.getProperty("path.separator"), joinPoint.getArgs(), System.getProperty("path.separator"), JSON.toJSONString(retValue));
    }

}
