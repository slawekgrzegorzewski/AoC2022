package com.adventofcode.day11;

import java.util.HashMap;
import java.util.Map;

import static java.util.Optional.ofNullable;

public class DividersNumber implements Number {
    private final int[] dividersToTrack = new int[]{2, 3, 5, 7, 11, 13, 17, 19, 23};
    private final Map<Integer, Integer> reminders = new HashMap<>();

    public DividersNumber(int input) {
        for (int divider : dividersToTrack) {
            reminders.put(divider, input % divider);
        }
    }

    public void add(int add) {
        for (int d : dividersToTrack) {
            reminders.compute(d, (divider, remainder) -> (ofNullable(remainder).orElse(0) + add) % divider);
        }
    }

    public void multiply(int multiply) {
        for (int d : dividersToTrack) {
            reminders.compute(d, (divider, remainder) -> (ofNullable(remainder).orElse(0) * multiply) % divider);
        }
    }

    public void divide(int divideBy) {

    }

    public void square() {
        for (int d : dividersToTrack) {
            reminders.compute(d, (divider, remainder) -> (ofNullable(remainder).orElse(0) * ofNullable(remainder).orElse(0)) % divider);
        }
    }

    public boolean isDivisibleBy(int divisior) {
        return reminders.get(divisior).equals(0);
    }
}

