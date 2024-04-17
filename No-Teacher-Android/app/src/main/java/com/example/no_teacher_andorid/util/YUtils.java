/**
 * YUtils.java[V1.0.0]
 *
 * @author 李斐 Create at 2019-3-7 11:06:10
 */
package com.example.no_teacher_andorid.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.content.FileProvider;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 常用工具
 * by：李斐 at:2020-01-13
 */
public class YUtils {
    private static final String TAG = "YUtils";
    public static final int REQUEST_CODE_APP_INSTALL = 9003;

    /**
     * 记录上次点击时间
     */
    private static long lastClickTime;

    /**
     * 是否快速双击点击
     *
     * @return isFastDoubleClick
     */
    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < 1000) {
            return true;
        } else {
            lastClickTime = time;
            return false;
        }
    }




    /**
     * manifest 中的 versionName 字段
     */
    public static String getAppVersion(Context context) {
        String appVersionName = null;
        if (appVersionName == null) {
            PackageManager manager = context.getPackageManager();
            try {
                android.content.pm.PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
                appVersionName = info.versionName;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (appVersionName == null) {
            return "";
        } else {
            return appVersionName;
        }
    }


    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 打开APK程序代码 安装apk by:lif 2016-03-21 08:57:03
     *
     * @param activity
     * @param file
     */
    private static void installApk(Activity activity, File file) {
        Log.e("OpenFile", file.getName());
//        Intent intent = new Intent();
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        intent.setAction(Intent.ACTION_VIEW);
//        intent.setDataAndType(Uri.fromFile(file),
//                "application/vnd.android.package-archive");

        Intent intent = new Intent(Intent.ACTION_VIEW);
        //版本在7.0以上是不能直接通过uri访问的
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N) {


//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                boolean hasInstallPermission = isHasInstallPermissionWithO(activity);
//                if (!hasInstallPermission) {
//                    startInstallPermissionSettingActivity(activity);
//                    return;
//                }
//            }else {
            //7.0以上
            //File file = (new File(apkPath));
            // 由于没有在Activity环境下启动Activity,设置下面的标签
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //参数1 上下文, 参数2 Provider主机地址 和配置文件中保持一致   参数3  共享的文件
            Uri apkUri = FileProvider.getUriForFile(activity, activity.getPackageName() + ".fileprovider", file);
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            //Uri contentUri = FileProvider.getUriForFile(mContext, "应用包名" + ".fileProvider", file);//参数二:应用包名+".fileProvider"(和步骤一中的Manifest文件中的provider节点下的authorities对应)
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
//            }
            activity.startActivity(intent);

        } else {
            //小于7.0
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
            activity.startActivity(intent);
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static boolean isHasInstallPermissionWithO(Context context) {
        if (context == null) {
            return false;
        }
        return context.getPackageManager().canRequestPackageInstalls();
    }

    /**
     * 开启设置安装未知来源应用权限界面
     *
     * @param context
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private static void startInstallPermissionSettingActivity(Context context) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES);
        ((Activity) context).startActivityForResult(intent, REQUEST_CODE_APP_INSTALL);
    }

    /* 卸载apk */
    public static void uninstallApk(Context context, String packageName) {
        Uri uri = Uri.parse("package:" + packageName);
        Intent intent = new Intent(Intent.ACTION_DELETE, uri);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }



    public static Date stringDateToDate(String dateStr, String format) {

        SimpleDateFormat sdf = new SimpleDateFormat(format);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        Date date = null;
        try {
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static long strDateTolong(String dateStr, String format) {

        SimpleDateFormat sdf = new SimpleDateFormat(format);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        long time = 0;
        try {
            time = sdf.parse(dateStr).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

    public static String getDateTimeString(long milliseconds, String format) {
        Date date = new Date(milliseconds);
        SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.getDefault());
        return formatter.format(date);
    }


    private static Date stringToDate(String strTime, String formatType) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(formatType);
            Date date;
            date = formatter.parse(strTime);
            return date;
        } catch (Exception e) {
            return null;
        }
    }

    public static String switchCreateTime(String createTime) {
        String formatStr2 = null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");//注意格式化的表达式
        try {
            Date time = format.parse(createTime);
            String date = time.toString();
            //将西方形式的日期字符串转换成java.util.Date对象
            SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
            Date datetime = (Date) sdf.parse(date);
            //再转换成自己想要显示的格式
//            formatStr2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(datetime);
//            formatStr2 = new SimpleDateFormat("yyyy-MM-dd").format(datetime);
            formatStr2 = new SimpleDateFormat("yyyy.MM.dd").format(datetime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formatStr2;
    }

    public static String switchYMdHms2YpMpd(String createTime) {
        String formatStr2 = null;
        String formatStr = (createTime != null && createTime.indexOf(" ") != -1) ? "yyyy-MM-dd HH:mm:ss" : "yyyy-MM-dd";
        SimpleDateFormat format = new SimpleDateFormat(formatStr);//注意格式化的表达式
        try {
            Date time = format.parse(createTime);

            //再转换成自己想要显示的格式
//            formatStr2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(datetime);
//            formatStr2 = new SimpleDateFormat("yyyy-MM-dd").format(datetime);
            formatStr2 = new SimpleDateFormat("yyyy.MM.dd").format(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formatStr2;
    }

    public static String switchCreateTime(String createTime, String format1) {
        String formatStr2 = null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");//注意格式化的表达式
        try {
            Date time = format.parse(createTime);
            String date = time.toString();
            //将西方形式的日期字符串转换成java.util.Date对象
            SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
            Date datetime = (Date) sdf.parse(date);
            //再转换成自己想要显示的格式
//            formatStr2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(datetime);
//            formatStr2 = new SimpleDateFormat("yyyy-MM-dd").format(datetime);
            if (format1 != null) {
                formatStr2 = new SimpleDateFormat(format1).format(datetime);
            } else {
                formatStr2 = new SimpleDateFormat("yyyy.MM.dd").format(datetime);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formatStr2;
    }


    /**
     * 校验国内手机号
     * @param mobile
     * @return
     */
    public static boolean isMobilePhoneNumber(String mobile) {
        String pattern = "^1[3-9]\\d{9}$";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(mobile);
        return m.matches();
    }

    /**
     * 校验国内座机
     * @param number
     * @return
     */
    public static boolean isTelPhoneNumber(String number){
        //^([0-9]{3}-?[0-9]{8})|([0-9]{4}-?[0-9]{7})$
        String pattern = "^((0\\d{2,3})[-]?\\d{7,8})$";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(number);
        return m.matches();
    }

    public static boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();

    }
}
