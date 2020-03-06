package com.example.seeker.Model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Category {

    private long id;
    private String title;
    private String description;
    private String category_type;
    private List<Project> projects = new ArrayList<>();
    private Set<Skill> skills = new HashSet<>();

    public Category(long id, String title, String description, String category_type, List<Project> projects, Set<Skill> skills) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.category_type = category_type;
        this.projects = projects;
        this.skills = skills;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getCategory_type() {
        return category_type;
    }

    public void setCategory_type(String category_type) {
        this.category_type = category_type;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public Set<Skill> getSkills() {
        return skills;
    }

    public void setSkills(Set<Skill> skills) {
        this.skills = skills;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", category_type='" + category_type + '\'' +
                ", projects=" + projects +
                ", skills=" + skills +
                '}';
    }
}
