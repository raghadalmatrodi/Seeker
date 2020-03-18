package com.example.seeker.Model;

import java.time.LocalDateTime;

public class Milestone {
    private long id;
    private double amount;
    private String status;
    private String deadline;
    private String description;
    private Project project;

    public Milestone(double amount, String status, String deadline, String description, Project project) {
        this.amount = amount;
        this.status = status;
        this.deadline = deadline;
        this.description = description;
        this.project = project;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    @Override
    public String toString() {
        return "Milestone{" +
                "id=" + id +
                ", amount=" + amount +
                ", status='" + status + '\'' +
                ", deadline=" + deadline +
                ", description='" + description + '\'' +
                ", project=" + project +
                '}';
    }
}
