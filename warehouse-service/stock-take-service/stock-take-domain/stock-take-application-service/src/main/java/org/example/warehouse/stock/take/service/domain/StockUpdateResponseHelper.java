package org.example.warehouse.stock.take.service.domain;

import lombok.extern.slf4j.Slf4j;
import org.example.domain.valueobject.StockTakeId;
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
public class StockUpdateResponseHelper {
    private final StockTakeRepository stockTakeRepository;
    private final StockTakeDomainService stockTakeDomainService;

    public StockUpdateResponseHelper(StockTakeRepository stockTakeRepository,
                                     StockTakeDomainService stockTakeDomainService) {
        this.stockTakeRepository = stockTakeRepository;
        this.stockTakeDomainService = stockTakeDomainService;
    }

    @Transactional
    public void persistRejectStockTake(StockUpdateResponse stockResponse) {
        log.info("Updating stock take status to rejected for stock take id: {}", stockResponse.stockTakeId());
        StockTake stockTake = getStockTake(stockResponse.stockTakeId());
        stockTakeDomainService.rejectStockTake(stockTake, stockResponse.failureMessages());
        stockTakeRepository.save(stockTake);
        log.info("Stock take with id: {} is rejected and persisted to db", stockTake.getId().getValue());
    }

    @Transactional
    public void persistAcceptStockTake(StockUpdateResponse stockResponse) {
        log.info("Updating stock take status to accepted for stock take id: {}", stockResponse.stockTakeId());
        StockTake stockTake = getStockTake(stockResponse.stockTakeId());
        stockTakeDomainService.acceptStockTake(stockTake);
        stockTakeRepository.save(stockTake);
        log.info("Stock take with id: {} is accepted and persisted to db", stockTake.getId().getValue());

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
