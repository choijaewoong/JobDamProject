package com.example.androidchoi.jobdam.Model;

/**
 * Created by Choi on 2015-11-16.
 */
public class User {

    private static User instance;
    public static User getInstance(){
        if (instance == null) {
            instance = new User();
        }
        return instance;
    }

    private String userId = "test@test.com22";
    private String userName = "user";
    private String message;

    public String getUserId() {
        return userId;
    }
    public String getUserName() {
        return userName;
    }
    public String getMessage() {
        return message;
    }
    public void setUser(String userId , String userName) {
        this.userId = userId;
        this.userName = userName;
    }
}
