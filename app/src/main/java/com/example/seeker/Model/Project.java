package com.example.seeker.Model;


import java.time.LocalDateTime;

public class Project  {
    
    private String title;
    private String description;
    private double budget;
    private String type;
    private String payment_type;
    private LocalDateTime expiry_date;
    private LocalDateTime deadline;

    public Project(String title, String description, double budget, String type, String payment_type, LocalDateTime expiry_date, LocalDateTime deadline) {
        this.title = title;
        this.description = description;
        this.budget = budget;
        this.type = type;
        this.payment_type = payment_type;
        this.expiry_date = expiry_date;
        this.deadline = deadline;
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

    public LocalDateTime getExpiry_date() {
        return expiry_date;
    }

    public void setExpiry_date(LocalDateTime expiry_date) {
        this.expiry_date = expiry_date;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    @Override
    public String toString() {
        return
                "\n" + "Title: " + title + "\n" + "\n" +
                        "Description: " + description + "\n" + "\n" +
                        "Budget: " + budget + "\n" + "\n" +
                        "Type: " + type + "\n" + "\n" +
                        "Payment type: " + payment_type + "\n" + "\n" +
                        "Expiry date: " + expiry_date + "\n" + "\n" +
                        "Deadline: " + deadline;
    }

}//End of project
