package com.adventofcode.day2;

import java.util.Map;

public enum RockPaperScissors {

    ROCK,
    PAPER,
    SCISSORS;

    private static final Map<RockPaperScissors, RockPaperScissors> BEATS = Map.of(
            ROCK, SCISSORS,
            PAPER, ROCK,
            SCISSORS, PAPER
    );

    public RockPaperScissors beats() {
        return BEATS.get(this);
    }

    public RockPaperScissors drawsWith() {
        return this;
    }

    public RockPaperScissors losesTo() {
        return BEATS.entrySet().stream().filter(e -> e.getValue().equals(this)).map(Map.Entry::getKey).findFirst().orElseThrow();
    }


}

