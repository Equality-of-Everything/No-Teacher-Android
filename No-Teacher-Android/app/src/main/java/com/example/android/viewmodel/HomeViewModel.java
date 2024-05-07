package com.example.android.viewmodel;

import static com.example.android.constants.BuildConfig.WORD_SERVICE;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.android.api.ApiService;
import com.example.android.bean.entity.Article;
import com.example.android.bean.entity.Result;
import com.example.android.bean.entity.WordDetail;
import com.example.android.http.retrofit.BaseResponse;
import com.example.android.http.retrofit.RetrofitManager;
import com.example.no_teacher_andorid.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @Author : Lee
 * @Date : Created in 2024/5/6 10:52
 * @Decription :
 */

public class HomeViewModel extends ViewModel {

    private MutableLiveData<ArrayList<Article>> articlesLiveData;
    private ApiService apiService;

    public HomeViewModel() {
        articlesLiveData = new MutableLiveData<>();
    }

    // 提供LiveData的访问方法
    public LiveData<ArrayList<Article>> getArticleLiveData() {
        return articlesLiveData;
    }

    public void setApiService(ApiService apiService)
    {
        this.apiService = apiService;
    }

    public void requestTestWordNum(Context context) {
        RetrofitManager.getInstance(context,WORD_SERVICE)
                .getApi(ApiService.class)
                .getWordNum()
                .enqueue(new Callback<BaseResponse<Integer>>() {
                    @Override
                    public void onResponse(Call<BaseResponse<Integer>> call, Response<BaseResponse<Integer>> response) {
                        if(response.isSuccessful() && response.body() != null) {
                            Log.e("AAAAAAAAAA", response.body().getData().toString());

                        } else {
                            // HTTP错误处理
                            Log.e("HTTP Error", "Response Code: " + response.code() + " Message: " + response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseResponse<Integer>> call, Throwable t) {
                        Log.e("HomeFragment-Error", "Network-Error");
                        t.printStackTrace();
                    }
                });
    }

    public void requestTestWords(Context context,int currentPage) {
        RetrofitManager.getInstance(context,WORD_SERVICE)
                .getApi(ApiService.class)
                .getWords(currentPage)
                .enqueue(new Callback<BaseResponse<List<WordDetail>>>() {
                    @Override
                    public void onResponse(Call<BaseResponse<List<WordDetail>>> call, Response<BaseResponse<List<WordDetail>>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            List<WordDetail> data = response.body().getData();
                            Log.e("NNNNNNNNNNNNNN", data.toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseResponse<List<WordDetail>>> call, Throwable t) {
                        Log.e("HomeViewModel-requestTestWords:", "Network-Error");
                        t.printStackTrace();
                    }
                });
    }

    public void fetchArticles() {
        // 模拟数据加载或从网络获取数据
        // 这里以模拟数据为例
        ArrayList<Article> articles = new ArrayList<>();
        articles.add(new Article(R.drawable.friend_item, "标题1", "难度600","200字","人工智能"));
        articles.add(new Article(R.drawable.friend_item, "标题2", "难度600","210字","人工智能"));
        articles.add(new Article(R.drawable.friend_item, "标题3", "难度600","201字","人工智能"));

        // 更新LiveData对象，这将通知观察者数据已改变
        articlesLiveData.postValue(articles);
    }
}
