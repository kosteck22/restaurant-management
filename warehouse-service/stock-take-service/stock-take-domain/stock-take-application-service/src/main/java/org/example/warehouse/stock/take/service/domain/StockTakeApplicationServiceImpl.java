package org.example.warehouse.stock.take.service.domain;

import lombok.extern.slf4j.Slf4j;
import org.example.warehouse.stock.take.service.domain.dto.create.CreateStockTakeCommand;
import org.example.warehouse.stock.take.service.domain.dto.create.CreateStockTakeResponse;
import org.example.warehouse.stock.take.service.domain.ports.input.service.StockTakeApplicationService;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Validated
@Service
public class StockTakeApplicationServiceImpl implements StockTakeApplicationService {
    private final StockTakeCreateCommandHandler stockTakeCreateCommandHandler;

    public StockTakeApplicationServiceImpl(StockTakeCreateCommandHandler stockTakeCreateCommandHandler) {
        this.stockTakeCreateCommandHandler = stockTakeCreateCommandHandler;
    }

    @Override
    public CreateStockTakeResponse createStockTake(CreateStockTakeCommand createStockTakeCommand) {
        return stockTakeCreateCommandHandler.createStockTake(createStockTakeCommand);
    }
}
