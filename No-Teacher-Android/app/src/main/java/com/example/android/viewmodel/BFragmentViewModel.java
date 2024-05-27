package com.example.android.viewmodel;

import static com.example.android.constants.BuildConfig.WORD_SERVICE;

import android.content.Context;
import android.util.Log;


import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.android.api.ApiService;
import com.example.android.bean.entity.WordDetail;
import com.example.android.http.retrofit.BaseResponse;
import com.example.android.http.retrofit.RetrofitManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BFragmentViewModel extends ViewModel {
    private ApiService apiService;
    private MutableLiveData<List<WordDetail>> recommendWordsLiveData = new MutableLiveData<>();
    public void setApiService(ApiService apiService) {
        this.apiService = apiService;
    }

    public MutableLiveData<List<WordDetail>> getRecommendWordsLiveData() {
        return recommendWordsLiveData;
    }

    public void setRecommendWords(Context context, String userId, int currentPage){
        RetrofitManager.getInstance(context,WORD_SERVICE).
                getApi(ApiService.class)
                .getRecommendWords(userId,currentPage)
                .enqueue(new Callback<BaseResponse<List<WordDetail>>>() {
                    @Override
                    public void onResponse(Call<BaseResponse<List<WordDetail>>> call, Response<BaseResponse<List<WordDetail>>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            if (response.body().isSuccess()) {
                                List<WordDetail> wordDetails = response.body().getData();
                                recommendWordsLiveData.postValue(wordDetails);
                                // 使用postValue在非UI线程更新LiveData
                                Log.e("BFragment", response.body().getData().toString());
                            } else {
                                // 业务逻辑错误处理
                                Log.e("BBBBBBBBgetWord Error", "Business Error: " + response.body().getMsg());
                            }
                        } else {
                            // HTTP错误处理
                            Log.e("BBBBBBBBgetWord Error", "Response Code: " + response.code() + " Message: " + response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseResponse<List<WordDetail>>> call, Throwable t) {
                        Log.e("BBBBBBBBgetWord Error", "Network Error: " + t.getMessage());
                    }
                });

    }
}
