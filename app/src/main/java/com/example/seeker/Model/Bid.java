package com.example.seeker.Model;

import java.time.LocalDateTime;

public class Bid {
    private String title;
    private String description;
    private double price;
    private LocalDateTime deliver_date;
    private String status;

    public Bid(String title, String description, double price, LocalDateTime deliver_date, String status) {
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

    public LocalDateTime getDeliver_date() {
        return deliver_date;
    }

    public void setDeliver_date(LocalDateTime deliver_date) {
        this.deliver_date = deliver_date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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