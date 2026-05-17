package com.islinkton.model;

import java.time.LocalDateTime;

public class Category {

    private int id;
    private String name;
    private String description;
    private LocalDateTime createdAt;

    // default constructor
    public Category() {}

    // constructor with important fields
    public Category(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}