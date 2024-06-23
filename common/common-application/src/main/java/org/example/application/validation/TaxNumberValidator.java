package org.example.application.validation;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class TaxNumberValidator implements ConstraintValidator<ValidTaxNumber, String> {
    @Override
    public boolean isValid(String taxNumber, ConstraintValidatorContext context) {
        if (taxNumber == null) {
            return true;
        }

        return taxNumber.replace("-", "").matches("^\\d{10}$");
    }
}
