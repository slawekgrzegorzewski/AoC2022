package com.adventofcode;

import com.adventofcode.day11.MonkeyBehaviour;
import com.adventofcode.input.Input;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;

public class Day11 {

    private Map<Integer, MonkeyBehaviour> monkeys;

    long part1() throws IOException {
        monkeys = Input.day11("/day11", 3);
        return rounds(20, false);
    }

    long part2() throws IOException {
        monkeys = Input.day11("/day11", 1);
        return rounds(10_000, true);
    }

    private long rounds(int rounds, boolean debug) {
        Map<Integer, Long> activityCounter = new HashMap<>();
        for (int round = 0; round < rounds; round++) {
            for (int i = 0; i < monkeys.size(); i++) {
                MonkeyBehaviour monkeyBehaviour = monkeys.get(i);
                activityCounter.compute(i, (key, value) ->
                        ofNullable(value).orElse(0L) + monkeyBehaviour.startingItems().size());
                monkeyBehaviour.throwToMonkey(monkeys);
            }
        }
        if(debug){
            monkeys.values()
                    .stream()
                    .flatMap(monkey -> monkey.startingItems().stream())
                    .map(item -> item.initialValue + ": " + item.visitedMonkeys.stream().map(String::valueOf).collect(Collectors.joining()))
                    .forEach(System.out::println);
        }
        return activityCounter.values().stream()
                .mapToLong(i -> -i)
                .sorted()
                .limit(2)
                .map(i -> -i)
                .reduce(1, (left, right) -> left * right);
    }
}


