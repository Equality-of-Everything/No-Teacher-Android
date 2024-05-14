package com.example.android.util;

import com.example.android.bean.entity.TextRes;
import com.example.android.interfaces.GetResult;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import retrofit2.Call;


/**
 * @Auther : Tcy
 * @Date : Create in 2024/5/13 14:12
 * @Decription:
 */

public class TextTranslator {
    String q;

    public TextTranslator(String s) {
        this.q = s;
    }

    static String APP_KEY = "4f5ddb5467c2046b", signType = "v3";
    static final String APP_SECRET = "elDrp6YBjgec4ZckHUUkeLtaEMT1ynaT";
    static String BASE_URL = "https://openapi.youdao.com/";

    public Call<TextRes> getCall(String from, String to) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final GetResult getR = retrofit.create(GetResult.class);

        String salt = String.valueOf(System.currentTimeMillis());
        String curtime = String.valueOf(System.currentTimeMillis() / 1000);
        String sign = getDigest(APP_KEY + truncate(q) + salt + curtime + APP_SECRET);
        return getR.getTextRes(from, to, APP_KEY, salt, sign, signType, curtime, q);
    }

    private static String truncate(String q) {
        if (q == null) {
            return null;
        }
        int len = q.length();
        return len <= 20 ? q : (q.substring(0, 10) + len + q.substring(len - 10, len));
    }

    private static String getDigest(String string) {
        if (string == null) {
            return null;
        }
        char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8',
                '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        byte[] btInput = string.getBytes(StandardCharsets.UTF_8);
        try {
            MessageDigest mdInst = MessageDigest.getInstance("SHA-256");
            mdInst.update(btInput);
            byte[] md = mdInst.digest();
            int j = md.length;
            char[] str = new char[j * 2];
            int k = 0;
            for (byte byte0 : md) {
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

}
