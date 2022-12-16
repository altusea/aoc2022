package org.example;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.example.common.Util.readToString;

class Day11 implements Day {

    @Override
    public int getDay() {
        return 11;
    }

    @Override
    public void solvePart1() throws IOException {
        System.out.printf("part1: %d\n", monkeyBusiness(20, true));
    }

    @Override
    public void solvePart2() throws IOException {
        System.out.printf("part2: %d\n", monkeyBusiness(10_000, false));
    }

    public static void main(String... args) throws Exception {
        Day day = new Day11();
        System.out.printf("day: %d%n", day.getDay());
        day.solvePart1();
        day.solvePart2();
    }

    static long monkeyBusiness(int rounds, boolean manageLevel) {
        var monkeys = parseInput();
        var commonMultiplier = monkeys.stream().mapToInt(Monkey::divisible).reduce(1, (a, b) -> a * b);
        long[] inspects = new long[monkeys.size()];

        for (int j = 0; j < rounds; j++) {
            for (int mi = 0; mi < monkeys.size(); mi++) {
                var m = monkeys.get(mi);
                for (var i : m.items()) {
                    inspects[mi]++;
                    var level = m.worryLevel(i) / (manageLevel ? 3 : 1) % commonMultiplier;
                    var target = level % m.divisible() == 0 ? m.trueTo() : m.falseTo();
                    monkeys.get(target).items().add(level);
                }
                m.items().clear();
            }
        }
        return Arrays.stream(inspects).boxed().sorted(Comparator.reverseOrder()).limit(2).reduce(1L, (a, b) -> a * b);
    }

    static List<Monkey> parseInput() {
        String inputFilePath = new File("").getAbsoluteFile() + File.separator + "input" + File.separator + "day11" + File.separator + "input";
        String content = readToString(new File(inputFilePath));

        return Arrays.stream(content.split("\n\n")).map(Monkey::parse).collect(Collectors.toList());
    }
}

record Monkey(List<Long> items, String[] operations, int divisible, int trueTo, int falseTo) {
    static Monkey parse(String input) {
        var lines = input.split("\n");
        var items = Arrays.stream(lines[1].split(": ")[1].split(",")).map(i -> Long.parseLong(i.trim())).collect(Collectors.toList());
        var operation = lines[2].split("= old ")[1].split(" ");
        var divisible = Integer.parseInt(lines[3].split("by ")[1]);
        var trueTo = Integer.parseInt(lines[4].split("monkey ")[1]);
        var falseTo = Integer.parseInt(lines[5].split("monkey ")[1]);
        return new Monkey(items, operation, divisible, trueTo, falseTo);
    }

    long worryLevel(long level) {
        var val = operations[1].equals("old") ? level : Integer.parseInt(operations[1]);
        return switch (operations[0]) {
            case "*" -> level * val;
            case "+" -> level + val;
            default -> throw new IllegalArgumentException();
        };
    }
}
