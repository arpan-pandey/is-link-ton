package com.islinkton.model;

import java.time.LocalDateTime;

public class Resource {

    private int id;
    private String title;
    private String description;
    private String filePath;
    private int uploadedBy;
    private String moduleName;
    private LocalDateTime createdAt;

    // default constructor
    public Resource() {}

    // constructor with important fields
    public Resource(String title, String description, String filePath, int uploadedBy, String moduleName) {
        this.title = title;
        this.description = description;
        this.filePath = filePath;
        this.uploadedBy = uploadedBy;
        this.moduleName = moduleName;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getFilePath() { return filePath; }
    public void setFilePath(String filePath) { this.filePath = filePath; }

    public int getUploadedBy() { return uploadedBy; }
    public void setUploadedBy(int uploadedBy) { this.uploadedBy = uploadedBy; }

    public String getModuleName() { return moduleName; }
    public void setModuleName(String moduleName) { this.moduleName = moduleName; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}