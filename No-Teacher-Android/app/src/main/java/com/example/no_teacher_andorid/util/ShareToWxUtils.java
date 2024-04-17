package com.example.no_teacher_andorid.util;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zhilehuo.advenglish.R;
import com.zhilehuo.advenglish.constants.Constants;

import java.io.ByteArrayOutputStream;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


/**
 * @Author wpt
 * @Date 2023/4/4-10:59
 * @desc
 */
public class ShareToWxUtils {

    public static IWXAPI api;

    /**
     * 分享网页到微信
     *
     * @param context
     * @param scene 类型，朋友圈、微信好友
     * @param webUrl 网页链接
     * @param logoUrl logo链接
     */
    public static void shareWebToWx(Context context, int scene, String webUrl, String logoUrl
    , String title, String description) {
        if (context == null) {
            return;
        }
        context = context.getApplicationContext();
        if (api == null){
            api = WXAPIFactory.createWXAPI(context, Constants.WX.APP_ID, false);
        }
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = webUrl;
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = title;
        msg.description = description;

        if (TextUtils.isEmpty(logoUrl) || !logoUrl.startsWith("http")) {
            Bitmap bmp = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);
            setBitmap(bmp,msg,scene);
        } else {
            Glide.with(context)
                    .asBitmap()
                    .load(logoUrl)
                    .error(R.mipmap.ic_launcher)
                    .into(new CustomTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            setBitmap(resource,msg,scene);
                        }

                        @Override
                        public void onLoadCleared(@Nullable Drawable placeholder) {

                        }

                        @Override
                        public void onLoadFailed(@Nullable Drawable errorDrawable) {
                            Log.d("onLoadCleared","onLoadFailed");
                            super.onLoadFailed(errorDrawable);
                            BitmapDrawable  bitmap = (BitmapDrawable) errorDrawable;
                            if (bitmap != null){
                                setBitmap(bitmap.getBitmap(),msg,scene);
                            }
                        }
                    });
        }
    }

    private static void setBitmap(Bitmap resource, WXMediaMessage msg, int scene){
        Bitmap thumbBmp = null;
        if (!resource.isRecycled()) {
            thumbBmp = Bitmap.createScaledBitmap(resource, 150, 150, true);
        }
        msg.thumbData = bmpToByteArray(thumbBmp, true);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("webpage");
        req.message = msg;
        req.scene = scene;
        api.sendReq(req);
    }

    private static byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, output);
        if (needRecycle) {
            bmp.recycle();
        }

        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void shareImageToWx(Context context, Bitmap bitmap,int scene){
        if (context == null) {
            return;
        }
        context = context.getApplicationContext();
        if (api == null){
            api = WXAPIFactory.createWXAPI(context, Constants.WX.APP_ID, false);
        }
        if (bitmap == null || bitmap.isRecycled()){
            return;
        }
        WXImageObject imgObj = new WXImageObject(bitmap);

        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = imgObj;

        Bitmap thumbBmp = Bitmap.createScaledBitmap(bitmap, 150, 150, true);
        /*if (bitmap != thumbBmp){
            bitmap.recycle();
        }*/
        msg.thumbData = bmpToByteArray(thumbBmp, true);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("img");
        req.message = msg;
        req.scene = scene;
        api.sendReq(req);
    }

    private static String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

    public static boolean isWeixinAvilible(Context context) {
        IWXAPI mWXApi = WXAPIFactory.createWXAPI(context, Constants.WX.APP_ID, true);

        if(mWXApi.isWXAppInstalled()){
            return true;
        }
        return false;


    }
}
