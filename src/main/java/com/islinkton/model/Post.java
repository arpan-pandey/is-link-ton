package com.islinkton.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Post {

    private int id;
    private int threadId;
    private int userId;
    private int parentPostId;
    private String content;
    private LocalDateTime createdAt;
    private String authorUserName; // for display
    private String userRole; // for display
    private String profileImage;
    
    private List<Post> replies = new ArrayList<>();

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
    
    public int getParentPostId() { return parentPostId; }
    public void setParentPostId(int parentPostId) { this.parentPostId = parentPostId; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public String getAuthorUserName() { return authorUserName; }
    public void setAuthorUserName(String authorUserName) { this.authorUserName = authorUserName; }

    public String getUserRole() { return userRole; }
    public void setUserRole(String userRole) { this.userRole = userRole; }
    
    public List<Post> getReplies() { return replies; }
    public void setReplies(List<Post> replies) { this.replies = replies; }
    
    public String getProfileImage() { return profileImage; }
    public void setProfileImage(String profileImage) { this.profileImage = profileImage; }
    
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