package com.example.seeker.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

//todo 7? hind implemented serialiazble
public class Employer implements Serializable {

    private long id;
    private int  num_of_ratings;
    private int  response_time;
    private int  num_of_posted_Projects;
    private int  total_on_time_payment;
    private User user;
    // حطيت بيسك مو البروجكت العادية عشان مايسوي لوب ومب لازم دايم احط كذا
    private List<Project> projects = new ArrayList<>();

    private float total_emp_ratings;


    //Employer object for rating


    public Employer(long id, int num_of_ratings, int response_time, int total_on_time_payment, float total_emp_ratings) {
        this.id = id;
        this.num_of_ratings = num_of_ratings;
        this.response_time = response_time;
        this.total_on_time_payment = total_on_time_payment;
        this.total_emp_ratings = total_emp_ratings;
    }

    public Employer(long id){
        this.id = id;
    }

//    public Employer(long id, int num_of_ratings, int response_time, int num_of_posted_Projects, int total_on_time_payment, User user, List<Project> projects) {
//        this.id = id;
//        this.num_of_ratings = num_of_ratings;
//        this.response_time = response_time;
//        this.num_of_posted_Projects = num_of_posted_Projects;
//        this.total_on_time_payment = total_on_time_payment;
//        this.user = user;
//        this.projects = projects;
//    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getNum_of_ratings() {
        return num_of_ratings;
    }

    public void setNum_of_ratings(int num_of_ratings) {
        this.num_of_ratings = num_of_ratings;
    }

    public int getResponse_time() {
        return response_time;
    }

    public void setResponse_time(int response_time) {
        this.response_time = response_time;
    }

    public int getNum_of_posted_Projects() {
        return num_of_posted_Projects;
    }

    public void setNum_of_posted_Projects(int num_of_posted_Projects) {
        this.num_of_posted_Projects = num_of_posted_Projects;
    }

    public int getTotal_on_time_payment() {
        return total_on_time_payment;
    }

    public void setTotal_on_time_payment(int total_on_time_payment) {
        this.total_on_time_payment = total_on_time_payment;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public float getTotal_emp_ratings() {
        return total_emp_ratings;
    }

    public void setTotal_emp_ratings(float total_emp_ratings) {
        this.total_emp_ratings = total_emp_ratings;
    }

    @Override
    public String toString() {
        return "Employer{" +
                "id=" + id +
                ", num_of_ratings=" + num_of_ratings +
                ", response_time=" + response_time +
                ", num_of_posted_Projects=" + num_of_posted_Projects +
                ", total_on_time_payment=" + total_on_time_payment +
                ", user=" + user +
                ", projects=" + projects +
                '}';
    }
}
