package com.islinkton.utils;

import jakarta.servlet.http.Part;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileUploadUtil {

    // 🏡 CHANGED ONLY THIS: Points to a safe, permanent folder inside the Home directory of any PC
    private static final String USER_HOME = System.getProperty("user.home");
    private static final String WEBAPP_ASSETS_PATH = USER_HOME + File.separator + "islinkton_uploads";

    public static final String IMAGES_DIR = "images";
    public static final String FILES_DIR = "files";

    public static boolean isImage(Part filePart) {
        if (filePart == null || filePart.getSize() == 0) {
            return false;
        }
        String contentType = filePart.getContentType();
        return contentType != null && contentType.startsWith("image/");
    }

    public static String uploadProfileImage(Part filePart) throws Exception {
        return uploadFile(filePart, IMAGES_DIR, "profile");
    }

    public static String uploadResourceFile(Part filePart) throws Exception {
        return uploadFile(filePart, FILES_DIR, "resource");
    }

    private static String uploadFile(Part filePart, String subDir, String prefix) throws Exception {
        if (filePart == null || filePart.getSize() == 0) {
            System.out.println("[FileUploadUtil] No file provided");
            return null;
        }

        String originalFileName = filePart.getSubmittedFileName();
        String fileExtension = "";
        if (originalFileName != null && originalFileName.contains(".")) {
            fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
        }

        // generate dynamic tracking file name
        String newFileName = System.currentTimeMillis() + "_" + prefix + fileExtension;

        // combines to point to: [User Home]/islinkton_uploads/[images or files]
        String uploadTargetFolderPath = WEBAPP_ASSETS_PATH + File.separator + subDir;
        String fullDestinationFilePath = uploadTargetFolderPath + File.separator + newFileName;

        File uploadDir = new File(uploadTargetFolderPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        // save file payload to source code assets directory
        try (InputStream input = filePart.getInputStream()) {
            Files.copy(input,
                       Paths.get(fullDestinationFilePath),
                       StandardCopyOption.REPLACE_EXISTING);
            System.out.println("[FileUploadUtil] Saved to source project successfully: " + fullDestinationFilePath);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        return newFileName;
    }
}