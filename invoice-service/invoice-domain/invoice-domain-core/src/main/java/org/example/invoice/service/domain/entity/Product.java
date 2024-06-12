package org.example.invoice.service.domain.entity;

import org.example.domain.entity.BaseEntity;
import org.example.invoice.service.domain.exception.InvoiceDomainException;
import org.example.domain.valueobject.Money;
import org.example.invoice.service.domain.valueobject.ProductId;
import org.example.domain.valueobject.UnitOfMeasure;
import org.example.domain.valueobject.VatRate;

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

    void validateProduct() {
        if (netPrice == null || grossPrice == null || vatRate == null) {
            throw new InvoiceDomainException("Net price, gross price and vatRate cannot be null!");
        }
        if (!netPrice.isGreaterThanZero() || !grossPrice.isGreaterThanZero()) {
            throw new InvoiceDomainException("Net price and gross price must be greater than zero!");
        }
        if (!netPrice.add(calculateVatAmount()).equals(grossPrice)) {
            throw new InvoiceDomainException("Net price plus vat must be equal to gross price!");
        }
    }

    private Money calculateVatAmount() {
        return netPrice.multiply(vatRate.getRate() / 100);
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
