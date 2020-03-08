package com.example.seeker.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Freelancer implements Serializable {

    private long id;
    private User user;
    private String maarof_account;
    private int num_of_ratings;
    private int total_response_time;
    private int total_quality_of_work;
    private int num_of_hired_projects;
    private List<Bid> bids = new ArrayList<>();

    public Freelancer(long id, User user, String maarof_account, int num_of_ratings, int total_response_time, int total_quality_of_work, int num_of_hired_projects, List<Bid> bids) {
        this.id = id;
        this.user = user;
        this.maarof_account = maarof_account;
        this.num_of_ratings = num_of_ratings;
        this.total_response_time = total_response_time;
        this.total_quality_of_work = total_quality_of_work;
        this.num_of_hired_projects = num_of_hired_projects;
        this.bids = bids;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getMaarof_account() {
        return maarof_account;
    }

    public void setMaarof_account(String maarof_account) {
        this.maarof_account = maarof_account;
    }

    public int getNum_of_ratings() {
        return num_of_ratings;
    }

    public void setNum_of_ratings(int num_of_ratings) {
        this.num_of_ratings = num_of_ratings;
    }

    public int getTotal_response_time() {
        return total_response_time;
    }

    public void setTotal_response_time(int total_response_time) {
        this.total_response_time = total_response_time;
    }

    public int getTotal_quality_of_work() {
        return total_quality_of_work;
    }

    public void setTotal_quality_of_work(int total_quality_of_work) {
        this.total_quality_of_work = total_quality_of_work;
    }

    public int getNum_of_hired_projects() {
        return num_of_hired_projects;
    }

    public void setNum_of_hired_projects(int num_of_hired_projects) {
        this.num_of_hired_projects = num_of_hired_projects;
    }

    public List<Bid> getBids() {
        return bids;
    }

    public void setBids(List<Bid> bids) {
        this.bids = bids;
    }

    public Freelancer(long id) {
        this.id = id;
    }
}//End class
