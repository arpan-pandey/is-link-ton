package com.islinkton.model;

import java.time.LocalDateTime;

public class Category {

    private int id;
    private String name;
    private String type;
    private LocalDateTime createdAt;

    // default constructor
    public Category() {}

    // constructor with important fields
    public Category(String name, String description) {
        this.name = name;
        this.type = description;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}