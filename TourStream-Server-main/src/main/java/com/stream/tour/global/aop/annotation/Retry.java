package com.stream.tour.global.aop.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 외부 api 재호출하는 annotation
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Retry {

    // 재시도 횟수
    int value() default 3; // 기본값은 3. 애노테이션을 사용할 때 이 값은 바꿀 수 있다.
}
