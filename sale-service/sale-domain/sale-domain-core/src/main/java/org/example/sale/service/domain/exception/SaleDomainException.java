package org.example.sale.service.domain.exception;

import org.example.domain.exception.DomainException;

public class SaleDomainException extends DomainException {
    public SaleDomainException(String message, Throwable cause) {
        super(message, cause);
    }
    public SaleDomainException(String message) {
        super(message);
    }
}
