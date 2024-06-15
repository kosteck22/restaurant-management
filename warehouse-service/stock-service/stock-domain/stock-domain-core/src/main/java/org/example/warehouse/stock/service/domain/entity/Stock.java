package org.example.warehouse.stock.service.domain.entity;

import org.example.domain.entity.AggregateRoot;
import org.example.domain.valueobject.Money;
import org.example.domain.valueobject.StockTakeId;
import org.example.warehouse.stock.service.domain.valueobject.StockId;
import org.example.warehouse.stock.service.domain.valueobject.StockStatus;

import java.util.List;

public class Stock extends AggregateRoot<StockId> {

    private final List<StockAddTransaction> addingTransactions;
    private final List<StockDeduceTransaction> deducingTransactions;

    private final StockTakeId fromStockTake;
    private final StockTakeId toStockTake;

    private Money totalGrossPrice;
    private StockStatus status;

    private Stock(Builder builder) {
        setId(builder.stockId);
        addingTransactions = builder.addingTransactions;
        deducingTransactions = builder.deducingTransactions;
        fromStockTake = builder.fromStockTake;
        toStockTake = builder.toStockTake;
        totalGrossPrice = builder.totalGrossPrice;
        status = builder.status;
    }

    public boolean isActive() {
        return status.equals(StockStatus.ACTIVE);
    }

    public boolean isClosed() {
        return status.equals(StockStatus.CLOSED);
    }

    public List<StockAddTransaction> getAddingTransactions() {
        return addingTransactions;
    }

    public List<StockDeduceTransaction> getDeducingTransactions() {
        return deducingTransactions;
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


    public static final class Builder {
        private StockId stockId;
        private List<StockAddTransaction> addingTransactions;
        private List<StockDeduceTransaction> deducingTransactions;
        private StockTakeId fromStockTake;
        private StockTakeId toStockTake;
        private Money totalGrossPrice;
        private StockStatus status;

        private Builder() {
        }

        public static Builder builder() {
            return new Builder();
        }

        public Builder stockId(StockId val) {
            stockId = val;
            return this;
        }

        public Builder addingTransactions(List<StockAddTransaction> val) {
            addingTransactions = val;
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
