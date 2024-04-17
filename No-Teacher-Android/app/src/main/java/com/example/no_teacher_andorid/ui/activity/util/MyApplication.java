package com.example.no_teacher_andorid.ui.activity.util;

import android.app.Application;

import com.google.android.material.color.DynamicColors;

/**
 * @Author : zhang
 * @Date : Created in 2023/12/2 19:31
 * @Decription :动态颜色Application
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // 应用动态颜色
        DynamicColors.applyToActivitiesIfAvailable(this);
    }
}
