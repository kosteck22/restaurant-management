package org.example.warehouse.stock.service.domain.ports.input.message.listener;

import org.example.warehouse.stock.service.domain.dto.message.StockUpdateRequest;

public interface StockUpdateRequestMessageListener {
    void updateStock(StockUpdateRequest stockTakeCreatedRequest);
}
