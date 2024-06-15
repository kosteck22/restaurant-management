package org.example.warehouse.stock.take.service.domain.ports.input.service;

import jakarta.validation.Valid;
import org.example.warehouse.stock.take.service.domain.dto.create.CreateStockTakeCommand;
import org.example.warehouse.stock.take.service.domain.dto.create.CreateStockTakeResponse;
import org.springframework.validation.annotation.Validated;

public interface StockTakeApplicationService {
    CreateStockTakeResponse createStockTake(@Valid CreateStockTakeCommand createStockTakeCommand);
}
