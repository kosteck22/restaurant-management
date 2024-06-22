package org.example.warehouse.stock.take.service.domain.entity;

import org.example.domain.entity.AggregateRoot;
import org.example.domain.valueobject.Money;
import org.example.domain.valueobject.TrackingId;
import org.example.warehouse.stock.take.service.domain.exception.StockTakeDomainException;
import org.example.domain.valueobject.StockTakeItemId;
import org.example.domain.valueobject.StockTakeId;
import org.example.warehouse.stock.take.service.domain.valueobject.StockTakeStatus;


import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class StockTake extends AggregateRoot<StockTakeId> {
    private final LocalDateTime preparedDate;
    private final Money totalPrice;
    private final List<StockTakeItem> items;


    private StockTakeStatus status;
    private TrackingId trackingId;
    private List<String> failureMessages;

    private StockTake(Builder builder) {
        setId(builder.stockTakeId);
        preparedDate = builder.preparedDate;
        totalPrice = builder.totalPrice;
        status = builder.status;
        items = builder.items;
        trackingId = builder.trackingId;
        failureMessages = builder.failureMessages;
    }

    public void accept() {
        if (StockTakeStatus.PENDING != status ) {
            throw new StockTakeDomainException("Stock take is not in correct state for accept operation!");
        }
        status = StockTakeStatus.ACCEPTED;
    }


    public void reject(List<String> failureMessages) {
        if (StockTakeStatus.PENDING != status ) {
            throw new StockTakeDomainException("Stock take is not in correct state for reject operation!");
        }
        status = StockTakeStatus.REJECTED;
        updateFailureMessages(failureMessages);
    }

    private void updateFailureMessages(List<String> failureMessages) {
        if (this.failureMessages != null && failureMessages != null) {
            this.failureMessages.addAll(failureMessages.stream().filter(message -> !message.isEmpty()).toList());
        }
        if (this.failureMessages == null) {
            this.failureMessages = failureMessages;
        }
    }


    public TrackingId getTrackingId() {
        return trackingId;
    }

    public List<String> getFailureMessages() {
        return failureMessages;
    }

    public StockTakeStatus getStatus() {
        return status;
    }

    public LocalDateTime getPreparedDate() {
        return preparedDate;
    }

    public Money getTotalPrice() {
        return totalPrice;
    }

    public List<StockTakeItem> getItems() {
        return items;
    }

    public static Builder builder() {
        return new Builder();
    }

    public void validateStockTake() {
        validateInitialStockTake();
        validateStockItems();
    }

    private void validateStockItems() {
        items.forEach(StockTakeItem::validateQuantity);
    }

    private void validateInitialStockTake() {
        if (status != null || getId() != null) {
            throw new StockTakeDomainException("Stock take is not in correct state for initialization!");
        }
    }

    public void initializeStockTake() {
        setId(new StockTakeId(UUID.randomUUID()));
        trackingId = new TrackingId(UUID.randomUUID());
        status = StockTakeStatus.PENDING;
        initializeStockTakeItems();
    }

    private void initializeStockTakeItems() {
        long itemId = 1;
        for (StockTakeItem stockItem: items) {
            stockItem.initializeStockItem(super.getId(), new StockTakeItemId(itemId++));
        }
    }

    public static final class Builder {
        private StockTakeId stockTakeId;
        private LocalDateTime preparedDate;
        private Money totalPrice;
        private StockTakeStatus status;
        private List<StockTakeItem> items;
        private TrackingId trackingId;
        private List<String> failureMessages;

        private Builder() {
        }

        public Builder stockTakeId(StockTakeId val) {
            stockTakeId = val;
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

        public Builder stockTakeStatus(StockTakeStatus val) {
            status = val;
            return this;
        }

        public Builder preparedDate(LocalDateTime val) {
            preparedDate = val;
            return this;
        }

        public Builder totalPrice(Money val) {
            totalPrice = val;
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

