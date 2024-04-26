package com.example.android.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @Author : Lee
 * @Date : Created in 2024/4/24 16:00
 * @Decription : 用于存取少量数据的工具类
 */

public class TokenManager {
    private static final String TOKEN_PREFS = "TokenPrefs";
    private static final String USER_ID = "userId";

    /**
     * @param context:
     * @param userId:
     * @return void
     * @author Lee
     * @description 登录/注册成功，存一下userId
     * @date 2024/4/26 14:14
     */
    public static void saveUserId(Context context, String userId) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(TOKEN_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_ID, userId);
        editor.apply();
    }

    /**
     * @param context:
     * @return String
     * @author Lee
     * @description 取userId
     * @date 2024/4/26 14:14
     */
    public static String getUserId(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(TOKEN_PREFS, Context.MODE_PRIVATE);
        return sharedPreferences.getString(USER_ID, null);
    }
}
