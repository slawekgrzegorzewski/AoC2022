package com.adventofcode;

import com.adventofcode.input.Input;
import com.adventofcode.input.Pair;
import com.google.common.primitives.Booleans;

import java.io.IOException;
import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.IntStream;

public class Day8 {

    private final int[][] mapTree;
    private final boolean[][] mapOfHiddenTrees;


    public Day8() throws IOException {
        mapTree = Input.day8("/day8");
        int[][] maxHeightFromRight = new int[mapTree.length][mapTree[0].length];
        int[][] maxHeightFromBottom = new int[mapTree.length][mapTree[0].length];
        mapOfHiddenTrees = new boolean[mapTree.length][mapTree[0].length];
        for (int x = maxHeightFromRight.length - 1; x >= 0; x--) {
            int maxHeight = -1;
            for (int y = maxHeightFromRight[0].length - 1; y >= 0; y--) {
                maxHeightFromRight[x][y] = maxHeight;
                if (maxHeight < mapTree[x][y]) {
                    maxHeight = mapTree[x][y];
                }
            }
        }
        for (int x = maxHeightFromBottom.length - 1; x >= 0; x--) {
            int maxHeight = -1;
            for (int y = maxHeightFromBottom[0].length - 1; y >= 0; y--) {
                maxHeightFromBottom[y][x] = maxHeight;
                if (maxHeight < mapTree[y][x]) {
                    maxHeight = mapTree[y][x];
                }
            }
        }
        for (int x = 0; x < mapTree.length; x++) {
            int maxHeight = -1;
            for (int y = 0; y < mapTree[0].length; y++) {
                boolean visibleFromLeft = mapTree[x][y] > maxHeight;
                boolean visibleFromRight = mapTree[x][y] > maxHeightFromRight[x][y];
                mapOfHiddenTrees[x][y] = !visibleFromLeft && !visibleFromRight;
                if (maxHeight < mapTree[x][y]) {
                    maxHeight = mapTree[x][y];
                }
            }
        }
        for (int x = 0; x < mapTree.length; x++) {
            int maxHeight = -1;
            for (int y = 0; y < mapTree[0].length; y++) {
                boolean visibleFromTop = mapTree[y][x] > maxHeight;
                boolean visibleFromBottom = mapTree[y][x] > maxHeightFromBottom[y][x];
                mapOfHiddenTrees[y][x] = mapOfHiddenTrees[y][x] && (!visibleFromTop && !visibleFromBottom);
                if (maxHeight < mapTree[y][x]) {
                    maxHeight = mapTree[y][x];
                }
            }
        }
    }

    long part1() {
        return Arrays.stream(mapOfHiddenTrees)
                .flatMap(line -> Booleans.asList(line).stream())
                .mapToInt(b -> !b ? 1 : 0)
                .sum();
    }

    long part2() {
        return IntStream.range(1, mapTree.length - 1)
                .mapToObj(x -> IntStream.range(1, mapTree.length - 1).mapToObj(y -> new Pair<>(x, y)))
                .flatMap(Function.identity())
                .mapToLong(p -> this.getScore(p.first(), p.second()))
                .max()
                .orElseThrow();
    }

    private int getScore(int x, int y) {
        int shouldBeLowerThan = mapTree[x][y];
        int upScore = 0;
        for (int x1 = x - 1; x1 >= 0; x1--) {
            upScore++;
            if (mapTree[x1][y] >= shouldBeLowerThan) {
                break;
            }
        }
        int downScore = 0;
        for (int x1 = x + 1; x1 < mapTree.length; x1++) {
            downScore++;
            if (mapTree[x1][y] >= shouldBeLowerThan) {
                break;
            }
        }
        int leftScore = 0;
        for (int y1 = y - 1; y1 >= 0; y1--) {
            leftScore++;
            if (mapTree[x][y1] >= shouldBeLowerThan) {
                break;
            }
        }
        int rightScore = 0;
        for (int y1 = y + 1; y1 < mapTree.length; y1++) {
            rightScore++;
            if (mapTree[x][y1] >= shouldBeLowerThan) {
                break;
            }
        }
        return upScore * downScore * leftScore * rightScore;
    }

}


