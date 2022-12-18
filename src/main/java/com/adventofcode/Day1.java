package com.adventofcode;

import com.adventofcode.input.Input;

import java.io.IOException;
import java.util.List;

public class Day1 {
    private final List<List<Integer>> calories;

    public static void main(String[] input) throws IOException {
        Day1 day1 = new Day1();
        System.out.println("part1 = " + day1.part1());
        System.out.println("part2 = " + day1.part2());
    }

    public Day1() throws IOException {
        this.calories = Input.day1();
    }

    int part1() {
        return findMaxCalories(1);
    }

    int part2() {
        return findMaxCalories(3);
    }

    private int findMaxCalories(int firstNElves) {
        return calories.stream()
                .mapToInt(list -> list.stream().mapToInt(i -> -i).sum())
                .sorted()
                .map(i -> -i)
                .limit(firstNElves)
                .sum();
    }
}


