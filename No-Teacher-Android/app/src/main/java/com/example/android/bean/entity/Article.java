package com.example.android.bean.entity;

/**
 * @Auther : Tcy
 * @Date : Create in 2024/5/6 14:54
 * @Decription:
 */
public class Article {
    private int imageResource;
    private String title;
    private String level;
    private String wordCount;

    private String badge;

    public Article(int imageResource, String title, String level,String wordCount,String badge) {
        this.imageResource = imageResource;
        this.title = title;
        this.level = level;
        this.wordCount = wordCount;
        this.badge = badge;
    }

    public int getImageResource() {
        return imageResource;
    }

    public String getTitle() {
        return title;
    }

    public String getLevel() {
        return level;
    }

    public String getWordCount() {
        return wordCount;
    }

    public String getBadge() {
        return badge;
    }

}

