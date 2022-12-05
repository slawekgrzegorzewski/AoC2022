package com.adventofcode.day5;

public class Move {
    private final int from;
    private final int to;
    private final int count;

    public static Move parse(String s){
        String buffer = s.substring(5);
        int indexOfFirstSpace = buffer.indexOf(' ');
        int move = Integer.parseInt(buffer.substring(0, indexOfFirstSpace));
        buffer = buffer.substring(indexOfFirstSpace + 6);
        indexOfFirstSpace = buffer.indexOf(' ');
        int from = Integer.parseInt(buffer.substring(0, indexOfFirstSpace));
        buffer = buffer.substring(indexOfFirstSpace + 4);
        int to = Integer.parseInt(buffer);
        return new Move(from, to, move);
    }

    private Move(int from, int to, int count) {
        this.from = from;
        this.to = to;
        this.count = count;
    }

    public int from() {
        return from;
    }

    public int to() {
        return to;
    }

    public int count() {
        return count;
    }
}
