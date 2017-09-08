package com.xz.dripping.common.aspect;

import com.xz.dripping.common.utils.JsonUtils;
import org.aspectj.lang.JoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 使用springAop打印请求参数和返回参数及异常信息
 */
public class LogAspect {

    private static Logger logger = LoggerFactory.getLogger(LogAspect.class);


    //任何通知方法都可以将第一个参数定义为 org.aspectj.lang.JoinPoint类型
    public void before(JoinPoint call) {
        //获取目标对象对应的类名
        String className = call.getTarget().getClass().getName();
        //获取目标对象上正在执行的方法名
        String methodName = call.getSignature().getName();
        logger.info(className + "." + methodName + "开始执行");
        //获取参数
        Object[] args = call.getArgs();
        for (int i = 0; i < args.length; i++) {
            Object obj = args[i];
            if (obj == null) {
                logger.info("请求参数" + (i + 1) + ":");
            } else {
                String req = JsonUtils.getArgsString(obj);
                logger.info("请求参数" + (i + 1) + ":" + req);
            }
        }
    }

    public void afterReturn(JoinPoint call, Object retValue) {
        //获取目标对象对应的类名
        String className = call.getTarget().getClass().getName();
        //获取目标对象上正在执行的方法名
        String methodName = call.getSignature().getName();
        String resp = JsonUtils.getArgsString(retValue);
        logger.info(className + "." + methodName + "返回值:" + resp);
    }

//    public void after() {
//        System.out.println("最终通知:不管方法有没有正常执行完成，一定会返回的");
//    }

    public void afterThrowing(JoinPoint call, Throwable e) {
        //获取目标对象对应的类名
        String className = call.getTarget().getClass().getName();
        //获取目标对象上正在执行的方法名
        String methodName = call.getSignature().getName();
        logger.error(className + "." + methodName + "发生异常:", e);
    }
}
