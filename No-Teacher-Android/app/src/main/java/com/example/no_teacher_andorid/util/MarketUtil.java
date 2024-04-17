package com.example.no_teacher_andorid.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;

import com.zhilehuo.advenglish.base.BaseApplication;


public class MarketUtil {

    public static void gotoMarket(Context context){
        if (context == null){
            return;
        }
        gotoMarket(context,context.getPackageName());
    }

    public static void gotoMarket(Context context,String packageName){
        gotoMarket(context,packageName,"https://a.app.qq.com/o/simple.jsp?pkgname="+packageName);
    }

    /**
     *
     * @param context
     * @param packageName
     * @param jumpUrl 跳转的url，如应用宝
     */
    public static void gotoMarket(Context context, String packageName, String jumpUrl) {
        if (TextUtils.isEmpty(jumpUrl)){
            jumpUrl = "https://a.app.qq.com/o/simple.jsp?pkgname="+packageName;
        }
        if (context == null){
            context = BaseApplication.mInstance;
        }
        if (InstallUtil.isAppInstalled(context, packageName)) {
            Intent intent = context.getApplicationContext().getPackageManager().getLaunchIntentForPackage(packageName);
            context.startActivity(intent);
        } else {
            if (isPublishToMarket()) {
                Uri uri = Uri.parse("market://details?id=" + packageName);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            } else {
                Uri uri = Uri.parse(jumpUrl);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                context.startActivity(intent);
            }
        }
    }

    /**
     * 是否为我们发布的市场
     * @return
     */
    public static boolean isPublishToMarket(){
        String brand = Build.BRAND;
        if (brand.equalsIgnoreCase("HONOR")){
            brand = "HUAWEI";
        }
        if (brand.equalsIgnoreCase("redmi")){
            brand = "XIAOMI";
        }
        return "HUAWEI".equalsIgnoreCase(brand) || "OPPO".equalsIgnoreCase(brand) || "VIVO".equalsIgnoreCase(brand)
                || "XIAOMI".equalsIgnoreCase(brand);
    }

    /**
     * 获取公司品牌,如果不是小米、华为、OPPO、vivo，则返回other
     * @return
     */
    public static String getUpdateBrand(){
        String brand = Build.BRAND;
        if (brand.equalsIgnoreCase("HONOR")){
            brand = "HUAWEI";
        }
        if (brand.equalsIgnoreCase("redmi")){
            brand = "XIAOMI";
        }
        return isPublishToMarket() ? Build.BRAND : "other";
    }
}
