package com.islinkton.utils;

import jakarta.servlet.http.Part;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileUploadUtil {

    public static final String UPLOAD_FOLDER = "uploads";

    public static String uploadFile(
            Part filePart,
            String applicationPath) throws Exception {

        // no file selected
        if (filePart == null || filePart.getSize() == 0) {
            return null;
        }

        String originalFileName = filePart.getSubmittedFileName();

        // create unique filename
        String fileName =
                System.currentTimeMillis()
                + "_"
                + originalFileName;

        String uploadPath =
                applicationPath
                + File.separator
                + UPLOAD_FOLDER;

        File uploadDir = new File(uploadPath);

        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }

        try(InputStream input = filePart.getInputStream()) {

            Files.copy(
                    input,
                    Paths.get(
                            uploadPath
                            + File.separator
                            + fileName
                    ),
                    StandardCopyOption.REPLACE_EXISTING
            );
        }

        return fileName;
    }

    public static boolean isImage(Part filePart) {

        if(filePart == null){
            return false;
        }

        String contentType = filePart.getContentType();

        return contentType != null && contentType.startsWith("image/");
    }
}