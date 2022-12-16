package org.example;

import kala.collection.mutable.MutableArrayList;

import java.io.*;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class Day03 implements Day {

    static int score(char c) {
        if (c >= 'a' && c <= 'z') {
            return c - 'a' + 1;
        } else {
            return c - 'A' + 27;
        }
    }

    public static void main(String[] args) throws IOException {
        Day day = new Day03();
        System.out.printf("day: %d%n", day.getDay());
        day.solvePart1();
        day.solvePart2();
    }

    @Override
    public int getDay() {
        return 3;
    }

    @Override
    public void solvePart1() throws IOException {
        String inputFilePath = new File("").getAbsoluteFile() + File.separator + "input" + File.separator + "day03" + File.separator + "input";

        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(inputFilePath)));
        String line;
        AtomicInteger score = new AtomicInteger();

        while ((line = br.readLine()) != null) {

            int len = line.length();
            String no1Part = line.substring(0, len / 2), no2Part = line.substring(len / 2);

            Set<Character> s1 = new HashSet<>(), s2 = new HashSet<>();
            for (char c : no1Part.toCharArray()) {
                s1.add(c);
            }
            for (char c : no2Part.toCharArray()) {
                s2.add(c);
            }
            s1.stream().filter(s2::contains).forEach(c -> score.addAndGet(score(c)));
        }

        System.out.println(score);
    }

    @Override
    public void solvePart2() throws IOException {
        String inputFilePath = new File("").getAbsoluteFile() + File.separator + "input" + File.separator + "day03" + File.separator + "input";

        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(inputFilePath)));
        String line;
        AtomicInteger score = new AtomicInteger();

        MutableArrayList<String> ss = new MutableArrayList<>();

        while ((line = br.readLine()) != null) {

            ss.append(line);

        }

        int sz = ss.size();
        int i = 0;
        while (i < sz) {

            String s1 = ss.get(i), s2 = ss.get(i + 1), s3 = ss.get(i + 2);

            Set<Character> cs1 = new HashSet<>(), cs2 = new HashSet<>(), cs3 = new HashSet<>();
            for (char c : s1.toCharArray()) {
                cs1.add(c);
            }
            for (char c : s2.toCharArray()) {
                cs2.add(c);
            }
            for (char c : s3.toCharArray()) {
                cs3.add(c);
            }

            cs1.stream().filter(cs2::contains).filter(cs3::contains).forEach(c -> score.addAndGet(score(c)));

            i += 3;

        }

        System.out.println(score);
    }
}
