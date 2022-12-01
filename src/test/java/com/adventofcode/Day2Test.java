package com.adventofcode;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class Day2Test {

    static Day2 day2;

    @BeforeAll
    public static void init() throws IOException {
        day2 = new Day2();
    }

    @Test
    void testPart1() {
        int part1Result = day2.part1();
        System.out.println("part1 = " + part1Result);
    }

    @Test
    void testPart2() {
        int part2Result = day2.part2();
        System.out.println("part2 = " + part2Result);
    }
}