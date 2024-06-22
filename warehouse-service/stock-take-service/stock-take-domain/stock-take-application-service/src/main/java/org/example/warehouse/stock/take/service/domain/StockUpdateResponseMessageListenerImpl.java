package org.example.warehouse.stock.take.service.domain;


import lombok.extern.slf4j.Slf4j;
import org.example.warehouse.stock.take.service.domain.dto.message.StockUpdateResponse;
import org.example.warehouse.stock.take.service.domain.ports.input.message.listener.StockUpdateResponseMessageListener;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Service
public class StockUpdateResponseMessageListenerImpl implements StockUpdateResponseMessageListener {
    private final StockUpdateResponseHelper stockUpdateResponseHelper;

    public StockUpdateResponseMessageListenerImpl(StockUpdateResponseHelper stockUpdateResponseHelper) {
        this.stockUpdateResponseHelper = stockUpdateResponseHelper;
    }

    @Override
    public void stockTakeRejected(StockUpdateResponse stockResponse) {
        stockUpdateResponseHelper.persistRejectStockTake(stockResponse);
    }

    @Override
    public void stockTakeAccepted(StockUpdateResponse stockResponse) {
        stockUpdateResponseHelper.persistAcceptStockTake(stockResponse);
    }
}
