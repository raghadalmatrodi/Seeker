package com.example.seeker.Model;

public class FreelancerRating {
    private int communication;
    private int professionalism;
    private int onTime;
    private int qualityOfWork;
    private int onBudget;

    private long freelancerId;
    private long employerId;

    public FreelancerRating(int communication, int professionalism, int onTime, int qualityOfWork, int onBudget, long freelancerId, long employerId) {
        this.communication = communication;
        this.professionalism = professionalism;
        this.onTime = onTime;
        this.qualityOfWork = qualityOfWork;
        this.onBudget = onBudget;
        this.freelancerId = freelancerId;
        this.employerId = employerId;
    }

    public int getCommunication() {
        return communication;
    }

    public void setCommunication(int communication) {
        this.communication = communication;
    }

    public int getProfessionalism() {
        return professionalism;
    }

    public void setProfessionalism(int professionalism) {
        this.professionalism = professionalism;
    }

    public int getOnTime() {
        return onTime;
    }

    public void setOnTime(int onTime) {
        this.onTime = onTime;
    }

    public int getQualityOfWork() {
        return qualityOfWork;
    }

    public void setQualityOfWork(int qualityOfWork) {
        this.qualityOfWork = qualityOfWork;
    }

    public int getOnBudget() {
        return onBudget;
    }

    public void setOnBudget(int onBudget) {
        this.onBudget = onBudget;
    }

    public long getFreelancerId() {
        return freelancerId;
    }

    public void setFreelancerId(long freelancerId) {
        this.freelancerId = freelancerId;
    }

    public long getEmployerId() {
        return employerId;
    }

    public void setEmployerId(long employerId) {
        this.employerId = employerId;
    }

    @Override
    public String toString() {
        return "FreelancerRating{" +
                "communication=" + communication +
                ", professionalism=" + professionalism +
                ", onTime=" + onTime +
                ", qualityOfWork=" + qualityOfWork +
                ", onBudget=" + onBudget +
                ", freelancerId=" + freelancerId +
                ", employerId=" + employerId +
                '}';
    }
}
