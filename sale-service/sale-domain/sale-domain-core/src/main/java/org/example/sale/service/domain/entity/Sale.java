package org.example.sale.service.domain.entity;

import org.example.domain.entity.AggregateRoot;
import org.example.domain.valueobject.Money;
import org.example.domain.valueobject.TrackingId;
import org.example.sale.service.domain.exception.SaleDomainException;
import org.example.domain.valueobject.SaleId;
import org.example.sale.service.domain.valueobject.SaleItemId;
import org.example.sale.service.domain.valueobject.SaleStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class Sale extends AggregateRoot<SaleId> {
    private LocalDateTime date;
    private final Money grossPrice;
    private final List<SaleItem> items;

    private Money netPrice;
    private Money vat;
    private TrackingId trackingId;
    private SaleStatus saleStatus;
    private List<String> failureMessages;

    private Sale(Builder builder) {
        setId(builder.saleId);
        date = builder.date;
        netPrice = builder.netPrice;
        vat = builder.vat;
        grossPrice = builder.grossPrice;
        items = builder.items;
        trackingId = builder.trackingId;
        saleStatus = builder.saleStatus;
        failureMessages = builder.failureMessages;
    }

    public void initializeSale() {
        setId(new SaleId(UUID.randomUUID()));
        trackingId = new TrackingId(UUID.randomUUID());
        saleStatus = SaleStatus.PENDING;
        date = LocalDateTime.now();
        initializeSaleItems();
        initializeNetPriceAndVat();
    }

    private void initializeNetPriceAndVat() {
        Money vat = items.stream()
                .map(saleItem -> {
                    saleItem.initializeNetPrice();
                    return saleItem.getGrossPrice().subtract(saleItem.getNetPrice())
                            .multiply(saleItem.getQuantity());
                }).reduce(Money.ZERO, Money::add);

        setVat(vat);
        setNetPrice(grossPrice.subtract(vat));
    }

    public void validateSale() {
        validateInitialSale();
        validateGrossPrice();
        validateMenuItemsAvailability();
        validateSaleItems();
    }

    private void initializeSaleItems() {
        long itemId = 1;
        for (SaleItem saleItem : items) {
            saleItem.initializeSaleItem(super.getId(), new SaleItemId(itemId++));
        }
    }

    private void validateMenuItemsAvailability() {
        items.forEach(saleItem -> {
            if (!saleItem.getMenuItem().isActive()) {
                throw new SaleDomainException("Menu item with id " +
                        saleItem.getMenuItem().getId().getValue() +
                        "is not available!");
            }
        });
    }

    private void validateSaleItems() {
        Money saleItemsTotal = items.stream().map(saleItem -> {
            validateItemPrice(saleItem);
            return saleItem.getGrossPriceTotal();
        }).reduce(Money.ZERO, Money::add);

        if (!grossPrice.equals(saleItemsTotal)) {
            throw new SaleDomainException("Gross price: " + grossPrice.getAmount()
                    + " is not equal to Sale items total: " + saleItemsTotal.getAmount() + "!");
        }
    }

    private void validateItemPrice(SaleItem saleItem) {
        if (!saleItem.isGrossPriceValid()) {
            throw new SaleDomainException("Sale item gross price: " +
                    saleItem.getGrossPrice().getAmount() +
                    " is not valid for menu item " + saleItem.getMenuItem().getId().getValue());
        }
    }

    private void validateGrossPrice() {
        if (grossPrice == null || !grossPrice.isGreaterThanZero()) {
            throw new SaleDomainException("Gross price must be greater than zero!");
        }
    }

    private void validateInitialSale() {
        if (saleStatus != null || getId() != null) {
            throw new SaleDomainException("Sale is not in correct state for initialization!");
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public LocalDateTime getDate() {
        return date;
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

    public List<SaleItem> getItems() {
        return items;
    }

    public TrackingId getTrackingId() {
        return trackingId;
    }

    public SaleStatus getSaleStatus() {
        return saleStatus;
    }

    public List<String> getFailureMessages() {
        return failureMessages;
    }

    public void setNetPrice(Money netPrice) {
        this.netPrice = netPrice;
    }

    public void setVat(Money vat) {
        this.vat = vat;
    }

    public static final class Builder {
        private SaleId saleId;
        private LocalDateTime date;
        private Money netPrice;
        private Money vat;
        private Money grossPrice;
        private List<SaleItem> items;
        private TrackingId trackingId;
        private SaleStatus saleStatus;
        private List<String> failureMessages;

        private Builder() {
        }

        public Builder saleId(SaleId val) {
            saleId = val;
            return this;
        }

        public Builder date(LocalDateTime val) {
            date = val;
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

        public Builder items(List<SaleItem> val) {
            items = val;
            return this;
        }

        public Builder trackingId(TrackingId val) {
            trackingId = val;
            return this;
        }

        public Builder saleStatus(SaleStatus val) {
            saleStatus = val;
            return this;
        }

        public Builder failureMessages(List<String> val) {
            failureMessages = val;
            return this;
        }

        public Sale build() {
            return new Sale(this);
        }
    }
}
