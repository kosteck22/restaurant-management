package org.example.sale.service.domain.entity;

import org.example.domain.entity.BaseEntity;
import org.example.domain.valueobject.Money;
import org.example.domain.valueobject.VatRate;
import org.example.sale.service.domain.valueobject.MenuItemId;

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
}
