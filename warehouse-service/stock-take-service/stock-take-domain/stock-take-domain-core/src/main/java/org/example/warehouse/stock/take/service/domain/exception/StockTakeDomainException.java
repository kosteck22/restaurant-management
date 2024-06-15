package org.example.warehouse.stock.take.service.domain.exception;

import org.example.domain.exception.DomainException;

public class StockTakeDomainException extends DomainException {
    public StockTakeDomainException(String message) {
        super(message);
    }
}
