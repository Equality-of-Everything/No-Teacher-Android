package com.example.android.viewmodel;

import static com.example.android.constants.BuildConfig.WORD_SERVICE;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.android.api.ApiService;
import com.example.android.bean.entity.ReadLogDataCount;
import com.example.android.bean.entity.WordDetailRecording;
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

    private MutableLiveData<List<WordDetailRecording>> wordDetailRecordingLiveData = new MutableLiveData<>();

    private MutableLiveData<List<WordDetailRecording>> wordDetailRecordingWeekLiveData = new MutableLiveData<>();

    public MutableLiveData<List<WordDetailRecording>> getWordDetailRecordingLiveData() {
        return wordDetailRecordingLiveData;
    }

    public MutableLiveData<List<ReadLogDataCount>> getReadlogDataCountLiveData() {
        return readlogDataCountLiveData;
    }

    public MutableLiveData<List<WordDetailRecording>> getWordDetailRecordingWeekLiveData() {
        return wordDetailRecordingWeekLiveData;
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
                            Log.e("EEEEEEEEEEEEEEEEE", response.body().getData().toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseResponse<List<ReadLogDataCount>>> call, Throwable t) {
                        Log.e("CalenderViewModel-getReadLongDataCount", "NetWork-Error");
                    }
                });
    }

    /**
     * @param context:
     * @param userId:
     * @param month:
     * @return void
     * @author zhang
     * @description 用于获取评分统计数据，按月获取
     * @date 2024/6/5 14:31
     */
    public void getRecordingCountData(Context context, String userId, String month) {
        RetrofitManager.getInstance(context,WORD_SERVICE)
                .getApi(ApiService.class)
                .getRecoringData(userId,month)
                .enqueue(new Callback<BaseResponse<List<WordDetailRecording>>>() {
                    @Override
                    public void onResponse(Call<BaseResponse<List<WordDetailRecording>>> call, Response<BaseResponse<List<WordDetailRecording>>> response) {
                        if (response.isSuccessful() && response.body().getData() != null) {
                            wordDetailRecordingLiveData.postValue(response.body().getData());
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseResponse<List<WordDetailRecording>>> call, Throwable t) {
                        t.printStackTrace();
                        Log.e("CalenderViewModel-getRecordingCountData", "NetWork-Error");
                    }
                });
    }

    /***
     * @param context:
     * @param userId:
     * @return void
     * @author zhang
     * @description 获取最新七天的数据
     * @date 2024/6/7 14:16
     */
    public void getRecordingDataWeek(Context context,String userId) {
        RetrofitManager.getInstance(context,WORD_SERVICE)
                .getApi(ApiService.class)
                .getRecordingDataWeek(userId)
                .enqueue(new Callback<BaseResponse<List<WordDetailRecording>>>() {
                    @Override
                    public void onResponse(Call<BaseResponse<List<WordDetailRecording>>> call, Response<BaseResponse<List<WordDetailRecording>>> response) {

                        if (response.isSuccessful() && response.body().getData() != null) {

                            wordDetailRecordingWeekLiveData.setValue(response.body().getData());
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseResponse<List<WordDetailRecording>>> call, Throwable t) {
                        t.printStackTrace();
                        Log.e("CalenderViewModel-getRecordingDataWeek", "NetWork-Error");
                    }
                });
    }
}
