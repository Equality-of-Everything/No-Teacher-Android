package com.example.no_teacher_andorid.api;

import com.example.no_teacher_andorid.http.request.VerifyEmailRequest;
import com.example.no_teacher_andorid.http.response.VerifyEmailResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface api_service {

    //发验证码
    @POST("send-email-verification-code")
    Call<VerifyEmailResponse> sendEmailVerificationCode(@Body VerifyEmailRequest request);

    //验证邮箱验证码
    @POST("verify-email")
    Call<Void> verifyEmail(@Body VerifyEmailRequest request);
}
