package com.example.android.bean.entity;

import java.sql.Timestamp;

public class ReadLogDataCount {

    private String id;

    private String userId;

    private Timestamp today;

    private Long totalReadDuration;

    private Integer totalReadWordNum;

    @Override
    public String toString() {
        return "ReadLogDataCount{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", today=" + today +
                ", totalReadDuration=" + totalReadDuration +
                ", totalReadWordNum=" + totalReadWordNum +
                '}';
    }

    public ReadLogDataCount(String id, String userId, Timestamp today, Long totalReadDuration, Integer totalReadWordNum) {
        this.id = id;
        this.userId = userId;
        this.today = today;
        this.totalReadDuration = totalReadDuration;
        this.totalReadWordNum = totalReadWordNum;
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

    public Timestamp getToday() {
        return today;
    }

    public void setToday(Timestamp today) {
        this.today = today;
    }

    public Long getTotalReadDuration() {
        return totalReadDuration;
    }

    public void setTotalReadDuration(Long totalReadDuration) {
        this.totalReadDuration = totalReadDuration;
    }

    public Integer getTotalReadWordNum() {
        return totalReadWordNum;
    }

    public void setTotalReadWordNum(Integer totalReadWordNum) {
        this.totalReadWordNum = totalReadWordNum;
    }
}
