package com.adventofcode.day11;

import java.math.BigDecimal;

public class RegularNumber implements Number {
    private BigDecimal value;

    public RegularNumber(int input) {
        this.value = BigDecimal.valueOf(input);
    }

    public void add(int add) {
        this.value = this.value.add(BigDecimal.valueOf(add));
    }

    public void multiply(int multiply) {
        this.value = this.value.multiply(BigDecimal.valueOf(multiply));
    }

    public void divide(int divideBy) {
        this.value = this.value.divideToIntegralValue(BigDecimal.valueOf(divideBy));
    }

    public void square() {
        this.value = this.value.pow(2);
    }

    public boolean isDivisibleBy(int divisior) {
        return this.value.remainder(BigDecimal.valueOf(divisior)).equals(BigDecimal.ZERO);
    }
}

