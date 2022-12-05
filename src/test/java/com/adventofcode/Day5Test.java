package com.adventofcode;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day5Test {

    static Day5 day5;

    @BeforeAll
    public static void init() throws IOException {
        day5 = new Day5();
    }

    @Test
    void testPart1() {
        String part1Result = day5.part1();
        assertEquals("TPGVQPFDH", part1Result);
    }

    @Test
    void testPart2() {
        String part2Result = day5.part2();
        assertEquals("DMRDFRHHH", part2Result);
    }
}