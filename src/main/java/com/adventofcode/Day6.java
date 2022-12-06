package com.adventofcode;

import com.adventofcode.input.Input;

import java.io.IOException;
import java.util.OptionalInt;

import static java.util.OptionalInt.empty;
import static java.util.OptionalInt.of;

public class Day6 {

    private final String input;

    public Day6() throws IOException {
        input = Input.day6("/day6");
    }

    int part1() {
        return findPositionOfNDistinctSequence(input.toCharArray(), 4)
                .orElseThrow() + 4;
    }

    int part2() {
        return findPositionOfNDistinctSequence(input.toCharArray(), 14)
                .orElseThrow() + 14;
    }

    private static OptionalInt findPositionOfNDistinctSequence(char[] chars, int n) {
        if (n > 26) return empty();
        for (int i = 0; i < chars.length - n + 1; i++) {
            if (consistOfNUniqueChars(chars, n, i)) {
                return of(i);
            }
        }
        return empty();
    }

    private static boolean consistOfNUniqueChars(char[] chars, int N, int from) {
        boolean[] radix = new boolean[26];
        for (int step = 0; step < N; step++) {
            int index = chars[from + step] - 97;
            if (radix[index]) {
                return false;
            }
            radix[index] = true;
        }
        return true;
    }
}


