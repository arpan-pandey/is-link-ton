package com.islinkton.model;

import java.time.LocalDateTime;

public class Thread {
    
    private int id;
    private String title;
    private String content;
    private int categoryId;
    private int authorId;
    private String authorName; // for display only
    private String categoryName; // for display only
    private boolean isApproved;
    private LocalDateTime createdAt;

    // default Constructor
    public Thread() {}

    // constructor for creating new thread
    public Thread(String title, String content, int categoryId, int authorId) {
        this.title = title;
        this.content = content;
        this.categoryId = categoryId;
        this.authorId = authorId;
        this.isApproved = false;
    }

    // getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public int getCategoryId() { return categoryId; }
    public void setCategoryId(int categoryId) { this.categoryId = categoryId; }

    public int getAuthorId() { return authorId; }
    public void setAuthorId(int authorId) { this.authorId = authorId; }

    public String getAuthorName() { return authorName; }
    public void setAuthorName(String authorName) { this.authorName = authorName; }

    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }

    public boolean isApproved() { return isApproved; }
    public void setApproved(boolean approved) { isApproved = approved; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}