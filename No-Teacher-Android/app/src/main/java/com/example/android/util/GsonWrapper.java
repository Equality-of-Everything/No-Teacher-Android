package com.example.android.util;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.internal.Primitives;
import com.google.gson.stream.JsonReader;

import java.io.Reader;
import java.io.StringReader;
import java.lang.reflect.Type;

/**
 * @Author wpt
 * @Date 2023/2/22-11:47
 * @desc
 */
public class GsonWrapper {
    static final String TAG = "GsonWrapper";
    private static final Gson GSON;

    public GsonWrapper() {
    }

    public String toJson(Object object) {
        return GSON.toJson(object);
    }

    public String toJson(Object object, Type type) {
        return GSON.toJson(object, type);
    }

    public <T> T fromJson(String json, Class<T> classOfT) {
        if (json != null && classOfT != null) {
            Object object = fromJson((Reader)(new StringReader(json)), (Type)classOfT);

            try {
                return Primitives.wrap(classOfT).cast(object);
            } catch (Throwable var4) {
                Log.e(TAG, "fail fromJson cast=" + var4);
                return null;
            }
        } else {
            return null;
        }
    }

    public <T> T fromJson(String json, Type typeOf) {
        return json != null && typeOf != null ? (T) fromJson((Reader) (new StringReader(json)), (Type) typeOf) : null;
    }

    public <T> T fromJson(Reader json, Type typeOfT) {
        if (json != null && typeOfT != null) {
            try {
                JsonReader jsonReader = new JsonReader(json);
                return GSON.fromJson(jsonReader, typeOfT);
            } catch (Throwable var3) {
                Log.e(TAG, "fail fromJson reader" + var3);
                return null;
            }
        } else {
            return null;
        }
    }

    public Gson gson() {
        return GSON;
    }

    static {
        GSON = new Gson();
    }
}
