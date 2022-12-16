package org.example;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static org.example.common.Util.readToString;

public class Day01 implements Day {

    public static void main(String[] args) throws IOException {
        Day day = new Day01();
        System.out.printf("day: %d%n", day.getDay());
        day.solvePart1();
        day.solvePart2();
    }

    @Override
    public int getDay() {
        return 1;
    }

    @Override
    public void solvePart1() {
        List<Integer> sum = parseInput();
        System.out.println(sum.stream().max(Comparator.naturalOrder()).get());
    }

    @Override
    public void solvePart2() {
        List<Integer> sum = parseInput();
        System.out.println(sum.stream().sorted(Comparator.reverseOrder()).limit(3).reduce(0, Integer::sum));
    }

    private static List<Integer> parseInput() {
        String inputFilePath = new File("").getAbsoluteFile() + File.separator + "input" + File.separator + "day01" + File.separator + "input";

        String content = readToString(new File(inputFilePath));

        List<Integer> sum = new ArrayList<>();
        for (String as : content.split("\n\n")) {
            sum.add(Arrays.stream(as.split("\n")).map(Integer::parseInt).reduce(0, Integer::sum));
        }

        return sum;
    }
}
