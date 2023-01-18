package com.liumj.kuaidi.aop;

import java.lang.reflect.Method;

import com.alibaba.fastjson.JSON;
import com.liumj.kuaidi.anno.LogIgnore;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import org.springframework.stereotype.Component;

/**
 * 日志切面
 */
@Component
@Aspect
@Slf4j
public class LogAspect {
	public static final String FILTER_RESULT = "SUCCESS";

	@Pointcut("@within(org.springframework.web.bind.annotation.RestController) @within(org.springframework.stereotype.Controller)")
	public void logPointCut() {
		// nothing to do
	}

	@Around("logPointCut()")
	public Object logAround(ProceedingJoinPoint pjp) throws Throwable {
		MethodSignature signature = (MethodSignature) pjp.getSignature();
		Method method = signature.getMethod();
		LogIgnore logIgnore = method.getAnnotation(LogIgnore.class);
		if (logIgnore != null && logIgnore.ignore()) {
			return pjp.proceed();
		}
		long start = System.currentTimeMillis();
		Object result = pjp.proceed();
		long runTime = System.currentTimeMillis() - start;
		if (!FILTER_RESULT.equals(result)) {
			String jsonString = JSON.toJSONString(result);
			log.debug("### 返回:{}ms, result:{},", runTime, jsonString);
		}
		return result;
	}
}
