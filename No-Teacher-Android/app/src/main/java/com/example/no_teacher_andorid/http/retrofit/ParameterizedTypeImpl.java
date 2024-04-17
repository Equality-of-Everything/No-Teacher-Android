package com.example.no_teacher_andorid.http.retrofit;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @Author wpt
 * @Date 2023/2/22-11:45
 * @desc
 */
public class ParameterizedTypeImpl implements ParameterizedType {
    private final Class raw;
    private final Type[] args;
    public ParameterizedTypeImpl(Class raw, Type[] args) {
        this.raw = raw;
        this.args = args != null ? args : new Type[0];
    }
    @Override
    public Type[] getActualTypeArguments() {
        return args;
    }
    @Override
    public Type getRawType() {
        return raw;
    }
    @Override
    public Type getOwnerType() {return null;}
}
