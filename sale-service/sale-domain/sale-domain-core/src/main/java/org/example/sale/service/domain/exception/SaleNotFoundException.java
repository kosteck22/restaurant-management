package org.example.sale.service.domain.exception;

import org.example.domain.exception.DomainException;

public class SaleNotFoundException extends DomainException {
    public SaleNotFoundException() {
    }

    public SaleNotFoundException(String message) {
        super(message);
    }
}
