package com.example.seeker.Model;


import java.time.LocalDateTime;

public class Project  {
    
    private String title;
    private String description;
    private double budget;
    private String type;
    private String payment_type;
    private String expiry_date;
    private String deadline;

    private String status;
    // 0 -> pending
    //1 -> inProgress
    //2 -> completed


    private Category category;

    public Project(String title, String description, double budget, String type, String payment_type, String  expiry_date, String deadline,  String status) {
        this.title = title;
        this.description = description;
        this.budget = budget;
        this.type = type;
        this.payment_type = payment_type;
        this.expiry_date = expiry_date;
        this.deadline = deadline;
//        this.category = category;
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

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPayment_type() {
        return payment_type;
    }

    public void setPayment_type(String payment_type) {
        this.payment_type = payment_type;
    }

    public String getExpiry_date() {
        return expiry_date;
    }

    public void setExpiry_date(String expiry_date) {
        this.expiry_date = expiry_date;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }
//    public Category getCategory() {
//        return category;
//    }
//
//    public void setCategory(Category category) {
//        this.category = category;
//    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Project{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", budget=" + budget +
                ", type='" + type + '\'' +
                ", payment_type='" + payment_type + '\'' +
                ", expiry_date='" + expiry_date + '\'' +
                ", deadline='" + deadline + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}//End of project
