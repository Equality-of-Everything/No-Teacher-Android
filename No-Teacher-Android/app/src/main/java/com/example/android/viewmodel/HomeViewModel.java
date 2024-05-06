package com.example.android.viewmodel;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.example.android.api.ApiService;
import com.example.android.bean.entity.WordDetail;
import com.example.android.http.retrofit.BaseResponse;
import com.example.android.http.retrofit.RetrofitManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @Author : Lee
 * @Date : Created in 2024/5/6 10:52
 * @Decription :
 */

public class HomeViewModel extends ViewModel {

    private ApiService apiService;

    public void setApiService(ApiService apiService)
    {
        this.apiService = apiService;
    }

    public void requestTestWord(Context context) {
        RetrofitManager.getInstance(context)
                .getApi(ApiService.class)
                .getWordNum()
                .enqueue(new Callback<BaseResponse<WordDetail>>() {
                    @Override
                    public void onResponse(Call<BaseResponse<WordDetail>> call, Response<BaseResponse<WordDetail>> response) {
                        if(response.isSuccessful() && response.body() != null) {
                            BaseResponse<WordDetail> body = response.body();

                            if(body.isSuccess()) {
                                WordDetail word = body.getData();
                                Log.e("HomeFragment",  word.toString());
                            } else {
                                Log.e("Request Error", "Error from server: " + body.getMessage());
                            }
                        } else {
                            // HTTP错误处理
                            Log.e("HTTP Error", "Response Code: " + response.code() + " Message: " + response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseResponse<WordDetail>> call, Throwable t) {
                        Log.e("HomeFragment-Error", "NetWOrd-Error");
                        t.printStackTrace();
                    }
                });
    }
}
