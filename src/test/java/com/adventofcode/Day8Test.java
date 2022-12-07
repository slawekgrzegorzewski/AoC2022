package com.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day8Test {

    static Day8 day8;

    @BeforeEach
    public void init() throws IOException {
        day8 = new Day8();
    }

    @Test
    void testPart1() {
        long part1Result = day8.part1();
        assertEquals(0L, part1Result);
    }

    @Test
    void testPart2() {
        long part2Result = day8.part2();
        assertEquals(0L, part2Result);
    }
}