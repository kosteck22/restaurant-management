package org.example.warehouse.stock.service.domain;


import org.example.warehouse.stock.service.domain.dto.message.deduce.StockSubtractRequest;
import org.example.warehouse.stock.service.domain.ports.input.message.listener.StockDeduceRequestMessageListener;
import org.springframework.stereotype.Service;

@Service
public class StockDeduceRequestMessageListenerImpl implements StockDeduceRequestMessageListener {
    private final StockDeduceRequestHelper stockDeduceRequestHelper;

    public StockDeduceRequestMessageListenerImpl(StockDeduceRequestHelper stockDeducreRequestHelper) {
        this.stockDeduceRequestHelper = stockDeducreRequestHelper;
    }

    @Override
    public void deduceStock(StockSubtractRequest stockSubtractRequest) {
        stockDeduceRequestHelper.subtractStock(stockSubtractRequest);
    }
}
