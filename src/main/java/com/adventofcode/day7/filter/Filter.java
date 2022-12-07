package com.adventofcode.day7.filter;

import com.adventofcode.day7.Dir;

import java.util.List;
import java.util.function.Predicate;

public interface Filter {
    List<Dir> filter(Dir dir, Predicate<Dir> filter);
}
