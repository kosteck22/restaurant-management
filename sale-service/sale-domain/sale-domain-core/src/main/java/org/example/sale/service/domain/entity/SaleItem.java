package org.example.sale.service.domain.entity;

import org.example.domain.entity.BaseEntity;
import org.example.domain.valueobject.Money;
import org.example.sale.service.domain.valueobject.SaleId;
import org.example.sale.service.domain.valueobject.SaleItemId;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class SaleItem extends BaseEntity<SaleItemId> {
    private SaleId saleId;
    private final MenuItem menuItem;
    private final int quantity;
    private final int discount;
    private Money netPrice;
    private final Money grossPrice;
    private final Money grossPriceTotal;

    private SaleItem(Builder builder) {
        setId(builder.saleItemId);
        saleId = builder.saleId;
        menuItem = builder.product;
        quantity = builder.quantity;
        discount = builder.discount;
        netPrice = builder.netPrice;
        grossPrice = builder.grossPrice;
        grossPriceTotal = builder.grossPriceTotal;
    }

    void initializeSaleItem(SaleId saleId, SaleItemId saleItemId) {
        this.saleId = saleId;
        super.setId(saleItemId);
    }

    public SaleId getSaleId() {
        return saleId;
    }

    public MenuItem getMenuItem() {
        return menuItem;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getDiscount() {
        return discount;
    }

    public Money getNetPrice() {
        return netPrice;
    }

    public Money getGrossPrice() {
        return grossPrice;
    }

    public Money getGrossPriceTotal() {
        return grossPriceTotal;
    }

    public void setNetPrice(Money netPrice) {
        this.netPrice = netPrice;
    }

    public static Builder builder() {
        return new Builder();
    }

    public boolean isGrossPriceValid() {
        return grossPrice.isGreaterThanZero() && grossPriceTotal.isGreaterThanZero()
                && grossPrice.equals(menuItem.getGrossPrice().afterDiscount(discount)) &&
                grossPriceTotal.equals(menuItem.getGrossPrice().afterDiscount(discount).multiply(quantity));
    }

    public void initializeNetPrice() {
        int vatRate = menuItem.getVatRate().getRate();
        BigDecimal b = BigDecimal.ONE.add(BigDecimal.valueOf(vatRate).divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP));
        setNetPrice(grossPrice.divide(b));
    }

    public static final class Builder {
        private SaleItemId saleItemId;
        private SaleId saleId;
        private MenuItem product;
        private int quantity;
        private int discount;
        private Money netPrice;
        private Money grossPrice;
        private Money grossPriceTotal;

        private Builder() {
        }

        public Builder saleItemId(SaleItemId val) {
            saleItemId = val;
            return this;
        }

        public Builder saleId(SaleId val) {
            saleId = val;
            return this;
        }

        public Builder menuItem(MenuItem val) {
            product = val;
            return this;
        }

        public Builder quantity(int val) {
            quantity = val;
            return this;
        }

        public Builder discount(int val) {
            discount = val;
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

        public Builder grossPriceTotal(Money val) {
            grossPriceTotal = val;
            return this;
        }

        public SaleItem build() {
            return new SaleItem(this);
        }
    }
}
