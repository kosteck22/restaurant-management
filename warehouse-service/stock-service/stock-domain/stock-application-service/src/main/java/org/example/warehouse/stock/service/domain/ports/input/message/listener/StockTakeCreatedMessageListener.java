package org.example.warehouse.stock.service.domain.ports.input.message.listener;

import org.example.warehouse.stock.service.domain.dto.message.StockTakeCreatedRequest;

public interface StockTakeCreatedMessageListener {
    void updateStock(StockTakeCreatedRequest stockTakeCreatedRequest);
}
