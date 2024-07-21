package org.example.warehouse.stock.service.domain.entity;

import org.example.domain.entity.AggregateRoot;
import org.example.domain.valueobject.*;
import org.example.warehouse.stock.service.domain.exception.StockDomainException;
import org.example.warehouse.stock.service.domain.valueobject.*;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Stock extends AggregateRoot<StockId> {

    private List<StockAddTransaction> addTransactions;
    private List<StockSubtractTransaction> subtractTransactions;
    private List<StockItemBeforeClosing> stockItemsBeforeClosing;

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

    public Money getCostOfGoodsSold() {
        if (!StockStatus.CLOSED.equals(status)) {
            throw new StockDomainException("Cost of goods sold can be obtained only by closed stock");
        }
        Money totalCostOfAddTransactions = getAddTransactionsCost();
        Money totalCostOfProductsLeft = getStockItemsBeforeClosingCost();
        return totalCostOfAddTransactions.subtract(totalCostOfProductsLeft);
    }

    public Map<ProductId, Quantity> getVariance() {
        if (!StockStatus.CLOSED.equals(status)) {
            throw new StockDomainException("Variance can be obtained only by closed stock");
        }
        Map<ProductId, Quantity> theoreticalUsage = productIdToQuantityForSubtractTransactions();
        Map<ProductId, Quantity> actualUsage = productIdToQuantityForActualUsage();

        return Stream.concat(theoreticalUsage.keySet().stream(), actualUsage.keySet().stream())
                .distinct()
                .collect(Collectors.toMap(
                        productId -> productId,
                        productId -> {
                            Quantity theoreticalQuantity = theoreticalUsage.getOrDefault(productId, Quantity.ZERO);
                            Quantity acturalQuantity = actualUsage.getOrDefault(productId, Quantity.ZERO);
                            return theoreticalQuantity.subtract(acturalQuantity);
                        }
                ));
    }

    public Money getSittingInventoryAsMoney() {
        if (StockStatus.CLOSED.equals(status)) {
            throw new StockDomainException("Sitting inventory can be obtained only by open stock");
        }
        Map<ProductId, List<StockAddTransaction>> productIdToStockAddTransactionMap = getProductIdToStockAddTransactionMap();
        Map<ProductId, Quantity> productIdToQuantityMap = productIdToQuantityForSubtractTransactions();

        Money totalMoney = Money.ZERO;

        for (Map.Entry<ProductId, Quantity> entry : productIdToQuantityMap.entrySet()) {
            Quantity leftQuantity = entry.getValue();
            List<StockAddTransaction> stockAddTransactions = productIdToStockAddTransactionMap.get(entry.getKey());
            for (StockAddTransaction stockAddTransaction : stockAddTransactions) {
                Quantity transactionQuantity = stockAddTransaction.getQuantity();
                if (transactionQuantity.isGreaterThan(leftQuantity)) {
                    totalMoney = totalMoney.add(stockAddTransaction.getGrossPrice()
                            .multiply(transactionQuantity.subtract(leftQuantity).getValue()));
                    leftQuantity = Quantity.ZERO;
                } else {
                    leftQuantity = leftQuantity.subtract(transactionQuantity);
                }
            }
        }

        return totalMoney;
    }

    public void close(StockTake stockTake) {
        toStockTake = stockTake.getId();
        status = StockStatus.CLOSED;
    }

    public void initializeStockItemsBeforeClosing(StockTake stockTake) {
        stockItemsBeforeClosing = new ArrayList<>();
        //(FIFO stock) first product's that were put to stock are used first
        Map<ProductId, List<StockAddTransaction>> productIdToListStockAddTransactionMap = getProductIdToStockAddTransactionMap();

        stockTake.getItems().forEach(stockTakeItem -> {
            long id = stockItemsBeforeClosing.size() + 1;
            Quantity realQuantity = stockTakeItem.getQuantity();
            List<StockAddTransaction> stockAddTransactions = productIdToListStockAddTransactionMap.get(stockTakeItem.getProductId());
            for (StockAddTransaction addTransaction : stockAddTransactions) {
                stockItemsBeforeClosing.add(StockItemBeforeClosing.builder()
                        .id(new StockItemBeforeClosingId(id))
                        .stockId(getId())
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
        addTransactions = addTransactions == null ? new ArrayList<>() : addTransactions;
        long transactionId = addTransactions.size();
        for (StockAddTransaction stockAddTransaction : transactions) {
            stockAddTransaction.validate();
            stockAddTransaction.initialize(getId(), new StockAddTransactionId(++transactionId));
            addTransactions.add(stockAddTransaction);
        }
    }

    public void addStockSubtractTransactions(Sale sale, List<Recipe> recipes, List<String> failureMessages) {
        if (isClosed()) {
            throw new StockDomainException("You cannot add new subtract transactions. Stock is closed!");
        }
        subtractTransactions = subtractTransactions == null ? new ArrayList<>() : subtractTransactions;
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

        Map<ProductId, Quantity> addTransactionMap = productIdToQuantityForAddTransactions();

        Map<ProductId, Quantity> previousSubtractTransactionMap = productIdToQuantityForSubtractTransactions();

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
        addTransactions = new ArrayList<>();
        subtractTransactions = new ArrayList<>();
        stockItemsBeforeClosing = new ArrayList<>();
    }

    private Map<ProductId, Quantity> productIdToQuantityForAddTransactions() {
        return addTransactions.stream()
                .collect(Collectors.toMap(
                        StockAddTransaction::getProductId,
                        StockAddTransaction::getQuantity,
                        Quantity::add
                ));
    }

    private Map<ProductId, Quantity> productIdToQuantityForActualUsage() {
        Map<ProductId, Quantity> quantityForAddTransactions = productIdToQuantityForAddTransactions();
        Map<ProductId, Quantity> quantityForStockTake = productIdToQuantityForStockItemsBeforeClosing();

        return Stream.concat(quantityForAddTransactions.keySet().stream(), quantityForStockTake.keySet().stream())
                .distinct()
                .collect(Collectors.toMap(
                        productId -> productId,
                        productId -> {
                            Quantity addQuantity = quantityForAddTransactions.getOrDefault(productId, Quantity.ZERO);
                            Quantity stockQuantity = quantityForStockTake.getOrDefault(productId, Quantity.ZERO);
                            return addQuantity.subtract(stockQuantity);
                        }
                ));
    }

    private Map<ProductId, Quantity> productIdToQuantityForStockItemsBeforeClosing() {
        return stockItemsBeforeClosing.stream()
                .collect(Collectors.toMap(StockItemBeforeClosing::getProductId, StockItemBeforeClosing::getQuantity, Quantity::add));
    }

    private Map<ProductId, Quantity> productIdToQuantityForSubtractTransactions() {
        return subtractTransactions.stream()
                .collect(Collectors.toMap(
                        StockSubtractTransaction::getProductId,
                        StockSubtractTransaction::getQuantity,
                        Quantity::add
                ));
    }

    private Money getAddTransactionsCost() {
        return addTransactions.stream()
                .map(StockAddTransaction::getTotalGrossPrice)
                .reduce(Money.ZERO, Money::add);
    }

    private Money getStockItemsBeforeClosingCost() {
        return stockItemsBeforeClosing.stream()
                .map(stockItemBeforeClosing -> stockItemBeforeClosing.getGrossPrice()
                        .multiply(stockItemBeforeClosing.getQuantity().getValue()))
                .reduce(Money.ZERO, Money::add);
    }

    private Map<ProductId, List<StockAddTransaction>> getProductIdToStockAddTransactionMap() {
        return addTransactions.stream()
                .collect(Collectors.groupingBy(StockAddTransaction::getProductId))
                .entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().stream()
                                .sorted(Comparator.comparing(StockAddTransaction::getAdditionDate).reversed())
                                .collect(Collectors.toList())));
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