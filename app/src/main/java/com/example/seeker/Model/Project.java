package com.example.seeker.Model;


import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Project implements Serializable {
//todo 4 hind implemented serializable


    private String title;
    private String description;
    private double budget;
    private String type;
    private String payment_type;
    private String expiry_date;
    private String deadline;
    private List<Milestone> milestones = new ArrayList<>();
    private Employer employer;
    private Set<Skill> skills = new HashSet<>();
    private String status;
    private String createdAt;

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

//  private List<Bid> bids = new ArrayList<>();

// 0 -> pending
    //1 -> inProgress
    //2 -> completed

    //todo hind 1 + added setter and getters + project id and its setters and getters
    private List<Bid> bids = new ArrayList<>();
    private long id;

    private Category category;



    //TODO: HIND ADDED BIDS LIST TO BE ABLE TO ADD BIDS TO THE PROJECT! (SAT MAR.7 - 1:00AM)

    public Project(String title, String description, double budget, String type, String payment_type, String expiry_date, String deadline,  Employer employer, Set<Skill> skills, String status, Category category, List<Bid> bid, String createdAt) {

        this.title = title;
        this.description = description;
        this.budget = budget;
        this.type = type;
        this.payment_type = payment_type;
        this.expiry_date = expiry_date;
        this.deadline = deadline;
        this.employer = employer;
        this.status = status;
        this.skills = skills;
        this.category=category;
        this.bids = bids;
        this.createdAt = createdAt;

    }
    public Project(long id){
        this.id = id;
    }

//    public Project(String title, String description, double budget, String type, String payment_type, String expiry_date, String deadline, List<Milestone> milestones, Employer employer, Set<Skill> skills, String status, Category category) {
//        this.title = title;
//        this.description = description;
//        this.budget = budget;
//        this.type = type;
//        this.payment_type = payment_type;
//        this.expiry_date = expiry_date;
//        this.deadline = deadline;
//        this.employer = employer;
//        this.status = status;
//    }
//




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

    public List<Milestone> getMilestones() {
        return milestones;
    }

    public void setMilestones(List<Milestone> milestones) {
        this.milestones = milestones;
    }

    public Employer getEmployer() {
        return employer;
    }

    public void setEmployer(Employer employer) {
        this.employer = employer;
    }

    public Set<Skill> getSkills() {
        return skills;
    }

    public void setSkills(Set<Skill> skills) {
        this.skills = skills;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }


    public List<Bid> getBids() {
        return bids;
    }

    public void setBids(List<Bid> bids) {
        this.bids = bids;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
                ", milestones=" + milestones +
                ", employer=" + employer +
                ", skills=" + skills +
                ", status='" + status + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", bids=" + bids +
                ", id=" + id +
                ", category=" + category +
                '}';
    }
}//End of project
