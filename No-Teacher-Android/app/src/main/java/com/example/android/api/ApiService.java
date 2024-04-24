package com.example.android.api;

import com.example.android.bean.EmailRequest;
import com.example.android.bean.RegisterRequest;
import com.example.android.http.request.VerifyEmailRequest;
import com.example.android.http.retrofit.BaseResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {

    //发送邮箱验证码
    @POST("user/login")
    Call<BaseResponse<Void>> sendVerificationEmail(@Body EmailRequest email);

    //验证邮箱验证码
    @POST("users/verify")
    Call<BaseResponse<Void>> verifyEmail(@Body VerifyEmailRequest request);
    //发验证码
    @POST("users/register")
    Call<BaseResponse<Void>> registerUser(@Body RegisterRequest registerRequest);
}
















