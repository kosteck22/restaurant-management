package org.example.invoice.service.domain.entity;

import org.example.domain.entity.BaseEntity;
import org.example.invoice.service.domain.exception.InvoiceDomainException;
import org.example.invoice.service.domain.valueobject.Money;
import org.example.invoice.service.domain.valueobject.OrderId;
import org.example.invoice.service.domain.valueobject.OrderItemId;

public class OrderItem extends BaseEntity<OrderItemId> {
    private OrderId orderId;
    private final Product product;
    private final int quantity;
    private final int discount;
    private final Money netTotal;
    private final Money vat;
    private final Money grossTotal;

    private OrderItem(Builder builder) {
        setId(builder.orderItemId);
        orderId = builder.orderId;
        product = builder.product;
        quantity = builder.quantity;
        discount = builder.discount;
        netTotal = builder.netTotal;
        vat = builder.vat;
        grossTotal = builder.grossTotal;
    }

    void initializeOrderItem(OrderId orderId, OrderItemId orderItemId) {
        this.orderId = orderId;
        super.setId(orderItemId);
    }

    void validate() {
        validateProduct();
        validateTotalPrices();
    }

    private void validateTotalPrices() {
        if (quantity < 1 || discount > 100 ||
                netTotal == null || vat == null || grossTotal == null) {
            throw new InvoiceDomainException("Total prices cannot be null!");
        }
        if (!netTotal.isGreaterThanZero() || vat.isGreaterThanZero() || grossTotal.isGreaterThanZero()) {
            throw new InvoiceDomainException("Total prices must be greater than zero!");
        }
        if (!netTotal.add(vat).multiply(quantity).equals(grossTotal)) {
            throw new InvoiceDomainException(
                    "Net price plus vat must be equal to grossPrice for product %s!"
                            .formatted(product.getName()) );
        }
    }

    private void validateProduct() {
        if (product == null) {
            throw new InvoiceDomainException("Product cannot be null!");
        }
        product.validateProduct();
    }

    public OrderId getOrderId() {
        return orderId;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getDiscount() {
        return discount;
    }

    public Money getNetTotal() {
        return netTotal;
    }

    public Money getVat() {
        return vat;
    }

    public Money getGrossTotal() {
        return grossTotal;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private OrderItemId orderItemId;
        private OrderId orderId;
        private Product product;
        private int quantity;
        private int discount;
        private Money netTotal;
        private Money vat;
        private Money grossTotal;

        private Builder() {
        }

        public Builder orderItemId(OrderItemId val) {
            orderItemId = val;
            return this;
        }

        public Builder orderId(OrderId val) {
            orderId = val;
            return this;
        }

        public Builder product(Product val) {
            product = val;
            return this;
        }

        public Builder quantity(int val) {
            quantity = val;
            return this;
        }

        public Builder discount(int val) {
            discount = val;
            return this;
        }

        public Builder netTotal(Money val) {
            netTotal = val;
            return this;
        }

        public Builder vat(Money val) {
            vat = val;
            return this;
        }

        public Builder grossTotal(Money val) {
            grossTotal = val;
            return this;
        }

        public OrderItem build() {
            return new OrderItem(this);
        }
    }
}
