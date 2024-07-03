package org.example.warehouse.recipe.service.domain.exception;

import org.example.domain.exception.DomainException;

public class RecipeDomainException extends DomainException {
    public RecipeDomainException(String message) {
        super(message);
    }

    public RecipeDomainException(String message, Throwable cause) {
        super(message, cause);
    }
}
