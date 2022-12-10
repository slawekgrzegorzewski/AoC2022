package com.adventofcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day10Test {

    static Day10 day10;

    @BeforeEach
    public void init() throws IOException {
        day10 = new Day10();
    }

    @Test
    void testPart1() {
        long part1Result = day10.part1();
        assertEquals(13220L, part1Result);
    }

    @Test
    void testPart2() {
        String part2Result = day10.part2();
        String expected = """
                ###..#..#..##..#..#.#..#.###..####.#..#.
                #..#.#..#.#..#.#.#..#..#.#..#.#....#.#..
                #..#.#..#.#..#.##...####.###..###..##...
                ###..#..#.####.#.#..#..#.#..#.#....#.#..
                #.#..#..#.#..#.#.#..#..#.#..#.#....#.#..
                #..#..##..#..#.#..#.#..#.###..####.#..#.""";
        assertEquals(expected, part2Result);
    }
}