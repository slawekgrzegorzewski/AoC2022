package com.adventofcode;

import com.adventofcode.day2.Game;
import com.adventofcode.input.Input;
import com.adventofcode.day2.RockPaperScissors;

import java.io.IOException;
import java.util.List;

public class Day2 {

    private final List<String[]> strategyGuide;

    public Day2() throws IOException {
        strategyGuide = Input.day2();
    }

    int part1() {
        Game game = new Game();
        strategyGuide
                .stream()
                .map(this::mapMovesForPart1)
                .forEach(r -> game.round(r[1], r[0]));
        return game.currentScore();
    }

    int part2() {
        Game game = new Game();
        strategyGuide
                .stream()
                .map(this::mapMovesForPart2)
                .forEach(r -> game.round(r[1], r[0]));
        return game.currentScore();
    }

    private RockPaperScissors[] mapMovesForPart1(String[] gameRound) {
        return new RockPaperScissors[]{
                mapOpponentShape(gameRound[0]),
                mapPlayersShape(gameRound[1])
        };
    }

    private RockPaperScissors[] mapMovesForPart2(String[] gameRound) {
        RockPaperScissors opponentsShape = mapOpponentShape(gameRound[0]);
        return new RockPaperScissors[]{
                opponentsShape,
                mapPlayersShape(gameRound[1], opponentsShape)
        };
    }

    private RockPaperScissors mapOpponentShape(String shape) {
        return switch (shape) {
            case "A" -> RockPaperScissors.ROCK;
            case "B" -> RockPaperScissors.PAPER;
            case "C" -> RockPaperScissors.SCISSORS;
            default -> throw new IllegalStateException();
        };
    }

    private RockPaperScissors mapPlayersShape(String shape) {
        return switch (shape) {
            case "X" -> RockPaperScissors.ROCK;
            case "Y" -> RockPaperScissors.PAPER;
            case "Z" -> RockPaperScissors.SCISSORS;
            default -> throw new IllegalStateException();
        };
    }

    private RockPaperScissors mapPlayersShape(String expectedOutcome, RockPaperScissors opponentsShape) {
        return switch (expectedOutcome) {
            case "X" -> opponentsShape.beats();
            case "Y" -> opponentsShape.drawsWith();
            case "Z" -> opponentsShape.losesTo();
            default -> throw new IllegalStateException();
        };
    }


}


