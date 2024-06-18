package org.example.warehouse.stock.service.domain.entity;

import org.example.domain.entity.BaseEntity;
import org.example.domain.valueobject.InvoiceId;
import org.example.domain.valueobject.Money;
import org.example.domain.valueobject.ProductId;
import org.example.domain.valueobject.Quantity;
import org.example.warehouse.stock.service.domain.valueobject.StockItemBeforeClosingId;

import java.time.LocalDateTime;

public class StockItemBeforeClosing extends BaseEntity<StockItemBeforeClosingId> {
    private final LocalDateTime additionDate;
    private final ProductId productId;
    private final Quantity quantity;
    private final Money grossPrice;
    private final InvoiceId invoiceId;

    private StockItemBeforeClosing(Builder builder) {
        setId(builder.stockItemBeforeClosingId);
        productId = builder.productId;
        quantity = builder.quantity;
        grossPrice = builder.grossPrice;
        invoiceId = builder.invoiceId;
        additionDate = builder.additionDate;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private StockItemBeforeClosingId stockItemBeforeClosingId;
        private ProductId productId;
        private Quantity quantity;
        private Money grossPrice;
        private InvoiceId invoiceId;
        private LocalDateTime additionDate;

        private Builder() {
        }

        public Builder id(StockItemBeforeClosingId val) {
            stockItemBeforeClosingId = val;
            return this;
        }

        public Builder additionDate(LocalDateTime val) {
            additionDate = val;
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

        public Builder grossPrice(Money val) {
            grossPrice = val;
            return this;
        }

        public Builder invoiceId(InvoiceId val) {
            invoiceId = val;
            return this;
        }

        public StockItemBeforeClosing build() {
            return new StockItemBeforeClosing(this);
        }
    }

    public ProductId getProductId() {
        return productId;
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

    public LocalDateTime getAdditionDate() {
        return additionDate;
    }
}
