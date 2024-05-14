package com.example.android.http.request;

/**
 * @Auther : Tcy
 * @Date : Create in 2024/5/11 16:34
 * @Decription:
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class HttpRequest {

    public static void main(String[] args) {
        String lan = "uk";
        String text = "petroleum";
        int spd = 3;
        String source = "web";

        try {
            // 构建请求URL
            String urlStr = "https://fanyi.baidu.com/gettts" +
                    "?lan=" + URLEncoder.encode(lan, "UTF-8") +
                    "&text=" + URLEncoder.encode(text, "UTF-8") +
                    "&spd=" + spd +
                    "&source=" + URLEncoder.encode(source, "UTF-8");

            // 创建URL对象
            URL url = new URL(urlStr);

            // 打开连接
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // 设置请求方法
            conn.setRequestMethod("GET");

            // 获取响应码
            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // 读取响应内容
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line;
                StringBuilder response = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                // 打印响应内容
                System.out.println("Response: " + response.toString());
            } else {
                System.out.println("Failed to get response. Response code: " + responseCode);
            }

            // 关闭连接
            conn.disconnect();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

