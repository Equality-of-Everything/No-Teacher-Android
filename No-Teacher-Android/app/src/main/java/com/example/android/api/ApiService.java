package com.example.android.api;

import com.example.android.bean.EmailRequest;
import com.example.android.bean.LexileRequest;
import com.example.android.bean.RegisterRequest;
import com.example.android.bean.entity.Article;
import com.example.android.bean.entity.Result;
import com.example.android.bean.entity.User;
import com.example.android.bean.entity.UserLevel;
import com.example.android.bean.entity.WordDetail;
import com.example.android.http.request.VerifyEmailRequest;
import com.example.android.http.retrofit.BaseResponse;

import java.lang.ref.Reference;
import java.util.HashMap;
import java.util.List;

import kotlin.Metadata;
import kotlin.ParameterName;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface ApiService {

    //发送邮箱验证码
    @POST("user/login")
    Call<BaseResponse<User>> sendVerificationEmail(@QueryMap HashMap<String, String> params);

    //验证邮箱验证码
    @POST("user/checkLogin")
    Call<BaseResponse<Void>> verifyEmail(@QueryMap HashMap<String, String> params);


    //发送请求获取单词总数
    @GET("word/getWordNum")
    Call<BaseResponse<Integer>> getWordNum();

    //发送请求测试单词
    @GET("word/getWords")
    Call<BaseResponse<List<WordDetail>>> getWords(@Query("currentPage") int currentPage);

    // 将测试结果发送给我服务端
    @POST("userLevel/insertData")
    Call<BaseResponse<Void>> sendTestResultToServer(@QueryMap HashMap<String, String> params);

    //上传头像
    @POST("userInfo/updateAvatar")
    Call<BaseResponse<Void>> uploadAvatar(@QueryMap HashMap<String, String> params);

    //更新个人资料
    @POST("userInfo/addUser")
    Call<BaseResponse<Void>> sendNewUserInfo(@QueryMap HashMap<String, String> params);

    @GET("userLevel/{userId}")
    Call<BaseResponse<UserLevel>> isTest(@Path("userId") String userId);

    @GET("article/{lexile}/{currentPage}")
    Call<BaseResponse<List<Article>>> getArticles(@Path("lexile") int lexile, @Path("currentPage") int currentPage);

    //获取该难度对应的文章总数
    @GET("article/getArticleByLexileNum/{lexile}")
    Call<BaseResponse<Integer>> getArticleNum(@Path("lexile") int lexile);

    //获取文库所有文章
    @GET("article/getAllArticles")
    Call<BaseResponse<List<Article>>> getAllArticle();

    //将用户自行选择的难度值传给后端
    @POST("userInfo/updateLexile")
//    Call<BaseResponse<Void>> updateLexile(@Body LexileRequest params);
    Call<BaseResponse<Void>> updateLexile(@QueryMap HashMap<String, String> params);
}
















