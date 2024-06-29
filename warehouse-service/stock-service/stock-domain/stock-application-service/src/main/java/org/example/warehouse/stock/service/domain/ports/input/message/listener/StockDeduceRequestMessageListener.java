package org.example.warehouse.stock.service.domain.ports.input.message.listener;

import org.example.warehouse.stock.service.domain.dto.message.deduce.StockSubtractRequest;

public interface StockDeduceRequestMessageListener {
    void deduceStock(StockSubtractRequest stockSubtractRequest);
}
