package org.example.warehouse.stock.service.domain;

import lombok.extern.slf4j.Slf4j;
import org.example.warehouse.stock.service.domain.dto.message.StockTakeCreatedRequest;
import org.example.warehouse.stock.service.domain.ports.input.message.listener.StockTakeCreatedMessageListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class StockTakeCreatedMessageListenerImpl implements StockTakeCreatedMessageListener {
    @Override
    public void updateStock(StockTakeCreatedRequest stockTakeCreatedRequest) {

    }
}
