package org.example.warehouse.stock.service.domain;

import lombok.extern.slf4j.Slf4j;
import org.example.warehouse.stock.service.domain.dto.message.update.StockUpdateRequest;
import org.example.warehouse.stock.service.domain.event.StockEvent;
import org.example.warehouse.stock.service.domain.ports.input.message.listener.StockUpdateRequestMessageListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class StockUpdateRequestMessageListenerImpl implements StockUpdateRequestMessageListener {
    private final StockUpdateRequestHelper stockUpdateRequestHelper;

    public StockUpdateRequestMessageListenerImpl(StockUpdateRequestHelper stockRequestHelper) {
        this.stockUpdateRequestHelper = stockRequestHelper;
    }

    @Override
    public void updateStock(StockUpdateRequest stockUpdateRequest) {
        StockEvent stockEvent = stockUpdateRequestHelper.updateStock(stockUpdateRequest);
        log.info("Publishing stock event with stock id: {} and stock take id: {}",
                stockEvent.getStock().getId().getValue(), stockUpdateRequest.stockTakeId());
        stockEvent.fire();
    }
}
