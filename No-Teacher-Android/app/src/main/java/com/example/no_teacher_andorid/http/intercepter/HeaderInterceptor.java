package com.example.no_teacher_andorid.http.intercepter;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;

import com.example.no_teacher_andorid.constants.BuildConfig;
import com.example.no_teacher_andorid.constants.Constants;
import com.example.no_teacher_andorid.util.DeviceInfoUtils;
import com.example.no_teacher_andorid.util.SPUtil;
import com.example.no_teacher_andorid.util.YUtils;



import java.io.IOException;
import java.net.URLEncoder;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @Author wpt
 * @Date 2022/12/7-10:49
 * @desc 公用header
 */
public class HeaderInterceptor implements Interceptor {

    private Context mContext;

    public HeaderInterceptor(Context context){
        this.mContext = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder builder = request.newBuilder();

        String devSid = SPUtil.getStringValue(mContext, Constants.SP.DEV_SID);
        String sid = SPUtil.getStringValue(mContext, Constants.SP.SID);
        if (BuildConfig.DEBUG && !TextUtils.isEmpty(devSid)){
            sid = devSid;
        }
        builder.addHeader("channel", BuildConfig.FLAVOR);
        builder.addHeader("platform", "Android");
        builder.addHeader("version", YUtils.getAppVersion(mContext));
        builder.addHeader("brand", encoderStr(Build.BRAND));
        builder.addHeader("deviceType",encoderStr(Build.MODEL));
        builder.addHeader("resolution", DeviceInfoUtils.getResolution(mContext));
        builder.addHeader("size",String.valueOf(DeviceInfoUtils.getScreenInch(mContext)));
        builder.addHeader("systemVersion",encoderStr(Build.VERSION.RELEASE));

        if (!TextUtils.isEmpty(sid)){
            sid = String.format("sid=%s",sid);
            builder.addHeader("Cookie", sid);
        }
        return chain.proceed(builder.build());
    }

    private String encoderStr(String text) throws IOException {
        if (TextUtils.isEmpty(text)){
            return "";
        }
        return URLEncoder.encode(text, "utf-8");
    }
}
