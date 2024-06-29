package org.example.warehouse.stock.service.domain.valueobject;

import org.example.domain.valueobject.MenuItemId;
import org.example.domain.valueobject.Quantity;

public class SaleItem {
    private final MenuItemId menuItemId;
    private final Quantity quantity;

    public SaleItem(MenuItemId menuItemId, Quantity quantity) {
        this.menuItemId = menuItemId;
        this.quantity = quantity;
    }

    private SaleItem(Builder builder) {
        menuItemId = builder.menuItemId;
        quantity = builder.quantity;
    }

    public MenuItemId getMenuItemId() {
        return menuItemId;
    }

    public Quantity getQuantity() {
        return quantity;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private MenuItemId menuItemId;
        private Quantity quantity;

        private Builder() {
        }

        public Builder menuItemId(MenuItemId val) {
            menuItemId = val;
            return this;
        }

        public Builder quantity(Quantity val) {
            quantity = val;
            return this;
        }

        public SaleItem build() {
            return new SaleItem(this);
        }
    }
}
