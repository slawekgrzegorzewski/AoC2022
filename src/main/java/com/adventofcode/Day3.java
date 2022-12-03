package com.adventofcode;

import com.adventofcode.input.Input;

import java.io.IOException;
import java.util.List;
import java.util.stream.IntStream;

public class Day3 {


    private final List<String> input;

    public Day3() throws IOException {
        input = Input.day3("/day3");
    }

    int part1() {
        return input.stream().mapToInt(this::findCommonPriorityInSingleLine).sum();
    }

    int part2() {
        return IntStream.iterate(0, i -> i < input.size(), i -> i + 3)
                .mapToObj(i -> input.subList(i, i + 3))
                .mapToInt(this::findCommonCharInLines)
                .sum();
    }

    private int findCommonPriorityInSingleLine(String line) {
        boolean[] bitMapIndex = new boolean[52];
        char[] chars = line.toCharArray();
        for (int i = 0; i < chars.length / 2; i++) {
            bitMapIndex[getPriority(chars[i]) - 1] = true;
        }
        for (int i = chars.length / 2; i < chars.length; i++) {
            int priority = getPriority(chars[i]);
            if (bitMapIndex[priority - 1]) return priority;
        }
        return 0;
    }

    private int findCommonCharInLines(List<String> lines) {
        boolean[][] bitMapIndices = new boolean[lines.size()][52];
        for (int i = 0; i < lines.size(); i++) {
            for (char c : lines.get(i).toCharArray()) {
                bitMapIndices[i][getPriority(c) - 1] = true;
            }
        }
        for (int priority = 1; priority <= 52; priority++) {
            boolean existsInAllLines = true;
            for (int i = 0; i < lines.size(); i++) {
                existsInAllLines = existsInAllLines && bitMapIndices[i][priority - 1];
            }
            if (existsInAllLines)
                return priority;
        }
        return 0;
    }

    private int getPriority(char c) {
        if (c >= 'a' && c <= 'z') {
            return c - 'a' + 1;
        }
        return c - 'A' + 27;
    }
}


