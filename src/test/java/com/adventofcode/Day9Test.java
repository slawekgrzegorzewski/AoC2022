package com.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day9Test {

    static Day9 day9;

    @BeforeEach
    public void init() throws IOException {
        day9 = new Day9();
    }

    @Test
    void testPart1() {
        long part1Result = day9.part1();
        assertEquals(0L, part1Result);
    }

    @Test
    void testPart2() {
        long part2Result = day9.part2();
        assertEquals(0L, part2Result);
    }
}