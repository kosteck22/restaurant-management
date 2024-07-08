package org.example.warehouse.stock.take.service.domain;


import lombok.extern.slf4j.Slf4j;
import org.example.domain.DomainConstants;
import org.example.warehouse.stock.take.service.domain.dto.message.StockUpdateResponse;
import org.example.warehouse.stock.take.service.domain.ports.input.message.listener.StockUpdateResponseMessageListener;
import org.springframework.stereotype.Service;

import static org.example.domain.DomainConstants.FAILURE_MESSAGE_DELIMITER;

@Slf4j
@Service
public class StockUpdateResponseMessageListenerImpl implements StockUpdateResponseMessageListener {
    private final AcceptStockTakeSaga acceptStockTakeSaga;

    public StockUpdateResponseMessageListenerImpl(AcceptStockTakeSaga acceptStockTakeSaga) {
        this.acceptStockTakeSaga = acceptStockTakeSaga;
    }

    @Override
    public void stockTakeAccepted(StockUpdateResponse stockResponse) {
        acceptStockTakeSaga.process(stockResponse);
        log.info("StockTake is accepted for stock take id: {}", stockResponse.stockTakeId());
    }

    @Override
    public void stockTakeRejected(StockUpdateResponse stockResponse) {
        acceptStockTakeSaga.rollback(stockResponse);
        log.info("StockTake is roll backed for stock take id: {} with failure messages: {}",
                stockResponse.stockTakeId(),
                String.join(FAILURE_MESSAGE_DELIMITER, stockResponse.failureMessages()));
    }
}
