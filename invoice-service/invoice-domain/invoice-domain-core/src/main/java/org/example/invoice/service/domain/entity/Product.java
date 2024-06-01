package org.example.invoice.service.domain.entity;

import org.example.domain.entity.BaseEntity;
import org.example.invoice.service.domain.valueobject.Money;
import org.example.invoice.service.domain.valueobject.ProductId;
import org.example.invoice.service.domain.valueobject.UnitOfMeasure;
import org.example.invoice.service.domain.valueobject.VatRate;

import java.util.UUID;

public class Product extends BaseEntity<ProductId> {
    private final String name;
    private final Money netPrice;
    private final Money grossPrice;
    private final VatRate vatRate;
    private final UnitOfMeasure unitOfMeasure;

    private Product(Builder builder) {
        setId(builder.productId);
        name = builder.name;
        netPrice = builder.netPrice;
        grossPrice = builder.grossPrice;
        vatRate = builder.vatRate;
        unitOfMeasure = builder.unitOfMeasure;
    }

    public String getName() {
        return name;
    }

    public Money getNetPrice() {
        return netPrice;
    }

    public Money getGrossPrice() {
        return grossPrice;
    }

    public VatRate getVatRate() {
        return vatRate;
    }

    public UnitOfMeasure getUnitOfMeasure() {
        return unitOfMeasure;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private ProductId productId;
        private String name;
        private Money netPrice;
        private Money grossPrice;
        private VatRate vatRate;
        private UnitOfMeasure unitOfMeasure;

        private Builder() {
        }

        public Builder productId(ProductId val) {
            productId = val;
            return this;
        }

        public Builder name(String val) {
            name = val;
            return this;
        }

        public Builder netPrice(Money val) {
            netPrice = val;
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

        public Builder unitOfMeasure(UnitOfMeasure val) {
            unitOfMeasure = val;
            return this;
        }

        public Product build() {
            return new Product(this);
        }
    }
}
