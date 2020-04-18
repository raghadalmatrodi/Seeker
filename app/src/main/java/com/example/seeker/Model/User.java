package com.example.seeker.Model;

import com.stfalcon.chatkit.commons.models.IUser;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

//todo 8 hind implemented serializable
public class User implements Serializable,IUser {
    private long id;
    private String username;
    private String password;
    private Set<Role> roles;
    private String email;
    private StorageDocument avatar;

    private String isEnabled;
    private String phone_number;
    private String national_id;
    private String rating;
    private String current_type;
    private String twitter;
    private String facebook;
    private String linkedIn;
    private String education;
    private List<StorageDocument> sampleWorks = new ArrayList<>();

    public List<StorageDocument> getSampleWorks() {
        return sampleWorks;
    }

    public void setSampleWorks(List<StorageDocument> sampleWorks) {
        this.sampleWorks = sampleWorks;
    }

    public User(long id, String username, Set<Role> roles, String email, String phone_number, String national_id, String rating, String current_type, String twitter, String facebook, String linkedIn, String education) {
        this.id = id;
        this.username = username;
        this.roles = roles;
        this.email = email;
        this.phone_number = phone_number;
        this.national_id = national_id;
        this.rating = rating;
        this.current_type = current_type;
        this.twitter = twitter;
        this.facebook = facebook;

        this.linkedIn = linkedIn;
        this.education = education;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getLinkedIn() {
        return linkedIn;
    }

    public void setLinkedIn(String linkedIn) {
        this.linkedIn = linkedIn;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public User(long id, String username) {
        this.id = id;
        this.username = username;
    }

    public User() {

    }

    public String getId() {
        return id +"";
    }

    @Override
    public String getName() {
        return username;
    }

    @Override
    public String getAvatar() {
        if(avatar !=null)
        return avatar.getUrl();
        else
       return null;
    }

    public void setAvatar(StorageDocument avatar) {
        this.avatar = avatar;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getNational_id() {
        return national_id;
    }

    public void setNational_id(String national_id) {
        this.national_id = national_id;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getCurrent_type() {
        return current_type;
    }

    public void setCurrent_type(String current_type) {
        this.current_type = current_type;
    }

    public String getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(String isEnabled) {
        this.isEnabled = isEnabled;
    }
}
