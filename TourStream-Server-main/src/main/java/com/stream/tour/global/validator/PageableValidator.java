package com.stream.tour.global.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = PageableValidatorImpl.class)
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface PageableValidator {
    String message() default "Invalid pagination. Max per page is {maxPerPage}.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    int maxPerPage() default 100;
}
