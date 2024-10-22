
package com.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day7Test {

    static Day7 day7;

    @BeforeEach
    public void init() throws IOException {
        day7 = new Day7();
    }

    @Test
    void testPart1() {
        long part1Result = day7.part1();
        assertEquals(1306611L, part1Result);
    }

    @Test
    void testPart2() {
        long part2Result = day7.part2();
        assertEquals(13210366L, part2Result);
    }
}