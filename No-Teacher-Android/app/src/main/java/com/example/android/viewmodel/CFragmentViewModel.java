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
}
