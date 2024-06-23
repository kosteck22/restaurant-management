package org.example.application.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PostalCodeValidator implements ConstraintValidator<ValidPostalCode, String> {
    @Override
    public boolean isValid(String postalCode, ConstraintValidatorContext context) {
        if (postalCode == null) {
            return true;
        }

        return postalCode.matches("^\\d{5}$");
    }
}
