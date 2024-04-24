package com.example.android.util;

/**
 * @Author wpt
 * @Date 2023/2/22-11:49
 * @desc
 */
public class GsonUtils {

    private static GsonWrapper instance;

    static {
        instance = new GsonWrapper();
    }

    private GsonUtils() {
    }

    public static GsonWrapper getGsonInstance() {
        return instance;
    }
}
