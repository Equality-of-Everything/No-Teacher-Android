package com.example.android.viewmodel;

import static com.example.android.constants.BuildConfig.USER_SERVICE;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.android.api.ApiService;
import com.example.android.bean.LexileRequest;
import com.example.android.http.retrofit.BaseResponse;
import com.example.android.http.retrofit.RetrofitManager;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @Author : Lee
 * @Date : Created in 2024/5/17 14:26
 * @Decription :
 */

public class SelectLevelViewModel extends ViewModel {
    private ApiService apiService;

    public void setApiService(ApiService apiService) {
        this.apiService = apiService;
    }


    /**
     * @param context:
     * @param lexile:
     * @return void
     * @author Lee
     * @description 将用户的Lexile值更新到服务器
     * @date 2024/5/17 14:49
     */
    public void updateLexile(Context context, String userId, int lexile) {
        LexileRequest lexileRequest = new LexileRequest(userId, lexile+"");
        HashMap<String, String> params = new HashMap<>();
        params.put("userId", userId);
        params.put("lexile", lexile+"");
        RetrofitManager.getInstance(context, USER_SERVICE)
                .getApi(ApiService.class)
                .updateLexile(params)
                .enqueue(new Callback<BaseResponse<Void>>() {
                    @Override
                    public void onResponse(Call<BaseResponse<Void>> call, Response<BaseResponse<Void>> response) {
                        Log.e("TAG", "onResponse: " + response.toString());
                        if (response.isSuccessful() && response.body() != null) {
                            Log.e("TAG", "onResponse: " + response.body().toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseResponse<Void>> call, Throwable t) {
                        Log.e("SetLexilError", "NetWOrk-Error");
                        t.printStackTrace();
                    }
                });

    }
}
