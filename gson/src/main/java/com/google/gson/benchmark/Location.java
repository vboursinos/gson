package com.google.gson.benchmark;

public class Location {
    private double lat;
    private double lon; // Using lon instead of long to avoid conflict with the data type

    public Location(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }
// Getters and setters (you can generate these using your IDE)

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }
}