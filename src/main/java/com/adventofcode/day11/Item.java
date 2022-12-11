package com.adventofcode.day11;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Item {
    public BigDecimal initialValue;
    public BigDecimal value;
    public List<Integer> visitedMonkeys = new ArrayList<>();

    public Item(BigDecimal value, int firstMonkey) {
        this.initialValue = value;
        this.value = value;
        this.visitedMonkeys.add(firstMonkey);
    }
}
