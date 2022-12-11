package com.adventofcode.day11;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MonkeyBehaviour {
    private final LinkedList<Item> items;
    private final Operation operation;
    private final Predicate<Number> test;
    private final int ifTrue;
    private final int ifFalse;
    private final int factorForWorryLevel;

    public static MonkeyBehaviour parse(List<String> input, int worryLevelManagingFactor, int initialMonkeyIndex) {
        Function<Integer, Number> numberCreator = worryLevelManagingFactor == 1
                ? DividersNumber::new
                : RegularNumber::new;
//        Function<Integer, Number> numberCreator = NumberForDebugging::new;
        LinkedList<Item> startingItems = Arrays.stream(input.get(1)
                        .replace("  Starting items:", "")
                        .trim()
                        .split(","))
                .map(v -> new Item(numberCreator.apply(Integer.parseInt(v.trim())), initialMonkeyIndex))
                .collect(Collectors.toCollection(LinkedList::new));

        String operation = input.get(2).trim().replace("Operation:", "").trim();

        int divisableBy = Integer.parseInt(input.get(3).trim().replace("Test: divisible by ", "").trim());
        Predicate<Number> test = n -> n.isDivisibleBy(divisableBy);

        int ifTrue = Integer.parseInt(input.get(4).trim().replace("If true: throw to monkey ", "").trim());

        int ifFalse = Integer.parseInt(input.get(5).trim().replace("If false: throw to monkey ", "").trim());

        return new MonkeyBehaviour(startingItems, new Operation(operation), test, ifTrue, ifFalse, worryLevelManagingFactor);
    }

    private MonkeyBehaviour(LinkedList<Item> items, Operation operation, Predicate<Number> test, int ifTrue, int ifFalse, int factorForWorryLevel) {
        this.items = items;
        this.operation = operation;
        this.test = test;
        this.ifTrue = ifTrue;
        this.ifFalse = ifFalse;
        this.factorForWorryLevel = factorForWorryLevel;
    }

    public LinkedList<Item> startingItems() {
        return items;
    }

    public void throwToMonkey(Map<Integer, MonkeyBehaviour> monkeys) {
        while (!items.isEmpty()) {
            Item item = items.removeFirst();
            operation.perform(item.value);
            if (factorForWorryLevel != 1)
                item.value.divide(factorForWorryLevel);
            int nextMonkey = test.test(item.value) ? ifTrue : ifFalse;
            item.visitedMonkeys.add(nextMonkey);
            MonkeyBehaviour monkeyBehaviour = monkeys.get(nextMonkey);
            monkeyBehaviour.items.addLast(item);
        }
    }

}
