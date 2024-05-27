package com.example.android.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * @Author wpt
 * @Date 2023/2/22-11:49
 * @desc
 */
public class GsonUtils {

    private static Gson gson;

    public static Gson getGsonInstance() {
        if (gson == null) {
            gson = new GsonBuilder()
                    .registerTypeAdapter(Timestamp.class, new TimestampTypeAdapter())
                    .create();
        }
        return gson;
    }
}