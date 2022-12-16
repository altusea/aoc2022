package org.example.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Util {

    public static String readToString(File file) {
        long fileLength = file.length();
        byte[] fileContent = new byte[(int) fileLength];
        try {
            FileInputStream in = new FileInputStream(file);
            in.read(fileContent);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new String(fileContent);
    }
}
