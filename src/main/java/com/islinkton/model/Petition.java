package com.islinkton.model;

import java.time.Duration;
import java.time.LocalDateTime;

public class Petition {

	private int id;
    private String title;
    private String content;      
    private String creatorUsername;
    private boolean isApproved;
    private int voteCount;
    private LocalDateTime createdAt;

    // default Constructor
    public Petition() {}

    // constructor with important fields
    public Petition(String title, String content, String creatorUsername) {
        this.title = title;
        this.content = content;
        this.creatorUsername = creatorUsername;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    
    public String getCreatorUsername() { return creatorUsername; }
    public void setCreatorUsername(String creatorUsername) { this.creatorUsername = creatorUsername; }
    
    public boolean isApproved() { return isApproved; }
    public void setApproved(boolean approved) { isApproved = approved; }
    
    public int getVoteCount() { return voteCount; }
    public void setVoteCount(int voteCount) { this.voteCount = voteCount; }
    
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
            return days + " d ago";
        }
    }
}