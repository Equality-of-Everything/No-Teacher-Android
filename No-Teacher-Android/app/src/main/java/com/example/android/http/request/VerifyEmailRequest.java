package com.example.android.http.request;

public class VerifyEmailRequest {
    /*
    请求邮箱验证码类模型
     */
    private String email;
    private String verificationCode;

    public VerifyEmailRequest(String email, String verificationCode) {
        this.email = email;
        this.verificationCode = verificationCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }
}
