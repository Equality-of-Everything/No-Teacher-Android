package com.example.no_teacher_andorid.http.retrofit;

import android.content.Context;

import com.example.no_teacher_andorid.constants.Constants;
import com.example.no_teacher_andorid.http.intercepter.HeaderInterceptor;

import java.io.File;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by wpt
 */
public class RetrofitManager {

    private long maxSizeCache = 10 * 1024 * 1024;           //最大缓存

    //缓存请求API对象
    private ConcurrentHashMap<String,Object> apiCacheMap = new ConcurrentHashMap();

    private static RetrofitManager instance;
    private Retrofit retrofit;
    private final OkHttpClient client;



    private RetrofitManager(final Context context) {
        File httpCacheDirectory = new File(context.getExternalCacheDir(), "responses");
        //设置缓存 10M
        Cache cache = new Cache(httpCacheDirectory, maxSizeCache);
        //设置https SSL
        // Create a trust manager that does not validate certificate chains
        final TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            @Override
            public void checkClientTrusted(
                    X509Certificate[] chain,
                    String authType) {
            }

            @Override
            public void checkServerTrusted(
                    X509Certificate[] chain,
                    String authType) {
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        }};
        SSLSocketFactory sslSocketFactory = null;
        try {
            final SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null,trustAllCerts, new java.security.SecureRandom());
            sslSocketFactory = sslContext.getSocketFactory();
        } catch (KeyManagementException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        //1.创建Retrofit对象
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(new HeaderInterceptor(context));
        client = builder.sslSocketFactory(sslSocketFactory, new X509TrustManager() {
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
                .readTimeout(30000, TimeUnit.MILLISECONDS)
                .cache(cache)
                .connectTimeout(30000, TimeUnit.MILLISECONDS)
                .hostnameVerifier((hostname, session) -> true)
                .build();
        retrofit = buildRetrofit(Constants.Host.BASE_URL);


    }

    /**
     * 单例模式
     *
     * @return
     */
    public static RetrofitManager getInstance(Context context) {
        if (instance == null) {
            synchronized (RetrofitManager.class) {
                if (instance == null) {
                    instance = new RetrofitManager(context);
                }
            }
        }
        return instance;
    }

    public <T> T getApi(final Class<T> service) {
        if (retrofit == null){
            retrofit = buildRetrofit(Constants.Host.BASE_URL);
        }
        Object cacheService = apiCacheMap.get(service.getName());
        if (cacheService != null){
            return (T) cacheService;
        }
        return retrofit.create(service);
    }

    /**
     *
     * @param service
     * @param baseUrl 域名
     * @param <T>
     * @return
     */
    public <T> T getApi(final Class<T> service,String baseUrl) {
        return buildRetrofit(baseUrl).create(service);
    }

    /**
     * 通过自定义域名构建Retrofit
     * @param baseUrl
     * @return
     */
    private Retrofit buildRetrofit(String baseUrl){
        return new Retrofit.Builder().client(client).baseUrl(baseUrl)// 定义访问的主机地址
                .validateEagerly(true)
                .addConverterFactory(GsonConverterFactory.create())//解析方法 Gson
                .build();
    }

}



