package com.example.seeker.Rating;

import android.widget.RatingBar;

public class PagerModel {

    String id;
    String title;
    private RatingBar ratingBar;


    public RatingBar getRatingBar() {
        return ratingBar;
    }

    public void setRatingBar(RatingBar ratingBar) {
        this.ratingBar = ratingBar;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public PagerModel(String id, String title) {
        this.id = id;
        this.title = title;
    }
}//End class
