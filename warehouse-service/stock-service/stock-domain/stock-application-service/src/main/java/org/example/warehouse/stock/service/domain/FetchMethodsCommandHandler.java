package org.example.warehouse.stock.service.domain;


import lombok.extern.slf4j.Slf4j;
import org.example.warehouse.stock.service.domain.entity.Stock;
import org.example.warehouse.stock.service.domain.exception.StockDomainException;
import org.example.warehouse.stock.service.domain.ports.output.repository.StockRepository;
import org.example.warehouse.stock.service.domain.valueobject.StockStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Component
public class FetchMethodsCommandHandler {
    private final StockRepository stockRepository;

    public FetchMethodsCommandHandler(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @Transactional
    public BigDecimal getCostOfGoodsSold() {
        Stock stock = getClosedStock();
        return stock.getCostOfGoodsSold().getAmount();
    }

    @Transactional
    public Map<UUID, BigDecimal> getVariance() {
        Stock stock = getClosedStock();
        return stock.getVariance().entrySet().stream()
                .collect(Collectors.toMap(entry -> entry.getKey().getValue(),
                                          entry -> entry.getValue().getValue()));
    }

    private Stock getClosedStock() {
        Optional<Stock> stock = stockRepository.findLatestStockByStatus(StockStatus.CLOSED);
        if (stock.isEmpty()) {
            log.warn("There is no closed stock right now!");
            throw new StockDomainException("There is no closed stock right now! You must create stock take first.");
        }
        return stock.get();
    }
}
