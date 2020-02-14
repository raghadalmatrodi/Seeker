package com.example.seeker.Model;

public class Category {
    private String title;
    private String description;

    public Category(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "CategorySearch{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
