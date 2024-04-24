package com.example.android.bean;

public class RegisterRequest {
    private String username;
    private String userBirthday;
    private String userSex;
    private String userImage;

    public RegisterRequest(String username, String userBirthday, String userSex, String userImage) {
        this.username = username;
        this.userBirthday = userBirthday;
        this.userSex = userSex;
        this.userImage = userImage;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserBirthday() {
        return userBirthday;
    }

    public void setUserBirthday(String userBirthday) {
        this.userBirthday = userBirthday;
    }

    public String getUserSex() {
        return userSex;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }
}
