package org.example.warehouse.stock.take.service.domain.ports.input.message.listener;

import org.example.warehouse.stock.take.service.domain.dto.message.StockUpdateResponse;

public interface StockUpdateResponseMessageListener {
    void stockTakeRejected(StockUpdateResponse stockResponse);
    void stockTakeAccepted(StockUpdateResponse stockResponse);
}
