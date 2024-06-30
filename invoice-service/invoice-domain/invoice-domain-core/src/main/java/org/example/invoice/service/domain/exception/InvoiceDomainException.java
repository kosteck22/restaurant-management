package org.example.invoice.service.domain.exception;

import org.example.domain.exception.DomainException;

public class InvoiceDomainException extends DomainException {
    public InvoiceDomainException(String message) {
        super(message);
    }

    public InvoiceDomainException(String message, Throwable cause) {
        super(message, cause);
    }
}
