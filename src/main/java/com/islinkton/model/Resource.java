package com.islinkton.model;

import java.time.LocalDateTime;

public class Resource {

    private int id;
    private String title;
    private String description;
    private String filePath;
    private String fileType;
    private int uploadedBy;
    private int categoryId;
    private String authorUserName;
    private String categoryName; // for display only
    private LocalDateTime createdAt;

    // default constructor
    public Resource() {}

    // constructor with important fields
    public Resource(String title, String description, String filePath, int uploadedBy, int categoryId) {
        this.title = title;
        this.description = description;
        this.filePath = filePath;
        this.uploadedBy = uploadedBy;
        this.categoryId = categoryId;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getFilePath() { return filePath; }
    public void setFilePath(String filePath) { this.filePath = filePath; }
    
    public String getFileType() { return fileType; }
    public void setFileType(String fileType) { this.fileType = fileType; }

    public int getUploadedBy() { return uploadedBy; }
    public void setUploadedBy(int uploadedBy) { this.uploadedBy = uploadedBy; }

    public int getCategoryId() { return categoryId; }
    public void setCategoryId(int categoryId) { this.categoryId = categoryId; }
    
    public String getAuthorUserName() { return authorUserName; }
    public void setAuthorUserName(String authorName) { this.authorUserName = authorName; }
    
    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}