package com.adventofcode.day11;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MonkeyBehaviour {
    private final LinkedList<Item> startingItems;
    private final Operation operation;
    private final Predicate<BigDecimal> test;
    private final int ifTrue;
    private final int ifFalse;
    private final int worryLevelManagingFactor;

    public static MonkeyBehaviour parse(List<String> input, int worryLevelManagingFactor, int initialMonkeyIndex) {
        LinkedList<Item> startingItems = Arrays.stream(input.get(1)
                        .replace("  Starting items:", "")
                        .trim()
                        .split(","))
                .map(v -> new Item(new BigDecimal(v.trim()), initialMonkeyIndex))
                .collect(Collectors.toCollection(LinkedList::new));
        String operation = input.get(2).trim().replace("Operation:", "").trim();
        BigDecimal divisableBy = new BigDecimal(input.get(3).trim().replace("Test: divisible by ", "").trim());
        Predicate<BigDecimal> test = bd -> bd.divideAndRemainder(divisableBy)[1].equals(BigDecimal.ZERO);
        int ifTrue = Integer.parseInt(input.get(4).trim().replace("If true: throw to monkey ", "").trim());
        int ifFalse = Integer.parseInt(input.get(5).trim().replace("If false: throw to monkey ", "").trim());
        return new MonkeyBehaviour(startingItems, new Operation(operation), test, ifTrue, ifFalse, worryLevelManagingFactor);
    }

    private MonkeyBehaviour(LinkedList<Item> startingItems, Operation operation, Predicate<BigDecimal> test, int ifTrue, int ifFalse, int worryLevelManagingFactor) {
        this.startingItems = startingItems;
        this.operation = operation;
        this.test = test;
        this.ifTrue = ifTrue;
        this.ifFalse = ifFalse;
        this.worryLevelManagingFactor = worryLevelManagingFactor;
    }

    public LinkedList<Item> startingItems() {
        return startingItems;
    }

    public Operation operation() {
        return operation;
    }

    public void throwToMonkey(Map<Integer, MonkeyBehaviour> monkeys) {
        while (!startingItems.isEmpty()) {
            Item item = startingItems.removeFirst();
            item.value = operation.perform(item.value).divideToIntegralValue(BigDecimal.valueOf(worryLevelManagingFactor));
            int nextMonkey = test.test(item.value) ? ifTrue : ifFalse;
            item.visitedMonkeys.add(nextMonkey);
            MonkeyBehaviour monkeyBehaviour = monkeys.get(nextMonkey);
            monkeyBehaviour.startingItems.addLast(item);
        }
    }

}
