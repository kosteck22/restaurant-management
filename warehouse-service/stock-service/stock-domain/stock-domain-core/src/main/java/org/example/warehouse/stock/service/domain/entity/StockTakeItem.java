package org.example.warehouse.stock.service.domain.entity;

import org.example.domain.entity.BaseEntity;
import org.example.domain.valueobject.*;

public class StockTakeItem extends BaseEntity<StockTakeItemId> {
    private ProductId productId;
    private Quantity quantity;

    private StockTakeItem(Builder builder) {
        setId(builder.stockTakeItemId);
        productId = builder.productId;
        quantity = builder.quantity;
    }


    public ProductId getProductId() {
        return productId;
    }

    public Quantity getQuantity() {
        return quantity;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private StockTakeItemId stockTakeItemId;
        private ProductId productId;
        private Quantity quantity;

        private Builder() {
        }

        public Builder id(StockTakeItemId val) {
            stockTakeItemId = val;
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

        public StockTakeItem build() {
            return new StockTakeItem(this);
        }
    }
}
