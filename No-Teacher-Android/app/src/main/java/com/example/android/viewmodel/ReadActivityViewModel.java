package com.example.android.viewmodel;

import static com.example.android.constants.BuildConfig.USER_SERVICE;
import static com.example.android.constants.BuildConfig.WORD_SERVICE;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.example.android.api.ApiService;
import com.example.android.bean.entity.ReadLog;
import com.example.android.http.retrofit.BaseResponse;
import com.example.android.http.retrofit.RetrofitManager;
import com.example.android.util.GsonUtils;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @Author : zhang
 * @Date : Created in 2024/5/24 17:02
 * @Decription :
 */


public class ReadActivityViewModel extends ViewModel {

    // 用于插入阅读记录
    public void insertReadLog(ReadLog readLog, Context context) {
        String json_readLog = GsonUtils.getGsonInstance().toJson(readLog);
        HashMap<String, String> params = new HashMap<>();
        params.put("json_readLog", json_readLog);
        Log.e("json_readLog", json_readLog);
        RetrofitManager.getInstance(context, WORD_SERVICE)
                .getApi(ApiService.class).
                insertReadLog(params)
                .enqueue(new Callback<BaseResponse<ReadLog>>() {
                    @Override
                    public void onResponse(Call<BaseResponse<ReadLog>> call, Response<BaseResponse<ReadLog>> response) {
                        if (response.isSuccessful() && response.body().getData() != null) {
                            Log.e("AAAAAAAAAAAAAAA", response.body().getData().toString());
                        }
                        Log.e("BBBBBBBBBBBBBBB", response.isSuccessful() + "");
                    }

                    @Override
                    public void onFailure(Call<BaseResponse<ReadLog>> call, Throwable t) {
                        t.printStackTrace();
                        Log.e("ReadActivityViewModel-insertReadLog","NetWork-Error");
                    }
                });
    }
}
