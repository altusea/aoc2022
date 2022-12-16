package org.example;

import java.io.*;
import java.util.*;

public class Day07 implements Day {

    public static MyDir buildTree() throws IOException {
        String inputFilePath = new File("").getAbsoluteFile() + File.separator + "input" + File.separator + "day07" + File.separator + "input";
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(inputFilePath)));
        String line;

        MyDir curDir = null, rootDir = new MyDir();

        while ((line = br.readLine()) != null) {
            if (line.startsWith("$")) {
                line = line.substring(2);
                if (line.equals("cd /")) {
                    curDir = rootDir;
                } else if (line.equals("cd ..")) {
                    assert curDir != null;
                    curDir = curDir.parentDir;
                } else if (line.startsWith("cd")) {
                    String dirName = line.split(" ")[1];
                    assert curDir != null;
                    curDir = curDir.childDirs.get(dirName);
                } else {
                    // "ls" cmd
                    // do nothing
                }
            } else {
                if (line.startsWith("dir")) {
                    String childDirName = line.split((" "))[1];
                    MyDir newDir = new MyDir();
                    newDir.parentDir = curDir;
                    curDir.childDirs.put(childDirName, newDir);
                } else {
                    // file
                    String[] info = line.split(" ");
                    assert curDir != null;
                    curDir.fileList.add(new MyFile(info[1], Long.parseLong(info[0]), curDir));
                }
            }
        }

        List<MyDir> allDirList = new ArrayList<>();
        Deque<MyDir> dirDeque = new LinkedList<>();
        dirDeque.add(rootDir);

        while (!dirDeque.isEmpty()) {
            MyDir headDir = dirDeque.pollFirst();
            dirDeque.addAll(headDir.childDirs.values());
            allDirList.add(headDir);
        }

        for (MyDir dir : allDirList) {
            for (MyFile f : dir.fileList) {

                long size = f.fileSize;

                MyDir tmpDir = f.parentDir;
                while (tmpDir != null) {
                    tmpDir.dirSize += size;
                    tmpDir = tmpDir.parentDir;
                }
            }
        }

        return rootDir;
    }

    public static void p1(MyDir rootDir) {

        List<MyDir> allDirList = new ArrayList<>();
        Deque<MyDir> dirDeque = new LinkedList<>();
        dirDeque.add(rootDir);

        while (!dirDeque.isEmpty()) {
            MyDir headDir = dirDeque.pollFirst();
            dirDeque.addAll(headDir.childDirs.values());
            allDirList.add(headDir);
        }

        long answer = 0;
        for (MyDir dir : allDirList) {
            if (dir.dirSize < 100000) {
                answer += dir.dirSize;
            }
        }

        System.out.println("p1's answer: " + answer);
    }

    public static void p2(MyDir rootDir) {

        List<MyDir> allDirList = new ArrayList<>();
        Deque<MyDir> dirDeque = new LinkedList<>();
        dirDeque.add(rootDir);

        while (!dirDeque.isEmpty()) {
            MyDir headDir = dirDeque.pollFirst();
            dirDeque.addAll(headDir.childDirs.values());
            allDirList.add(headDir);
        }

        ArrayList<MyDir> s = new ArrayList<>(allDirList);
        s.sort(Comparator.comparingLong(d -> d.dirSize));
        long needed = 30000000 - (70000000 - rootDir.dirSize);

        System.out.print("p2's answer: ");

        for (MyDir cur : s) {
            if (cur.dirSize > needed) {
                System.out.println(cur.dirSize);
                break;
            }
        }
    }

    public static void main(String[] args) throws IOException {
        Day day = new Day07();
        System.out.printf("day: %d%n", day.getDay());
        day.solvePart1();
        day.solvePart2();
    }

    @Override
    public int getDay() {
        return 7;
    }

    @Override
    public void solvePart1() throws IOException {
        MyDir rootDir = buildTree();
        p1(rootDir); // 1297683
    }

    @Override
    public void solvePart2() throws IOException {
        MyDir rootDir = buildTree();
        p2(rootDir); // 5756764
    }
}

interface MyStorage {
}

class MyDir implements MyStorage {

    //    String dirName;
    public List<MyFile> fileList = new ArrayList<>();
    public Map<String, MyDir> childDirs = new HashMap<>();
    public MyDir parentDir = null;

    public long dirSize = 0;
}

class MyFile implements MyStorage {

    String fileName;
    public long fileSize;
    public MyDir parentDir;

    public MyFile(String fileName, long fileSize, MyDir parentDir) {
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.parentDir = parentDir;
    }
}
