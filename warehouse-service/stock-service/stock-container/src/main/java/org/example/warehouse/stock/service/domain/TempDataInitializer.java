package org.example.warehouse.stock.service.domain;

import org.checkerframework.checker.units.qual.A;
import org.example.domain.valueobject.Money;
import org.example.warehouse.stock.service.domain.entity.Stock;
import org.example.warehouse.stock.service.domain.ports.output.repository.StockRepository;
import org.example.warehouse.stock.service.domain.valueobject.StockId;
import org.example.warehouse.stock.service.domain.valueobject.StockStatus;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.UUID;

@Component
public class TempDataInitializer implements CommandLineRunner {
    private final StockRepository stockRepository;

    public TempDataInitializer(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }


    @Override
    public void run(String... args) throws Exception {
        stockRepository.save(Stock.builder()
                .stockId(new StockId(UUID.randomUUID()))
                .addingTransactions(new ArrayList<>())
                .stockItemsBeforeClosing(new ArrayList<>())
                .subtractTransactions(new ArrayList<>())
                .totalGrossPrice(new Money(BigDecimal.ZERO))
                .status(StockStatus.ACTIVE)
                .build());
    }
}