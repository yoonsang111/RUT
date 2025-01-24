package com.stream.tour.global.utils;

import java.util.UUID;

public class FileUtil {
    public static String getUuid() {
        return UUID.randomUUID().toString();
    }

    public static boolean isExcelFile(String extension) {
        if (extension.equals("xls") || extension.equals("xlsx")) {
            return true;
        }
        return false;
    }
}
