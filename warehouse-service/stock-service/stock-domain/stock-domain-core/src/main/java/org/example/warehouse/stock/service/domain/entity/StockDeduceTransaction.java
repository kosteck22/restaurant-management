package org.example.warehouse.stock.service.domain.entity;

import org.example.domain.entity.BaseEntity;
import org.example.domain.valueobject.*;
import org.example.warehouse.stock.service.domain.valueobject.StockAddTransactionType;
import org.example.warehouse.stock.service.domain.valueobject.StockDeduceTransactionId;
import org.example.warehouse.stock.service.domain.valueobject.StockDeduceTransactionType;
import org.example.warehouse.stock.service.domain.valueobject.StockId;

import java.time.LocalDateTime;

public class StockDeduceTransaction extends BaseEntity<StockDeduceTransactionId> {
    private final StockId stockId;
    private final LocalDateTime deductionDate;
    private final ProductId productId;
    private final Quantity quantity;
    private final StockDeduceTransactionType stockDeduceTransactionType;
    private final SaleId saleId;

    private StockDeduceTransaction(Builder builder) {
        setId(builder.stockDeduceTransactionId);
        stockId = builder.stockId;
        deductionDate = builder.deductionDate;
        productId = builder.productId;
        quantity = builder.quantity;
        stockDeduceTransactionType = builder.stockDeduceTransactionType;
        saleId = builder.saleId;
    }


    public StockId getStockId() {
        return stockId;
    }

    public LocalDateTime getDeductionDate() {
        return deductionDate;
    }

    public ProductId getProductId() {
        return productId;
    }

    public Quantity getQuantity() {
        return quantity;
    }

    public StockDeduceTransactionType getStockDeduceTransactionType() {
        return stockDeduceTransactionType;
    }

    public SaleId getSaleId() {
        return saleId;
    }

    public static final class Builder {
        private StockDeduceTransactionId stockDeduceTransactionId;
        private StockId stockId;
        private LocalDateTime deductionDate;
        private ProductId productId;
        private Quantity quantity;
        private StockDeduceTransactionType stockDeduceTransactionType;
        private SaleId saleId;

        private Builder() {
        }

        public static Builder builder() {
            return new Builder();
        }

        public Builder stockDeduceTransactionId(StockDeduceTransactionId val) {
            stockDeduceTransactionId = val;
            return this;
        }

        public Builder stockId(StockId val) {
            stockId = val;
            return this;
        }

        public Builder deductionDate(LocalDateTime val) {
            deductionDate = val;
            return this;
        }

        public Builder productId(ProductId val) {
            productId = val;
            return this;
        }

        public Builder quantity(Quantity val) {
            quantity = val;
            return this;
        }

        public Builder stockDeduceTransactionType(StockDeduceTransactionType val) {
            stockDeduceTransactionType = val;
            return this;
        }

        public Builder saleId(SaleId val) {
            saleId = val;
            return this;
        }

        public StockDeduceTransaction build() {
            return new StockDeduceTransaction(this);
        }
    }
}
