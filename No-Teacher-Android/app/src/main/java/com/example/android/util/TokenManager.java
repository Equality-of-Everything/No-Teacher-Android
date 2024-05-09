package com.example.android.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

    /**
     * @param words:
     * @param context:
     * @return void
     * @author Lee
     * @description 每次调用该方法存入8个单词，再次调用覆盖前面存的(从网络获取)
     * @date 2024/5/7 9:16
     */
    public static void saveServerWordsToSharedPreferences(List<String> words, Context context, Runnable onComplete) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("WordPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // 限制保存的单词数量不超过8个
        int wordsCount = Math.min(words.size(), 8);  // 确保不超过8个单词
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < wordsCount; i++) {
            if (stringBuilder.length() > 0) {
                stringBuilder.append(",");
            }
            stringBuilder.append(words.get(i));
        }

        // 保存字符串到SharedPreferences
        editor.putString("words", stringBuilder.toString());
        editor.apply();  // 异步写入

        if (onComplete != null) {
            onComplete.run();  // 在数据保存后执行回调
        }
    }

    /**
     * @param context:
     * @return List<String>
     * @author Lee
     * @description 取出存的8个单词，如果为空返回空列表(从网络获取)
     * @date 2024/5/7 9:20
     */
    public static List<String> loadServerWordsFromSharedPreferences(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("WordPreferences", Context.MODE_PRIVATE);
        String wordsString = sharedPreferences.getString("words", "");

        // 将字符串分割成单词列表
        if (!wordsString.isEmpty()) {
            return Arrays.asList(wordsString.split(","));
        } else {
            return new ArrayList<>(); // 如果没有数据，返回空列表
        }
    }

    /**
     * @param wordMap:
     * @param context:
     * @param onComplete:
     * @return void
     * @author Lee
     * @description 存一下单词和对应id
     * @date 2024/5/9 8:33
     */
    public static void saveServerWordsAndIds(Map<String, Integer> wordMap, Context context, Runnable onComplete) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("WordPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // 只取前8个键值对
        Set<Map.Entry<String, Integer>> entrySet = wordMap.entrySet();
        int count = 0;
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, Integer> entry : entrySet) {
            if (count >= 8) break;  // 确保不超过8个键值对
            if (stringBuilder.length() > 0) {
                stringBuilder.append(",");
            }
            stringBuilder.append(entry.getKey()).append(":").append(entry.getValue());
            count++;
        }

        // 保存字符串到 SharedPreferences
        editor.putString("words", stringBuilder.toString());
        editor.apply();  // 异步写入

        if (onComplete != null) {
            onComplete.run();  // 在数据保存后执行回调
        }
    }

    /**
     * @param context:
     * @return Map<String,Integer>
     * @author Lee
     * @description 取单词和对应的id
     * @date 2024/5/9 8:34
     */
    public static Map<String, Integer> loadServerWordsIds(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("WordPreferences", Context.MODE_PRIVATE);
        String wordsString = sharedPreferences.getString("words", "");

        Map<String, Integer> wordMap = new HashMap<>();
        if (!wordsString.isEmpty()) {
            String[] entries = wordsString.split(",");
            for (String entry : entries) {
                String[] parts = entry.split(":");
                if (parts.length == 2) {
                    wordMap.put(parts[0], Integer.parseInt(parts[1]));
                }
            }
        }
        return wordMap;
    }


}












