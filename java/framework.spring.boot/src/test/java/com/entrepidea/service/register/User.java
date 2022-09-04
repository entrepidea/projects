package com.entrepidea.service.register;

import java.time.LocalDateTime;

public class User {
    private String id;
    private String email;
    private LocalDateTime dt;

    public User(String id, String email){
        this.id = id;
        this.email = email;
    }

    public LocalDateTime getRegistrationDate(){
        return dt;
    }
    public void setRegistrationDate(LocalDateTime dt){
        this.dt = dt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
