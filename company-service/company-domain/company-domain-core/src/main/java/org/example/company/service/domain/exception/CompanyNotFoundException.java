package org.example.company.service.domain.exception;

import org.example.domain.exception.DomainException;


public class CompanyNotFoundException extends DomainException {
    public CompanyNotFoundException(String message) {
        super(message);
    }

    public CompanyNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
