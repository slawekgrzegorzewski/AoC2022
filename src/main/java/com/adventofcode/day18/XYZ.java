package com.adventofcode.day18;

import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public record XYZ(long x, long y, long z) {

    public static final Pattern PATTERN = Pattern.compile("([0-9]*),([0-9]*),([0-9]*)");

    public static Stream<Function<XYZ, XYZ>> allDirectionsStream() {
        return Stream.of(XYZ::left, XYZ::right, XYZ::up, XYZ::down, XYZ::forward, XYZ::backward);
    }

    public static XYZ parse(String value) {
        Matcher matcher = PATTERN.matcher(value);
        if (!matcher.find()) {
            throw new RuntimeException();
        }
        return new XYZ(
                Long.parseLong(matcher.group(1)),
                Long.parseLong(matcher.group(2)),
                Long.parseLong(matcher.group(3))
        );
    }

    public XYZ left() {
        return new XYZ(x - 1, y, z);
    }

    public XYZ right() {
        return new XYZ(x + 1, y, z);
    }

    public XYZ down() {
        return new XYZ(x, y - 1, z);
    }

    public XYZ up() {
        return new XYZ(x, y + 1, z);
    }

    public XYZ backward() {
        return new XYZ(x, y, z - 1);
    }

    public XYZ forward() {
        return new XYZ(x, y, z + 1);
    }

}
