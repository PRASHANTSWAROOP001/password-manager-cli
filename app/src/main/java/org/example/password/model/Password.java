package org.example.password.model;

import java.time.LocalDateTime;



public class Password {
    private String id;
    private String password;
    private String notes;
    private LocalDateTime createdAt;
    private String username;
    private String website;
    private boolean deleted;

    public String getNotes() {
        return notes;
    }

    public Password() {
        this.deleted = false;
    }

    public void setId(String id){
        if(id.isEmpty()){
            throw new IllegalArgumentException("id cannot be empty");
        }
        this.id = id;
    }

    public String getId(){
        return this.id;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        if (username.isEmpty()){
            throw new IllegalArgumentException("Username cannot be empty");
        }
        this.username = username;
    }

    public String getWebsite() {
        return this.website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        if(password.isEmpty()){
            throw new IllegalArgumentException("Password cant be empty");
        }
        this.password = password;
    }

    public void setDeleted() {
        this.deleted = true;
    }

    public boolean isDeleted() {
        return this.deleted;
    }
}
