package com.example.android.api;

import com.example.android.bean.entity.Words;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface DictionaryAPI {
    @GET("api/v2/entries/en/{word}")
    Call<List<Words>> getWordDetails(@Path("word") String word);
}