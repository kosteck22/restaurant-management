package org.example.warehouse.stock.service.domain.entity;

import org.example.domain.entity.BaseEntity;
import org.example.domain.valueobject.*;
import org.example.warehouse.stock.service.domain.valueobject.StockSubtractTransactionId;
import org.example.warehouse.stock.service.domain.valueobject.StockSubtractTransactionType;
import org.example.warehouse.stock.service.domain.valueobject.StockId;

import java.time.LocalDateTime;

public class StockSubtractTransaction extends BaseEntity<StockSubtractTransactionId> {
    private final StockId stockId;
    private final LocalDateTime subtractDate;
    private final ProductId productId;
    private final Quantity quantity;
    private final StockSubtractTransactionType stockSubtractTransactionType;
    private final SaleId saleId;

    private StockSubtractTransaction(Builder builder) {
        setId(builder.stockSubtractTransactionId);
        stockId = builder.stockId;
        subtractDate = builder.subtractDate;
        productId = builder.productId;
        quantity = builder.quantity;
        stockSubtractTransactionType = builder.stockSubtractTransactionType;
        saleId = builder.saleId;
    }


    public StockId getStockId() {
        return stockId;
    }

    public LocalDateTime getSubtractDate() {
        return subtractDate;
    }

    public ProductId getProductId() {
        return productId;
    }

    public Quantity getQuantity() {
        return quantity;
    }

    public StockSubtractTransactionType getStockSubtractTransactionType() {
        return stockSubtractTransactionType;
    }

    public SaleId getSaleId() {
        return saleId;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private StockSubtractTransactionId stockSubtractTransactionId;
        private StockId stockId;
        private LocalDateTime subtractDate;
        private ProductId productId;
        private Quantity quantity;
        private StockSubtractTransactionType stockSubtractTransactionType;
        private SaleId saleId;

        private Builder() {
        }

        public Builder stockSubtractTransactionId(StockSubtractTransactionId val) {
            stockSubtractTransactionId = val;
            return this;
        }

        public Builder stockId(StockId val) {
            stockId = val;
            return this;
        }

        public Builder subtractDate(LocalDateTime val) {
            subtractDate = val;
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

        public Builder stockSubtractTransactionType(StockSubtractTransactionType val) {
            stockSubtractTransactionType = val;
            return this;
        }

        public Builder saleId(SaleId val) {
            saleId = val;
            return this;
        }

        public StockSubtractTransaction build() {
            return new StockSubtractTransaction(this);
        }
    }
}
