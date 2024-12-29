package com.ourmenu.backend.domain.menu.util;

public class FileUtil {
    public static final String FILE_EXTENSION_DELIMITER = ".";

    public static String buildFileName(String originalFileName) {
        int fileExtensionIndex = originalFileName.lastIndexOf(FILE_EXTENSION_DELIMITER);
        String fileExtension = originalFileName.substring(fileExtensionIndex);
        String fileName = originalFileName.substring(0, fileExtensionIndex);
        String now = String.valueOf(System.currentTimeMillis())
                .replace("/", "_");
        return fileName + "_" + now + fileExtension;
    }
}