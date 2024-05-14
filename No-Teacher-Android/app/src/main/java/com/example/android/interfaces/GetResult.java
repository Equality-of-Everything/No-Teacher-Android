package com.example.android.interfaces;

import com.example.android.bean.entity.TextRes;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface GetResult {

    @POST("api")
    Call<TextRes> getTextRes(@Query("from") String from, @Query("to") String to,
                             @Query("appKey") String ID, @Query("salt") String salt,
                             @Query("sign") String sign, @Query("signType") String signType,
                             @Query("curtime") String curtime, @Query("q") String q);

}