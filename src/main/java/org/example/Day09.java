package org.example;

import kala.collection.immutable.ImmutableArray;
import kala.collection.mutable.MutableArrayList;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

public class Day09 implements Day {

    @Override
    public int getDay() {
        return 9;
    }

    @Override
    public void solvePart1() throws IOException {
        var rope = new Rope(2);
        ImmutableArray<Move> moves = parseInput();
        for (Move move : moves) {
            rope.move(move);
        }
        System.out.println(rope.seen.size());
    }

    @Override
    public void solvePart2() throws IOException {
        var rope = new Rope(10);
        ImmutableArray<Move> moves = parseInput();
        for (Move move : moves) {
            rope.move(move);
        }
        System.out.println(rope.seen.size());
    }

    public static void main(String[] args) throws IOException {
        Day day = new Day09();
        System.out.printf("day: %d%n", day.getDay());
        day.solvePart1();
        day.solvePart2();
    }

    private static final class Rope {

        private final Position[] knots;
        private final Set<Position> seen = new HashSet<>();

        private Rope(int N) {
            this.knots = new Position[N];
            for (int i = 0; i < N; i++) {
                this.knots[i] = new Position(0, 0);
            }
            seen.add(this.knots[N - 1]);
        }

        private void move(Move move) {
            var distance = move.steps();
            for (int i = 0; i < distance; i++) {
                switch (move.direction()) {
                    case UP -> move(0, 1);
                    case DOWN -> move(0, -1);
                    case LEFT -> move(-1, 0);
                    case RIGHT -> move(1, 0);
                }
            }
        }

        private void move(int dx, int dy) {
            knots[0] = knots[0].move(dx, dy);
            for (int i = 1; i < knots.length; i++) {
                dx = knots[i - 1].x() - knots[i].x();
                dy = knots[i - 1].y() - knots[i].y();
                if (knots[i - 1].x() == knots[i].x() && Math.abs(dy) > 1) {
                    knots[i] = knots[i].move(0, (int) Math.signum(dy));
                } else if (knots[i - 1].y() == knots[i].y() && Math.abs(dx) > 1) {
                    knots[i] = knots[i].move((int) Math.signum(dx), 0);
                } else if (Math.abs(dx) > 1 || Math.abs(dy) > 1) {
                    knots[i] = knots[i].move((int) Math.signum(dx), (int) Math.signum(dy));
                }
            }
            seen.add(knots[knots.length - 1]);
        }
    }

    private static ImmutableArray<Move> parseInput() throws IOException {
        String inputFilePath = new File("").getAbsoluteFile() + File.separator + "input" + File.separator + "day09" + File.separator + "input";
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(inputFilePath)));
        String line;

        MutableArrayList<Move> moves = new MutableArrayList<>();

        while ((line = br.readLine()) != null) {
            var direction = line.charAt(0);
            var distance = Integer.parseInt(line.substring(2));
            switch (direction) {
                case 'U' -> moves.append(new Move(Direction.UP, distance));
                case 'D' -> moves.append(new Move(Direction.DOWN, distance));
                case 'L' -> moves.append(new Move(Direction.LEFT, distance));
                case 'R' -> moves.append(new Move(Direction.RIGHT, distance));
            }
        }

        return moves.toImmutableArray();
    }

    record Position(int x, int y) {
        public Position move(int dx, int dy) {
            return new Position(x + dx, y + dy);
        }
    }

    enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    record Move(Direction direction, int steps) {
    }
}
