package com.google.gson.benchmark;

public class Profile {
    private String name;
    private String company;
    private String dob;
    private String address;
    private Location location;
    private String about;

    // Getters and setters (you can generate these using your IDE)
    public Profile(String name, String company, String dob, String address, Location location, String about) {
        this.name = name;
        this.company = company;
        this.dob = dob;
        this.address = address;
        this.location = location;
        this.about = about;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

}