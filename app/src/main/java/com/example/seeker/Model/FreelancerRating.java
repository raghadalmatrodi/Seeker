package com.example.seeker.Model;

public class FreelancerRating {
    private int communication;
    private int professionalism;
    private int onTime;
    private int qualityOfWork;
    private int onBudget;

    private Freelancer freelancer;
    private Employer employer;

    public FreelancerRating(int communication, int professionalism, int onTime, int qualityOfWork, int onBudget, Freelancer freelancer, Employer employer) {
        this.communication = communication;
        this.professionalism = professionalism;
        this.onTime = onTime;
        this.qualityOfWork = qualityOfWork;
        this.onBudget = onBudget;
        this.freelancer = freelancer;
        this.employer = employer;
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

    public Freelancer getFreelancer() {
        return freelancer;
    }

    public void setFreelancer(Freelancer freelancer) {
        this.freelancer = freelancer;
    }

    public Employer getEmployer() {
        return employer;
    }

    public void setEmployer(Employer employer) {
        this.employer = employer;
    }

    @Override
    public String toString() {
        return "FreelancerRating{" +
                "communication=" + communication +
                ", professionalism=" + professionalism +
                ", onTime=" + onTime +
                ", qualityOfWork=" + qualityOfWork +
                ", onBudget=" + onBudget +
                ", freelancer=" + freelancer +
                ", employer=" + employer +
                '}';
    }
}
