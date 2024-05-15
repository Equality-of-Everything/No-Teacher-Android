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
        String cover = "http://shiqu-img.qn.zhilehuo.com/advEnglish/img/formal/z863633071_The_Food_Chain_--ar_43_--niji_5_7fed0c63-53a0-4435-832c-1e36d1cc417f_3.png";
        articles.add(new Article(cover, "标题1", 600,200,"人工智能"));
        articles.add(new Article(cover, "标题1", 600,200,"人工智能"));
        articles.add(new Article(cover, "标题1", 600,200,"人工智能"));
        articles.add(new Article(cover, "标题1", 600,200,"人工智能"));
        // 更新LiveData对象，这将通知观察者数据已改变
        articlesLiveData.postValue(articles);
    }
}
