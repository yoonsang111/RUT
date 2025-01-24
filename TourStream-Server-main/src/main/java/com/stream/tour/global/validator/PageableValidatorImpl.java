package com.stream.tour.global.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.data.domain.Pageable;

public class PageableValidatorImpl implements ConstraintValidator<PageableValidator, Pageable> {
    private int maxPerPage;

    @Override
    public void initialize(PageableValidator constraintAnnotation) {
        this.maxPerPage = constraintAnnotation.maxPerPage();
    }

    @Override
    public boolean isValid(Pageable pageable, ConstraintValidatorContext context) {
        return pageable.getPageSize() <= this.maxPerPage;
    }
}
