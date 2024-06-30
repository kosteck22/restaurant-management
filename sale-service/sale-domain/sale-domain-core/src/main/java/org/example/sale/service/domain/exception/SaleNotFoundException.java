package org.example.sale.service.domain.exception;

import org.example.domain.exception.DomainException;

public class SaleNotFoundException extends DomainException {
    public SaleNotFoundException(String message) {
        super(message);
    }

    public SaleNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
