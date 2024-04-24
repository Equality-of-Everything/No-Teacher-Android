package com.example.android.bean.entity;

/**
 * @Author : Lee
 * @Date : Created in 2024/4/19 21:16
 * @Decription :
 */

public class User {

    private String userid;
    private String username;
    private String email;
    private String verifyCode;

    public User() {}
    public User(String userid, String username, String email, String verifyCode) {
        this.userid = userid;
        this.username = username;
        this.email = email;
        this.verifyCode = verifyCode;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }
}








