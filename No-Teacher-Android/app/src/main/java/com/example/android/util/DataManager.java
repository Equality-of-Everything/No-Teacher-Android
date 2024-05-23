package com.example.android.util;

import androidx.lifecycle.MutableLiveData;

/**
 * @Author : Lee
 * @Date : Created in 2024/5/17 16:10
 * @Decription : 设置两个测试按钮的全局共享单例类
 */

public class DataManager {
    private static DataManager instance;
    private MutableLiveData<Boolean> isSelectLiveData;//测试按钮

    private MutableLiveData<Boolean> isRefreshNameLiveData;//用户名更新
    private MutableLiveData<Boolean> isRefreshAvatarLiveData;//头像更新

    private DataManager() {
        isSelectLiveData = new MutableLiveData();
        isRefreshNameLiveData = new MutableLiveData<>();
        isRefreshAvatarLiveData = new MutableLiveData<>();
    }

    public static synchronized DataManager getInstance() {
        if (instance == null) {
            instance = new DataManager();
        }
        return instance;
    }

    public MutableLiveData<Boolean>getIsSelectLiveData() {
        return isSelectLiveData;
    }

    public void setIsSelectLiveData(Boolean isSelect) {
        isSelectLiveData.postValue(isSelect);
    }

    public MutableLiveData<Boolean> getIsRefreshNameLiveData() {
        return isRefreshNameLiveData;
    }

    public void setIsRefreshNameLiveData(Boolean isRefreshName) {
        isRefreshNameLiveData.postValue(isRefreshName);
    }

    public MutableLiveData<Boolean> getIsRefreshAvatarLiveData() {
        return isRefreshAvatarLiveData;
    }

    public void setIsRefreshAvatarLiveData(Boolean isRefreshAvatar) {
        isRefreshAvatarLiveData.postValue(isRefreshAvatar);
    }
}
