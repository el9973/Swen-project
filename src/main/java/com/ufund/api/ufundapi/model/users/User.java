package com.ufund.api.ufundapi.model.users;

public abstract class User {
    
    private String userName;

    public User(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return this.userName;
    }
}
