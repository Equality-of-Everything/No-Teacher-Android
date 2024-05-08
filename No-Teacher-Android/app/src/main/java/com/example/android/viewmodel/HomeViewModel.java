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
import com.example.android.util.TokenManager;
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
    private MutableLiveData<Boolean> navigateToWordTest = new MutableLiveData<>();
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

    public LiveData<Boolean> getNavigateToWordTest() {
        return navigateToWordTest;
    }

    /**
     * @param context:
     * @return void
     * @author Lee
     * @description 获取单词总数
     * @date 2024/5/8 9:17
     */
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

    /**
     * @param context:
     * @param currentPage:
     * @return void
     * @author Lee
     * @description 获取测试单词
     * @date 2024/5/8 9:17
     */
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

                            List<String> words = new ArrayList<>();
                            for (WordDetail wordDetail : data) {
                                words.add(wordDetail.getWord());
                                Log.e("AAAAAAAAAA", wordDetail.getWord());
                            }

                            TokenManager.saveServerWordsToSharedPreferences(words, context.getApplicationContext());
                            List<String> word = TokenManager.loadServerWordsFromSharedPreferences(context.getApplicationContext());
                            Log.e("AAAAAAAAAA", word.toString());

                            navigateToWordTest.postValue(true);
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseResponse<List<WordDetail>>> call, Throwable t) {
                        Log.e("HomeViewModel-requestTestWords:", "Network-Error");
                        t.printStackTrace();
                        navigateToWordTest.postValue(false);
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
