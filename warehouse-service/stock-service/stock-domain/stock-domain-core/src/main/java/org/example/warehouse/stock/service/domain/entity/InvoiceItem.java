package org.example.warehouse.stock.service.domain.entity;

import org.example.domain.entity.BaseEntity;
import org.example.domain.valueobject.Money;
import org.example.domain.valueobject.Quantity;
import org.example.domain.valueobject.UnitOfMeasure;
import org.example.warehouse.stock.service.domain.valueobject.InvoiceItemId;

public class InvoiceItem extends BaseEntity<InvoiceItemId> {
    private final String name;
    private final UnitOfMeasure unitOfMeasure;
    private final Quantity quantity;
    private final Money grossPrice;

    private InvoiceItem(Builder builder) {
        name = builder.name;
        setId(builder.invoiceItemId);
        quantity = builder.quantity;
        grossPrice = builder.grossPrice;
        unitOfMeasure = builder.unitOfMeasure;
    }

    public String getName() {
        return name;
    }

    public UnitOfMeasure getUnitOfMeasure() {
        return unitOfMeasure;
    }

    public Quantity getQuantity() {
        return quantity;
    }

    public Money getGrossPrice() {
        return grossPrice;
    }


    public static final class Builder {
        private InvoiceItemId invoiceItemId;
        private Quantity quantity;
        private Money grossPrice;
        private String name;
        private UnitOfMeasure unitOfMeasure;

        private Builder() {
        }

        public static Builder builder() {
            return new Builder();
        }

        public Builder invoiceItemId(InvoiceItemId val) {
            invoiceItemId = val;
            return this;
        }

        public Builder unitOfMeasure(UnitOfMeasure val) {
            unitOfMeasure = val;
            return this;
        }

        public Builder name(String val) {
            name = val;
            return this;
        }

        public Builder quantity(Quantity val) {
            quantity = val;
            return this;
        }

        public Builder grossPrice(Money val) {
            grossPrice = val;
            return this;
        }

        public InvoiceItem build() {
            return new InvoiceItem(this);
        }
    }
}
