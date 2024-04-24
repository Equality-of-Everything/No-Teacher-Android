package com.example.android.bean;

/**
 * @Author : Lee
 * @Date : Created in 2024/4/23 10:27
 * @Decription :
 */

public class verifyRequest {
    private String email;
    private String verifyCode;

    public verifyRequest() {}
    public verifyRequest(String email, String verifyCode) {
        this.email = email;
        this.verifyCode = verifyCode;
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
