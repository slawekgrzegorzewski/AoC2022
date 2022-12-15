package com.adventofcode.day4;

import com.adventofcode.input.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public class Range implements Comparable<Range> {
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

    public static boolean rangesDontContain(Set<Range> excludes, int value) {
        return excludes.stream().noneMatch(r -> r.containsValue(value));
    }

    public Range(int fromInclusive, int toInclusive) {
        this.fromInclusive = fromInclusive;
        this.toInclusive = toInclusive;
    }

    public int fromInclusive() {
        return fromInclusive;
    }

    public int toInclusive() {
        return toInclusive;
    }

    public int size() {
        return toInclusive - fromInclusive + 1;
    }

    public boolean containsValue(int value) {
        return getCommonPart(new Range(value, value)).isPresent();
    }

    public boolean overlap(Range other) {
        return getCommonPart(other).isPresent();
    }

    public Optional<Range> getCommonPart(Range other) {
        int from = Math.max(fromInclusive, other.fromInclusive);
        int to = Math.min(toInclusive, other.toInclusive);
        if (to >= from) {
            return Optional.of(new Range(from, to));
        }
        return Optional.empty();
    }

    public Range join(Range other) {
        getCommonPart(other).orElseThrow();
        return new Range(Math.min(this.fromInclusive, other.fromInclusive), Math.max(this.toInclusive, other.toInclusive));
    }

    public boolean isCorrect() {
        return this.fromInclusive <= this.toInclusive;
    }

    public Set<Range> addToSetOfRangesAndCompact(Set<Range> excludes) {
        Set<Range> result = new HashSet<>();
        if (excludes.isEmpty()) {
            result.add(this);
        } else {
            Range newRange = this;
            for (Range r : excludes) {
                if (r.overlap(newRange))
                    newRange = r.join(newRange);
                else result.add(r);
            }
            result.add(newRange);
        }
        return result;
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

    @Override
    public int compareTo(@NotNull Range o) {
        if (getCommonPart(o).isPresent()) throw new RuntimeException();
        return this.fromInclusive - o.toInclusive;
    }
}
