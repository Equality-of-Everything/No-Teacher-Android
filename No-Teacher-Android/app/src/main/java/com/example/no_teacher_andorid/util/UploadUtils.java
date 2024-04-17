package com.example.no_teacher_andorid.util;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.MimeTypeMap;

import com.zhilehuo.advenglish.constants.Constants;
import com.zhilehuo.advenglish.http.intercepter.HeaderInterceptor;

import java.io.File;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class UploadUtils {
    /**
     * 通过上传的文件的完整路径生成RequestBody
     * @param fileNames 完整的文件路径
     * @return
     */
    private static RequestBody getRequestBody(List<String> fileNames) {
        //创建MultipartBody.Builder，用于添加请求的数据
        MultipartBody.Builder builder = new MultipartBody.Builder();
        for (int i = 0; i < fileNames.size(); i++) { //对文件进行遍历
            File file = new File(fileNames.get(i)); //生成文件
            //根据文件的后缀名，获得文件类型
            String fileType = getMimeType(file.getName());
            builder.addFormDataPart( //给Builder添加上传的文件
                    "file",  //请求的名字
                    file.getName(), //文件的文字，服务器端用来解析的
                    RequestBody.create(MediaType.parse(fileType), file) //创建RequestBody，把上传的文件放入
            );
        }
        return builder.build(); //根据Builder创建请求
    }

    public static String getMimeType(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return "";
        }
        String type = null;
        String extension = getExtensionName(filePath.toLowerCase());
        if (!TextUtils.isEmpty(extension)) {
            MimeTypeMap mime = MimeTypeMap.getSingleton();
            type = mime.getMimeTypeFromExtension(extension);
        }
        Log.i("TAG", "url:" + filePath + " " + "type:" + type);

        // FIXME
        if (TextUtils.isEmpty(type) && filePath.endsWith("aac")) {
            type = "audio/aac";
        } else if (TextUtils.isEmpty(type) && filePath.endsWith("mp3")) {
            type = "audio/mpeg";
        } else if (TextUtils.isEmpty(type) && filePath.endsWith("wav")) {
            type = "audio/x-wav";
        } else if (TextUtils.isEmpty(type) && filePath.endsWith("jpg")) {
            type = "image/jpeg";
        } else if (TextUtils.isEmpty(type) && filePath.endsWith("jpeg")) {
            type = "image/jpeg";
        } else if (TextUtils.isEmpty(type) && filePath.endsWith("png")) {
            type = "image/png";
        }

        return type;
    }

    // 获取文件扩展名
    public static String getExtensionName(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length() - 1))) {
                return filename.substring(dot + 1);
            }
        }
        return "";
    }

    /**
     * 获得Request实例
     * @param url
     * @param fileNames 完整的文件路径
     * @return
     */
    private static Request getRequest(String url, List<String> fileNames) {

        Request.Builder builder = new Request.Builder();
        builder.url(url)
                .post(getRequestBody(fileNames));

        // 添加请求日志打印
        logRequest(builder.build());

        return builder.build();
    }

    private static void logRequest(Request request) {
        Log.d("RequestLog", "Request URL: " + request.url());
        Log.d("RequestLog", "Request Method: " + request.method());

        Headers headers = request.headers();
        for (String name : headers.names()) {
            Log.d("RequestLog", "Header: " + name + " = " + headers.get(name));
        }

        RequestBody requestBody = request.body();
        if (requestBody != null) {
            Log.d("RequestLog", "Request Body: " + requestBody.toString());
        }
    }

    /**
     * 根据url，发送异步Post请求
     * @param url 提交到服务器的地址
     * @param fileNames 完整的上传的文件的路径名
     * @param callback OkHttp的回调接口
     */
    public static void upLoadFile(Context context,String url, List<String> fileNames, Callback callback){
//        OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(15, TimeUnit.SECONDS).writeTimeout(60, TimeUnit.SECONDS).readTimeout(60, TimeUnit.SECONDS).build();
        Call call = getClient(context).newCall(getRequest(url, fileNames)) ;
        call.enqueue(callback);
    }

    private static OkHttpClient getClient(Context context){
        SSLSocketFactory sslSocketFactory = null;
        try {
            final SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[]{new X509TrustManager() {
                @Override
                public void checkClientTrusted(
                        X509Certificate[] chain,
                        String authType) throws CertificateException {
                }

                @Override
                public void checkServerTrusted(
                        X509Certificate[] chain,
                        String authType) throws CertificateException {
                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            }}, new java.security.SecureRandom());
            sslSocketFactory = sslContext.getSocketFactory();

        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        OkHttpClient okHttpClient = new OkHttpClient
                .Builder().
                addInterceptor(new HeaderInterceptor(context)).
                connectTimeout(15, TimeUnit.SECONDS).
                writeTimeout(60, TimeUnit.SECONDS).
                readTimeout(60, TimeUnit.SECONDS)
                .sslSocketFactory(sslSocketFactory, new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

                    }

                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[0];
                    }
                })
                .build();
        return okHttpClient;
    }
}
