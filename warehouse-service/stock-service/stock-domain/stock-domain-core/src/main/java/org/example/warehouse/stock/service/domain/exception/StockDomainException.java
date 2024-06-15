package org.example.warehouse.stock.service.domain.exception;

import org.example.domain.exception.DomainException;

public class StockDomainException extends DomainException {
    public StockDomainException(String message) {
        super(message);
    }
}
