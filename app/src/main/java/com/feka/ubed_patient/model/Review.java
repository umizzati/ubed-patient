package com.feka.ubed_patient.model;

public class Review {

    private String user_id;
    private String user_name;
    private String review_msg;
    private float rating;

    public Review(String user_name, String review_msg, float rating) {
        this.user_name = user_name;
        this.review_msg = review_msg;
        this.rating = rating;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getReview_msg() {
        return review_msg;
    }

    public void setReview_msg(String review_msg) {
        this.review_msg = review_msg;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public Review() {
    }

}
