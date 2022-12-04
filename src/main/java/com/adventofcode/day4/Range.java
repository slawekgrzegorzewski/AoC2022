package com.adventofcode.day4;

import com.adventofcode.input.Pair;

import java.util.Objects;
import java.util.Optional;

public class Range {
    private final int fromInclusive;
    private final int toInclusive;

    public static Range parse(String s) {
        String[] range = s.split("-");
        return new Range(Integer.parseInt(range[0]), Integer.parseInt(range[1]));
    }

    public static boolean isOneSubsetOfOther(Pair<Range, Range> ranges) {
        return ranges.first().getCommonPart(ranges.second())
                .map(ranges::contains)
                .orElse(false);
    }

    private Range(int fromInclusive, int toInclusive) {
        this.fromInclusive = fromInclusive;
        this.toInclusive = toInclusive;
    }

    public Optional<Range> getCommonPart(Range other) {
        int from = Math.max(fromInclusive, other.fromInclusive);
        int to = Math.min(toInclusive, other.toInclusive);
        if (to >= from) {
            return Optional.of(new Range(from, to));
        }
        return Optional.empty();
    }

    public boolean overlap(Range other) {
        return getCommonPart(other).isPresent();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Range range = (Range) o;
        return fromInclusive == range.fromInclusive && toInclusive == range.toInclusive;
    }

    @Override
    public int hashCode() {
        return Objects.hash(fromInclusive, toInclusive);
    }

    @Override
    public String toString() {
        return "[" + fromInclusive + "-" + toInclusive + ']';
    }
}
