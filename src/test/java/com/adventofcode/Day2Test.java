package com.adventofcode;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day2Test {

    static Day2 day2;

    @BeforeAll
    public static void init() throws IOException {
        day2 = new Day2();
    }

    @Test
    void testPart1() {
        int part1Result = day2.part1();
        assertEquals(10941, part1Result);
    }

    @Test
    void testPart2() {
        int part2Result = day2.part2();
        assertEquals(13071, part2Result);
    }
}