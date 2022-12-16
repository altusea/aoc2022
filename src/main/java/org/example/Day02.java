package org.example;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Day02 implements Day {

    public static String R = "Rock";
    public static String P = "Paper";
    public static String S = "Scissors";

    static Map<String, String> opMap = new HashMap<>() {{
        put("A", R);
        put("B", P);
        put("C", S);
    }};

    static Map<String, String> meMap = new HashMap<>() {{
        put("X", R);
        put("Y", P);
        put("Z", S);
    }};

    public static int meWinOrNot(String op, String me) {

        String op1 = opMap.get(op), me1 = meMap.get(me);
        if (me1.equals(R) && op1.equals(S) || me1.equals(S) && op1.equals(P) || me1.equals(P) && op1.equals(R)) {
            return 1;
        } else if (me1.equals(op1)) {
            return 0;
        } else {
            return -1;
        }
    }

    static Map<String, Integer> meShapeScore = new HashMap<>() {{
        put(R, 1);
        put(P, 2);
        put(S, 3);
    }};

    static Map<String, String> meWinShape = new HashMap<>() {{
        put(R, P);
        put(P, S);
        put(S, R);
    }};

    static Map<String, String> meLoseShape = new HashMap<>() {{
        put(R, S);
        put(P, R);
        put(S, P);
    }};

    public static void main(String[] args) throws IOException {
        Day day = new Day02();
        System.out.printf("day: %d%n", day.getDay());
        day.solvePart1();
        day.solvePart2();
    }

    @Override
    public int getDay() {
        return 2;
    }

    @Override
    public void solvePart1() throws IOException {
        String inputFilePath = new File("").getAbsoluteFile() + File.separator + "input" + File.separator + "day02" + File.separator + "input";

        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(inputFilePath)));
        String line;
        int meScore = 0;

        while ((line = br.readLine()) != null) {
            String[] info = line.split(" ");
            int res = meWinOrNot(info[0], info[1]);
            meScore += meShapeScore.get(meMap.get(info[1]));
            meScore += res > 0 ? 6 : (res < 0 ? 0 : 3);
        }

        System.out.println(meScore);
    }

    @Override
    public void solvePart2() throws IOException {
        String inputFilePath = new File("").getAbsoluteFile() + File.separator + "input" + File.separator + "day02" + File.separator + "input";

        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(inputFilePath)));
        String line;
        int meScore = 0;

        while ((line = br.readLine()) != null) {
            String[] info = line.split(" ");

            String meShape;
            if (info[1].equals("X")) {
                // lose the round
                meShape = meLoseShape.get(opMap.get(info[0]));
                meScore += meShapeScore.get(meShape);
                meScore += 0;
            } else if (info[1].equals("Y")) {
                // draw
                meShape = opMap.get(info[0]);
                meScore += meShapeScore.get(meShape);
                meScore += 3;
            } else {
                meShape = meWinShape.get(opMap.get(info[0]));
                meScore += meShapeScore.get(meShape);
                meScore += 6;
            }
        }

        System.out.println(meScore);
    }
}
