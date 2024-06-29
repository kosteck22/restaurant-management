package org.example.domain.valueobject;

import java.math.BigDecimal;

public class Quantity {
    private final BigDecimal value;

    public static final Quantity ZERO = new Quantity(BigDecimal.ZERO);

    public Quantity(BigDecimal value) {
        if (value.compareTo(BigDecimal.ZERO) < 0)
            throw new IllegalArgumentException("Quantity cannot be negative!");
        this.value = value;
    }

    public Quantity add(Quantity quantity) {
        return new Quantity(this.value.add(quantity.value));
    }

    public Quantity subtract(Quantity quantity) {
        return new Quantity(this.value.subtract(quantity.value));
    }

    public Quantity multiply(Quantity quantity) {
        return new Quantity(this.value.multiply(quantity.value));
    }

    public boolean isZero() {
        return this.value.compareTo(BigDecimal.ZERO) == 0;
    }

    public boolean isPositive() {
        return this.value.compareTo(BigDecimal.ZERO) > 0;
    }

    public boolean isNegative() {
        return this.value.compareTo(BigDecimal.ZERO) < 0;
    }

    public boolean isGreaterThan(Quantity quantity) {
        return this.value.compareTo(quantity.value) > 0;
    }

    public BigDecimal getValue() {
        return value;
    }
}
