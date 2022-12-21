package com.adventofcode.day20;

import java.util.List;

public class CircularList {

    private final List<CircularListElement> elements;

    public CircularList(List<CircularListElement> elements) {
        this.elements = elements;
    }

    public static CircularList copyOf(List<Long> values) {
        List<CircularListElement> collect = values.stream().map(CircularListElement::new).toList();
        for (int i = 0; i < collect.size(); i++) {
            collect.get(i).setNext(collect.get(i == collect.size() - 1 ? 0 : i + 1));
            collect.get(i).setPrevious(collect.get(i == 0 ? collect.size() - 1 : i - 1));
        }
        return new CircularList(collect);
    }

    public void mix() {
        for (CircularListElement circularListElement : elements) {
            long steps = Math.abs(circularListElement.value()) % (elements.size() - 1);
            if (circularListElement.value() > 0) {
                for (int i = 0; i < steps; i++) {
                    circularListElement.moveNext();
                }
            } else {
                for (int i = 0; i < steps; i++) {
                    circularListElement.moveBack();
                }
            }
        }
    }

    public long sum() {
        CircularListElement circularListElement = elements.stream().filter(e -> e.value() == 0).findFirst().orElseThrow();
        long sum = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 1000; j++) {
                circularListElement = circularListElement.next();
            }
            sum += circularListElement.value();
        }
        return sum;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        CircularListElement zeroElement = elements.get(0);
        CircularListElement element = zeroElement;
        do {
            stringBuilder.append(element.value()).append(", ");
            element = element.next();
        } while (element != zeroElement);
        return stringBuilder.toString();
    }
}
