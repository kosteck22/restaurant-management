package org.example.sale.service.domain.exception;

import org.example.domain.exception.DomainException;

public class SaleDomainException extends DomainException {
    public SaleDomainException() {
    }

    public SaleDomainException(String message) {
        super(message);
    }
}
