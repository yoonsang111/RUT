package com.stream.tour.global.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DatePatternValidatorImpl.class)
public @interface DatePatternValidator {
    String message() default "입력한 값(${validatedValue})이 형식({pattern})에 맞지 않습니다.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    String pattern() default "yyyy-MM-dd";
    boolean required() default true;
}
