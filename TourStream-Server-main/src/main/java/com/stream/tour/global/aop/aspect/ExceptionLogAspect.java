package com.stream.tour.global.aop.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Slf4j
@Aspect
public class ExceptionLogAspect {
    @Pointcut("execution(* com.stream.tour..*.*(..)) && !execution(* com.stream.tour.global.filter..*.*(..))")
    public void exceptionLog() {}

    @Around("exceptionLog()")
    public Object doExceptionLog(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            return joinPoint.proceed();
        } catch (Exception e) {
            log.error("[exceptionLog] {} error={} args={}", joinPoint.getSignature(), e.getMessage(), joinPoint.getArgs());
            log.error(e.getClass().getSimpleName(), e);
            throw e;
        }
    }
}
