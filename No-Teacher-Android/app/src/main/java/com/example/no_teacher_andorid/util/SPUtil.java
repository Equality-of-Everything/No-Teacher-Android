package com.example.no_teacher_andorid.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhilehuo.advenglish.base.BaseApplication;
import com.zhilehuo.advenglish.bean.HomeConfigBean;
import com.zhilehuo.advenglish.bean.UpdateInfo;
import com.zhilehuo.advenglish.bean.UserBean;
import com.zhilehuo.advenglish.constants.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wpt
 */
public class SPUtil {

    private static final String PREFS_NAME = "zhl_edv_english";

    /**
     * 保存int值
     *
     * @param context
     * @param key 保存字段的key
     * @param value 保存字段的值
     */
    public static void setInt(Context context, String key, int value) {
        setInt(context,null,key,value);
    }

    /**
     * 保存int值
     *
     * @param context
     * @param prefsName 保存的sp文件的文件名
     * @param key 保存字段的key
     * @param value 保存字段的值
     */
    public static void setInt(Context context, String prefsName, String key, int value) {
        if (context == null){
            context = BaseApplication.mInstance;
        }
        context = context.getApplicationContext();
        SharedPreferences setting = context.getSharedPreferences(TextUtils.isEmpty(prefsName) ? PREFS_NAME : prefsName, 0);
        SharedPreferences.Editor editor = setting.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public static int getInt(Context context, String key, int defaultValue) {
        return getInt(context,null,key,defaultValue);
    }

    public static int getInt(Context context, String prefsName, String key, int defaultValue) {
        if (context == null){
            context = BaseApplication.mInstance;
        }
        context = context.getApplicationContext();
        SharedPreferences setting = context.getSharedPreferences(TextUtils.isEmpty(prefsName) ? PREFS_NAME : prefsName, 0);
        return setting.getInt(key, defaultValue);
    }


    public static void setStringValue(Context context, String key, String value){
        setStringValue(context,null,key,value);
    }

    /**
     *
     * @param context
     * @param prefsName 保存的sp文件的文件名
     * @param key 保存字段的key
     * @param value 保存字段的值
     */
    public static void setStringValue(Context context, String prefsName, String key, String value) {
        if (context == null){
            context = BaseApplication.mInstance;
        }
        context = context.getApplicationContext();
        SharedPreferences setting = context.getSharedPreferences(TextUtils.isEmpty(prefsName) ? PREFS_NAME : prefsName, 0);
        SharedPreferences.Editor editor = setting.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getStringValue(Context context, String preKey){
        return getStringValue(context,null,preKey);
    }

    public static String getStringValue(Context context, String prefsName, String preKey){
        if (context == null){
            context = BaseApplication.mInstance;
        }
        context = context.getApplicationContext();
        SharedPreferences setting = context.getSharedPreferences(TextUtils.isEmpty(prefsName) ? PREFS_NAME : prefsName, 0);
        return setting.getString(preKey, "");
    }

    public static String getStringValue(Context context, String prefsName, String preKey, String preValue){
        if (context == null){
            context = BaseApplication.mInstance;
        }
        context = context.getApplicationContext();
        SharedPreferences setting = context.getSharedPreferences(TextUtils.isEmpty(prefsName) ? PREFS_NAME : prefsName, 0);
        return setting.getString(preKey, preValue);
    }

    public static void setBooleanValue(Context context, String preKey, boolean preValue){
        setBooleanValue(context,null,preKey,preValue);
    }

    /**
     *
     * @param context
     * @param prefsName 保存的sp文件的文件名
     * @param preKey 保存字段的key
     * @param preValue 保存字段的值
     */
    public static void setBooleanValue(Context context, String prefsName, String preKey, boolean preValue){
        if (context == null){
            context = BaseApplication.mInstance;
        }
        context = context.getApplicationContext();
        SharedPreferences setting = context.getSharedPreferences(TextUtils.isEmpty(prefsName) ? PREFS_NAME : prefsName, 0);
        SharedPreferences.Editor editor = setting.edit();
        editor.putBoolean(preKey, preValue);
        editor.commit();
    }

    public static boolean getBooleanValue(Context context, String preKey){
        return getBooleanValue(context,null,preKey);
    }


    public static boolean getBooleanValue(Context context, String prefsName, String preKey){
        if (context == null){
            context = BaseApplication.mInstance;
        }
        context = context.getApplicationContext();
        SharedPreferences setting = context.getSharedPreferences(TextUtils.isEmpty(prefsName) ? PREFS_NAME : prefsName, 0);
        return setting.getBoolean(preKey, false);
    }

    public static boolean getBooleanValue(Context context, String prefsName, String preKey, boolean preValue){
        if (context == null){
            context = BaseApplication.mInstance;
        }
        context = context.getApplicationContext();
        SharedPreferences setting = context.getSharedPreferences(TextUtils.isEmpty(prefsName) ? PREFS_NAME : prefsName, 0);
        return setting.getBoolean(preKey, preValue);
    }

    public static long getLongValue(Context context, String preKey){
        return getLongValue(context,null,preKey);
    }

    public static long getLongValue(Context context, String prefsName, String preKey){
        if (context == null){
            context = BaseApplication.mInstance;
        }
        context = context.getApplicationContext();
        SharedPreferences setting = context.getSharedPreferences(TextUtils.isEmpty(prefsName) ? PREFS_NAME : prefsName, 0);
        return setting.getLong(preKey, 0);
    }

    public static void setLongValue(Context context, String preKey, long preValue){
        setLongValue(context,null,preKey,preValue);
    }

    /**
     *
     * @param context
     * @param prefsName 保存的sp文件的文件名
     * @param preKey 保存字段的key
     * @param preValue 保存字段的值
     */
    public static void setLongValue(Context context, String prefsName, String preKey, long preValue){
        if (context == null){
            context = BaseApplication.mInstance;
        }
        context = context.getApplicationContext();
        SharedPreferences setting = context.getApplicationContext().getSharedPreferences(TextUtils.isEmpty(prefsName) ? PREFS_NAME : prefsName, 0);
        SharedPreferences.Editor editor = setting.edit();
        editor.putLong(preKey, preValue);
        editor.commit();
    }

    /**
     *保存List
     * @param context
     * @param prefsName 保存的sp文件的文件名
     * @param preKey 保存字段的key
     * @param datalist 保存字段的值
     */
    public static <T> void setDataList(Context context, String prefsName,String preKey, List<T> datalist) {
        if (context == null || null == datalist || datalist.size() <= 0){
            return;
        }
        context = context.getApplicationContext();
        SharedPreferences setting = context.getApplicationContext().getSharedPreferences(TextUtils.isEmpty(prefsName) ? PREFS_NAME : prefsName, 0);
        SharedPreferences.Editor editor = setting.edit();
        Gson gson = new Gson();
        //转换成json数据，再保存
        String strJson = gson.toJson(datalist);
        editor.putString(preKey, strJson);
        editor.apply();
    }

    /**
     * 获取List
     * @param tag
     * @return
     */
    public static <T> List<T> getDataList(Context context, String prefsName,String tag) {
        if (context == null){
            return null;
        }
        context = context.getApplicationContext();
        SharedPreferences sp = context.getSharedPreferences(TextUtils.isEmpty(prefsName) ? PREFS_NAME : prefsName, 0);

        List<T> dataList=new ArrayList<>();
        String strJson = sp.getString(tag, null);
        if (null == strJson) {
            return dataList;
        }
        Gson gson = new Gson();
        dataList = gson.fromJson(strJson, new TypeToken<List<T>>() {
        }.getType());
        return dataList;
    }

    /**
     * 存储一个bean
     *
     * @param key
     * @param bean
     */
    public static void setBean(Context context,String key, Object bean) {
        Gson gson = new Gson();
        setStringValue(context,key,gson.toJson(bean));
    }

    /**
     * 获取一个bean的json
     *
     * @param key
     * @return
     */
    public static String getBean(Context context,String key) {
        return getStringValue(context,key);
    }

    /**
     * 存储一个bean
     *
     * @param context
     * @param bean
     */
    public static void setHomeConfigBean(Context context, HomeConfigBean bean) {
        Gson gson = new Gson();
        setStringValue(context,  Constants.SP.HOME_CONFIG_BEAN,bean == null ? "" :gson.toJson(bean));
    }

    /**
     * 获取一个bean的json
     *
     * @return
     */
    public static HomeConfigBean getHomeConfigBean(Context context) {
        if (context == null){
            context = BaseApplication.mInstance;
        }
        context = context.getApplicationContext();
        String jsonStr = SPUtil.getBean(context,Constants.SP.HOME_CONFIG_BEAN);
        if (TextUtils.isEmpty(jsonStr)){
            return null;
        }
        return new Gson().fromJson(jsonStr,new TypeToken<HomeConfigBean>() {}.getType());
    }

    /**
     * 存储一个bean
     *
     * @param context
     * @param bean
     */
    public static void setUpdateInfo(Context context, UpdateInfo bean){
        Gson gson = new Gson();
        setStringValue(context, Constants.SP.UPDATE_INFO, bean == null ? "" :gson.toJson(bean));
    }

    /**
     * 获取一个bean的json
     *
     * @return
     */
    public static UpdateInfo getUpdateInfo(Context context){
        if (context == null){
            context = BaseApplication.mInstance;
        }
        context = context.getApplicationContext();
        String jsonStr = SPUtil.getBean(context,Constants.SP.UPDATE_INFO);
        if (TextUtils.isEmpty(jsonStr)){
            return null;
        }
        return new Gson().fromJson(jsonStr,new TypeToken<UpdateInfo>() {}.getType());
    }
    /**
     * 存储一个bean
     *
     * @param context
     * @param bean
     */
    public static void setUserBean(Context context,UserBean bean) {
        Gson gson = new Gson();
        setStringValue(context,  Constants.SP.USER,bean == null ? "" :gson.toJson(bean));
    }

    /**
     * 获取一个bean的json
     *
     * @return
     */
    public static UserBean getUserBean(Context context) {
        if (context == null){
            context = BaseApplication.mInstance;
        }
        context = context.getApplicationContext();
        String jsonStr = SPUtil.getBean(context,Constants.SP.USER);
        if (TextUtils.isEmpty(jsonStr)){
            return null;
        }
        return new Gson().fromJson(jsonStr,new TypeToken<UserBean>() {}.getType());
    }

    public static int getLexile(Context context){
        UserBean userBean = getUserBean(context);
        if (userBean == null){
            return -1;
        }
        return userBean.getLexile();
    }

    public static void cleanUserData(Context context){
        if (context == null){
            context = BaseApplication.mInstance;
        }
        context = context.getApplicationContext();
        setStringValue(context,Constants.SP.SID,"");
        setBooleanValue(context,Constants.SP.IS_LOGIN,false);
        setUserBean(context,null);
        setHomeConfigBean(context,null);
    }


}
