package com.example.no_teacher_andorid.http.retrofit;

import android.text.TextUtils;

import com.zhilehuo.advenglish.gson.GsonUtils;
import com.zhilehuo.advenglish.http.respone.BaseResponse;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @Author wpt
 * @Date 2023/2/22-11:33
 * @desc
 */
public abstract class ResultCallBack<T> implements Callback<T> {

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        // Gson不能直接解析泛型T，所以需要下面这些步骤
        if (response.body() == null){
            onFail(call,null);
            return;
        }
        String s = response.body().toString();
        if (TextUtils.isEmpty(s)){
            return;
        }
        Type type = getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            Type[] types = ((ParameterizedType) type).getActualTypeArguments();
//            val types = type.actualTypeArguments
            ParameterizedTypeImpl ty = new ParameterizedTypeImpl(BaseResponse.class, Arrays.copyOf(types, 1));
            BaseResponse<T> result = GsonUtils.getGsonInstance().fromJson(s, ty);
            onSuccess(result);
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        onFail(call,t);
    }

    public abstract void onSuccess(BaseResponse<T> response);

    public abstract void onFail(Call<T> call, Throwable t);


}
