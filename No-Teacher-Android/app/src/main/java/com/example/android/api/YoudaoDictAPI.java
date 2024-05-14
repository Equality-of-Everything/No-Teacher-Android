package com.example.android.api;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.UUID;

/**
 * @Auther : Tcy
 * @Date : Create in 2024/5/12 16:08
 * @Decription:
 */


public class YoudaoDictAPI {

    public static String getUsPhonetic(String word, String appId, String appKey) {
        try {
            String salt = UUID.randomUUID().toString(); // 随机生成一个UUID作为salt

            // 构建请求URL
            String url = "https://openapi.youdao.com/v2/dict";

            // 构建请求参数
            String params = String.format("q=%s&langType=auto&appKey=%s&salt=%s&sign=%s&signType=v3&curtime=%s&dicts=ec",
                    word, appId, salt, generateSign(word, appId, appKey, salt), System.currentTimeMillis() / 1000);

            // 发送 HTTP GET 请求
            HttpURLConnection connection = (HttpURLConnection) new URL(url + "?" + params).openConnection();
            connection.setRequestMethod("GET");

            // 获取响应内容
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            // 解析响应并提取美式音标信息
            // 这里需要根据实际返回的数据格式进行解析
            String usPhonetic = parseResponse(response.toString());

            // 关闭连接
            connection.disconnect();

            // 返回美式音标
            return usPhonetic;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 解析响应，提取美式音标信息
    private static String parseResponse(String jsonResponse) {
        // 这里根据实际返回的数据格式进行解析，提取美式音标信息
        // 假设返回的JSON数据包含一个名为 "us-phonetic" 的字段，该字段对应的值即为美式音标
        // 你需要根据具体的返回数据格式进行解析
        // 以下是一个示例解析的代码
        // JSONObject jsonObject = new JSONObject(jsonResponse);
        // String usPhonetic = jsonObject.getString("us-phonetic");
        // return usPhonetic;
        return jsonResponse; // 这里简单地返回整个响应内容作为示例
    }

    // 生成签名
    private static String generateSign(String word, String appId, String appKey, String salt) {
        try {
            String input = word.length() > 20 ? word.substring(0, 10) + word.length() + word.substring(word.length() - 10) : word;
            String signStr = appId + input + salt + System.currentTimeMillis() / 1000 + appKey;
            return sha256(signStr);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 计算SHA-256哈希值
    private static String sha256(String input) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(input.getBytes());
        return Base64.getEncoder().encodeToString(hash);
    }
}
