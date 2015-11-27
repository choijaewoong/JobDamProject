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

    private String user_id = "test@test.com2";
    private String user_name = "user";
    private String message;

    public String getUserId() {
        return user_id;
    }
    public String getUserName() {
        return user_name;
    }
    public String getMessage() {
        return message;
    }
    public void setUserId(String user_id) {
        this.user_id = user_id;
    }
    public void setUserName(String user_name) {
        this.user_name = user_name;
    }
}
