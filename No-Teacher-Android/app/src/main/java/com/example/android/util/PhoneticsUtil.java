package com.example.android.util;


/**
 * @Auther : Tcy
 * @Date : Create in 2024/5/11 18:47
 * @Decription:
 */
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class PhoneticsUtil {

    // 获取英式和美式音标
    public static void getPhonetics(String word, PhoneticsCallback callback) {
        // 替换成你的应用ID和应用密钥
        String appId = "4f5ddb5467c2046b";
        String appKey = "elDrp6YBjgec4ZckHUUkeLtaEMT1ynaT";

        try {
            // 构建请求URL
            String url = "https://openapi.youdao.com/v2/dict";

            // 构建请求参数
            String salt = "123456789";
            String sign = generateSign(word, appId, appKey, salt);
            String params = String.format("q=%s&langType=auto&appKey=%s&salt=%s&sign=%s&signType=v3&curtime=%s",
                    word, appId, salt, sign, System.currentTimeMillis() / 1000);

            // 发送 HTTP 请求并获取响应
            HttpURLConnection connection = (HttpURLConnection) new URL(url + "?" + params).openConnection();
            connection.setRequestMethod("GET");

            // 解析响应
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            // 解析响应并提取音标信息
            JSONObject jsonResponse = new JSONObject(response.toString());
            JSONArray phoneticsArray = jsonResponse.getJSONArray("phonetic");
            if (phoneticsArray.length() >= 2) {
                String britishPhonetic = phoneticsArray.getString(0);
                String americanPhonetic = phoneticsArray.getString(1);
                callback.onSuccess(britishPhonetic, americanPhonetic);
            } else {
                callback.onFailure("Failed to parse phonetics");
            }

            // 关闭连接
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
            callback.onFailure("Exception: " + e.getMessage());
        }
    }

    // 生成签名
    private static String generateSign(String word, String appId, String appKey, String salt) {
        long curtime = System.currentTimeMillis() / 1000;
        String input = word.length() > 20 ? word.substring(0, 10) + word.length() + word.substring(word.length() - 10) : word;
        String signStr = appId + input + salt + curtime + appKey;
        return sha256(signStr);
    }

    // 计算SHA-256哈希值
    private static String sha256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    // 定义回调接口
    public interface PhoneticsCallback {
        void onSuccess(String britishPhonetic, String americanPhonetic);
        void onFailure(String errorMessage);
    }
}