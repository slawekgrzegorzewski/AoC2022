package com.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day20Test {

    static Day20 day20;

    @BeforeEach
    public void init() throws IOException {
        day20 = new Day20();
    }

    @Test
    void testPart1() throws IOException {
        //10791 too high
        //1837 too low
        assertEquals(0L, day20.part1());
    }

    @Test
    void testPart2() throws IOException {
        assertEquals(0L, day20.part2());
    }
}