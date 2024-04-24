package com.example.android.bean;

/**
 * @Author : Lee
 * @Date : Created in 2024/4/23 9:59
 * @Decription : 请求发送邮箱验证码
 */

public class EmailRequest {
    private String email;

    public EmailRequest() {}
    public EmailRequest(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
