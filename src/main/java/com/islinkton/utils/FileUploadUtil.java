package com.islinkton.utils;

import jakarta.servlet.http.Part;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileUploadUtil {

    public static String uploadFile(Part filePart, String uploadPath) throws Exception {
        if (filePart == null || filePart.getSize() == 0) {
            return null;
        }

        String originalFileName = filePart.getSubmittedFileName();
        String fileExtension = "";

        if (originalFileName != null && originalFileName.contains(".")) {
            fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
        }

        String fileName = System.currentTimeMillis() + "_" + 
                         (int)(Math.random()*10000) + fileExtension;

        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        try (InputStream input = filePart.getInputStream()) {
            Files.copy(input, 
                       Paths.get(uploadPath + File.separator + fileName),
                       StandardCopyOption.REPLACE_EXISTING);
        }

        return fileName;
    }

    public static boolean isImage(Part filePart) {
        if (filePart == null) return false;
        String contentType = filePart.getContentType();
        return contentType != null && contentType.startsWith("image/");
    }
}