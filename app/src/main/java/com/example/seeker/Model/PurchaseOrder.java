package com.example.seeker.Model;

import java.io.Serializable;

public class PurchaseOrder implements Serializable {
    private Long id;
    private String orderNumber;
    private String transactionId;
    private double amount;
    Milestone milestone;

    public PurchaseOrder() {
    }

    public PurchaseOrder( String orderNumber, String transactionId, double amount , Milestone milestone) {
        this.orderNumber = orderNumber;
        this.transactionId = transactionId;
        this.amount = amount;
        this.milestone = milestone;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Milestone getMilestone() {
        return milestone;
    }

    public void setMilestone(Milestone milestone) {
        this.milestone = milestone;
    }

    @Override
    public String toString() {
        return "PurchaseOrder{" +
                "id=" + id +
                ", orderNumber='" + orderNumber + '\'' +
                ", transactionId='" + transactionId + '\'' +
                ", amount=" + amount +
                ", milestone=" + milestone +
                '}';
    }
}
