package com.example.android.util;


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
