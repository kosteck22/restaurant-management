package org.example.company.service.domain.exception;

import org.example.domain.exception.DomainException;

public class CompanyDomainException extends DomainException {
    public CompanyDomainException(String message) {
        super(message);
    }

    public CompanyDomainException(String message, Throwable cause) {
        super(message, cause);
    }
}
