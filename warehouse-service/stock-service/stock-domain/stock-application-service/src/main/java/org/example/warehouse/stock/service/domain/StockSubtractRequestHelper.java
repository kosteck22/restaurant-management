package org.example.warehouse.stock.service.domain;

import lombok.extern.slf4j.Slf4j;
import org.example.warehouse.stock.service.domain.dto.message.deduce.StockSubtractRequest;
import org.example.warehouse.stock.service.domain.entity.Recipe;
import org.example.warehouse.stock.service.domain.entity.Sale;
import org.example.warehouse.stock.service.domain.entity.Stock;
import org.example.warehouse.stock.service.domain.mapper.StockDataMapper;
import org.example.warehouse.stock.service.domain.ports.output.repository.RecipeRepository;
import org.example.warehouse.stock.service.domain.ports.output.repository.StockRepository;
import org.example.warehouse.stock.service.domain.valueobject.StockStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class StockSubtractRequestHelper {
    private final StockDomainService stockDomainService;
    private final StockRepository stockRepository;
    private final StockDataMapper stockDataMapper;
    private final RecipeRepository recipeRepository;

    public StockSubtractRequestHelper(StockDomainService stockDomainService,
                                      StockRepository stockRepository,
                                      StockDataMapper stockDataMapper,
                                      RecipeRepository recipeRepository) {
        this.stockDomainService = stockDomainService;
        this.stockRepository = stockRepository;
        this.stockDataMapper = stockDataMapper;
        this.recipeRepository = recipeRepository;
    }


    @Transactional
    public void subtractStock(StockSubtractRequest stockSubtractRequest) {
        log.info("Received stock subtract request for sale id: {}", stockSubtractRequest.saleId());
        Sale sale = stockDataMapper.stockSubtractRequestToSale(stockSubtractRequest);
        List<Recipe> recipes = recipeRepository.findAllById(sale.getItems().stream()
                .map(saleItem -> saleItem.getMenuItemId().getValue())
                .collect(Collectors.toList()));
        Stock stock = getActiveStock();
        List<String> failureMessages = new ArrayList<>();
        stockDomainService.subtractStock(stock, sale, recipes, failureMessages);
        if (!failureMessages.isEmpty()) {
            log.error("Stock subtract failed for sale id: {}, with message: {}",
                    sale.getId().getValue(),
                    String.join(",", failureMessages));
            //implement failure path
            //e.g. save sale id with error message to db
            return;
        }
        stockRepository.save(stock);
        log.info("Stock subtracted for sale id: {}", sale.getId().getValue());
    }

    private Stock getActiveStock() {
        List<Stock> stocks = stockRepository.findByStatus(StockStatus.ACTIVE);
        return stocks.get(0);
    }
}
