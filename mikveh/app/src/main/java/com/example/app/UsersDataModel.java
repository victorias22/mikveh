package com.example.app;

public class UsersDataModel {

    String email, userName, profile, id;

    //empty constructor required for firebase
    public UsersDataModel() {}

    //constructor for our object class.
    public UsersDataModel(String email, String userName, String profile) {
        this.email = email;
        this.userName = userName;
        this.profile = profile;
        this.id = "";
    }

    //getter and setter methods

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return userName;
    }
    public void setUsername(String userName) {
        this.userName = userName;
    }

    public String getProfile() {
        return profile;
    }
    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getID() {
        return id;
    }
    public void setID(String id_p) {
        this.id = id_p;
    }
}
