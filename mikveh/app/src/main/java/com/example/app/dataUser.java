package com.example.app;

public class dataUser {
    private String address;
    private String city;
    private String time;
    private String date;
    private String name;
    private String id;


    //Constructors
    public dataUser() {}
    public dataUser(String address, String city, String time, String date, String name, String id) {
        this.address = address;
        this.city = city;
        this.time = time;
        this.date = date;
        this.name = name;
        this.id = "";
    }

    //Getters and Setters
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }

    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getID() { return id; }
    public void setID(String id_p) { this.id = id_p; }


}
