package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;

public class Day05 implements Day {

    public static void main(String[] args) throws IOException {
        Day day = new Day05();
        System.out.printf("day: %d%n", day.getDay());
        day.solvePart1();
        day.solvePart2();
    }

    @Override
    public int getDay() {
        return 5;
    }

    @Override
    public void solvePart1() throws IOException {
        String inputFilePath = new File("").getAbsoluteFile() + File.separator + "input" + File.separator + "day05" + File.separator + "input1";

        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(inputFilePath)));

        ArrayList<Deque<Character>> arrayList = new ArrayList<>(10);

        for (int i = 0; i < 9; i++) {
            arrayList.add(new LinkedList<>());
        }

        String[] ss = new String[]{"LNWTD", "CPH", "WPHNDGMJ", "CWSNTQL", "PHCN", "THNDMWQB", "MBRJGSL", "ZNWGVBRT", "WGDNPL"};

        for (int i = 0; i < 9; i++) {
            for (char c : ss[i].toCharArray()) {
                arrayList.get(i).add(c);
            }
        }

        String line;
        while ((line = br.readLine()) != null) {
            String[] info = line.split(" ");

            int moveNum = Integer.parseInt(info[1]);
            int from = Integer.parseInt(info[3]) - 1;
            int to = Integer.parseInt(info[5]) - 1;

            Deque<Character> fromStack = arrayList.get(from);
            Deque<Character> toStack = arrayList.get(to);

            while (!fromStack.isEmpty() && moveNum > 0) {
                toStack.addLast(fromStack.pollLast());
                moveNum--;
            }
        }

        for (int i = 0; i < 9; i++) {
            System.out.print(arrayList.get(i).peekLast());
        }
        System.out.println();
    }

    @Override
    public void solvePart2() throws IOException {
        String inputFilePath = new File("").getAbsoluteFile() + File.separator + "input" + File.separator + "day05" + File.separator + "input1";

        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(inputFilePath)));

        ArrayList<Deque<Character>> arrayList = new ArrayList<>(10);

        for (int i = 0; i < 9; i++) {
            arrayList.add(new LinkedList<>());
        }

        String[] ss = new String[]{"LNWTD", "CPH", "WPHNDGMJ", "CWSNTQL", "PHCN", "THNDMWQB", "MBRJGSL", "ZNWGVBRT", "WGDNPL"};

        for (int i = 0; i < 9; i++) {
            for (char c : ss[i].toCharArray()) {
                arrayList.get(i).add(c);
            }
        }

        Deque<Character> midQueue = new LinkedList<>();

        String line;
        while ((line = br.readLine()) != null) {
            String[] info = line.split(" ");

            int moveNum = Integer.parseInt(info[1]);
            int from = Integer.parseInt(info[3]) - 1;
            int to = Integer.parseInt(info[5]) - 1;

            Deque<Character> fromStack = arrayList.get(from);
            Deque<Character> toStack = arrayList.get(to);

            while (!fromStack.isEmpty() && moveNum > 0) {
                midQueue.addLast(fromStack.pollLast());
                moveNum--;
            }
            while (!midQueue.isEmpty()) {
                toStack.addLast(midQueue.pollLast());
            }
        }

        for (int i = 0; i < 9; i++) {
            System.out.print(arrayList.get(i).peekLast());
        }
        System.out.println();
    }
}
