package com.example.seeker.Model;

import java.io.Serializable;
import java.nio.file.Path;
import java.time.LocalDateTime;

public class Contract implements Serializable {
    private long id;
    private double price;
    private String deadline;
    private String type;
    private Project project;
    private Freelancer freelancer;

    public Contract(long id, double price, String deadline, String type, Project project, Freelancer freelancer) {
        this.id = id;
        this.price = price;
        this.deadline = deadline;
        this.type = type;
        this.project = project;
        this.freelancer = freelancer;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Freelancer getFreelancer() {
        return freelancer;
    }

    public void setFreelancer(Freelancer freelancer) {
        this.freelancer = freelancer;
    }

    @Override
    public String toString() {
        return "Contract{" +
                "id=" + id +
                ", price=" + price +
                ", deadline=" + deadline +
                ", type='" + type + '\'' +
                ", project=" + project +
                ", freelancer=" + freelancer +
                '}';
    }
}

