package org.example.warehouse.stock.service.domain;

import lombok.extern.slf4j.Slf4j;
import org.example.domain.event.publisher.DomainEventPublisher;
import org.example.domain.valueobject.MenuItemId;
import org.example.domain.valueobject.ProductId;
import org.example.domain.valueobject.Quantity;
import org.example.warehouse.stock.service.domain.entity.*;
import org.example.warehouse.stock.service.domain.event.StockClosedFailedEvent;
import org.example.warehouse.stock.service.domain.event.StockClosedSuccessEvent;
import org.example.warehouse.stock.service.domain.event.StockEvent;
import org.example.warehouse.stock.service.domain.valueobject.StockAddTransactionType;
import org.example.warehouse.stock.service.domain.valueobject.StockStatus;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.example.domain.DomainConstants.UTC;

@Slf4j
public class StockDomainServiceImpl implements StockDomainService {

    @Override
    public void addStock(Stock stock, Invoice invoice, List<Product> products) {
        initializeMissingProducts(invoice, products);
        List<StockAddTransaction> stockAddTransactions = invoiceToStockAddTransaction(invoice, products);
        stock.addStockAddTransactions(stockAddTransactions);
    }

    @Override
    public void subtractStock(Stock stock, Sale sale, List<Recipe> recipes, List<String> failureMessages) {
        checkIfMenuItemHaveRecipe(sale, recipes, failureMessages);
        if (failureMessages.isEmpty()) {
            stock.addStockSubtractTransactions(sale, recipes, failureMessages);
        }
    }

    @Override
    public StockEvent closeActiveStock(Stock stock, StockTake stockTake, List<String> failureMessages,
                                       DomainEventPublisher<StockClosedSuccessEvent> stockClosedSuccessEventPublisherDomainEventPublisher,
                                       DomainEventPublisher<StockClosedFailedEvent> stockClosedFailedEventDomainEventPublisher) {
        validateStockTakeList(stock, stockTake, failureMessages);
        if (!failureMessages.isEmpty()) {
            log.info("Closing stock failed for stock take id: {}", stockTake.getId().getValue());
            return new StockClosedFailedEvent(stock, stockTake.getId(), failureMessages, ZonedDateTime.now(ZoneId.of(UTC)), stockClosedFailedEventDomainEventPublisher);
        } else {
            log.info("Stock with id: {} is closed", stock.getId().getValue());
            stock.initializeStockItemsBeforeClosing(stockTake);
            stock.close(stockTake);
            Stock newStock = initializeNewStock(stockTake, stock.getStockItemsBeforeClosing());
            return new StockClosedSuccessEvent(newStock, stockTake.getId(), failureMessages, ZonedDateTime.now(ZoneId.of(UTC)), stockClosedSuccessEventPublisherDomainEventPublisher);
        }
    }


    private void checkIfMenuItemHaveRecipe(Sale sale, List<Recipe> recipes, List<String> failureMessages) {
        Map<MenuItemId, Recipe> menuItemIdRecipeMap = recipes.stream().collect(Collectors.toMap(Recipe::getMenuItemId, Function.identity()));
        sale.getItems().forEach(saleItem -> {
            MenuItemId menuItemId = saleItem.getMenuItemId();
            Recipe recipe = menuItemIdRecipeMap.get(menuItemId);
            if (recipe == null) {
                log.warn("Recipe for menu item id: {} not found", menuItemId.getValue());
                failureMessages.add("Recipe for menu item id: %s not found".formatted(menuItemId.getValue()));
            }
        });
    }

    private Stock initializeNewStock(StockTake stockTake, List<StockItemBeforeClosing> stockItemsBeforeClosing) {
        Stock stock = Stock.builder()
                .fromStockTake(stockTake.getId())
                .status(StockStatus.ACTIVE)
                .build();
        stock.initializeStock();
        stock.addStockAddTransactions(stockItemsBeforeClosing.stream()
                .map(stockItemBeforeClosing -> StockAddTransaction.builder()
                        .stockAddTransactionType(StockAddTransactionType.STOCK_TAKE)
                        .invoiceId(stockItemBeforeClosing.getInvoiceId())
                        .grossPrice(stockItemBeforeClosing.getGrossPrice())
                        .productId(stockItemBeforeClosing.getProductId())
                        .additionDate(stockItemBeforeClosing.getAdditionDate())
                        .build())
                .collect(Collectors.toList()));
        return stock;
    }

