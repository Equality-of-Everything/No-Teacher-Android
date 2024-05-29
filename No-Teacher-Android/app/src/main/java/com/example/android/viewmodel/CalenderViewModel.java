package com.example.android.viewmodel;

import static com.example.android.constants.BuildConfig.WORD_SERVICE;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.android.api.ApiService;
import com.example.android.bean.entity.ReadLogDataCount;
import com.example.android.http.retrofit.BaseResponse;
import com.example.android.http.retrofit.RetrofitManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @Author : zhang
 * @Date : Created in 2024/5/29 9:45
 * @Decription :
 */


public class CalenderViewModel extends ViewModel {
    private MutableLiveData<List<ReadLogDataCount>> readlogDataCountLiveData = new MutableLiveData<>();

    public MutableLiveData<List<ReadLogDataCount>> getReadlogDataCountLiveData() {
        return readlogDataCountLiveData;
    }


    public void getReadLongDataCount(Context context, String userId) {
        RetrofitManager.getInstance(context, WORD_SERVICE)
                .getApi(ApiService.class)
                .getReadLongDataCount(userId)
                .enqueue(new Callback<BaseResponse<List<ReadLogDataCount>>>() {
                    @Override
                    public void onResponse(Call<BaseResponse<List<ReadLogDataCount>>> call, Response<BaseResponse<List<ReadLogDataCount>>> response) {
                        if (response.isSuccessful() && response.body().getData() != null) {
                            readlogDataCountLiveData.postValue(response.body().getData());
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseResponse<List<ReadLogDataCount>>> call, Throwable t) {
                        Log.e("CalenderViewModel-getReadLongDataCount", "NetWork-Error");
                    }
                });
    }
}
