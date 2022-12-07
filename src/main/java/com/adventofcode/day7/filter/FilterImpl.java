package com.adventofcode.day7.filter;

import com.adventofcode.day7.Dir;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class FilterImpl implements Filter {

    @Override
    public List<Dir> filter(Dir dir, Predicate<Dir> filter) {
        List<Dir> result = new ArrayList<>();
        if (filter.test(dir)) {
            result.add(dir);
        }
        dir.children().stream()
                .filter(Dir.class::isInstance)
                .map(Dir.class::cast)
                .flatMap(d -> filter(d, filter).stream())
                .forEach(result::add);
        return result;
    }
}
