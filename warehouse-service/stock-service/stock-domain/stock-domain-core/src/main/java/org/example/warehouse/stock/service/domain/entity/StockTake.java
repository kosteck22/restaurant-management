package org.example.warehouse.stock.service.domain.entity;

import org.example.domain.entity.AggregateRoot;
import org.example.domain.valueobject.Money;
import org.example.domain.valueobject.StockTakeId;

import java.time.LocalDateTime;
import java.util.List;

public class StockTake extends AggregateRoot<StockTakeId> {
    private final LocalDateTime preparedDate;
    private final List<StockTakeItem> items;

    private StockTake(Builder builder) {
        setId(builder.stockTakeId);
        preparedDate = builder.preparedDate;
        items = builder.items;
    }

    public LocalDateTime getPreparedDate() {
        return preparedDate;
    }

    public List<StockTakeItem> getItems() {
        return items;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private StockTakeId stockTakeId;
        private LocalDateTime preparedDate;
        private List<StockTakeItem> items;

        private Builder() {
        }

        public Builder id(StockTakeId val) {
            stockTakeId = val;
            return this;
        }

        public Builder preparedDate(LocalDateTime val) {
            preparedDate = val;
            return this;
        }

        public Builder items(List<StockTakeItem> val) {
            items = val;
            return this;
        }

        public StockTake build() {
            return new StockTake(this);
        }
    }
}
