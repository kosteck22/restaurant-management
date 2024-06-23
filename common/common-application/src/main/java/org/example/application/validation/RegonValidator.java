package org.example.application.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class RegonValidator implements ConstraintValidator<ValidRegon, String> {
    @Override
    public boolean isValid(String regon, ConstraintValidatorContext context) {
        if (regon == null) {
            return true;
        }

        return regon.matches("^\\d{8}$");
    }
}
