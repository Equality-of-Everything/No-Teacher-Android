package com.example.android.http;

/**
 * @Auther : Tcy
 * @Date : Create in 2024/5/14 10:22
 * @Decription:
 */

import com.example.android.api.DictionaryAPI;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static final String BASE_URL = "https://api.dictionaryapi.dev/";
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static DictionaryAPI getDictionaryAPI() {
        return getClient().create(DictionaryAPI.class);
    }
}