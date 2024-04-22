package com.example.no_teacher_andorid.bean;

/**
 * @Author : Lee
 * @Date : Created in 2024/4/19 21:16
 * @Decription :
 */

public class User {

    private String email;
    private String password;

    public User() {}
    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}








