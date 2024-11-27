package com.fleet.common;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class ValueValidator implements ConstraintValidator<ValidValue, String> {

    private String[] acceptedValues;

    @Override
    public void initialize(ValidValue constraintAnnotation) {
        this.acceptedValues = constraintAnnotation.acceptedValues();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // Let @NotNull handle null validation if required
        }
        return Arrays.asList(acceptedValues).contains(value);
    }
}
