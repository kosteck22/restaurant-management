package org.example.domain.valueobject;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public class Money {
    private final BigDecimal amount;

    public static final Money ZERO = new Money(BigDecimal.ZERO);

    public Money(BigDecimal amount) {
        this.amount = amount;
    }

    public boolean isGreaterThanZero() {
        return this.amount != null &&
                this.amount.compareTo(BigDecimal.ZERO) > 0;
    }

    public boolean isGreaterThanOrEqualZero() {
        return this.amount != null &&
                this.amount.compareTo(BigDecimal.ZERO) >= 0;
    }

    public boolean isGreaterThan(Money money) {
        return this.amount != null &&
                money != null &&
                this.amount.compareTo(money.getAmount()) > 0;
    }

    public Money afterDiscount(int discount) {
        BigDecimal b = discount < 1 ? BigDecimal.ONE : BigDecimal.ONE
                .subtract(BigDecimal.valueOf(discount)
                        .divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP));
        return new Money(setScale(getAmount().multiply(b)));
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Money add(Money money) {
        return new Money(setScale(this.amount.add(money.getAmount())));
    }

    public Money subtract(Money money) {
        return new Money(setScale(this.amount.subtract(money.getAmount())));
    }

    public Money multiply(int multiplier) {
        return new Money(setScale(this.amount.multiply(new BigDecimal(multiplier))));
    }

    public Money multiply(BigDecimal multiplier) {
        return new Money(setScale(this.amount.multiply(multiplier)));
    }

    public Money divide(BigDecimal d) {
        return new Money(setScale(this.amount.divide(d, RoundingMode.HALF_UP)));
    }

    private BigDecimal setScale(BigDecimal input) {
        return input.setScale(2, RoundingMode.HALF_EVEN);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Money money = (Money) o;
        return Objects.equals(amount, money.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount);
    }
}
