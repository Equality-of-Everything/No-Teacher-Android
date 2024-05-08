package com.example.android.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

//    public static void saveServerWordsToSharedPreferences(List<String> words, Context context) {
//        // 获取SharedPreferences编辑器
//        SharedPreferences sharedPreferences = context.getSharedPreferences("WordPreferences", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//
//        // 将列表转换为一个字符串，这里使用逗号分隔每个单词
//        StringBuilder stringBuilder = new StringBuilder();
//        // 存储至多8个单词
//        int count = Math.min(words.size(), 8);
//        for (int i = 0; i < count; i++) {
//            stringBuilder.append(words.get(i));
//            if (i < count - 1) { // 避免在最后一个单词后添加逗号
//                stringBuilder.append(",");
//            }
//        }
//
//        // 保存字符串到SharedPreferences
//        editor.putString("words", stringBuilder.toString());
//        editor.apply();
//    }


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
     * @param context:
     * @return List<String>
     * @author Lee
     * @description 获取已掌握的单词列表
     * @date 2024/5/7 10:42
     */
    public static List<String> getKnownWords(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("WordPreferences", Context.MODE_PRIVATE);
        String wordsString = sharedPreferences.getString("known_words", "");
        return wordsString.isEmpty() ? new ArrayList<>() : new ArrayList<>(Arrays.asList(wordsString.split(",")));
    }

    /**
     * @param context:
     * @return List<String>
     * @author Lee
     * @description 获取未掌握的单词列表
     * @date 2024/5/7 10:42
     */
    public static List<String> getUnknownWords(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("WordPreferences", Context.MODE_PRIVATE);
        String wordsString = sharedPreferences.getString("unknown_words", "");
        return wordsString.isEmpty() ? new ArrayList<>() : new ArrayList<>(Arrays.asList(wordsString.split(",")));
    }

    /**
     * @param words:
     * @param context:
     * @return void
     * @author Lee
     * @description 存储已掌握的单词列表
     * @date 2024/5/7 10:43
     */
    public static void saveKnownWords(List<String> words, Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("WordPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("known_words", String.join(",", words));
        editor.apply();
    }

    /**
     * @param words:
     * @param context:
     * @return void
     * @author Lee
     * @description 存储未掌握的单词列表
     * @date 2024/5/7 10:43
     */
    public static void saveUnknownWords(List<String> words, Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("WordPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("unknown_words", String.join(",", words));
        editor.apply();
    }


    /**
     * @param word:
     * @param context:
     * @return void
     * @author Lee
     * @description 将单词添加到已掌握列表中
     * @date 2024/5/7 10:45
     */
    public static void addWordToKnownList(String word, Context context) {
        List<String> knownWords = getKnownWords(context);
        List<String> unknownWords = getUnknownWords(context);
        if (!knownWords.contains(word) && !unknownWords.contains(word)) {
            knownWords.add(word);
            saveKnownWords(knownWords, context);
        }
    }

    /**
     * @param word:
     * @param context:
     * @return void
     * @author Lee
     * @description 将单词添加到未掌握列表中
     * @date 2024/5/7 10:45
     */
    public static void addWordToUnknownList(String word, Context context) {
        List<String> knownWords = getKnownWords(context);
        List<String> unknownWords = getUnknownWords(context);
        if (!unknownWords.contains(word) && !knownWords.contains(word)) {
            unknownWords.add(word);
            saveUnknownWords(unknownWords, context);
        }
    }

    /**
     * @param word:
     * @param context:
     * @return void
     * @author Lee
     * @description 将单词从已掌握列表中移除
     * @date 2024/5/7 10:45
     */
    public static void removeWordFromKnownList(String word, Context context) {
        List<String> words = getKnownWords(context);
        if (words.remove(word)) {
            saveKnownWords(words, context);
        }
    }

    /**
     * @param word:
     * @param context:
     * @return void
     * @author Lee
     * @description 将单词从未掌握列表中移除
     * @date 2024/5/7 10:46
     */
    public static void removeWordFromUnknownList(String word, Context context) {
        List<String> words = getUnknownWords(context);
        if (words.remove(word)) {
            saveUnknownWords(words, context);
        }
    }



}
