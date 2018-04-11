package com.nuoxin.virtual.rep.api.web.intercept;

import com.alibaba.fastjson.JSON;
import com.nuoxin.virtual.rep.api.common.exception.NeedLoginException;
import com.nuoxin.virtual.rep.api.utils.DateUtil;
import com.nuoxin.virtual.rep.api.utils.EmailUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


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
     *
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
     *
     * @param joinPoint
     */
    public void doAfter(JoinPoint joinPoint) {
        logger.debug("{} doAfter class-{} {}" +
                        " method-{} {}参数：{}", System.getProperty("path.separator"), joinPoint.getThis().toString(), System.getProperty("path.separator"),
                joinPoint.getSignature().getName(), System.getProperty("path.separator"), joinPoint.getArgs());
    }

    /**
     * 核心业务逻辑调用正常退出后，不管是否有返回值，正常退出后，均执行此Advice
     *
     * @param joinPoint
     */
    @AfterReturning(pointcut="execution(* com.nuoxin.virtual.rep.api.service..*.*(..))", returning="retValue")
    public void doReturn(JoinPoint joinPoint, Object retValue) {
        logger.debug("{} doReturn class-{} {}" +
                        " method-{} {} 参数：{} {} 返回值：{}", System.getProperty("path.separator"), joinPoint.getThis().toString(), System.getProperty("path.separator"),
                joinPoint.getSignature().getName(), System.getProperty("path.separator"), joinPoint.getArgs(), System.getProperty("path.separator"), JSON.toJSONString(retValue));
//        logger.debug("method-{}-参数：{}-返回值：{}", joinPoint.getThis().toString() + joinPoint.getSignature().getName(), joinPoint.getArgs(), JSON.toJSONString(retValue));
    }

    /**
     * 核心业务逻辑调用异常退出后，执行此Advice，处理错误信息
     *
     * @param joinPoint
     * @param ex
     */
//    @AfterThrowing(throwing="ex",pointcut="execution(* com.nuoxin.virtual.rep.api.service..*.*(..))")
//    public void doThrowing(JoinPoint joinPoint, Throwable ex) {
//        ex.printStackTrace();
//        if(ex instanceof NeedLoginException){
//        }else{
//            try {
//                StringBuffer sb = new StringBuffer("<html><body><h3><b>异常日志</b></h3><p>类:");
//                sb.append(joinPoint.getThis().toString());
//                sb.append("</p><p>方法:");
//                sb.append(joinPoint.getSignature().getName());
//                sb.append("</p><p>参数:");
//                sb.append(JSON.toJSONString(joinPoint.getArgs()));
//                sb.append("</p><div>");
//                sb.append(ex);
//                sb.append("</div></body></html>");
//                String subiect = "";
//                if(ex.getMessage()!=null){
//                    subiect = ex.getMessage().substring(0,ex.getMessage().length()>10?10:ex.getMessage().length())+"-虚拟代表接口错误日志-" + DateUtil.getDateStr(DateUtil.DATE_FORMAT_YMR);
//                }else{
//                    subiect = "虚拟代表接口错误日志-" + DateUtil.getDateStr(DateUtil.DATE_FORMAT_YMR);
//                }
//                EmailUtil.htmlMail(new String[]{"gang.feng@naxions.com", "cun.tian@naxions.com"},subiect,sb.toString());
//            } catch (Exception e) {
//                e.printStackTrace();
//                logger.error("失败原因【{}】" , e.getMessage(), e);
//            }
//        }
//    }
}
