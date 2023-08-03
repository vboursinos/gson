package com.google.gson.benchmark;

import com.google.gson.benchmark.Profile;

import java.util.List;

public class User {
    private String id;
    private String email;
    private String username;
    private Profile profile;
    private String apiKey;
    private List<String> roles;
    private String createdAt;
    private String updatedAt;

    public User(String id, String email, String username, Profile profile, String apiKey, List<String> roles, String createdAt, String updatedAt) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.profile = profile;
        this.apiKey = apiKey;
        this.roles = roles;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
