package org.example.warehouse.stock.take.service.domain;

import lombok.extern.slf4j.Slf4j;
import org.example.warehouse.stock.take.service.domain.dto.create.CreateStockTakeCommand;
import org.example.warehouse.stock.take.service.domain.dto.create.CreateStockTakeResponse;
import org.example.warehouse.stock.take.service.domain.entity.Product;
import org.example.warehouse.stock.take.service.domain.entity.StockItem;
import org.example.warehouse.stock.take.service.domain.entity.StockTake;
import org.example.warehouse.stock.take.service.domain.event.StockTakeCreatedEvent;
import org.example.warehouse.stock.take.service.domain.exception.StockTakeDomainException;
import org.example.warehouse.stock.take.service.domain.mapper.StockTakeDataMapper;
import org.example.warehouse.stock.take.service.domain.ports.output.repository.ProductRepository;
import org.example.warehouse.stock.take.service.domain.ports.output.repository.StockTakeRepository;
import org.example.warehouse.stock.take.service.domain.valueobject.StockTakeStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class StockTakeCreateCommandHandler {
    private final StockTakeDataMapper stockTakeDataMapper;
    private final StockTakeRepository stockTakeRepository;
    private final ProductRepository productRepository;
    private final StockTakeDomainService stockTakeDomainService;

    public StockTakeCreateCommandHandler(StockTakeDataMapper stockTakeDataMapper,
                                         StockTakeRepository stockTakeRepository,
                                         ProductRepository productRepository,
                                         StockTakeDomainService stockTakeDomainService) {
        this.stockTakeDataMapper = stockTakeDataMapper;
        this.stockTakeRepository = stockTakeRepository;
        this.productRepository = productRepository;
        this.stockTakeDomainService = stockTakeDomainService;
    }

    @Transactional
    public CreateStockTakeResponse createStockTake(CreateStockTakeCommand createStockTakeCommand) {
        checkPreparedDate(createStockTakeCommand);
        List<Product> products = getProducts(createStockTakeCommand);
        StockTake stockTake = stockTakeDataMapper.createStockTakeCommandToStockTake(createStockTakeCommand);
        StockTakeCreatedEvent stockTakeCreatedEvent = stockTakeDomainService.validateAndInitiateStockTake(stockTake, products);
        StockTake savedStockTake = saveStockTake(stockTake);
        return stockTakeDataMapper.stockTakeToCreateStockTakeResponse(
                savedStockTake,
                "Stock take created successfully!");
    }

    private StockTake saveStockTake(StockTake stockTake) {
        StockTake stockTakeResult = stockTakeRepository.save(stockTake);
        if (stockTakeResult == null) {
            log.error("Could not save stock take!");
            throw new StockTakeDomainException("Could not save stock take!");
        }
        log.info("Stock take is saved with id: {}", stockTakeResult.getId().getValue());
        return stockTakeResult;
    }

    private List<Product> getProducts(CreateStockTakeCommand createStockTakeCommand) {
        List<Product> products = stockTakeDataMapper.createStockTakeCommandToProducts(createStockTakeCommand);
        return productRepository.findProductsInformation(products);
    }

    private void checkPreparedDate(CreateStockTakeCommand createStockTakeCommand) {
        List<StockTake> stockTakesAfterPreparedDate = stockTakeRepository
                .findByStatusAndPreparedDateAfter(
                        StockTakeStatus.ACCEPTED,
                        createStockTakeCommand.preparedDate());
        if (!stockTakesAfterPreparedDate.isEmpty()) {
            log.warn("There is already accepted stock take with further date: {}", createStockTakeCommand.preparedDate());
            throw new StockTakeDomainException("There is already accepted stock take with further date: %s".formatted(createStockTakeCommand.preparedDate()));
        }
    }
}
