package org.example.warehouse.stock.service.domain;


import lombok.extern.slf4j.Slf4j;
import org.example.warehouse.stock.service.domain.entity.Stock;
import org.example.warehouse.stock.service.domain.exception.StockDomainException;
import org.example.warehouse.stock.service.domain.ports.output.repository.StockRepository;
import org.example.warehouse.stock.service.domain.valueobject.StockStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Slf4j
@Component
public class CostOfGoodsSoldCommandHandler {
    private final StockRepository stockRepository;

    public CostOfGoodsSoldCommandHandler(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @Transactional
    public BigDecimal getCostOfGoodsSold() {
        Stock stock = checkStock();
        return stock.getCostOfGoodsSold().getAmount();
    }

    private Stock checkStock() {
        Optional<Stock> stock = stockRepository.findLatestStockByStatus(StockStatus.CLOSED);
        if (stock.isEmpty()) {
            log.warn("There is no closed stock right now!");
            throw new StockDomainException("There is no closed stock right now! You must create stock take first.");
        }
        return stock.get();
    }
}
