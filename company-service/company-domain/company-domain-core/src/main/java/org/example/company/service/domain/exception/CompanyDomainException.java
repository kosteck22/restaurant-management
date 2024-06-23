package org.example.company.service.domain.exception;

import org.example.domain.exception.DomainException;

public class CompanyDomainException extends DomainException {
    public CompanyDomainException() {
    }

    public CompanyDomainException(String message) {
        super(message);
    }
}
