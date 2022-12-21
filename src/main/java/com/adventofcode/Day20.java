package com.adventofcode;

import com.adventofcode.day20.NumberWrapper;
import com.adventofcode.input.Input;

import java.io.IOException;
import java.util.*;

public class Day20 {
    private final List<NumberWrapper> numbers;

    public Day20() throws IOException {
        numbers = Input.day20();
    }

    long part1() throws IOException {
        System.out.println(numbers);
        List<NumberWrapper> decrypted = new ArrayList<>(numbers);
        for (NumberWrapper valueToMove : numbers) {
            int indexOfElementToMove = decrypted.indexOf(valueToMove);
            int steps = valueToMove.value() % decrypted.size();
            int nextIndex = indexOfElementToMove + steps;

            if (steps > 0) {
//                if (nextIndex == decrypted.size())
//                    nextIndex = 0;
                if (nextIndex >= decrypted.size())
                    nextIndex -= (decrypted.size() - 1);
            }
            if (steps < 0) {
//                if (nextIndex == 0)
//                    nextIndex = decrypted.size() - 1;
                if (nextIndex < 0)
                    nextIndex = decrypted.size() + nextIndex - 1;
            }

            if (indexOfElementToMove != nextIndex) {
                moveElement(decrypted, indexOfElementToMove, nextIndex);
            }
            System.out.println(decrypted);
        }
        int _0index = decrypted.indexOf(new NumberWrapper(0));
        int _1000th = decrypted.get((1000 + _0index) % decrypted.size()).value();
        int _2000th = decrypted.get((2000 + _0index) % decrypted.size()).value();
        int _3000th = decrypted.get((3000 + _0index) % decrypted.size()).value();
        return _1000th + _2000th + _3000th;
    }

    private void moveElement(List<NumberWrapper> list, int from, int to) {
        NumberWrapper valueToMove = list.get(from);
        if (from > to) {
            for (int i = from; i > to; i--) {
                list.set(i, list.get(i - 1));
            }
        } else if (to > from) {
            for (int i = from; i < to; i++) {
                list.set(i, list.get(i + 1));
            }
        } else {
            throw new RuntimeException();
        }
        list.set(to, valueToMove);
    }

    long part2() throws IOException {
        return 0L;
    }

}