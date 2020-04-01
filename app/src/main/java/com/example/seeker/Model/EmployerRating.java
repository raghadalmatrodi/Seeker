package com.example.seeker.Model;

public class EmployerRating {

    private int communication;
    private int professionalism;
    private int onTimePayment;

    private Freelancer freelancer;
    private Employer employer;

    public EmployerRating(int communication, int professionalism, int onTimePayment, Freelancer freelancer, Employer employer) {
        this.communication = communication;
        this.professionalism = professionalism;
        this.onTimePayment = onTimePayment;
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

    public int getOnTimePayment() {
        return onTimePayment;
    }

    public void setOnTimePayment(int onTimePayment) {
        this.onTimePayment = onTimePayment;
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
        return "EmployerRating{" +
                "communication=" + communication +
                ", professionalism=" + professionalism +
                ", onTimePayment=" + onTimePayment +
                ", freelancer=" + freelancer +
                ", employer=" + employer +
                '}';
    }
}
