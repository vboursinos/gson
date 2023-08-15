package com.google.gson.benchmark;

class Country {
    private String countryName;
    private int population;

    public Country(String countryName, int population) {
        this.countryName = countryName;
        this.population = population;
    }

    public String getCountryName() {
        return countryName;
    }

    public int getPopulation() {
        return population;
    }
}