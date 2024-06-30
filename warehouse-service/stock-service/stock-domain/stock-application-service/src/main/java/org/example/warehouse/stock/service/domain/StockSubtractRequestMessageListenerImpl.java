package org.example.warehouse.stock.service.domain;


import org.example.warehouse.stock.service.domain.dto.message.deduce.StockSubtractRequest;
import org.example.warehouse.stock.service.domain.ports.input.message.listener.StockSubtractRequestMessageListener;
import org.springframework.stereotype.Service;

@Service
public class StockSubtractRequestMessageListenerImpl implements StockSubtractRequestMessageListener {
    private final StockSubtractRequestHelper stockSubtractRequestHelper;

    public StockSubtractRequestMessageListenerImpl(StockSubtractRequestHelper stockSubtractRequestHelper) {
        this.stockSubtractRequestHelper = stockSubtractRequestHelper;
    }

    @Override
    public void subtractStock(StockSubtractRequest stockSubtractRequest) {
        stockSubtractRequestHelper.subtractStock(stockSubtractRequest);
    }
}
