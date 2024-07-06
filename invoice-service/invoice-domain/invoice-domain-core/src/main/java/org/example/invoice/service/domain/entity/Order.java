package org.example.invoice.service.domain.entity;

import org.example.domain.entity.BaseEntity;
import org.example.invoice.service.domain.exception.InvoiceDomainException;
import org.example.domain.valueobject.InvoiceId;
import org.example.domain.valueobject.Money;
import org.example.invoice.service.domain.valueobject.OrderId;
import org.example.invoice.service.domain.valueobject.OrderItemId;

import java.util.List;

public class Order extends BaseEntity<OrderId> {
    private InvoiceId invoiceId;
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


    void initializeOrder(InvoiceId invoiceId, OrderId orderId) {
        this.invoiceId = invoiceId;
        super.setId(orderId);
        initializeOrderItems();
    }

    private void initializeOrderItems() {
        long itemId = 1;
        for (OrderItem orderItem: items) {
            orderItem.initializeOrderItem(super.getId(), new OrderItemId(itemId++));
        }
    }

    void validateTotalPrices() {
        if (netPrice == null || grossPrice == null || vat == null ||
        !netPrice.isGreaterThanZero() || !grossPrice.isGreaterThanZero() ||
                !vat.isGreaterThanOrEqualZero()) {
            throw new InvoiceDomainException("Total prices must be greater than zero!");
        }
        if (!netPrice.add(vat).equals(grossPrice)) {
            throw new InvoiceDomainException("Net price plus vat must be equal to grossPrice!");
        }
    }

    void validateOrderItems() {
        Money orderItemsGrossTotal = Money.ZERO;
        Money orderItemsNetTotal = Money.ZERO;
        Money orderItemsVatTotal = Money.ZERO;

        for (OrderItem orderItem : items) {
            orderItem.validate();
            orderItemsGrossTotal = orderItemsGrossTotal.add(orderItem.getGrossTotal());
            orderItemsNetTotal = orderItemsNetTotal.add(orderItem.getNetTotal());
            orderItemsVatTotal = orderItemsVatTotal.add(orderItem.getVat());
        }

        if (!grossPrice.equals(orderItemsGrossTotal) ||
        !netPrice.equals(orderItemsNetTotal) ||
        !vat.equals(orderItemsVatTotal)) {
            throw new InvoiceDomainException("Sum of order item prices must be equal to Order Total price!");
        }
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
