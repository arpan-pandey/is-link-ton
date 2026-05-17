package com.islinkton.model;

import java.time.LocalDateTime;

public class Thread {

    private int id;
    private String title;
    private String content;
    private int categoryId;
    private int authorId;
    private boolean isApproved;
    private LocalDateTime createdAt;

    // default Constructor
    public Thread() {}

    // constructor with important fields
    public Thread(String title, String content, int categoryId, int authorId) {
        this.title = title;
        this.content = content;
        this.categoryId = categoryId;
        this.authorId = authorId;
        this.isApproved = false; // threads need admin approval
    }

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

    public boolean isApproved() { return isApproved; }
    public void setApproved(boolean approved) { isApproved = approved; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}