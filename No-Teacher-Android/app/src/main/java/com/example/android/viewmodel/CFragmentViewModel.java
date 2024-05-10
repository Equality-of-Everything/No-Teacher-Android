package com.example.android.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.android.api.ApiService;
import com.example.android.bean.entity.Article;
import com.example.no_teacher_andorid.R;

import java.util.ArrayList;

public class CFragmentViewModel extends ViewModel {
    private MutableLiveData<ArrayList<Article>> articlesLiveData;
    private MutableLiveData<Boolean> navigateToWordTest = new MutableLiveData<>();
    private ApiService apiService;

    public CFragmentViewModel() {
        articlesLiveData = new MutableLiveData<>();
    }

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