    private void validateStockTakeList(Stock stock, StockTake stockTake, List<String> failureMessages) {
        checkIfProductsExistsInStock(stock, stockTake, failureMessages);
        checkIfGivenQuantitiesAreValid(stock, stockTake, failureMessages);
    }

    private static void checkIfProductsExistsInStock(Stock stock, StockTake stockTake, List<String> failureMessages) {
        Map<ProductId, StockAddTransaction> productIdStockAddTransactionMap =
                stock.getAddTransactions()
                        .stream()
                        .collect(Collectors.toMap(StockAddTransaction::getProductId, Function.identity()));
        stockTake.getItems()
                .forEach(stockTakeItem -> {
                    StockAddTransaction stockAddTransaction = productIdStockAddTransactionMap.get(stockTakeItem.getProductId());
                    if (stockAddTransaction == null) {
                        log.error("There is no product with id: {} in stock", stockTakeItem.getProductId().getValue());
                        failureMessages.add("There is no product with id: %s in stock".formatted(stockTakeItem.getProductId().getValue()));
                    }
                });
    }

    private void checkIfGivenQuantitiesAreValid(Stock stock, StockTake stockTake, List<String> failureMessages) {
        //checking if stock take quantities aren't bigger than stock add transactions
        Map<ProductId, Quantity> productIdToQuantityMap = stock.getAddTransactions().stream()
                .collect(Collectors.groupingBy(StockAddTransaction::getProductId,
                        Collectors.collectingAndThen(
                                Collectors.mapping(
                                        product -> product.getQuantity().getValue(),
                                        Collectors.reducing(BigDecimal.ZERO, BigDecimal::add)
                                ),
                                Quantity::new
                        )
                ));
        stockTake.getItems().forEach(stockTakeItem -> {
            ProductId productId = stockTakeItem.getProductId();
            Quantity quantityInStockTransactions = productIdToQuantityMap.get(productId);
            if (stockTakeItem.getQuantity().isGreaterThan(quantityInStockTransactions)) {
                log.error("Stock take quantity for a product with id: {} cannot be greater than the quantity added to the stock", productId.getValue());
                failureMessages.add(("Stock take quantity for a product with id: %s " +
                        "cannot be greater than the quantity added to the stock").formatted(productId.getValue()));
            }
        });
    }

    private void initializeMissingProducts(Invoice invoice, List<Product> products) {
        Map<String, Product> productNameProductMap = products.stream()
                .collect(Collectors.toMap(Product::getName, Function.identity()));
        invoice.getItems().forEach(invoiceItem -> {
            Product product = productNameProductMap.get(invoiceItem.getName());
            if (product == null) {
                products.add(Product.builder()
                        .productId(new ProductId(UUID.randomUUID()))
                        .name(invoiceItem.getName())
                        .unitOfMeasure(invoiceItem.getUnitOfMeasure())
                        .build());
            }
        });
    }

    private List<StockAddTransaction> invoiceToStockAddTransaction(Invoice invoice, List<Product> products) {
        List<StockAddTransaction> stockAddTransactions = new ArrayList<>();
        Map<String, Product> productNameToProductMap = products.stream()
                .collect(Collectors.toMap(Product::getName, Function.identity()));

        for (InvoiceItem invoiceItem : invoice.getItems()) {
            stockAddTransactions.add(StockAddTransaction.builder()
                    .productId(productNameToProductMap.get(invoiceItem.getName()).getId())
                    .additionDate(invoice.getDate().atStartOfDay())
                    .quantity(invoiceItem.getQuantity())
                    .grossPrice(invoiceItem.getGrossPrice())
                    .totalGrossPrice(invoiceItem.getGrossPrice().multiply(invoiceItem.getQuantity().getValue()))
                    .stockAddTransactionType(StockAddTransactionType.INVOICE)
                    .invoiceId(invoice.getId())
                    .build());

        }
        return stockAddTransactions;
    }
}
