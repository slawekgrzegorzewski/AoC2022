package com.adventofcode;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day1Test {

    static Day1 day1;

    @BeforeAll
    public static void init() throws IOException {
        day1 = new Day1();
    }

    @Test
    void testPart1() {
        int part1Result = day1.part1();
        System.out.println("part1t = " + part1Result);
        assertEquals(70720, part1Result);
    }

    @Test
    void testPart2() {
        int part2Result = day1.part2();
        System.out.println("part2 = " + part2Result);
        assertEquals(207148, part2Result);
    }
}