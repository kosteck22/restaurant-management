package org.example.warehouse.stock.service.domain.ports.input.message.listener;

import org.example.warehouse.stock.service.domain.dto.message.StockTakeCreatedRequest;
import org.example.warehouse.stock.service.domain.event.StockEvent;

public interface StockTakeCreatedMessageListener {
    StockEvent updateStock(StockTakeCreatedRequest stockTakeCreatedRequest);
}
