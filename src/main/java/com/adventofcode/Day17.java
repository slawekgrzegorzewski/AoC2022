package com.adventofcode;

import com.adventofcode.input.XY;
import com.adventofcode.day17.Tile;
import com.adventofcode.input.Input;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static com.adventofcode.day17.Tile.*;

public class Day17 {

    public static final int WIDTH = 7;
    private final List<Character> input;

    public Day17() throws IOException {
        input = Input.day17();
//        checkShapes();
    }

    long part1() throws IOException {
        return simulate(2022);
    }

    long part2() throws IOException {
        return simulate(1000000000000L);
    }

    private long simulate(long numberOfTiles) {
        long height = 0;
        LinkedList<Character> blows = new LinkedList<>(input);
        Map<Long, Map<Long, Character>> chamber = new HashMap<>();
        Tile[] tileSource = new Tile[]{LINE, PLUS, L, I, SQUARE};
        long previousI = 0;
        for (long i = 0; i < numberOfTiles; i++) {
            Tile tile = tileSource[(int) (i % 5)];
            XY leftBottom = new XY(height(chamber) + 3, 2);
            while (true) {
                if (blows.isEmpty()) blows = new LinkedList<>(input);
                char blow = blows.removeFirst();
                if ('<' == blow) {
                    if ((leftBottom.y() > 0) && tile.canMoveToPositionOfLeftBottomCorner(chamber, leftBottom.moveLeft())) {
                        leftBottom = leftBottom.moveLeft();
                    }
                }
                if ('>' == blow) {
                    if ((leftBottom.y() + tile.width() < WIDTH) && tile.canMoveToPositionOfLeftBottomCorner(chamber, leftBottom.moveRight())) {
                        leftBottom = leftBottom.moveRight();
                    }
                }
                if (leftBottom.x() > 0 && tile.canMoveToPositionOfLeftBottomCorner(chamber, leftBottom.moveDown())) {
                    leftBottom = leftBottom.moveDown();
                } else {
                    long fullLine = tile.placeInChamber(chamber, leftBottom);
                    if (fullLine > -1) {
                        if (fullLine == 389 || fullLine == 2666) {
                            long diff = i - previousI;
                            previousI = i;
                            Map<Long, Map<Long, Character>> newChamber = new HashMap<>();
                            for (long line = fullLine + 1; line < chamber.size(); line++) {
                                newChamber.put(line - fullLine - 1, chamber.get(line));
                            }
                            long removedLines = chamber.size() - newChamber.size();
                            chamber = newChamber;
                            height += removedLines;

                            System.out.println(diff + ":" + removedLines);
                        }
                    }
                    break;
                }
            }
        }
        return height + chamber.size();
    }


    private void print(Map<Long, Map<Long, Character>> chamber) {
        for (long x = height(chamber); x >= 0; x--) {
            for (long y = 0; y < WIDTH; y++)
                System.out.print(chamber.getOrDefault(x, Map.of()).getOrDefault(y, '.'));
            System.out.println();
        }
        System.out.println("-------");
        System.out.println();
    }

    private long height(Map<Long, Map<Long, Character>> chamber) {
        return chamber.keySet().stream().mapToLong(l -> l + 1).max().orElse(0);
    }

    private void checkShapes() {
        Tile[] tileSource = new Tile[]{LINE, PLUS, L, I, SQUARE};
        for (int i = 0; i < 5; i++) {
            Map<Long, Map<Long, Character>> chamber = new HashMap<>();
            Tile tile = tileSource[i % 5];
            tile.placeInChamber(chamber, new XY(0, 0));
            print(chamber);
        }
    }
}