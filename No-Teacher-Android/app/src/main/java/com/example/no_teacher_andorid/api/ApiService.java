package com.example.no_teacher_andorid.api;

import com.example.no_teacher_andorid.bean.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

//网络服务接口(Retrofit)
public interface ApiService {

    @POST("register")
    Call<User> register(@Body User user);

    @POST("login")
    Call<User> login(@Body User user);
}
