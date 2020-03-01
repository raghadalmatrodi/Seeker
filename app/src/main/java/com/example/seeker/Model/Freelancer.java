package com.example.seeker.Model;

import java.util.ArrayList;
import java.util.List;

public class Freelancer {

    private long id;
    private User user;
    private String maarof_account;
    private int num_of_ratings;
    private int total_response_time;
    private int total_quality_of_work;
    private int num_of_hired_projects;
    private List<Bid> bids = new ArrayList<>();


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Freelancer(long id) {
        this.id = id;
    }
}//End class
