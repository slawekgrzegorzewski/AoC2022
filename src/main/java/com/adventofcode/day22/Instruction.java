package com.adventofcode.day22;

import com.adventofcode.day14.XY;

import java.util.List;
import java.util.Map;

public record Instruction(Map<XY, Character> map, List<String> instructions) {

}
