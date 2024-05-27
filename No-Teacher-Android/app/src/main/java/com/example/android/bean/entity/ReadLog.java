package com.example.android.bean.entity;

import java.sql.Timestamp;

/**
 * @Author : zhang
 * @Date : Created in 2024/5/24 14:34
 * @Decription :
 */


public class ReadLog {
    private String id;
    private String userId;
    private Timestamp startTime;
    private Timestamp endTime;
    private long readDuration;
    private int articleId;

    public ReadLog(String id, String userId, Timestamp startTime, Timestamp endTime, long readDuration, int articleId) {
        this.id = id;
        this.userId = userId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.readDuration = readDuration;
        this.articleId = articleId;
    }

    public ReadLog() {
    }

    @Override
    public String toString() {
        return "ReadLog{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", readDuration=" + readDuration +
                ", articleId=" + articleId +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public long getReadDuration() {
        return readDuration;
    }

    public void setReadDuration(long readDuration) {
        this.readDuration = readDuration;
    }

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }
}
