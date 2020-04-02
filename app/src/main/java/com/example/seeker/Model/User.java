package com.example.seeker.Model;

import com.stfalcon.chatkit.commons.models.IUser;

import java.io.Serializable;
import java.util.Set;

//todo 8 hind implemented serializable
public class User implements Serializable,IUser {
    private long id;
    private String username;
    private String password;
    private Set<Role> roles;
    private String email;
    private String phone_number;
    private String national_id;
    private String rating;
    String education;

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
        return null;
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
}
