package com.xz.dripping.common.aspect;

import com.xz.dripping.common.aspect.validate.FieldValidator;
import com.xz.dripping.common.dto.BaseResponse;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import javax.validation.Valid;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 功能: 字段基础校验切面
 * 日期: 2017/2/10 0010 09:31
 * 版本: V1.0
 */
public class FieldValidateAspect {

    @Autowired
    private FieldValidator fieldValidator;

    /**
     * 参数校验切面处理
     *
     * @param pjp
     * @return
     * @throws Throwable
     */
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        //获取当前执行方法
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        Method method = methodSignature.getMethod();
        List<Integer> indexList = getMethodValidateIndexList(method);
        //如果有需要校验的参数
        if (!CollectionUtils.isEmpty(indexList)) {
            //获取方法参数
            Object[] args = pjp.getArgs();
            for (Integer index : indexList) {
                Object arg = args[index];
                BaseResponse resp = fieldValidator.validateField(arg);
                if (resp != null) {
                    return resp;
                }
            }
        }
        return pjp.proceed();
    }

    /**
     * 获取被标记@Valid的参数索引
     *
     * @param method
     * @return
     */
    private List<Integer> getMethodValidateIndexList(Method method) {
        Annotation[][] annotationsArray = method.getParameterAnnotations();
        List<Integer> indexList = new ArrayList<Integer>();
        for (int i = 0; i < annotationsArray.length; i++) {
            Annotation[] annotations = annotationsArray[i];
            for (int j = 0; j < annotations.length; j++) {
                if (annotations[j] instanceof Valid) {
                    indexList.add(i);
                }
            }
        }
        return indexList;
    }
}
