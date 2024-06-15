package org.example.warehouse.stock.service.domain.entity;

import org.example.domain.entity.BaseEntity;
import org.example.domain.valueobject.InvoiceId;
import org.example.domain.valueobject.Money;
import org.example.domain.valueobject.ProductId;
import org.example.domain.valueobject.Quantity;
import org.example.warehouse.stock.service.domain.valueobject.StockAddTransactionId;
import org.example.warehouse.stock.service.domain.valueobject.StockAddTransactionType;
import org.example.warehouse.stock.service.domain.valueobject.StockId;

import java.time.LocalDateTime;

public class StockAddTransaction extends BaseEntity<StockAddTransactionId> {
    private final StockId stockId;
    private final LocalDateTime additionDate;
    private final ProductId productId;
    private final Quantity quantity;
    private final Money totalGrossPrice;
    private final Money grossPrice;
    private final StockAddTransactionType stockAddTransactionType;
    private final InvoiceId invoiceId;

    private StockAddTransaction(Builder builder) {
        setId(builder.stockAddTransactionId);
        stockId = builder.stockId;
        additionDate = builder.additionDate;
        productId = builder.productId;
        quantity = builder.quantity;
        totalGrossPrice = builder.totalGrossPrice;
        grossPrice = builder.grossPrice;
        stockAddTransactionType = builder.stockAddTransactionType;
        invoiceId = builder.invoiceId;
    }

    public StockId getStockId() {
        return stockId;
    }

    public LocalDateTime getAdditionDate() {
        return additionDate;
    }

    public ProductId getProductId() {
        return productId;
    }

    public Quantity getQuantity() {
        return quantity;
    }

    public Money getTotalGrossPrice() {
        return totalGrossPrice;
    }

    public Money getGrossPrice() {
        return grossPrice;
    }

    public StockAddTransactionType getStockAddTransactionType() {
        return stockAddTransactionType;
    }

    public InvoiceId getInvoiceId() {
        return invoiceId;
    }


    public static final class Builder {
        private StockAddTransactionId stockAddTransactionId;
        private StockId stockId;
        private LocalDateTime additionDate;
        private ProductId productId;
        private Quantity quantity;
        private Money totalGrossPrice;
        private Money grossPrice;
        private StockAddTransactionType stockAddTransactionType;
        private InvoiceId invoiceId;

        private Builder() {
        }

        public static Builder builder() {
            return new Builder();
        }

        public Builder stockAddTransactionId(StockAddTransactionId val) {
            stockAddTransactionId = val;
            return this;
        }

        public Builder stockId(StockId val) {
            stockId = val;
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

        public Builder totalGrossPrice(Money val) {
            totalGrossPrice = val;
            return this;
        }

        public Builder grossPrice(Money val) {
            grossPrice = val;
            return this;
        }

        public Builder stockAddTransactionType(StockAddTransactionType val) {
            stockAddTransactionType = val;
            return this;
        }

        public Builder invoiceId(InvoiceId val) {
            invoiceId = val;
            return this;
        }

        public StockAddTransaction build() {
            return new StockAddTransaction(this);
        }
    }
}
