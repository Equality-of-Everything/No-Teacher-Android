package com.example.android.bean.entity;

/**
 * @Author : zhang
 * @Date : Created in 2024/5/14 8:25
 * @Decription :
 */


public class ArticleTypeList {
    private int id;
    private String type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArticleTypeList(int id, String type) {
        this.id = id;
        this.type = type;
    }
}
