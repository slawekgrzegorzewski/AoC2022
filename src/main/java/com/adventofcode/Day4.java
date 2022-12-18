package com.adventofcode;

import com.adventofcode.day4.Range;
import com.adventofcode.input.Input;
import com.adventofcode.input.Pair;

import java.io.IOException;
import java.util.List;

public class Day4 {

    private final List<Pair<Range, Range>> input;

    public Day4() throws IOException {
        input = Input.day4();
    }

    int part1() {
        return input.stream()
                .filter(Range::isOneSubsetOfOther)
                .mapToInt(value -> 1)
                .sum();
    }

    int part2() {
        return input.stream()
                .filter(ranges -> ranges.first().overlap(ranges.second()))
                .mapToInt(value -> 1)
                .sum();
    }
}


