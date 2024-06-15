package org.example.warehouse.stock.service.domain.entity;

import org.example.domain.entity.AggregateRoot;
import org.example.domain.valueobject.InvoiceId;

import java.time.LocalDate;
import java.util.List;

public class Invoice extends AggregateRoot<InvoiceId> {
    private final LocalDate date;
    private final List<InvoiceItem> items;

    private Invoice(Builder builder) {
        setId(builder.invoiceId);
        date = builder.date;
        items = builder.items;
    }

    public LocalDate getDate() {
        return date;
    }

    public List<InvoiceItem> getItems() {
        return items;
    }


    public static final class Builder {
        private InvoiceId invoiceId;
        private LocalDate date;
        private List<InvoiceItem> items;

        private Builder() {
        }

        public static Builder builder() {
            return new Builder();
        }

        public Builder invoiceId(InvoiceId val) {
            invoiceId = val;
            return this;
        }

        public Builder date(LocalDate val) {
            date = val;
            return this;
        }

        public Builder items(List<InvoiceItem> val) {
            items = val;
            return this;
        }

        public Invoice build() {
            return new Invoice(this);
        }
    }
}
