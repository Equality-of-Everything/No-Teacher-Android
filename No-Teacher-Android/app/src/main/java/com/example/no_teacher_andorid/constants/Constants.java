package com.example.no_teacher_andorid.constants;


/**
 * @Author wpt
 * @Date 2023/2/21-10:22
 * @desc
 */
public class Constants {

    public static final String PRIVACY_AGREEMENT = "https://com-zhilehuo-images0.oss-cn-hangzhou.aliyuncs.com/shiqu/web/html/sqgjyyyszc0720.html";
    public static final String USER_AGREEMENT = "https://com-zhilehuo-images0.oss-cn-hangzhou.aliyuncs.com/shiqu/web/html/sqgjyyyhxy0720.html";

    public static class Host {
        public static String BASE_URL_TEST = "https://test.shiqu.zhilehuo.com/englishgpt/";
//        public static String BASE_URL_TEST = "https://h3og0xytwewt.guyubao.com/";
        public static String BASE_URL_RELEASE = "https://shiqu.zhilehuo.com/advenglish/";
        public static String BASE_URL = "test".equalsIgnoreCase(BuildConfig.BUILD_ENV) ? BASE_URL_TEST : BASE_URL_RELEASE;

        //上传图片的地址
        public static String UPLOAD_IMG = BASE_URL + "user/uploadHeadImg";
    }

    public static class SP {
        public static String IS_LOGIN = "isLogin";
        public static String SID = "sid";
        public static String DEV_SID = "devSid";
        public static String USER = "user";
        public static String HOME_CONFIG_BEAN = "HomeConfigBean";
        public static String AGREE_PROTOCOL = "agreeProtocol";

        public static String FIRST_WELCOME = "FIRST_PLAY_WELCOME_LISTENING";
        public static String UPDATE_INFO = "updateInfo"; //首页更新信息
        public static String UPDATE_ALERT = "updateAlert"; //首次进入应用提醒
        public static String IS_SHOW_LIBRARY_TIP = "IS_SHOW_LIBRARY_TIP"; //文库勾选了不再弹出弹框
        public static String LIBRARY_TIP_TIME = "LIBRARY_TIP_TIME"; //文库弹框提示的时间
        public static String HEARING_REFRESH_TIP = "HEARING_REFRESH_TIP"; //听力刷新提示
        public static String READ_NEW_ARTICLE = "READ_NEW_ARTICLE"; //阅读了新文章
    }

    public static class UMENG {
        public static String APP_KEY = "647ea0d7e31d6071ec49de71";
        public static String CHANNEL = "advEnglish";
    }

    public static class BUGLY {
        public static String APP_ID = "b255e45a8f";
    }

    public static class WX {
        public static String APP_ID = "wxe2f690e0c82c5e4f";
    }


}
