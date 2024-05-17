package com.example.android.util;

import androidx.lifecycle.MutableLiveData;

/**
 * @Author : Lee
 * @Date : Created in 2024/5/17 16:10
 * @Decription : 设置两个测试按钮的全局共享单例类
 */

public class DataManager {
    private static DataManager instance;
    private MutableLiveData<Boolean> isSelectLiveData;

    private DataManager() {
        isSelectLiveData = new MutableLiveData();
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
}
