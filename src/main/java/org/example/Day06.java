package org.example;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

public class Day06 implements Day {

    @Override
    public void solvePart1() throws IOException {

        String inputFilePath = new File("").getAbsoluteFile() + File.separator + "input" + File.separator + "day06" + File.separator + "input";
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(inputFilePath)));
        String content = br.readLine();

        int[] count = new int[26];
        Set<Character> set = new HashSet<>();

        char[] cs = content.toCharArray();
        int n = cs.length;

        for (int i = 0; i < 4; i++) {
            set.add(cs[i]);
            count[cs[i] - 'a'] += 1;
        }

        if (set.size() == 4) {
            System.out.println(4);
            return;
        }

        for (int i = 4; i < n; i++) {
            char c = cs[i], prev4 = cs[i - 4];

            int prev4Idx = prev4 - 'a';
            count[prev4Idx] -= 1;
            if (count[prev4Idx] == 0) {
                set.remove(prev4);
            }

            if (set.contains(c)) {
                count[c - 'a'] += 1;
            } else {
                set.add(c);
                count[c - 'a'] += 1;
                if (set.size() == 4) {
                    System.out.println(i + 1);
                    break;
                }
            }
        }
    }

    @Override
    public void solvePart2() throws IOException {

        String inputFilePath = new File("").getAbsoluteFile() + File.separator + "input" + File.separator + "day06" + File.separator + "input";
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(inputFilePath)));
        String content = br.readLine();

        int[] count = new int[26];
        Set<Character> set = new HashSet<>();

        char[] cs = content.toCharArray();
        int n = cs.length;

        for (int i = 0; i < 14; i++) {
            set.add(cs[i]);
            count[cs[i] - 'a'] += 1;
        }

        if (set.size() == 14) {
            System.out.println(14);
            return;
        }

        for (int i = 14; i < n; i++) {
            char c = cs[i], prev4 = cs[i - 14];

            int prev4Idx = prev4 - 'a';
            count[prev4Idx] -= 1;
            if (count[prev4Idx] == 0) {
                set.remove(prev4);
            }

            if (set.contains(c)) {
                count[c - 'a'] += 1;
            } else {
                set.add(c);
                count[c - 'a'] += 1;
                if (set.size() == 14) {
                    System.out.println(i + 1);
                    break;
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        Day day = new Day06();
        System.out.printf("day: %d%n", day.getDay());
        day.solvePart1();
        day.solvePart2();
    }

    @Override
    public int getDay() {
        return 6;
    }
}
