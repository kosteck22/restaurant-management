package org.example.company.service.domain.exception;

import org.example.domain.exception.DomainException;

public class CompanyNotFoundException extends DomainException {
    public CompanyNotFoundException() {
    }

    public CompanyNotFoundException(String message) {
        super(message);
    }
}
