package com.stream.tour.global.validator;

import io.micrometer.common.util.StringUtils;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DatePatternValidatorImpl implements ConstraintValidator<DatePatternValidator, String>  {
    private String pattern;
    private boolean required;

    @Override
    public void initialize(DatePatternValidator constraintAnnotation) {
        this.required = constraintAnnotation.required();
        this.pattern = constraintAnnotation.pattern();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        try {
            if (required && StringUtils.isBlank(value)) {
                return false;
            }

            if (!required && StringUtils.isBlank(value)) {
                return true;
            }

            LocalDate.from(LocalDate.parse(value, DateTimeFormatter.ofPattern(pattern)));
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }

}
