
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
        int part1Result = day6.part1();
        assertEquals(1538, part1Result);
    }

    @Test
    void testPart2() {
        int part2Result = day6.part2();
        assertEquals(2315, part2Result);
    }
}