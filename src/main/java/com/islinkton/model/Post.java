package com.islinkton.model;

import java.time.LocalDateTime;

public class Post {

    private int id;
    private int threadId;
    private int userId;
    private String content;
    private LocalDateTime createdAt;
    private String authorUserName; // for display
    private String userRole; // for display

    // default constructor
    public Post() {}

    // constructor with important fields
    public Post(int threadId, int userId, String content) {
        this.threadId = threadId;
        this.userId = userId;
        this.content = content;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getThreadId() { return threadId; }
    public void setThreadId(int threadId) { this.threadId = threadId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public String getAuthorUserName() { return authorUserName; }
    public void setAuthorUserName(String authorUserName) { this.authorUserName = authorUserName; }

    public String getUserRole() { return userRole; }
    public void setUserRole(String userRole) { this.userRole = userRole; }
}