package org.example.warehouse.stock.service.domain;

import lombok.extern.slf4j.Slf4j;
import org.example.warehouse.stock.service.domain.dto.message.StockTakeCreatedRequest;
import org.example.warehouse.stock.service.domain.entity.Stock;
import org.example.warehouse.stock.service.domain.entity.StockTake;
import org.example.warehouse.stock.service.domain.event.StockEvent;
import org.example.warehouse.stock.service.domain.mapper.StockDataMapper;
import org.example.warehouse.stock.service.domain.ports.output.repository.StockRepository;
import org.example.warehouse.stock.service.domain.valueobject.StockStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class StockRequestHelper {
    private final StockDomainService stockDomainService;
    private final StockRepository stockRepository;
    private final StockDataMapper stockDataMapper;

    public StockRequestHelper(StockDomainService stockDomainService,
                              StockRepository stockRepository,
                              StockDataMapper stockDataMapper) {
        this.stockDomainService = stockDomainService;
        this.stockRepository = stockRepository;
        this.stockDataMapper = stockDataMapper;
    }

    @Transactional
    public StockEvent updateStock(StockTakeCreatedRequest stockTakeCreatedRequest) {
        log.info("Received stock take created event for stockTake id: {}", stockTakeCreatedRequest.stockTakeId());
        StockTake stockTake = stockDataMapper.stockTakeCreatedRequestToStockTake(stockTakeCreatedRequest);
        Stock stock = getActiveStock();
        List<String> failureMessages = new ArrayList<>();
        StockEvent stockEvent = stockDomainService.closeActiveStock(stock, stockTake, failureMessages);
        if (failureMessages.isEmpty()) {
            persistDbObjects(stock, stockEvent.getStock(), failureMessages);
        }
        return stockEvent;
    }

    private void persistDbObjects(Stock closedStock, Stock newStock, List<String> failureMessages) {
        if (failureMessages.isEmpty()) {
            stockRepository.save(closedStock);
            stockRepository.save(newStock);
        }
    }

    private Stock getActiveStock() {
        List<Stock> stocks = stockRepository.findByStatus(StockStatus.ACTIVE);
        return stocks.get(0);
    }
}
