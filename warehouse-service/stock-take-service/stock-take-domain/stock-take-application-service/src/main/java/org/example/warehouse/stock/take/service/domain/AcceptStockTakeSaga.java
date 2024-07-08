package org.example.warehouse.stock.take.service.domain;

import lombok.extern.slf4j.Slf4j;
import org.example.domain.event.EmptyEvent;
import org.example.domain.valueobject.StockTakeId;
import org.example.saga.SagaStep;
import org.example.warehouse.stock.take.service.domain.dto.message.StockUpdateResponse;
import org.example.warehouse.stock.take.service.domain.entity.StockTake;
import org.example.warehouse.stock.take.service.domain.exception.StockTakeNotFound;
import org.example.warehouse.stock.take.service.domain.ports.output.repository.StockTakeRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
public class AcceptStockTakeSaga implements SagaStep<StockUpdateResponse, EmptyEvent, EmptyEvent> {
    private final StockTakeRepository stockTakeRepository;
    private final StockTakeDomainService stockTakeDomainService;

    public AcceptStockTakeSaga(StockTakeRepository stockTakeRepository,
                               StockTakeDomainService stockTakeDomainService) {
        this.stockTakeRepository = stockTakeRepository;
        this.stockTakeDomainService = stockTakeDomainService;
    }

    @Override
    @Transactional
    public EmptyEvent process(StockUpdateResponse stockResponse) {
        log.info("Updating stock take status to accepted for stock take id: {}", stockResponse.stockTakeId());
        StockTake stockTake = getStockTake(stockResponse.stockTakeId());
        stockTakeDomainService.acceptStockTake(stockTake);
        stockTakeRepository.save(stockTake);
        log.info("Stock take with id: {} is accepted and persisted to db", stockTake.getId().getValue());
        return EmptyEvent.INSTANCE;
    }

    @Override
    @Transactional
    public EmptyEvent rollback(StockUpdateResponse stockResponse) {
        log.info("Updating stock take status to rejected for stock take id: {}", stockResponse.stockTakeId());
        StockTake stockTake = getStockTake(stockResponse.stockTakeId());
        stockTakeDomainService.rejectStockTake(stockTake, stockResponse.failureMessages());
        stockTakeRepository.save(stockTake);
        log.info("Stock take with id: {} is rejected and persisted to db", stockTake.getId().getValue());
        return EmptyEvent.INSTANCE;
    }

    private StockTake getStockTake(String stockTakeId) {
        Optional<StockTake> stockTakeResponse = stockTakeRepository.findById(new StockTakeId(UUID.fromString(stockTakeId)));
        if (stockTakeResponse.isEmpty()) {
            log.error("Stock take with id: {} could not be found!", stockTakeId);
            throw new StockTakeNotFound("Stock take with id " + stockTakeId + " could not be found!");
        }
        return stockTakeResponse.get();
    }
}
