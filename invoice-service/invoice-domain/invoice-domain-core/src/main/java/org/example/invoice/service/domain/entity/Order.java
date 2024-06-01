package org.example.invoice.service.domain.entity;

import org.example.domain.entity.BaseEntity;
import org.example.invoice.service.domain.valueobject.InvoiceId;
import org.example.invoice.service.domain.valueobject.Money;
import org.example.invoice.service.domain.valueobject.OrderId;

import java.util.List;

public class Order extends BaseEntity<OrderId> {
    private final InvoiceId invoiceId;
    private final Money netPrice;
    private final Money vat;
    private final Money grossPrice;
    private final List<OrderItem> items;

    private Order(Builder builder) {
        setId(builder.orderId);
        invoiceId = builder.invoiceId;
        netPrice = builder.netPrice;
        vat = builder.vat;
        grossPrice = builder.grossPrice;
        items = builder.items;
    }

    public InvoiceId getInvoiceId() {
        return invoiceId;
    }

    public Money getNetPrice() {
        return netPrice;
    }

    public Money getVat() {
        return vat;
    }

    public Money getGrossPrice() {
        return grossPrice;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public static Builder builder() {
        return new Builder();
    }
    public static final class Builder {
        private OrderId orderId;
        private InvoiceId invoiceId;
        private Money netPrice;
        private Money vat;
        private Money grossPrice;
        private List<OrderItem> items;

        private Builder() {
        }

        public Builder id(OrderId val) {
            orderId = val;
            return this;
        }

        public Builder invoiceId(InvoiceId val) {
            invoiceId = val;
            return this;
        }

        public Builder netPrice(Money val) {
            netPrice = val;
            return this;
        }

        public Builder vat(Money val) {
            vat = val;
            return this;
        }

        public Builder grossPrice(Money val) {
            grossPrice = val;
            return this;
        }

        public Builder items(List<OrderItem> val) {
            items = val;
            return this;
        }

        public Order build() {
            return new Order(this);
        }
    }
}
