package com.stream.tour.global.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.stream.Stream;

public class EnumValidatorImpl implements ConstraintValidator<EnumValidator, String> {

    List<String> acceptedValues;
    boolean required;

    @Override
    public void initialize(EnumValidator annotation) {
        required = annotation.required();
        acceptedValues = Stream.of(annotation.enumClass().getEnumConstants())
                .map(Enum::name)
                .toList();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (required && StringUtils.isBlank(value)) {
            return false;
        }

        if (!required && StringUtils.isBlank(value)) {
            return true;
        }

        return acceptedValues.contains(value.toUpperCase());
    }
}
