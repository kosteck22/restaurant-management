package org.example.sale.service.domain.entity;

import org.example.domain.entity.BaseEntity;
import org.example.domain.valueobject.Money;
import org.example.domain.valueobject.VatRate;
import org.example.domain.valueobject.MenuItemId;

public class MenuItem extends BaseEntity<MenuItemId> {
    private String name;
    private Money grossPrice;
    private VatRate vatRate;
    private boolean active;

    public MenuItem(String name, Money grossPrice, VatRate vatRate) {
        this.name = name;
        this.grossPrice = grossPrice;
        this.vatRate = vatRate;
    }

    public MenuItem(MenuItemId menuItemId) {
        super.setId(menuItemId);
    }

    private MenuItem(Builder builder) {
        setId(builder.menuItemId);
        name = builder.name;
        grossPrice = builder.grossPrice;
        vatRate = builder.vatRate;
        active = builder.active;
    }

    public void updateWithConfirmedData(String name, Money grossPrice, VatRate vatRate, boolean active) {
        this.name = name;
        this.grossPrice = grossPrice;
        this.vatRate = vatRate;
        this.active = active;
    }

    public String getName() {
        return name;
    }

    public Money getGrossPrice() {
        return grossPrice;
    }

    public VatRate getVatRate() {
        return vatRate;
    }

    public boolean isActive() {
        return active;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private MenuItemId menuItemId;
        private String name;
        private Money grossPrice;
        private VatRate vatRate;
        private boolean active;

        private Builder() {
        }

        public Builder menuItemId(MenuItemId val) {
            menuItemId = val;
            return this;
        }

        public Builder name(String val) {
            name = val;
            return this;
        }

        public Builder grossPrice(Money val) {
            grossPrice = val;
            return this;
        }

        public Builder vatRate(VatRate val) {
            vatRate = val;
            return this;
        }

        public Builder active(boolean val) {
            active = val;
            return this;
        }

        public MenuItem build() {
            return new MenuItem(this);
        }
    }
}
