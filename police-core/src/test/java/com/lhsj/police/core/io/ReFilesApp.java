package com.lhsj.police.core.io;

import java.io.File;
import java.io.IOException;

import static com.lhsj.police.core.constant.Constants.EMPTY;

public class ReFilesApp {

    public static void main(String[] args) throws IOException {
        testFileExists();
    }

    private static void testFileExists() throws IOException {
        String absolutePath = "/tmp/smart.groovy";
        System.out.println(ReFiles.isFileExists(absolutePath));
    }

    private static void testTouch() throws IOException {
        String absolutePath = "/tmp/test.groovy";

        if (ReFiles.isFileExists(absolutePath)) {
            System.out.println("isFileExists");
            ReFiles.write(EMPTY, new File(absolutePath));
        } else {
            System.out.println("isNotFileExists");
            ReFiles.touch(absolutePath);
        }
    }

}
