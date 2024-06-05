package org.example.menu.service.domain.entity;

import org.example.domain.entity.AggregateRoot;
import org.example.domain.valueobject.MenuItemId;
import org.example.domain.valueobject.Money;
import org.example.domain.valueobject.VatRate;
import org.example.menu.service.domain.exception.MenuItemDomainException;
import org.example.menu.service.domain.valueobject.CategoryId;

import java.util.UUID;

public class MenuItem extends AggregateRoot<MenuItemId> {
    private final String name;
    private final String shortName;
    private final String description;
    private final VatRate vatRate;
    private final Money grossPrice;
    private final Category category;
    private final boolean active;

    private MenuItem(Builder builder) {
        setId(builder.menuItemId);
        name = builder.name;
        shortName = builder.shortName;
        description = builder.description;
        vatRate = builder.vatRate;
        grossPrice = builder.grossPrice;
        category = builder.category;
        active = builder.active;
    }

    public String getName() {
        return name;
    }

    public String getShortName() {
        return shortName;
    }

    public String getDescription() {
        return description;
    }

    public VatRate getVatRate() {
        return vatRate;
    }

    public Money getGrossPrice() {
        return grossPrice;
    }

    public Category getCategory() {
        return category;
    }

    public boolean isActive() {
        return active;
    }

    public static Builder builder() {
        return new Builder();
    }

    public void validateMenuItem() {
        validateInitialMenuItem();
        validatePrice();
    }

    private void validatePrice() {
        if (grossPrice == null || !grossPrice.isGreaterThanZero()) {
            throw new MenuItemDomainException("Gross price must be greater than zero!");
        }
    }

    private void validateInitialMenuItem() {
        if (getId() != null) {
            throw new MenuItemDomainException("Menu item is not in correct state for initialization!");
        }
    }

    public void initializeMenuItem() {
        setId(new MenuItemId(UUID.randomUUID()));
        if (category.getId() == null) {
            category.setId(new CategoryId(UUID.randomUUID()));
        }
    }

    public static final class Builder {
        private MenuItemId menuItemId;
        private String name;
        private String shortName;
        private String description;
        private VatRate vatRate;
        private Money grossPrice;
        private Category category;
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

        public Builder shortName(String val) {
            shortName = val;
            return this;
        }

        public Builder description(String val) {
            description = val;
            return this;
        }

        public Builder vatRate(VatRate val) {
            vatRate = val;
            return this;
        }

        public Builder grossPrice(Money val) {
            grossPrice = val;
            return this;
        }

        public Builder category(Category val) {
            category = val;
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
