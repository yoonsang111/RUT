package com.stream.tour.global.validator;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Slf4j
public class TimePatternValidatorImpl implements ConstraintValidator<TimePatternValidator, String> {

    private String pattern;

    @Override
    public void initialize(TimePatternValidator constraintAnnotation) {
        this.pattern = constraintAnnotation.pattern();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        try {
            LocalTime.from(LocalTime.parse(value, DateTimeFormatter.ofPattern(pattern)));
        } catch (DateTimeParseException e) {
            log.error("TimePatternValidator", e);
            return false;
        }
        return true;
    }
}
