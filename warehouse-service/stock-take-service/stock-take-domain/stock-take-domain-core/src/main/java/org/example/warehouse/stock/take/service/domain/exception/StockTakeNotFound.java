package org.example.warehouse.stock.take.service.domain.exception;

import org.example.domain.exception.DomainException;

public class StockTakeNotFound extends DomainException {
    public StockTakeNotFound(String message) {
        super(message);
    }

}
