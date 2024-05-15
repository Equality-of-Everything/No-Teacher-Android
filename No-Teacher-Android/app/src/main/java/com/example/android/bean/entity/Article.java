package com.example.android.bean.entity;

/**
 * @Author : Zhang
 * @Date : Created in 2024/5/7 8:19
 * @Decription :
 */

public class Article {
    private int id;

    private int isRead;

    private String title;

    private int wordNum;

    private int lexile;

    private int typeId;

    private String type;

    private String cover;

    private String content;

    private String userId;

    public Article(String cover,String title,int lexile,int wordNum,String type) {
        this.cover = cover;
        this.title = title;
        this.lexile = lexile;
        this.wordNum = wordNum;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIsRead() {
        return isRead;
    }

    public void setIsRead(int isRead) {
        this.isRead = isRead;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getWordNum() {
        return wordNum;
    }

    public void setWordNum(int wordNum) {
        this.wordNum = wordNum;
    }

    public int getLexile() {
        return lexile;
    }

    public void setLexile(int lexile) {
        this.lexile = lexile;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Article(int id, int isRead, String title, int wordNum, int lexile, int typeId, String type, String cover, String content, String userId) {
        this.id = id;
        this.isRead = isRead;
        this.title = title;
        this.wordNum = wordNum;
        this.lexile = lexile;
        this.typeId = typeId;
        this.type = type;
        this.cover = cover;
        this.content = content;
        this.userId = userId;
    }
}
