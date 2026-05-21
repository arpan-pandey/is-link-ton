package com.islinkton.model;

public class Vote {
    private int id;
    private int targetId; // Represents thread_id or petition_id
    private int userId;
    private String targetType; // "THREAD" or "PETITION"

    public Vote() {}

    public Vote(int targetId, int userId, String targetType) {
        this.targetId = targetId;
        this.userId = userId;
        this.targetType = targetType;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getTargetId() { return targetId; }
    public void setTargetId(int targetId) { this.targetId = targetId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getTargetType() { return targetType; }
    public void setTargetType(String targetType) { this.targetType = targetType; }
}