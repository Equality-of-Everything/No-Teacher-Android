package com.example.android.viewmodel;

import static com.example.android.constants.BuildConfig.USER_SERVICE;
import static com.example.android.constants.BuildConfig.WORD_SERVICE;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.android.api.ApiService;
import com.example.android.bean.entity.Article;
import com.example.android.bean.entity.Result;
import com.example.android.bean.entity.UserLevel;
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

    private MutableLiveData<List<Article>> articlesLiveData;
    private MutableLiveData<Boolean> navigateToWordTest = new MutableLiveData<>();
    private MutableLiveData<Boolean> isTestLiveData = new MutableLiveData<>();
    private MutableLiveData<Long> TodayReadDurationLiveData=new MutableLiveData<>();
    private MutableLiveData<Integer> currentPageLiveData = new MutableLiveData<>();
    private MutableLiveData<Integer> TotalWordNumLiveData=new MutableLiveData<>();
    private MutableLiveData<Integer> TodayReadWordNumLiveData=new MutableLiveData<>();

    private ApiService apiService;
    private int lexile = 110;
    private int curPage = 0;
    private int totalPages = 1;//初始设置为-1，表示还未获取到总页数

    public HomeViewModel() {
        articlesLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<Integer> getCurrentPageLiveData() {
        return currentPageLiveData;
    }


    // 提供LiveData的访问方法
    public LiveData<List<Article>> getArticleLiveData() {
        return articlesLiveData;
    }

    public MutableLiveData<Boolean> getIsTestLiveData() {
        return isTestLiveData;
    }
    public  MutableLiveData<Long> getTodayReadDurationLiveData(){
        return TodayReadDurationLiveData;
    }

    public  MutableLiveData<Integer> getTotalWordNumLiveData(){
        return TotalWordNumLiveData;
    }

    public MutableLiveData<Integer> getTodayReadWordNumLiveData(){
        return  TodayReadWordNumLiveData;
    }
    public void setApiService(ApiService apiService) {
        this.apiService = apiService;
    }

    public LiveData<Boolean> getNavigateToWordTest() {
        return navigateToWordTest;
    }

    //获取当前页码
    public int getCurrentPage() {
        return curPage;
    }

    public int getTotalPages() {
        return totalPages;
    }

    /**
     * @param context:
     * @return void
     * @author Lee
     * @description 获取单词总数
     * @date 2024/5/8 9:17
     */
    public void requestTestWordNum(Context context) {
        RetrofitManager.getInstance(context, WORD_SERVICE)
                .getApi(ApiService.class)
                .getWordNum()
                .enqueue(new Callback<BaseResponse<Integer>>() {
                    @Override
                    public void onResponse(Call<BaseResponse<Integer>> call, Response<BaseResponse<Integer>> response) {
                        if(response.isSuccessful() && response.body() != null) {
                            Log.e("AllArticleNum", response.body().getData().toString());

                            Integer num = response.body().getData();
                            totalPages = (int) Math.ceil(num / 5.0);  // 计算总页数
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
    public void requestTestWords(Context context, int currentPage) {
        RetrofitManager.getInstance(context, WORD_SERVICE)
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

    /**
     * @param context:
     * @param userId:
     * @return void
     * @author zhang
     * @description 是否已经进行过单词测试
     * @date 2024/5/11 14:40
     */
    public void isTest(Context context, String userId) {
        RetrofitManager.getInstance(context, WORD_SERVICE)
                .getApi(ApiService.class)
                .isTest(userId)
                .enqueue(new Callback<BaseResponse<UserLevel>>() {
                    @Override
                    public void onResponse(Call<BaseResponse<UserLevel>> call, Response<BaseResponse<UserLevel>> response) {
                        if (response.isSuccessful() && response.body().getData() != null) {
                            isTestLiveData.setValue(true);
                            return;
                        }
                        isTestLiveData.postValue(false);
                    }

                    @Override
                    public void onFailure(Call<BaseResponse<UserLevel>> call, Throwable t) {
                        t.printStackTrace();
                        Log.e("UserTestViewModel-isTest", "Network-Error");
                    }
                });
    }

    public void getTodayReadDuration(Context context ,String userId){
        RetrofitManager.getInstance(context, WORD_SERVICE)
                .getApi(ApiService.class)
                .getTodayReadDuration(userId)
                .enqueue(new Callback<BaseResponse<Long>>() {
                    @Override
                    public void onResponse(Call<BaseResponse<Long>> call, Response<BaseResponse<Long>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                                Log.e("getTodayReadDuration", ""+response.body().getData()+ response.body().getMsg());
                                TodayReadDurationLiveData.postValue(response.body().getData());
                            }else {
                            TodayReadDurationLiveData.setValue(0L);
                            Log.e("getTodayReadDuration", "Business Error: " + response.body().getMsg());
                        }
                    }
                    @Override
                    public void onFailure(Call<BaseResponse<Long>> call, Throwable t) {
                        Log.e("getTodayReadDuration-Error", "Network-Error");
                        t.printStackTrace();
                    }
                });
    }

    public void getTotalWordNum(Context context,String userId){
        RetrofitManager.getInstance(context, WORD_SERVICE)
                .getApi(ApiService.class)
                .getTotalWordNum(userId)
                .enqueue(new Callback<BaseResponse<Integer>>() {
                    @Override
                    public void onResponse(Call<BaseResponse<Integer>> call, Response<BaseResponse<Integer>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            Log.e("getTotalWordNum", ""+response.body().getData()+ response.body().getMsg());
                            TotalWordNumLiveData.postValue(response.body().getData());
                        }else {
                            TotalWordNumLiveData.setValue(0);
                            Log.e("getTotalWordNum", "Business Error: " + response.body().getMsg());
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseResponse<Integer>> call, Throwable t) {
                        Log.e("getTotalWordNum-Error", "Network-Error");
                        t.printStackTrace();
                    }
                });
    }

    public void getTodayReadWordNumByuserId(Context context,String userId){
        RetrofitManager.getInstance(context, WORD_SERVICE)
                .getApi(ApiService.class)
                .getTodayReadWordNumByuserId(userId)
                .enqueue(new Callback<BaseResponse<Integer>>() {
                    @Override
                    public void onResponse(Call<BaseResponse<Integer>> call, Response<BaseResponse<Integer>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            Log.e("getTodayReadWordNumByuserId", ""+response.body().getData()+ response.body().getMsg());
                            TodayReadWordNumLiveData.postValue(response.body().getData());
                        }else {
                            Log.e("getTodayReadWordNumByuserId", "Business Error: " + response.body().getMsg());
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseResponse<Integer>> call, Throwable t) {
                        Log.e("getTodayReadWordNumByuserId-Error", "Network-Error");
                        t.printStackTrace();
                    }
                });
    }


    /**
     * @param context:
     * @return void
     * @author Lee
     * @description 获取文章总数
     * @date 2024/5/15 14:52
     */
    public void getArticleNum(Context context){
        RetrofitManager.getInstance(context, WORD_SERVICE)
                .getApi(ApiService.class)
                .getArticleNum(lexile)
                .enqueue(new Callback<BaseResponse<Integer>>() {
                    @Override
                    public void onResponse(Call<BaseResponse<Integer>> call, Response<BaseResponse<Integer>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            Log.e("AAAAAAAAAA", response.body().getData().toString());

                        } else {
                            // HTTP错误处理
                            Log.e("getArticleNum Error", "Response Code: " + response.code() + " Message: " + response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseResponse<Integer>> call, Throwable t) {
                        Log.e("HomeFragment-Error", "Network-Error");
                    }
                });
    }

    /**
     * @param lexile:
     * @param currentPage:
     * @return void
     * @author zhang
     * @description 通过英文水平和难度去获取文章
     * @date 2024/5/13 16:20
     */
    public void fetchArticles(Context context, int lexile, int currentPage) {
        RetrofitManager.getInstance(context, WORD_SERVICE).
                getApi(ApiService.class)
                .getArticles(lexile, currentPage)
                .enqueue(new Callback<BaseResponse<List<Article>>>() {
                    @Override
                    public void onResponse(Call<BaseResponse<List<Article>>> call, Response<BaseResponse<List<Article>>> response) {
                        if (response.isSuccessful() && response.body().getData() != null) {
                            List<Article> articles = response.body().getData();
                            // 更新LiveData对象，这将通知观察者数据已改变
                            articlesLiveData.postValue(articles);
                        }
                    }
                    @Override
                    public void onFailure(Call<BaseResponse<List<Article>>> call, Throwable t) {
                        Log.e("HomeViewModel-fetchArticles", "Network-Error");
                    }
                });
    }

    /**
     * @param context:
     * @return void
     * @author Lee
     * @description 获取所有文章（文库）
     * @date 2024/5/16 8:12
     */
    public void fetchAllArticle(Context context, int typeId,int lexile){
        RetrofitManager.getInstance(context, WORD_SERVICE)
                .getApi(ApiService.class)
                .getAllArticle()
                .enqueue(new Callback<BaseResponse<List<Article>>>() {
                    @Override
                    public void onResponse(Call<BaseResponse<List<Article>>> call, Response<BaseResponse<List<Article>>> response) {
                        if(response.isSuccessful() && response.body() != null) {
//                            Log.e("getAllArticle", response.body().getData().toString());

                            List<Article> list = response.body().getData();
                            List<Article> articles = new ArrayList<>();
                            for(Article article : list) {
                                if(article.getTypeId() == typeId && article.getLexile() == lexile) {
                                    articles.add(article);
                                }
                                articlesLiveData.postValue(articles);
//                                Log.e("ArticleType", article.getTypeId()+"");
                            }


                        } else {
                            // HTTP错误处理
                            Log.e("HTTP Error", "Response Code: " + response.code() + " Message: " + response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseResponse<List<Article>>> call, Throwable t) {
                        Log.e("UserTest-Error", "Network-Error");
                        t.printStackTrace();
                    }
                });
    }



}
