package com.adventofcode.day2;

public class Game {

    int score = 0;

    public void round(RockPaperScissors player, RockPaperScissors opponent) {
        this.score += getPointForShape(player) + getPointsForResult(player, opponent);
    }

    private int getPointForShape(RockPaperScissors player) {
        return switch (player) {
            case ROCK -> 1;
            case PAPER -> 2;
            case SCISSORS -> 3;
        };
    }

    private int getPointsForResult(RockPaperScissors player, RockPaperScissors opponent) {
        if (player.beats().equals(opponent)) {
            return 6;
        }
        if (player.drawsWith().equals(opponent)) {
            return 3;
        }
        return 0;
    }

    public int currentScore() {
        return score;
    }
}
