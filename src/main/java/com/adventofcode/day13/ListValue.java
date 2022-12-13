package com.adventofcode.day13;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ListValue implements Value {
    private final List<Value> value;

    public static ListValue parse(String line) {
        return parse(line.toCharArray());
    }

    private static ListValue parse(char[] chars) {
        final List<Value> values = new ArrayList<>();
        if (chars[0] != '[' || chars[chars.length - 1] != ']') throw new RuntimeException();
        for (int i = 1; i < chars.length; i++) {
            char currentValue = chars[i];
            if (',' == currentValue) {
                continue;
            }
            if (Character.isDigit(currentValue)) {
                int j = findIndexOfEndOfNumber(chars, i);
                values.add(SingleValue.parse(Arrays.copyOfRange(chars, i, j + 1)));
                i = j;
            }
            if ('[' == currentValue) {
                int j = findIndexOfClosingBracket(chars, i);
                values.add(ListValue.parse(Arrays.copyOfRange(chars, i, j + 1)));
                i = j;
            }
        }
        return new ListValue(values);
    }

    static int findIndexOfClosingBracket(char[] chars, int i) {
        int countOfOpeningBrackets = 1;
        while (countOfOpeningBrackets != 0) {
            i++;
            if (chars[i] == '[') countOfOpeningBrackets++;
            if (chars[i] == ']') countOfOpeningBrackets--;
        }
        return i;
    }

    private static int findIndexOfEndOfNumber(char[] chars, int j) {
        while (Character.isDigit(chars[j])) j++;
        return j - 1;
    }

    public ListValue(List<Value> value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "[" + value.stream().map(Object::toString).collect(Collectors.joining(",")) + ']';
    }

    @Override
    public int compareTo(@NotNull Value other) {
        if (other instanceof SingleValue) {
            return this.compareTo(new ListValue(List.of(other)));
        }
        ListValue otherList = (ListValue) other;
        int i = 0;
        for (; i < this.value.size(); i++) {
            if (otherList.value.size() > i) {
                int compare = this.value.get(i).compareTo(otherList.value.get(i));
                if (compare != 0)
                    return compare;
            } else {
                return 1;
            }
        }
        if (otherList.value.size() > i) return -1;
        return 0;
    }
}
