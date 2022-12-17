package org.example;

import org.apache.commons.lang3.tuple.Pair;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.example.common.Util.readToString;

public class Day13 implements Day {

    @Override
    public int getDay() {
        return 13;
    }

    @Override
    public void solvePart1() throws IOException {
        var sum = 0;
        var pairs = parseInput();
        for (var i = 0; i < pairs.size(); i++) {
            var pair = pairs.get(i);
            if (comparator().compare(pair.getLeft(), pair.getRight()) <= 0) {
                sum += i + 1;
            }
        }
        System.out.println(sum);
    }

    private static final String DIVIDER_PACKET_1 = "[[2]]";
    private static final String DIVIDER_PACKET_2 = "[[6]]";

    @Override
    public void solvePart2() throws IOException {
        var pairs = parseInput();
        var packets = pairs.stream().flatMap(pair -> Stream.of(pair.getLeft(), pair.getRight())).collect(Collectors.toCollection(ArrayList::new));
        var divider1 = parsePacket(DIVIDER_PACKET_1);
        var divider2 = parsePacket(DIVIDER_PACKET_2);
        packets.add(divider1);
        packets.add(divider2);
        packets.sort(comparator());
        var key = 1;
        for (var i = 0; i < packets.size(); i++) {
            if (packets.get(i) == divider1 || packets.get(i) == divider2) {
                key *= i + 1;
            }
        }
        System.out.println(key);
    }

    public static void main(String[] args) throws IOException {
        Day day = new Day13();
        System.out.printf("day: %d%n", day.getDay());
        day.solvePart1();
        day.solvePart2();
    }

    private static Comparator<Node> comparator() {
        return (first, second) -> {
            if (first instanceof ValueNode left && second instanceof ValueNode right) {
                return Integer.compare(left.element, right.element);
            } else if (first instanceof ArrayNode left && second instanceof ArrayNode right) {
                for (int i = 0; i < Math.min(left.elements.size(), right.elements.size()); i++) {
                    var result = comparator().compare(left.elements.get(i), right.elements.get(i));
                    if (result != 0) {
                        return result;
                    }
                }
                return Integer.compare(left.elements.size(), right.elements.size());
            } else if (first instanceof ValueNode left && second instanceof ArrayNode right) {
                return comparator().compare(new ArrayNode(List.of(left)), right);
            } else if (first instanceof ArrayNode left && second instanceof ValueNode right) {
                return comparator().compare(left, new ArrayNode(List.of(right)));
            }
            throw new IllegalArgumentException("Invalid nodes: " + first + ", " + second);
        };
    }

    private List<Pair<Node, Node>> parseInput() {

        String inputFilePath = new File("").getAbsoluteFile() + File.separator + "input" + File.separator + "day13" + File.separator + "input";
        String content = readToString(new File(inputFilePath));

        return Arrays.stream(content.split("\n\n")).map(block -> {
            var lines = block.split("\n");
            var packet1 = parsePacket(lines[0]);
            var packet2 = parsePacket(lines[1]);
            return Pair.of(packet1, packet2);
        }).toList();
    }

    private static Node parsePacket(String packet) {
        var stack = new Stack<Node>();
        var matcher = Pattern.compile("\\[|]|\\d+").matcher(packet);
        while (matcher.find()) {
            switch (matcher.group()) {
                case "[" -> {
                    var node = new ArrayNode();
                    if (!stack.isEmpty()) {
                        var parent = stack.peek();
                        if (parent instanceof ArrayNode arr) {
                            arr.elements.add(node);
                        }
                    }
                    stack.push(node);
                }
                case "]" -> {
                    if (stack.size() > 1) {
                        stack.pop();
                    }
                }
                default -> {
                    var value = Integer.parseInt(matcher.group());
                    if (stack.isEmpty()) {
                        stack.push(new ValueNode(value));
                    } else {
                        var parent = stack.peek();
                        if (parent instanceof ArrayNode arr) {
                            arr.elements.add(new ValueNode(value));
                        }
                    }
                }
            }
        }
        return stack.pop();
    }

    private interface Node {
    }

    private record ArrayNode(List<Node> elements) implements Node {
        private ArrayNode() {
            this(new ArrayList<>());
        }

        @Override
        public String toString() {
            return elements.toString().replaceAll(" ", "");
        }
    }

    private record ValueNode(int element) implements Node {
        @Override
        public String toString() {
            return String.valueOf(element);
        }
    }
}
