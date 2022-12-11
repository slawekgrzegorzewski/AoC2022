package com.adventofcode.day11;

import java.util.ArrayList;
import java.util.List;

public class Item {
    public final Number value;
    public List<Integer> visitedMonkeys = new ArrayList<>();

    public Item(Number value, int firstMonkey) {
        this.value = value;
        this.visitedMonkeys.add(firstMonkey);
    }
}
