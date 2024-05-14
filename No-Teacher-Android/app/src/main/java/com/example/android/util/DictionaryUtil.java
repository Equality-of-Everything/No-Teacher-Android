package com.example.android.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DictionaryUtil {

    public static String getWordDefinition(String word) {
        try {
            // 构建 API URL
            String apiUrl = "http://apii.dict.cn/mini.php?q=" + word;
            URL url = new URL(apiUrl);

            // 打开连接
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // 设置请求方法
            connection.setRequestMethod("GET");

            // 获取响应代码
            int responseCode = connection.getResponseCode();

            // 读取响应内容
            BufferedReader reader;
            if (responseCode == HttpURLConnection.HTTP_OK) {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            } else {
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
            }

            // 将响应内容读取为字符串
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            // 返回响应字符串
            return response.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        String word = "test";
        String definition = getWordDefinition(word);
        if (definition != null) {
            System.out.println("Definition of " + word + ": " + definition);
        } else {
            System.out.println("Failed to get definition for " + word);
        }
    }
}
