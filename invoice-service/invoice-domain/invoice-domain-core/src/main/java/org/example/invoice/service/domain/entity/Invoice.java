package org.example.invoice.service.domain.entity;

import org.example.domain.entity.AggregateRoot;
import org.example.invoice.service.domain.exception.InvoiceDomainException;
import org.example.invoice.service.domain.valueobject.Company;
import org.example.domain.valueobject.InvoiceId;
import org.example.invoice.service.domain.valueobject.OrderId;
import org.example.domain.valueobject.TrackingId;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class Invoice extends AggregateRoot<InvoiceId> {
    private final String invoiceNumber;
    private final LocalDate createdAt;
    private final Company seller;
    private final Company buyer;
    private final Order order;
    private TrackingId trackingId;
    private List<String> failureMessages;

    private Invoice(Builder builder) {
        setId(builder.invoiceId);
        invoiceNumber = builder.invoiceNumber;
        createdAt = builder.createdAt;
        seller = builder.seller;
        buyer = builder.buyer;
        order = builder.order;
        trackingId = builder.trackingId;
        failureMessages = builder.failureMessages;
    }

    public void initializeInvoice() {
        setId(new InvoiceId(UUID.randomUUID()));
        trackingId = new TrackingId(UUID.randomUUID());
        order.initializeOrder(super.getId(), new OrderId(UUID.randomUUID()));
    }

    public void validateInvoice() {
        validateInitialInvoice();
        validateOrder();
    }

    private void validateOrder() {
        order.validateTotalPrices();
        order.validateOrderItems();
    }


    private void validateInitialInvoice() {
        if (getId() != null || order.getId() != null) {
            throw new InvoiceDomainException(
                    "Invoice is not in correct state for initialization!");
        }
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public Company getSeller() {
        return seller;
    }

    public Company getBuyer() {
        return buyer;
    }

    public Order getOrder() {
        return order;
    }

    public TrackingId getTrackingId() {
        return trackingId;
    }

    public List<String> getFailureMessages() {
        return failureMessages;
    }

    public static Builder builder() {
        return new Builder();
    }
    public static final class Builder {
        private InvoiceId invoiceId;
        private String invoiceNumber;
        private LocalDate createdAt;
        private Company seller;
        private Company buyer;
        private Order order;
        private TrackingId trackingId;
        private List<String> failureMessages;

        private Builder() {
        }

        public Builder invoiceId(InvoiceId val) {
            invoiceId = val;
            return this;
        }

        public Builder invoiceNumber(String val) {
            invoiceNumber = val;
            return this;
        }

        public Builder createdAt(LocalDate val) {
            createdAt = val;
            return this;
        }

        public Builder seller(Company val) {
            seller = val;
            return this;
        }

        public Builder buyer(Company val) {
            buyer = val;
            return this;
        }

        public Builder order(Order val) {
            order = val;
            return this;
        }

        public Builder trackingId(TrackingId val) {
            trackingId = val;
            return this;
        }

        public Builder failureMessages(List<String> val) {
            failureMessages = val;
            return this;
        }

        public Invoice build() {
            return new Invoice(this);
        }
    }
}
