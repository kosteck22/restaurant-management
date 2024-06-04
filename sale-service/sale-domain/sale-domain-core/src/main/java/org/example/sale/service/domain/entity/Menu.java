package org.example.sale.service.domain.entity;

import org.example.domain.entity.AggregateRoot;
import org.example.sale.service.domain.valueobject.MenuId;

import java.util.List;

public class Menu extends AggregateRoot<MenuId> {
    private final List<MenuItem> items;

    private Menu(Builder builder) {
        setId(builder.menuId);
        items = builder.items;
    }

    public List<MenuItem> getItems() {
        return items;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private MenuId menuId;
        private List<MenuItem> items;

        private Builder() {
        }

        public Builder menuId(MenuId val) {
            menuId = val;
            return this;
        }

        public Builder items(List<MenuItem> val) {
            items = val;
            return this;
        }

        public Menu build() {
            return new Menu(this);
        }
    }
}
