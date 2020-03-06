package com.example.seeker.Model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Bid implements Serializable {
    private String title;
    private String description;
    private double price;
    private String deliver_date;
    private String status;
    private long freelancerId;
    private Freelancer freelancer;

    //todo 10 hind added project obj to bid and its constructor

    private Project project;

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Bid(String title, String description, double price, String deliver_date, String status, Freelancer freelancer, Project project) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.deliver_date = deliver_date;
        this.status = status;
        this.freelancer = freelancer;
        this.project = project;
    }

    //todo hind

    public Bid(){

    }
    public Bid(String title, String description, double price, String deliver_date, String status) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.deliver_date = deliver_date;
        this.status = status;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDeliver_date() {
        return deliver_date;
    }

    public void setDeliver_date(String deliver_date) {
        this.deliver_date = deliver_date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Freelancer getFreelancer() {
        return freelancer;
    }

    public void setFreelancer(Freelancer freelancer) {
        this.freelancer = freelancer;
    }

    @Override
    public String toString() {
        return "Bid{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", deliver_date=" + deliver_date +
                ", status='" + status + '\'' +
                '}';
    }
}
