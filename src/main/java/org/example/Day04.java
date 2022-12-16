package org.example;

import java.io.*;

public class Day04 implements Day {

    public static boolean fullyContain(int no1St, int no1Ed, int no2St, int no2Ed) {
        return no1St <= no2St && no1Ed >= no2Ed || no2St <= no1St && no2Ed >= no1Ed;
    }

    public static boolean overlap(int no1St, int no1Ed, int no2St, int no2Ed) {
        return no2St >= no1St && no2St <= no1Ed || no2Ed >= no1St && no2St <= no1Ed;
    }

    public static void main(String[] args) throws IOException {
        Day day = new Day04();
        System.out.printf("day: %d%n", day.getDay());
        day.solvePart1();
        day.solvePart2();
    }

    @Override
    public int getDay() {
        return 4;
    }

    @Override
    public void solvePart1() throws IOException {
        String inputFilePath = new File("").getAbsoluteFile() + File.separator + "input" + File.separator + "day04" + File.separator + "input";

        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(inputFilePath)));
        String line;

        int count = 0;

        while ((line = br.readLine()) != null) {

            String[] param = line.split(",");
            String[] pair0 = param[0].split("-"), pair1 = param[1].split("-");
            if (fullyContain(Integer.parseInt(pair0[0]), Integer.parseInt(pair0[1]), Integer.parseInt(pair1[0]), Integer.parseInt(pair1[1]))) {
                count += 1;
            }
        }
        System.out.println(count);
    }

    @Override
    public void solvePart2() throws IOException {
        String inputFilePath = new File("").getAbsoluteFile() + File.separator + "input" + File.separator + "day04" + File.separator + "input";

        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(inputFilePath)));
        String line;

        int count = 0;

        while ((line = br.readLine()) != null) {

            String[] param = line.split(",");
            String[] pair0 = param[0].split("-"), pair1 = param[1].split("-");
            if (overlap(Integer.parseInt(pair0[0]), Integer.parseInt(pair0[1]), Integer.parseInt(pair1[0]), Integer.parseInt(pair1[1]))) {
                count += 1;
            }
        }
        System.out.println(count);
    }
}
