package org.example.warehouse.stock.service.domain.entity;

import org.example.domain.entity.AggregateRoot;
import org.example.domain.valueobject.SaleId;
import org.example.warehouse.stock.service.domain.valueobject.SaleItem;

import java.time.LocalDateTime;
import java.util.List;

public class Sale extends AggregateRoot<SaleId> {
    private final LocalDateTime date;
    private final List<SaleItem> items;

    private Sale(Builder builder) {
        setId(builder.saleId);
        date = builder.date;
        items = builder.items;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public List<SaleItem> getItems() {
        return items;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private SaleId saleId;
        private LocalDateTime date;
        private List<SaleItem> items;

        private Builder() {
        }

        public Builder id(SaleId val) {
            saleId = val;
            return this;
        }

        public Builder date(LocalDateTime val) {
            date = val;
            return this;
        }

        public Builder items(List<SaleItem> val) {
            items = val;
            return this;
        }

        public Sale build() {
            return new Sale(this);
        }
    }
}
