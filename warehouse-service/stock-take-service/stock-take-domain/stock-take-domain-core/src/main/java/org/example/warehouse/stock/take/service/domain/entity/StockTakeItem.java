package org.example.warehouse.stock.take.service.domain.entity;

import org.example.domain.entity.BaseEntity;
import org.example.domain.valueobject.InvoiceId;
import org.example.domain.valueobject.Money;
import org.example.domain.valueobject.ProductId;
import org.example.domain.valueobject.Quantity;
import org.example.warehouse.stock.take.service.domain.exception.StockTakeDomainException;
import org.example.domain.valueobject.StockTakeId;
import org.example.domain.valueobject.StockTakeItemId;

public class StockTakeItem extends BaseEntity<StockTakeItemId> {
    private StockTakeId stockTakeId;
    private ProductId productId;
    private String name;
    private Quantity quantity;
    private Money grossPrice;
    private Money totalGrossPrice;
    private InvoiceId invoiceId;

    private StockTakeItem(Builder builder) {
        setId(builder.stockTakeItemId);
        stockTakeId = builder.stockTakeId;
        productId = builder.productId;
        name = builder.name;
        quantity = builder.quantity;
        grossPrice = builder.grossPrice;
        totalGrossPrice = builder.totalGrossPrice;
        invoiceId = builder.invoiceId;
    }

    public StockTakeId getStockTakeId() {
        return stockTakeId;
    }

    public ProductId getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public Quantity getQuantity() {
        return quantity;
    }

    public Money getGrossPrice() {
        return grossPrice;
    }

    public InvoiceId getInvoiceId() {
        return invoiceId;
    }

    public Money getTotalGrossPrice() {
        return totalGrossPrice;
    }

    public static Builder builder() {
        return new Builder();
    }

    public void updateName(String name) {
        this.name = name;
    }

    public void validateQuantity() {
        if (quantity == null || quantity.isNegative()) {
            throw new StockTakeDomainException("Quantity cannot be negative");
        }
    }

    public void initializeStockItem(StockTakeId stockTakeId, StockTakeItemId stockTakeItemId) {
        this.stockTakeId = stockTakeId;
        super.setId(stockTakeItemId);
    }

    public static final class Builder {
        private StockTakeId stockTakeId;
        private StockTakeItemId stockTakeItemId;
        private ProductId productId;
        private String name;
        private Quantity quantity;
        private Money totalGrossPrice;
        private Money grossPrice;
        private InvoiceId invoiceId;

        private Builder() {
        }

        public Builder grossPrice(Money val) {
            grossPrice = val;
            return this;
        }

        public Builder invoiceId(InvoiceId val) {
            invoiceId = val;
            return this;
        }

        public Builder stockTakeItemId(StockTakeItemId val) {
            stockTakeItemId = val;
            return this;
        }

        public Builder stockTakeId(StockTakeId val) {
            stockTakeId = val;
            return this;
        }

        public Builder productId(ProductId val) {
            productId = val;
            return this;
        }

        public Builder name(String val) {
            name = val;
            return this;
        }

        public Builder quantity(Quantity val) {
            quantity = val;
            return this;
        }

        public Builder totalPrice(Money val) {
            totalGrossPrice = val;
            return this;
        }

        public StockTakeItem build() {
            return new StockTakeItem(this);
        }
    }
}
