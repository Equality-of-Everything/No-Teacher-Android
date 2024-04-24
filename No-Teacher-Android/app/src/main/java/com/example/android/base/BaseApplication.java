package com.example.android.base;

import android.app.Application;

import com.google.android.material.color.DynamicColors;

/**
 * @Author : zhang
 * @Date : Created in 2024/4/22 14:28
 * @Decription :
 */


public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // 应用动态颜色
        DynamicColors.applyToActivitiesIfAvailable(this);
    }
}
