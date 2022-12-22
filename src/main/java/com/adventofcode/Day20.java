package com.adventofcode;

import com.adventofcode.day20.CircularList;
import com.adventofcode.day20.NumberWrapper;
import com.adventofcode.input.Input;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class Day20 {
    private final List<NumberWrapper> numbers;

    public Day20() throws IOException {
        numbers = Input.day20();
    }

    long part1() throws IOException {
        CircularList circularList = CircularList.copyOf(numbers.stream().map(NumberWrapper::value).collect(Collectors.toList()));
        circularList.mix();
        return circularList.sum();
    }

    long part2() throws IOException {
        CircularList circularList = CircularList.copyOf(numbers.stream().map(NumberWrapper::value).map(l -> l * 811589153L).collect(Collectors.toList()));
        for (int i = 0; i < 10; i++) {
            circularList.mix();
        }
        return circularList.sum();
    }

}