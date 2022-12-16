package org.example;

import java.io.File;
import java.io.IOException;

import static org.example.common.Util.readToString;

public class Day08 implements Day {

    public static void p1(int[][] treeHeights) {

        int rows = treeHeights.length, cols = treeHeights[0].length;

        // left -> right
        boolean[][] arrLeftRight = new boolean[rows][cols];
        for (int i = 0; i < rows; i++) {
            int curMax = treeHeights[i][0];
            arrLeftRight[i][0] = true;
            for (int j = 1; j < cols; j++) {
                int cur = treeHeights[i][j];
                if (cur > curMax) {
                    arrLeftRight[i][j] = true;
                    curMax = cur;
                }
            }
        }

        // right -> left
        boolean[][] arrRightLeft = new boolean[rows][cols];
        for (int i = 0; i < rows; i++) {
            int curMax = treeHeights[i][cols - 1];
            arrRightLeft[i][cols - 1] = true;
            for (int j = cols - 2; j > -1; j--) {
                int cur = treeHeights[i][j];
                if (cur > curMax) {
                    arrRightLeft[i][j] = true;
                    curMax = cur;
                }
            }
        }

        // up -> down
        boolean[][] arrUpDown = new boolean[rows][cols];
        for (int j = 0; j < cols; j++) {
            int curMax = treeHeights[0][j];
            arrUpDown[0][j] = true;
            for (int i = 1; i < rows; i++) {
                int cur = treeHeights[i][j];
                if (cur > curMax) {
                    arrUpDown[i][j] = true;
                    curMax = cur;
                }
            }
        }

        // down -> up
        boolean[][] arrDownUp = new boolean[rows][cols];
        for (int j = 0; j < cols; j++) {
            int curMax = treeHeights[rows - 1][j];
            arrDownUp[rows - 1][j] = true;
            for (int i = rows - 2; i > -1; i--) {
                int cur = treeHeights[i][j];
                if (cur > curMax) {
                    arrDownUp[i][j] = true;
                    curMax = cur;
                }
            }
        }

        int sum = 2 * (rows + cols) - 4;

        for (int i = 1; i < rows - 1; i++) {
            for (int j = 1; j < cols - 1; j++) {
                if (arrLeftRight[i][j] || arrRightLeft[i][j] || arrUpDown[i][j] || arrDownUp[i][j]) {
                    sum += 1;
                }
            }
        }

        System.out.print("p1's answer: ");
        System.out.println(sum);
    }

    public static void p2(int[][] treeHeights) {

        long maxScore = Long.MIN_VALUE;
        int rows = treeHeights.length, cols = treeHeights[0].length;

        for (int x = 1; x < rows - 1; x++) {
            for (int y = 1; y < cols - 1; y++) {

                int val = treeHeights[x][y];
                int distanceFromLeft = y;
                int distanceFromRight = cols - y - 1;
                int distanceFromTop = x;
                int distanceFromBottom = rows - x - 1;

                // visible from left
                int indexLeft = 1;
                while (y - indexLeft > -1 && val > treeHeights[x][y - indexLeft]) {
                    indexLeft++;
                }
                if (y - indexLeft == -1) {
                    indexLeft = distanceFromLeft;
                }

                // visible from right
                int indexRight = 1;
                while (y + indexRight < cols && val > treeHeights[x][y + indexRight]) {
                    indexRight++;
                }
                if (y + indexRight == cols) {
                    indexRight = distanceFromRight;
                }

                // visible from up
                int indexTop = 1;
                while (x - indexTop > -1 && val > treeHeights[x - indexTop][y]) {
                    indexTop++;
                }
                if (x - indexTop == -1) {
                    indexTop = distanceFromTop;
                }

                // visible from bottom
                int indexBottom = 1;
                while (x + indexBottom < rows && val > treeHeights[x + indexBottom][y]) {
                    indexBottom++;
                }
                if (x + indexBottom == cols) {
                    indexBottom = distanceFromBottom;
                }

                long curScore = (long) indexLeft * indexRight * indexTop * indexBottom;
                if (curScore > maxScore) {
                    maxScore = curScore;
                }
            }
        }

        System.out.print("p2's answer: ");
        System.out.println(maxScore);
    }

    public static void main(String[] args) throws IOException {
        Day day = new Day08();
        System.out.printf("day: %d%n", day.getDay());
        day.solvePart1();
        day.solvePart2();
    }

    @Override
    public int getDay() {
        return 8;
    }

    @Override
    public void solvePart1() throws IOException {
        int[][] arr = parseInput();
        p1(arr);
    }

    @Override
    public void solvePart2() throws IOException {
        int[][] arr = parseInput();
        p2(arr);
    }

    private static int[][] parseInput() {
        String inputFilePath = new File("").getAbsoluteFile() + File.separator + "input" + File.separator + "day08" + File.separator + "input";

        String content = readToString(new File(inputFilePath));


        String[] lines = content.split("\n");
        int n = lines.length;
        int m = lines[0].length();

        int[][] arr = new int[n][m];

        for (int i = 0; i < n; i++) {
            char[] cs = lines[i].toCharArray();
            for (int j = 0; j < m; j++) {
                arr[i][j] = cs[j] - '0';
            }
        }
        return arr;
    }
}
