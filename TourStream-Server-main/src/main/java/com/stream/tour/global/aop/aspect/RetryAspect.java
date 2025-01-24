package com.stream.tour.global.aop.aspect;

import com.stream.tour.global.aop.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Slf4j
@Aspect
public class RetryAspect {

    /**
     * try-catch에서 예외가 발생하지 않으면 메서드는 한번만 실행되고 예외가 발생된다면 maxRetry 횟수만큼 재시도된다.
     * maxRetry 횟수만큼 재시도 후에도 예외가 발생된다면 exceptionHolder에 의해 가장 마지막 예외가 던져진다.
     */
    @Around("@annotation(retry)")
    public Object doRetry(ProceedingJoinPoint joinPoint, Retry retry) throws Throwable {
        log.info("[retry] {} retry={}", joinPoint.getSignature(), retry);

        int maxRetry = retry.value(); // 재시도 횟수
        Exception exceptionHolder = null;

        for (int retryCount = 1; retryCount <= maxRetry; retryCount++) {
            try {
                log.info("[retry] try count={}/{}", retryCount, maxRetry);
                return joinPoint.proceed();
            } catch (Exception e){
                exceptionHolder = e; // 예외를 잡고 있는다. 예외가 터져도 maxRetry 횟수만큼 for문이 진행된다. maxRetry횟수만큼  시도헸는데도 안되면
            }
        }

        throw exceptionHolder; // 마지막 예외가 던져진다.
    }
}
