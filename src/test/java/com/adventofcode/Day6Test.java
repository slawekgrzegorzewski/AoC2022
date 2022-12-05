
package com.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day6Test {

    static Day6 day6;

    @BeforeEach
    public void init() throws IOException {
        day6 = new Day6();
    }

    @Test
    void testPart1() {
        String part1Result = day6.part1();
        assertEquals("", part1Result);
    }

    @Test
    void testPart2() {
        String part2Result = day6.part2();
        assertEquals("", part2Result);
    }
}