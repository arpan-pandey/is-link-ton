package com.islinkton.model;

import java.time.Duration;
import java.time.LocalDateTime;

public class Thread {
    
    private int id;
    private String title;
    private String content;
    private int categoryId;
    private int authorId;
    private String authorUserName; // for display only
    private String categoryName; // for display only
    private int voteCount;
    private int commentCount;
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

    public String getAuthorUserName() { return authorUserName; }
    public void setAuthorUserName(String authorName) { this.authorUserName = authorName; }

    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }
    
    public int getVoteCount() { return voteCount; }
    public void setVoteCount(int voteCount) { this.voteCount = voteCount; }
    
    public int getCommentCount() { return commentCount; }
    public void setCommentCount(int commentCount) { this.commentCount = commentCount; } 

    public boolean isApproved() { return isApproved; }
    public void setApproved(boolean approved) { isApproved = approved; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public String getTimeAgo() {
    	
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(this.createdAt, now);
        
        long seconds = duration.getSeconds();
        long minutes = duration.toMinutes();
        long hours = duration.toHours();
        long days = duration.toDays();

        if (seconds < 60) {
            return "Just now";
        } else if (minutes < 60) {
            return minutes + "m ago";
        } else if (hours < 24) {
            return hours + "h ago";
        } else {
            return days + "d ago";
        }
    }
}