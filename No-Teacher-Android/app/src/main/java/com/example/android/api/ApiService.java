package com.example.android.api;

import com.example.android.bean.EmailRequest;
import com.example.android.bean.RegisterRequest;
import com.example.android.bean.entity.Result;
import com.example.android.bean.entity.User;
import com.example.android.http.request.VerifyEmailRequest;
import com.example.android.http.retrofit.BaseResponse;

import java.lang.ref.Reference;
import java.util.HashMap;

import kotlin.Metadata;
import kotlin.ParameterName;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

public interface ApiService {

    //发送邮箱验证码
    @POST("/user/login")
    Call<BaseResponse<User>> sendVerificationEmail(@QueryMap HashMap<String, String> params);

    //验证邮箱验证码
    @POST("user/checkLogin")
    Call<BaseResponse<Void>> verifyEmail(@QueryMap HashMap<String,String> params);
    //注册
    @POST("users/register")
    Call<BaseResponse<Void>> registerUser(@Body RegisterRequest registerRequest);


}
















