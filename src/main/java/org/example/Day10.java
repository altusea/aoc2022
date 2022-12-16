package org.example;

import kala.range.Ranges;
import kala.range.primitive.IntRange;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.example.common.Util.readToString;

public class Day10 implements Day {

    public static void p1(List<Instruction> instructionList) {

        long cycle = 0, x_register = 1, signal_strength = 0;
        Set<Long> measuredAt = new HashSet<>();
        for (long i = 20; i <= 220; i += 40) {
            measuredAt.add(i);
        }

        for (Instruction instruction : instructionList) {
            int cycles = instruction.getCycles();
            for (int i = 0; i < cycles; i++) {
                cycle += 1;
                if (measuredAt.contains(cycle)) {
                    signal_strength += cycle * x_register;
                }
            }
            if (instruction instanceof AddX) {
                x_register += ((AddX) instruction).v;
            }
        }

        System.out.println(signal_strength);
    }

    public static void p2(List<Instruction> instructionList) {

        final int WIDTH = 40;

        long cycle = 0;
        int x_register = 1;

        StringBuilder crt = new StringBuilder();

        for (Instruction instruction : instructionList) {
            int cycles = instruction.getCycles();
            IntRange spritePositions = Ranges.closed(x_register - 1, x_register + 1);
            for (int i = 0; i < cycles; i++) {

                int positionToDraw = (int) (cycle % WIDTH);
                if (spritePositions.contains(positionToDraw)) {
                    crt.append('#');
                } else {
                    crt.append('.');
                }

                if (positionToDraw == WIDTH - 1) {
                    crt.append('\n');
                }
                cycle += 1;
            }
            if (instruction instanceof AddX) {
                x_register += ((AddX) instruction).v;
            }

        }

        System.out.println(crt);
    }

    private static List<Instruction> parseInput() {
        String inputFilePath = new File("").getAbsoluteFile() + File.separator + "input" + File.separator + "day10" + File.separator + "input";
        String content = readToString(new File(inputFilePath));
        String[] lines = content.split("\n");

        List<Instruction> instructions = new ArrayList<>();
        for (String line : lines) {
            if (line.startsWith("n")) {
                // noop
                instructions.add(new Noop());
            } else {
                String[] info = line.split(" ");
                int v = Integer.parseInt(info[1]);
                instructions.add(new AddX(v));
            }
        }

        return instructions;
    }

    public static void main(String[] args) throws IOException {
        Day day = new Day10();
        System.out.printf("day: %d%n", day.getDay());
        day.solvePart1();
        day.solvePart2();
    }

    @Override
    public int getDay() {
        return 10;
    }

    @Override
    public void solvePart1() throws IOException {
        List<Instruction> instructions = parseInput();
        p1(instructions);
    }

    @Override
    public void solvePart2() throws IOException {
        List<Instruction> instructions = parseInput();
        p2(instructions); // BZPAJELK
    }
}

interface Instruction {
    int getCycles();
}

class Noop implements Instruction {

    @Override
    public int getCycles() {
        return 1;
    }
}

class AddX implements Instruction {

    int v;

    AddX(int v) {
        this.v = v;
    }

    @Override
    public int getCycles() {
        return 2;
    }
}
