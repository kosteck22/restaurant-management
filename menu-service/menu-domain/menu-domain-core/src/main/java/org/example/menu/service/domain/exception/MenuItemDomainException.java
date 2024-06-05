package org.example.menu.service.domain.exception;

import org.example.domain.exception.DomainException;

public class MenuItemDomainException extends DomainException {
    public MenuItemDomainException() {
    }

    public MenuItemDomainException(String message) {
        super(message);
    }
}