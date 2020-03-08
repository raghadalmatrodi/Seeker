package com.example.seeker.Model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Bid implements Serializable {
    private long id;
    private String title;
    private String description;
    private double price;
    private String deliver_date;
    private String status;
    private long freelancerId;
    private Freelancer freelancer;

    //todo 10 hind added project obj to bid and its constructor

    private Project project;
    public long getId() {
        return id;
    }

    public Bid(long id, String title, String description, double price, String deliver_date, String status, long freelancerId, Freelancer freelancer) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.deliver_date = deliver_date;
        this.status = status;
        this.freelancerId = freelancerId;
        this.freelancer = freelancer;
    }
    public Bid(long id, String title, String description, double price, String deliver_date, String status, long freelancerId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.deliver_date = deliver_date;
        this.status = status;
        this.freelancerId = freelancerId;
        this.freelancer = freelancer;
    }
    public void setId(long id) {
        this.id = id;
    }

    public Project getProject() {
        return project;
    }
    public long getFreelancerId() {
        return freelancerId;
    }

    public void setFreelancerId(long freelancerId) {
        this.freelancerId = freelancerId;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    //todo: hind added str name of current freelancer to be able to view it in view bids :) + user
    User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    String currentFrName;

    public String getCurrentFrName() {
        return currentFrName;
    }

    public void setCurrentFrName(String currentFrName) {
        this.currentFrName = currentFrName;
    }

    public Bid(String title, String description, double price, String deliver_date, String status, Freelancer freelancer, Project project, User user) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.deliver_date = deliver_date;
        this.status = status;
        this.freelancer = freelancer;
        this.project = project;
        this.user = user;
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
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", deliver_date='" + deliver_date + '\'' +
                ", status='" + status + '\'' +
                ", freelancerId=" + freelancerId +
                ", freelancer=" + freelancer +
                '}';
    }
}
