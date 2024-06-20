package org.example.warehouse.stock.service.domain.entity;

import org.example.domain.entity.AggregateRoot;
import org.example.domain.valueobject.Money;
import org.example.domain.valueobject.ProductId;
import org.example.domain.valueobject.Quantity;
import org.example.domain.valueobject.StockTakeId;
import org.example.warehouse.stock.service.domain.exception.StockDomainException;
import org.example.warehouse.stock.service.domain.valueobject.StockAddTransactionId;
import org.example.warehouse.stock.service.domain.valueobject.StockId;
import org.example.warehouse.stock.service.domain.valueobject.StockItemBeforeClosingId;
import org.example.warehouse.stock.service.domain.valueobject.StockStatus;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;


public class Stock extends AggregateRoot<StockId> {

    private final List<StockAddTransaction> addingTransactions;
    private final List<StockDeduceTransaction> deducingTransactions;
    private final List<StockItemBeforeClosing> stockItemsBeforeClosing;

    private StockTakeId fromStockTake;
    private StockTakeId toStockTake;

    private Money totalGrossPrice;
    private StockStatus status;

    private Stock(Builder builder) {
        setId(builder.stockId);
        addingTransactions = builder.addingTransactions;
        deducingTransactions = builder.deducingTransactions;
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
        Map<ProductId, List<StockAddTransaction>> productIdToListStockAddTransactionMap = addingTransactions.stream()
                .collect(Collectors.groupingBy(StockAddTransaction::getProductId))
                .entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().stream()
                                .sorted(Comparator.comparing(StockAddTransaction::getAdditionDate).reversed())
                                .collect(Collectors.toList())));

        stockTake.getItems().forEach(stockTakeItem -> {
            Quantity realQuantity = stockTakeItem.getQuantity();
            List<StockAddTransaction> stockAddTransactions = productIdToListStockAddTransactionMap.get(stockTakeItem.getProductId());
            for (StockAddTransaction addTransaction : stockAddTransactions) {
                stockItemsBeforeClosing.add(StockItemBeforeClosing.builder()
                        .id(new StockItemBeforeClosingId(UUID.randomUUID()))
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

        long transactionId = addingTransactions.size();
        for (StockAddTransaction stockAddTransaction : transactions) {
            stockAddTransaction.validate();
            stockAddTransaction.initialize(super.getId(), new StockAddTransactionId(++transactionId));
            addingTransactions.add(stockAddTransaction);
        }
    }

    protected boolean isActive() {
        return status.equals(StockStatus.ACTIVE);
    }

    protected boolean isClosed() {
        return status.equals(StockStatus.CLOSED);
    }


    public List<StockAddTransaction> getAddingTransactions() {
        return addingTransactions;
    }

    public List<StockDeduceTransaction> getDeducingTransactions() {
        return deducingTransactions;
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

    public void initializeStock() {
        setId(new StockId(UUID.randomUUID()));

    }

    public static final class Builder {
        private StockId stockId;
        private List<StockAddTransaction> addingTransactions;
        private List<StockDeduceTransaction> deducingTransactions;
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
            addingTransactions = val;
            return this;
        }

        public Builder stockItemsBeforeClosing(List<StockItemBeforeClosing> val) {
            stockItemsBeforeClosing = val;
            return this;
        }

        public Builder deducingTransactions(List<StockDeduceTransaction> val) {
            deducingTransactions = val;
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
