package com.qiangdong.reader.aop;

import com.qiangdong.reader.annotation.Validation;
import com.qiangdong.reader.utils.SpringContextUtils;
import com.qiangdong.reader.exception.InternalException;
import com.qiangdong.reader.exception.ValidationException;
import com.qiangdong.reader.request.BaseRequest;
import com.qiangdong.reader.validate.RequestValidator;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(2)
public class RequestValidateAop {
    @Autowired
    private SpringContextUtils springContextUtils;

    @Pointcut("execution(public * com.qiangdong.reader.serviceImpl.*.*(..))")
    public void validatePointCut() {}

    @Before("validatePointCut()")
    public void validateBeforeService(JoinPoint joinPoint) throws Throwable {
        Method method = Arrays.stream(joinPoint.getSignature()
            .getDeclaringType()
            .getDeclaredMethods())
            .filter(m -> m.getName().equals(joinPoint.getSignature().getName()))
            .findFirst()
            .orElseThrow(() -> new InternalException(String.format("check Permission aop has error. method: %s not found",
                joinPoint.getSignature().getName())));

        Validation validation = method.getAnnotation(Validation.class);
        if (validation != null) {
            Class<?> clz = validation.value();
            Method validateMethod;
            try {
                validateMethod = clz.getDeclaredMethod("validate", BaseRequest.class);
            } catch (NoSuchMethodException e) {
                throw new ValidationException("No Validate Method in RequestValidator Class");
            }
            BaseRequest request = (BaseRequest) Arrays.stream(joinPoint.getArgs())
                .filter(obj -> obj instanceof BaseRequest)
                .findFirst()
                .orElseThrow(() -> new ValidationException("No BaseRequest Class in Validate"));
            RequestValidator bean = (RequestValidator) springContextUtils.getBean(
                StringUtils.uncapitalize(clz.getSimpleName()));
            try {
                validateMethod.invoke(bean, request);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw e.getCause();
            }
        }
    }
}
