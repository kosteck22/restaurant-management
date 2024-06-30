package org.example.warehouse.stock.service.domain.entity;

import org.example.domain.entity.AggregateRoot;
import org.example.domain.valueobject.*;
import org.example.warehouse.stock.service.domain.exception.StockDomainException;
import org.example.warehouse.stock.service.domain.valueobject.*;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;


public class Stock extends AggregateRoot<StockId> {

    private final List<StockAddTransaction> addTransactions;
    private final List<StockSubtractTransaction> subtractTransactions;
    private final List<StockItemBeforeClosing> stockItemsBeforeClosing;

    private StockTakeId fromStockTake;
    private StockTakeId toStockTake;

    private Money totalGrossPrice;
    private StockStatus status;

    private Stock(Builder builder) {
        setId(builder.stockId);
        addTransactions = builder.addTransactions;
        subtractTransactions = builder.subtractTransactions;
        stockItemsBeforeClosing = builder.stockItemsBeforeClosing;
        fromStockTake = builder.fromStockTake;
        toStockTake = builder.toStockTake;
        totalGrossPrice = builder.totalGrossPrice;
        status = builder.status;
    }

    public void close(StockTake stockTake) {
        toStockTake = stockTake.getId();
        status = StockStatus.CLOSED;
    }

    public void initializeStockItemsBeforeClosing(StockTake stockTake) {
        //(FIFO stock) first product's that were put to stock are used first
        Map<ProductId, List<StockAddTransaction>> productIdToListStockAddTransactionMap = addTransactions.stream()
                .collect(Collectors.groupingBy(StockAddTransaction::getProductId))
                .entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().stream()
                                .sorted(Comparator.comparing(StockAddTransaction::getAdditionDate).reversed())
                                .collect(Collectors.toList())));

        stockTake.getItems().forEach(stockTakeItem -> {
            long id = stockItemsBeforeClosing.size() + 1;
            Quantity realQuantity = stockTakeItem.getQuantity();
            List<StockAddTransaction> stockAddTransactions = productIdToListStockAddTransactionMap.get(stockTakeItem.getProductId());
            for (StockAddTransaction addTransaction : stockAddTransactions) {
                stockItemsBeforeClosing.add(StockItemBeforeClosing.builder()
                        .id(new StockItemBeforeClosingId(id))
                        .additionDate(addTransaction.getAdditionDate())
                        .productId(addTransaction.getProductId())
                        .grossPrice(addTransaction.getGrossPrice())
                        .quantity(addTransaction.getQuantity().isGreaterThan(realQuantity) ? realQuantity : addTransaction.getQuantity())
                        .invoiceId(addTransaction.getInvoiceId())
                        .build());
                realQuantity = realQuantity.subtract(addTransaction.getQuantity());

                if (realQuantity.isNegative() || realQuantity.isZero()) {
                    break;
                }
            }
        });
    }

    public void addStockAddTransactions(List<StockAddTransaction> transactions) {
        if (isClosed()) {
            throw new StockDomainException("You cannot add new transactions. Stock is closed!");
        }

        long transactionId = addTransactions.size();
        for (StockAddTransaction stockAddTransaction : transactions) {
            stockAddTransaction.validate();
            stockAddTransaction.initialize(super.getId(), new StockAddTransactionId(++transactionId));
            addTransactions.add(stockAddTransaction);
        }
    }


    public void addStockSubtractTransactions(Sale sale, List<Recipe> recipes, List<String> failureMessages) {
        if (isClosed()) {
            throw new StockDomainException("You cannot add new subtract transactions. Stock is closed!");
        }

        Map<MenuItemId, Recipe> menuItemIdRecipeMap = recipes.stream()
                .collect(Collectors.toMap(Recipe::getMenuItemId, recipe -> recipe));

        Map<ProductId, Quantity> productIdQuantityMap = sale.getItems().stream()
                .flatMap(saleItem -> menuItemIdRecipeMap.get(saleItem.getMenuItemId())
                        .getItems().stream()
                        .map(recipeItem -> new AbstractMap.SimpleEntry<>(
                                recipeItem.getProductId(),
                                recipeItem.getQuantity().multiply(saleItem.getQuantity())
                        )))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        Quantity::add
                ));

        Map<ProductId, Quantity> addTransactionMap = addTransactions.stream()
                .collect(Collectors.toMap(
                        StockAddTransaction::getProductId,
                        StockAddTransaction::getQuantity,
                        Quantity::add
                ));

        Map<ProductId, Quantity> previousSubtractTransactionMap = subtractTransactions.stream()
                .collect(Collectors.toMap(
                        StockSubtractTransaction::getProductId,
                        StockSubtractTransaction::getQuantity,
                        Quantity::add
                ));

        productIdQuantityMap.forEach(((productId, quantity) -> {
            Quantity quantityInStock = addTransactionMap.get(productId)
                    .subtract(previousSubtractTransactionMap.getOrDefault(
                            productId, new Quantity(BigDecimal.ZERO)));
            if (quantity.isGreaterThan(quantityInStock)) {
                failureMessages.add("Insufficient quantity for product id: %s".formatted(productId));
            } else {
                StockSubtractTransaction stockSubtractTransaction = StockSubtractTransaction.builder()
                        .stockId(getId())
                        .stockSubtractTransactionId(new StockSubtractTransactionId(1L + subtractTransactions.size()))
                        .subtractDate(sale.getDate())
                        .productId(productId)
                        .quantity(quantity)
                        .stockSubtractTransactionType(StockSubtractTransactionType.SALE)
                        .saleId(sale.getId())
                        .build();
                subtractTransactions.add(stockSubtractTransaction);
            }
        }));
    }

    public void initializeStock() {
        setId(new StockId(UUID.randomUUID()));
    }

    protected boolean isActive() {
        return status.equals(StockStatus.ACTIVE);
    }

    protected boolean isClosed() {
        return status.equals(StockStatus.CLOSED);
    }


    public List<StockAddTransaction> getAddTransactions() {
        return addTransactions;
    }

    public List<StockSubtractTransaction> getSubtractTransactions() {
        return subtractTransactions;
    }

    public List<StockItemBeforeClosing> getStockItemsBeforeClosing() {
        return stockItemsBeforeClosing;
    }

    public StockTakeId getFromStockTake() {
        return fromStockTake;
    }

    public StockTakeId getToStockTake() {
        return toStockTake;
    }

    public Money getTotalGrossPrice() {
        return totalGrossPrice;
    }

    public StockStatus getStatus() {
        return status;
    }

    public void setToStockTake(StockTakeId toStockTake) {
        this.toStockTake = toStockTake;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private StockId stockId;
        private List<StockAddTransaction> addTransactions;
        private List<StockSubtractTransaction> subtractTransactions;
        private List<StockItemBeforeClosing> stockItemsBeforeClosing;
        private StockTakeId fromStockTake;
        private StockTakeId toStockTake;
        private Money totalGrossPrice;
        private StockStatus status;

        private Builder() {
        }

        public Builder stockId(StockId val) {
            stockId = val;
            return this;
        }

        public Builder addingTransactions(List<StockAddTransaction> val) {
            addTransactions = val;
            return this;
        }

        public Builder stockItemsBeforeClosing(List<StockItemBeforeClosing> val) {
            stockItemsBeforeClosing = val;
            return this;
        }

        public Builder subtractTransactions(List<StockSubtractTransaction> val) {
            subtractTransactions = val;
            return this;
        }

        public Builder fromStockTake(StockTakeId val) {
            fromStockTake = val;
            return this;
        }

        public Builder toStockTake(StockTakeId val) {
            toStockTake = val;
            return this;
        }

        public Builder totalGrossPrice(Money val) {
            totalGrossPrice = val;
            return this;
        }

        public Builder status(StockStatus val) {
            status = val;
            return this;
        }

        public Stock build() {
            return new Stock(this);
        }
    }
}
