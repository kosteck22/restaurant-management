package org.example.application.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = RegonValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidRegon {
    String message() default "Postal code format is not correct must be **-*** where * is digit";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
